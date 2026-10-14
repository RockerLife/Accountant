package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.Base64;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** Stores and retrieves documents from the configured local filesystem. */
public final class FileSystemDocumentStorage {
    private static final String ENCRYPTED_PREFIX = "enc:v1:";
    private static final SecureRandom RANDOM = new SecureRandom();
    private FileSystemDocumentStorage() {
    }

    public static String upload(String folderName, String baseDir, String documentName,
            String sourceFile) throws Exception {
        Path source = sourceFile == null ? null : Paths.get(sourceFile);
        return store(configuredRoot(), folderName, baseDir, documentName, source);
    }

    public static byte[] download(String reference, String baseDir, String folderName)
            throws Exception {
        String alternateDir = SystemProperties.getInstance()
                .getProperty("document.storage.alternate.directory");
        return load(configuredRoot(), reference, baseDir, alternateDir, folderName);
    }

    public static long downloadTo(String reference, String baseDir, String folderName,
            OutputStream destination) throws Exception {
        String alternateDir = SystemProperties.getInstance()
                .getProperty("document.storage.alternate.directory");
        return copyTo(configuredRoot(), reference, baseDir, alternateDir, folderName, destination);
    }

    static String store(Path configuredRoot, String folderName, String baseDir,
            String docName, Path source) throws IOException {
        return store(configuredRoot, folderName, baseDir, docName, source, referenceKey());
    }

    static String store(Path configuredRoot, String folderName, String baseDir,
            String docName, Path source, SecretKey key) throws IOException {
        if (source == null || !Files.isRegularFile(source)) {
            throw new IOException("Document upload source does not exist: " + source);
        }
        String originalFolderName = safeSegment(folderName, "folder name");
        String originalDocumentName = safeSegment(docName, "document name");

        Path root = secureRoot(configuredRoot);
        String storageLocation = normalizeBaseDir(baseDir) + "/" + originalFolderName
                + "/" + originalDocumentName;
        String reference = encryptReference(storageLocation, key);
        Path target = safePath(root, storageLocation);
        Path parent = target.getParent();
        Files.createDirectories(parent);
        if (!parent.toRealPath().startsWith(root)) {
            throw new IOException("Document path resolves outside document.storage.root");
        }
        if (Files.exists(target) || Files.isSymbolicLink(target)) {
            throw new IOException("Document already exists: " + storageLocation);
        }
        Path temporary = Files.createTempFile(parent, ".upload-", ".tmp");
        try {
            Files.copy(source, temporary, StandardCopyOption.REPLACE_EXISTING);
            move(temporary, target);
        } finally {
            Files.deleteIfExists(temporary);
        }
        return reference;
    }

    private static void move(Path source, Path target) throws IOException {
        Files.move(source, target);
    }

    static byte[] load(Path configuredRoot, String docUrl, String baseDir,
            String alternateDir) throws IOException {
        return load(configuredRoot, docUrl, baseDir, alternateDir, null);
    }

    static byte[] load(Path configuredRoot, String docUrl, String baseDir,
            String alternateDir, String folderName) throws IOException {
        return load(configuredRoot, docUrl, baseDir, alternateDir, folderName, null);
    }

    static byte[] load(Path configuredRoot, String docUrl, String baseDir,
            String alternateDir, String folderName, SecretKey key) throws IOException {
        Path root = secureRoot(configuredRoot);
        return Files.readAllBytes(resolveDocument(root, docUrl, baseDir, alternateDir, folderName, key));
    }

    static long copyTo(Path configuredRoot, String docUrl, String baseDir,
            String alternateDir, String folderName, OutputStream destination) throws IOException {
        return copyTo(configuredRoot, docUrl, baseDir, alternateDir, folderName,
                destination, null);
    }

    static long copyTo(Path configuredRoot, String docUrl, String baseDir,
            String alternateDir, String folderName, OutputStream destination, SecretKey key) throws IOException {
        if (destination == null) {
            throw new IllegalArgumentException("Document destination is missing.");
        }
        Path root = secureRoot(configuredRoot);
        return Files.copy(resolveDocument(root, docUrl, baseDir, alternateDir, folderName, key),
                destination);
    }

    private static Path resolveDocument(Path root, String docUrl, String baseDir,
            String alternateDir, String folderName, SecretKey key) throws IOException {
        if (docUrl != null && docUrl.startsWith(ENCRYPTED_PREFIX)) {
            String relativePath = decryptReference(docUrl, key == null ? referenceKey() : key);
            Path file = safePath(root, relativePath);
            Path basePath = safePath(root, normalizeBaseDir(baseDir));
            String alternate = normalizeOptionalBaseDir(alternateDir);
            if (!file.startsWith(basePath) && (alternate.length() == 0
                    || !file.startsWith(safePath(root, alternate)))) {
                throw new IOException("Document is outside its storage directory");
            }
            return readableFile(root, file, docUrl);
        }
        String documentId = documentId(docUrl);
        if (documentId != null) {
            Path file = mappedDocument(root, baseDir, folderName, documentId);
            if (file == null && normalizeOptionalBaseDir(alternateDir).length() > 0) {
                file = mappedDocument(root, alternateDir, folderName, documentId);
            }
            if (file == null) {
                file = safePath(root, storedRelativePath(baseDir, documentId));
            }
            return readableFile(root, file, docUrl);
        }

        String relativePath = getServerRelativePath(docUrl, baseDir, alternateDir).substring(1);
        relativePath = URLDecoder.decode(relativePath.replace("+", "%2B"), "UTF-8");
        return readableFile(root, safePath(root, relativePath), docUrl);
    }

    private static Path readableFile(Path root, Path file, String reference) throws IOException {
        if (!Files.isRegularFile(file)) {
            throw new IOException("Document was not found: " + reference);
        }
        Path realFile = file.toRealPath();
        if (!realFile.startsWith(root)) {
            throw new IOException("Document path resolves outside document.storage.root");
        }
        return realFile;
    }

    private static Path mappedDocument(Path root, String baseDir, String folderName,
            String documentId) throws IOException {
        String uuidLocation = storedRelativePath(baseDir, documentId);
        Path uuidFile = safePath(root, uuidLocation);
        if (Files.isRegularFile(uuidFile)) {
            return uuidFile;
        }

        String normalizedFolder = folderName == null ? "" : folderName.trim();
        if (normalizedFolder.length() > 0) {
            Path metadata = safePath(root, normalizeBaseDir(baseDir) + "/"
                    + safeSegment(normalizedFolder, "folder name") + "/" + documentId
                    + ".metadata.properties");
            if (Files.isRegularFile(metadata)) {
                return mappedFile(root, baseDir, documentId, metadata);
            }
        }

        Path metadata = safePath(root, uuidLocation + ".metadata.properties");
        if (Files.isRegularFile(metadata)) {
            return mappedFile(root, baseDir, documentId, metadata);
        }

        Path basePath = safePath(root, normalizeBaseDir(baseDir));
        if (!Files.isDirectory(basePath)) {
            return null;
        }
        if (!basePath.toRealPath().startsWith(root)) {
            throw new IOException("Document directory resolves outside document.storage.root");
        }
        Path found = null;
        try (DirectoryStream<Path> folders = Files.newDirectoryStream(basePath)) {
            for (Path folder : folders) {
                if (!Files.isDirectory(folder) || Files.isSymbolicLink(folder)) {
                    continue;
                }
                Path oldMetadata = folder.resolve(documentId + ".metadata.properties");
                if (Files.isRegularFile(oldMetadata)) {
                    if (found != null) {
                        throw new IOException("Duplicate document UUID mapping: " + documentId);
                    }
                    found = mappedFile(root, baseDir, documentId, oldMetadata);
                }
            }
        }
        return found;
    }

    private static Path mappedFile(Path root, String baseDir, String documentId, Path metadata)
            throws IOException {
        Path realMetadata = metadata.toRealPath();
        if (!realMetadata.startsWith(root)) {
            throw new IOException("Document metadata resolves outside document.storage.root");
        }

        Properties mapping = new Properties();
        try (InputStream input = Files.newInputStream(realMetadata)) {
            mapping.load(input);
        }
        String storageLocation = mapping.getProperty("document.storageLocation");
        if (storageLocation == null || storageLocation.trim().length() == 0) {
            throw new IOException("Document UUID mapping is missing its storage location: "
                    + documentId);
        }
        Path file = safePath(root, storageLocation);
        Path basePath = safePath(root, normalizeBaseDir(baseDir));
        if (!file.startsWith(basePath)) {
            throw new IOException("Document UUID mapping is outside its storage directory: "
                    + documentId);
        }
        return file;
    }

    private static String documentId(String reference) {
        if (reference == null) {
            return null;
        }
        String value = reference.trim();
        if (value.length() == 0) {
            throw new IllegalArgumentException("Document URL is missing.");
        }
        try {
            return UUID.fromString(value).toString();
        } catch (IllegalArgumentException ex) {
            if (value.indexOf('/') >= 0 || value.indexOf('\\') >= 0) {
                return null;
            }
            throw new IllegalArgumentException("Invalid document UUID", ex);
        }
    }

    static String getServerRelativePath(String docUrl, String baseDir, String alternateDir) {
        if (docUrl == null || docUrl.trim().length() == 0) {
            throw new IllegalArgumentException("Document URL is missing.");
        }

        String normalizedUrl = docUrl.trim().replace('\\', '/');
        int queryIndex = normalizedUrl.indexOf('?');
        int fragmentIndex = normalizedUrl.indexOf('#');
        int suffixIndex = queryIndex < 0 ? fragmentIndex
                : fragmentIndex < 0 ? queryIndex : Math.min(queryIndex, fragmentIndex);
        if (suffixIndex >= 0) {
            normalizedUrl = normalizedUrl.substring(0, suffixIndex);
        }
        if (!normalizedUrl.startsWith("/") && normalizedUrl.indexOf("://") < 0) {
            normalizedUrl = "/" + normalizedUrl;
        }

        String normalizedBaseDir = normalizeBaseDir(baseDir);
        String normalizedAlternateDir = normalizeOptionalBaseDir(alternateDir);
        if (normalizedAlternateDir.length() > 0
                && findBaseDir(normalizedUrl, normalizedAlternateDir) >= 0) {
            normalizedBaseDir = normalizedAlternateDir;
        }

        int baseDirIndex = findBaseDir(normalizedUrl, normalizedBaseDir);
        if (baseDirIndex < 0) {
            throw new IllegalArgumentException("Document URL does not contain storage directory '"
                    + normalizedBaseDir + "'.");
        }
        return normalizedUrl.substring(baseDirIndex);
    }

    private static Path configuredRoot() throws Exception {
        String configured = SystemProperties.getInstance().getString("document.storage.root");
        if (configured == null || configured.trim().length() == 0) {
            throw new IllegalArgumentException("document.storage.root is missing");
        }
        return Paths.get(configured.trim());
    }

    private static Path secureRoot(Path configuredRoot) throws IOException {
        if (configuredRoot == null) {
            throw new IllegalArgumentException("document.storage.root is missing");
        }
        Path root = configuredRoot.toAbsolutePath().normalize();
        Files.createDirectories(root);
        return root.toRealPath();
    }

    private static Path safePath(Path root, String relativePath) throws IOException {
        Path path = root.resolve(relativePath).normalize();
        if (!path.startsWith(root)) {
            throw new IOException("Document path is outside document.storage.root");
        }
        return path;
    }

    private static SecretKey referenceKey() throws IOException {
        // shortcut: v1 uses one active key; add key IDs before rotating stored references.
        String encoded = System.getenv("DOCUMENT_STORAGE_KEY");
        if (encoded == null || encoded.trim().length() == 0) {
            throw new IOException("DOCUMENT_STORAGE_KEY is missing");
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(encoded.trim());
            if (bytes.length != 32) {
                throw new IllegalArgumentException("Expected a 256-bit key");
            }
            return new SecretKeySpec(bytes, "AES");
        } catch (IllegalArgumentException ex) {
            throw new IOException("DOCUMENT_STORAGE_KEY must be a Base64-encoded 256-bit key", ex);
        }
    }

    private static String encryptReference(String relativePath, SecretKey key) throws IOException {
        byte[] nonce = new byte[12];
        RANDOM.nextBytes(nonce);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, nonce));
            byte[] ciphertext = cipher.doFinal(relativePath.getBytes(StandardCharsets.UTF_8));
            byte[] value = new byte[nonce.length + ciphertext.length];
            System.arraycopy(nonce, 0, value, 0, nonce.length);
            System.arraycopy(ciphertext, 0, value, nonce.length, ciphertext.length);
            return ENCRYPTED_PREFIX + Base64.getUrlEncoder().withoutPadding().encodeToString(value);
        } catch (Exception ex) {
            throw new IOException("Could not encrypt document reference", ex);
        }
    }

    private static String decryptReference(String reference, SecretKey key) throws IOException {
        try {
            byte[] value = Base64.getUrlDecoder().decode(reference.substring(ENCRYPTED_PREFIX.length()));
            if (value.length < 29) {
                throw new IllegalArgumentException("Encrypted reference is too short");
            }
            byte[] nonce = new byte[12];
            System.arraycopy(value, 0, nonce, 0, nonce.length);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, nonce));
            return new String(cipher.doFinal(value, nonce.length, value.length - nonce.length),
                    StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IOException("Invalid encrypted document reference", ex);
        }
    }

    private static String storedRelativePath(String baseDir, String documentId) {
        return normalizeBaseDir(baseDir) + "/" + documentId.substring(0, 2)
                + "/" + documentId.substring(2, 4) + "/" + documentId;
    }

    private static String safeSegment(String value, String label) {
        String segment = value == null ? "" : value.trim();
        if (segment.length() == 0 || ".".equals(segment) || "..".equals(segment)
                || segment.indexOf('/') >= 0 || segment.indexOf('\\') >= 0
                || segment.indexOf(':') >= 0) {
            throw new IllegalArgumentException("Invalid " + label);
        }
        for (int i = 0; i < segment.length(); i++) {
            if (segment.charAt(i) < 32 || segment.charAt(i) == 127) {
                throw new IllegalArgumentException("Invalid " + label);
            }
        }
        return segment;
    }

    private static int findBaseDir(String normalizedUrl, String baseDir) {
        String lowerUrl = normalizedUrl.toLowerCase(Locale.ENGLISH);
        String marker = "/" + baseDir.toLowerCase(Locale.ENGLISH);
        int index = lowerUrl.indexOf(marker + "/");
        return index < 0 && lowerUrl.endsWith(marker) ? lowerUrl.length() - marker.length() : index;
    }

    private static String normalizeBaseDir(String baseDir) {
        String normalized = normalizeOptionalBaseDir(baseDir);
        if (normalized.length() == 0) {
            throw new IllegalArgumentException("Invalid document storage directory");
        }
        return normalized;
    }

    private static String normalizeOptionalBaseDir(String baseDir) {
        String normalized = baseDir == null ? "" : baseDir.trim().replace('\\', '/');
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        if (normalized.indexOf(':') >= 0) {
            throw new IllegalArgumentException("Invalid document storage directory");
        }
        if (normalized.length() > 0) {
            String[] segments = normalized.split("/");
            for (String segment : segments) {
                safeSegment(segment, "document storage directory");
            }
        }
        return normalized;
    }
}

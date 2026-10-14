package com.util;

/**
 * @deprecated Compatibility facade for legacy SharePoint call sites. New code
 *             should use {@link FileSystemDocumentStorage}.
 */
@Deprecated
public class SharePointUtils {
    public String uploadDocToSharePoint(String docLibPathName, String folderName, String baseDir,
            String docName, String srcFileUrl, String userName, String password, String domain,
            String workStation, int port, String spUrl) throws Exception {
        return FileSystemDocumentStorage.upload(folderName, baseDir, docName, srcFileUrl);
    }

    public byte[] downloadDocFromSharePoint(String docUrl, String baseDir, String userName,
            String password, String domain, String workStation, int port) throws Exception {
        return downloadDocFromSharePoint(docUrl, baseDir, userName, password, domain,
                workStation, port, null);
    }

    public byte[] downloadDocFromSharePoint(String docUrl, String baseDir, String userName,
            String password, String domain, String workStation, int port, String folderName)
            throws Exception {
        return FileSystemDocumentStorage.download(docUrl, baseDir, folderName);
    }
}

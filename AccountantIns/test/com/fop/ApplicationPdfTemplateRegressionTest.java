package com.fop;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ApplicationPdfTemplateRegressionTest {
	private static final Path BASE = Paths.get("web", "ISMIE", "ApplicationPDF");
	private static final String[] NEW_TEMPLATES = {
			"NewApplicationPDF.xsl", "NewApplicationPDFNYFTZ.xsl", "MDNewBusinessApplication.xsl",
			"MENewBusinessApplication.xsl", "NCNewBusinessApplication.xsl" };
	private static final String[] RENEW_TEMPLATES = {
			"renewalapplicationnewapp.xsl", "renewalapplicationnewappNYFTZ.xsl", "MERenewalApplication.xsl",
			"NCRenewalApplication.xsl" };
	private static final Pattern FORCED_NOTICE_BREAK = Pattern.compile(
			"<fo:block page-break-before=\"always\"\\s*/>\\s*<fo:block>\\s*(?:<!--\\s*)?<fo:block[^>]*>\\s*(?:ANY PERSON WHO KNOWINGLY|Completion and/or signing)",
			Pattern.DOTALL);

	public static void main(String[] args) throws Exception {
		String visibilityRules = read(BASE.resolve("supplement-visibility.xsl"));
		assertTrue(visibilityRules.contains("/response/StatusDesc != 'Issued'"),
				"Shared supplement rules lost the non-issued status condition");
		assertTrue(visibilityRules.contains("showNewPracticeManagementSupplement")
				&& visibilityRules.contains("showNewClaimSupplement")
				&& visibilityRules.contains("showRenewClaimSupplement"),
				"Shared supplement rules are incomplete");

		String newNoSupplements = "<response><StatusDesc>Issued</StatusDesc><GrossAmount_0>361585</GrossAmount_0>"
				+ "<FIRM_FREEFORM_01><data><IsAwarenessOfAnyProfLiability>N</IsAwarenessOfAnyProfLiability>"
				+ "<IsAwarenessOfAnyProfLiabilitySuitAgainst>N</IsAwarenessOfAnyProfLiabilitySuitAgainst>"
				+ "</data></FIRM_FREEFORM_01></response>";
		String renewNoSupplements = "<response><StatusDesc>Issued</StatusDesc><firm_freeform_01><data>"
				+ "<IsAwarenessOfAnyProfLiability>N</IsAwarenessOfAnyProfLiability>"
				+ "<IsAwarenessOfAnyProfLiabilitySuitAgainst>N</IsAwarenessOfAnyProfLiabilitySuitAgainst>"
				+ "</data></firm_freeform_01></response>";

		for (String template : NEW_TEMPLATES) {
			Path path = BASE.resolve("NewBusiness").resolve(template);
			String source = read(path);
			assertTrue(source.contains("<xsl:include href=\"../supplement-visibility.xsl\" />"),
					template + " does not use the shared supplement rules");
			assertTrue(source.contains("test=\"$showNewPracticeManagementSupplement\""),
					template + " has the wrong practice supplement trigger");
			assertTrue(source.contains("test=\"$showNewClaimSupplement\""),
					template + " has the wrong claim supplement trigger");
			assertTrue(!source.contains("IsAwarenessOfAnyProfLiability != 'N'"),
					template + " treats an unanswered claim question as Yes");
			int heading = source.indexOf("COVERAGE SELECTION");
			assertTrue(heading >= 0 && source.substring(Math.max(0, heading - 700), heading)
					.contains("keep-together.within-page=\"always\""), template + " can orphan Coverage Selection");
			assertTrue(!FORCED_NOTICE_BREAK.matcher(source).find(), template + " forces notices onto a mostly empty page");

			Templates compiled = compile(path);
			assertSupplementsAbsent(transform(compiled, newNoSupplements), template);
			String quoted = transform(compiled, newNoSupplements.replace("Issued", "Quoted"));
			assertTrue(quoted.contains("PRACTICE MANAGEMENT SUPPLEMENT ("),
					template + " does not show the practice supplement before issue");
			assertTrue(quoted.contains("CLAIM SUPPLEMENT ("),
					template + " does not show the claim supplement before issue");
			assertTrue(transform(compiled, "<response><StatusDesc>Issued</StatusDesc><GrossAmount_0>500001</GrossAmount_0></response>")
					.contains("PRACTICE MANAGEMENT SUPPLEMENT ("), template + " lost the revenue trigger");
			assertTrue(transform(compiled,
					"<response><StatusDesc>Issued</StatusDesc><FIRM_FREEFORM_01><data><IsAwarenessOfAnyProfLiability>Y</IsAwarenessOfAnyProfLiability>"
							+ "</data></FIRM_FREEFORM_01></response>").contains("CLAIM SUPPLEMENT ("),
					template + " lost the claim trigger");
		}

		for (String template : RENEW_TEMPLATES) {
			Path path = BASE.resolve("RenewBusiness").resolve(template);
			String source = read(path);
			assertTrue(source.contains("<xsl:include href=\"../supplement-visibility.xsl\" />"),
					template + " does not use the shared supplement rules");
			assertTrue(source.contains("test=\"$showRenewClaimSupplement\""),
					template + " has the wrong claim supplement trigger");
			assertTrue(!source.contains("IsAwarenessOfAnyProfLiability != 'N'"),
					template + " treats an unanswered claim question as Yes");
			assertTrue(!FORCED_NOTICE_BREAK.matcher(source).find(), template + " forces notices onto a mostly empty page");

			Templates compiled = compile(path);
			assertSupplementsAbsent(transform(compiled, renewNoSupplements), template);
			assertTrue(transform(compiled, renewNoSupplements.replace("Issued", "Quoted"))
					.contains("CLAIM SUPPLEMENT ("), template + " does not show the claim supplement before issue");
			assertTrue(transform(compiled,
					"<response><StatusDesc>Issued</StatusDesc><firm_freeform_01><data><IsAwarenessOfAnyProfLiabilitySuitAgainst>Y</IsAwarenessOfAnyProfLiabilitySuitAgainst>"
							+ "</data></firm_freeform_01></response>").contains("CLAIM SUPPLEMENT ("),
					template + " lost the claim trigger");
		}
	}

	private static Templates compile(Path template) throws Exception {
		StreamSource source = new StreamSource(new StringReader(read(template)));
		source.setSystemId(template.toUri().toString());
		return TransformerFactory.newInstance().newTemplates(source);
	}

	private static String transform(Templates template, String xml) throws Exception {
		Transformer transformer = template.newTransformer();
		Source input = new StreamSource(new StringReader(xml));
		StringWriter output = new StringWriter();
		transformer.transform(input, new StreamResult(output));
		return output.toString();
	}

	private static String read(Path path) throws Exception {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	private static void assertSupplementsAbsent(String output, String template) {
		assertTrue(!output.contains("PRACTICE MANAGEMENT SUPPLEMENT ("), template + " showed practice supplement");
		assertTrue(!output.contains("CLAIM SUPPLEMENT ("), template + " showed claim supplement");
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition)
			throw new AssertionError(message);
	}
}

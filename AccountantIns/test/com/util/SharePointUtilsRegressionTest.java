package com.util;

public class SharePointUtilsRegressionTest {
	public static void main(String[] args) {
		assertEquals("/AccountantDocs/QN-1/file.pdf",
				SharePointUtils.getServerRelativePath("http://server/AccountantDocs/QN-1/file.pdf", "AccountantDocs", "LawyersDocs"));
		assertEquals("/LawyersDocs/QN-2/file.pdf",
				SharePointUtils.getServerRelativePath("http://server/LawyersDocs/QN-2/file.pdf", "AccountantDocs", "LawyersDocs"));

		try {
			SharePointUtils.getServerRelativePath("http://server/OtherDocs/file.pdf", "AccountantDocs", "LawyersDocs");
			throw new AssertionError("Expected invalid SharePoint URL to be rejected");
		} catch (IllegalArgumentException expected) {
			// expected
		}
	}

	private static void assertEquals(String expected, String actual) {
		if (!expected.equals(actual))
			throw new AssertionError("Expected " + expected + " but got " + actual);
	}
}

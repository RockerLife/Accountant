package com.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlConfigurationRegressionTest {
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		factory.setExpandEntityReferences(false);
		DocumentBuilder parser = factory.newDocumentBuilder();

		validateXmlFiles(parser, Paths.get("web", "XML", "AccountantIns"), ".xml");
		validateXmlFiles(parser, Paths.get("web", "ISMIE", "ApplicationPDF"), ".xsl");

		Document components = parser.parse(Paths.get("web", "XML", "AccountantIns", "components",
				"components.xml").toFile());
		validateRequiredAttribute(components, "databaseoperation", "name");
		validateRequiredAttribute(components, "workflow", "seq");
		validateRequiredAttribute(components, "actionparam", "paramid");
	}

	private static void validateXmlFiles(DocumentBuilder parser, Path root, String suffix) throws Exception {
		try (Stream<Path> paths = Files.walk(root)) {
			for (Path path : (Iterable<Path>) paths.filter(Files::isRegularFile)
					.filter(file -> file.getFileName().toString().endsWith(suffix))::iterator)
				parser.parse(path.toFile());
		}
	}

	private static void validateRequiredAttribute(Document document, String elementName, String attributeName) {
		NodeList nodes = document.getElementsByTagName(elementName);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			String value = element.getAttribute(attributeName).trim();
			if (value.isEmpty())
				throw new AssertionError(elementName + " is missing " + attributeName);
		}
	}
}

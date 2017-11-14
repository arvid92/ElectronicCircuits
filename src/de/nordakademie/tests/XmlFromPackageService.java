package de.nordakademie.tests;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Mit diesem Service kann ein Document aus dem exampleprotocols-Package gezogen
 * werden
 * 
 * @author Markus Jaehn
 */
class XmlFromPackageService {

	/**
	 * Konvertiert eine Datei in ein Document und gibt dieses zurueck
	 * 
	 * @param fileName
	 *            der Name der Datei, welche in ein Document konvertiert werden
	 *            soll
	 * @return das konvertierte Document
	 */
	Document getDoc(String fileName) {
		try {
			File fXmlFile = new File(getClass().getResource("exampleprotocols/" + fileName + ".xml").toURI().getPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

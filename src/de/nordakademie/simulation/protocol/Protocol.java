package de.nordakademie.simulation.protocol;

import de.nordakademie.exceptions.ProtocolCouldNotBeCreatedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Abstrakte Superclass fuer die Protokolle (derzeit GlitchDetectionProtocol und
 * SimulationProtocol). Stellt Methoden zum erzeugen und printen der Protokolle
 * bereit.
 *
 * @author Markus Jaehn
 */
public abstract class Protocol {

	Document document;
	Element rootElement;
	int elementCounter = 1;
	private final String OUTPUT_PATH = "./";

	Protocol() {
		createDocument(getProtocolType());
	}

	public Document getDocument() {
		return this.document;
	}

	public void print() {
		System.out.println("Protocol of " + getProtocolType() + ":");
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			Writer out = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(out));
			System.out.println(out.toString());
			transformer.transform(new DOMSource(getDocument()),
					new StreamResult(new FileOutputStream(OUTPUT_PATH + getOutputFileName() + ".xml")));
		} catch (TransformerFactoryConfigurationError | TransformerException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	abstract String getProtocolType();

	private void createDocument(String rootElementString) {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.newDocument();
			rootElement = document.createElement(rootElementString);
			document.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			throw new ProtocolCouldNotBeCreatedException(e.getMessage());
		}
	}

	private String getOutputFileName() {
		return getProtocolType() + "-protocol";
	}
}

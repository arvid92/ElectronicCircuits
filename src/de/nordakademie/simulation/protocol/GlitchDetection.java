package de.nordakademie.simulation.protocol;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Die GlitchDetection ist eine Service-Klasse, mithilfe dessen die Glitches aus
 * dem uebergebenen Dokument des Simulationsprotokolls ermittelt werden koennen.
 * 
 * @author Markus Jaehn
 */
public class GlitchDetection {

	public GlitchDetectionProtocol detectGlitches(Document doc) {
		GlitchDetectionProtocol protocol = new GlitchDetectionProtocol();
		NodeList changeElements = doc.getFirstChild().getChildNodes();

		for (int i = 0; i < changeElements.getLength(); i++) {
			Node changeElement = changeElements.item(i);
			if (detectGlitchInChangeElement(changeElement)) {
				protocol.writeGlitch(changeElement);
			}
		}
		return protocol;
	}

	private boolean detectGlitchInChangeElement(Node changeElement) {
		boolean lastValue = Boolean
				.valueOf(changeElement.getFirstChild().getLastChild().getLastChild().getFirstChild().getNodeValue());
		NodeList outputValuesAfterInputChange = changeElement.getLastChild().getLastChild().getChildNodes();

		int changeCounter = 0;
		for (int i = 0; i < outputValuesAfterInputChange.getLength(); i++) {
			boolean currentValue = Boolean.valueOf(outputValuesAfterInputChange.item(i).getTextContent());
			changeCounter = (lastValue != currentValue) ? changeCounter + 1 : changeCounter;
			lastValue = currentValue;
		}
		return changeCounter >= 2;
	}
}

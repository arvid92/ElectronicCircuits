package de.nordakademie.simulation.protocol;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Das GlitchDetectionProtocol gibt eine Xml aus, aus der entnommen werden kann,
 * in welchen Changes, also von welchem zu welchem Input, Glitches auftreten.
 * 
 * @author Markus Jaehn
 */
public class GlitchDetectionProtocol extends Protocol {

	private Element currentGlitchElement;
	
	@Override
	String getProtocolType() {
		return "glitch-detection";
	}

	void writeGlitch(Node changeElement) {
		currentGlitchElement = document.createElement("glitch");
		currentGlitchElement.setAttribute("number", String.valueOf(elementCounter++));
		rootElement.appendChild(currentGlitchElement);

		NodeList fromNodes = changeElement.getFirstChild().getFirstChild().getChildNodes();
		writeOutputValueNodes("from", fromNodes);

		NodeList toNodes = changeElement.getLastChild().getFirstChild().getChildNodes();
		writeOutputValueNodes("to", toNodes);
	}

	private void writeOutputValueNodes(String nodeName, NodeList originalValues) {
		Element elementToValuesAppendOn = document.createElement(nodeName);
		currentGlitchElement.appendChild(elementToValuesAppendOn);
		for (int i = 0; i < originalValues.getLength(); i++) {
			Element x = document.createElement("x" + i);
			x.appendChild(document.createTextNode(originalValues.item(i).getTextContent()));
			elementToValuesAppendOn.appendChild(x);
		}
	}
}

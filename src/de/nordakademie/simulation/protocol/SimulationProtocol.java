package de.nordakademie.simulation.protocol;

import org.w3c.dom.Element;

/**
 * Das SimulationProtocolist fuer die Protokollierung der Inputs und Outputs bei
 * den jeweiligen Changes des Inputs zustaendig. Es erbt von der Klasse Protocol.
 * 
 * @author Markus Jaehn
 */
public class SimulationProtocol extends Protocol {

	private Element beforeChangeElement;
	private Element currentChangeElement;
	private Element currentOutputsElement;

	@Override
	String getProtocolType() {
		return "simulation";
	}

	public void addInputsBeforeChange(boolean[] values) {
		// create new change element
		createChangeElement();

		// append new beforeChange element to change element
		beforeChangeElement = document.createElement("beforeChange");
		currentChangeElement.appendChild(beforeChangeElement);

		// add inputs to beforeChange element
		addInputs(beforeChangeElement, values);

		// create output Element for later inserting by MeasureComponent
		createOutputElement(beforeChangeElement);
	}

	public void addInputsAfterChange(boolean[] values) {
		// append new afterChange element to change element
		final Element afterChangeElement = document.createElement("afterChange");
		currentChangeElement.appendChild(afterChangeElement);

		// add inputs to afterChange element
		addInputs(afterChangeElement, values);

		// create output Element for later inserting by MeasureComponent
		createOutputElement(afterChangeElement);
	}

	public void addOutputValues(boolean[] values) {
		if (currentOutputsElement.getParentNode().isEqualNode(beforeChangeElement)) {
			removeOutputChildNodes();
		}
		for (int i = 0; i < values.length; i++) {
			final Element outputElement = document.createElement("y" + i);
			currentOutputsElement.appendChild(outputElement);
			outputElement.appendChild(document.createTextNode(String.valueOf(values[i])));
		}
	}

	private void addInputs(Element parentElement, boolean[] values) {
		final Element inputsElement = document.createElement("input");
		parentElement.appendChild(inputsElement);
		for (int i = 0; i < values.length; i++) {
			final Element inputElement = document.createElement("x" + i);
			inputsElement.appendChild(inputElement);
			inputElement.appendChild(document.createTextNode(String.valueOf(values[i])));
		}
	}

	private void createChangeElement() {
		currentChangeElement = document.createElement("change");
		currentChangeElement.setAttribute("number", String.valueOf(elementCounter++));
		rootElement.appendChild(currentChangeElement);
	}

	private void createOutputElement(Element parentElement) {
		currentOutputsElement = document.createElement("output");
		parentElement.appendChild(currentOutputsElement);
	}

	private void removeOutputChildNodes() {
		while (currentOutputsElement.hasChildNodes()) {
			currentOutputsElement.removeChild(currentOutputsElement.getFirstChild());
		}
	}
}

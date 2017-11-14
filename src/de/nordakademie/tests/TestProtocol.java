package de.nordakademie.tests;

import de.nordakademie.circuit.Circuit;
import de.nordakademie.circuit.CircuitBuilder;
import de.nordakademie.circuit.components.input.SingleInputChangeBehaviour;
import de.nordakademie.simulation.protocol.GlitchDetection;
import de.nordakademie.simulation.protocol.GlitchDetectionProtocol;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Testet die Protokollierung der Simulation
 * 
 * @author Markus Jaehn
 */
public class TestProtocol {

	private SimulationProtocol simulationProtocol;
	private GlitchDetectionProtocol glitchDetectionProtocol;
	private final XmlFromPackageService xmlPckgService = new XmlFromPackageService();

	@Before
	public void setUp() {
		CircuitBuilder circuitBuilder = new CircuitBuilder();
		Circuit circuit = circuitBuilder.buildWikipediaCircuit();
		circuit.startCircuitSimulation(new SingleInputChangeBehaviour());

		simulationProtocol = circuit.getProtocol();
		GlitchDetection glitchDetection = new GlitchDetection();
		glitchDetectionProtocol = glitchDetection.detectGlitches(simulationProtocol.getDocument());
		glitchDetectionProtocol.print();
	}

	/**
	 * Mit diesem Test soll gezeigt werden, dass die Anzahl der möglichen
	 * Eingangsbelegungen im BeforeChange mit der Anzahl der möglichen
	 * Eingangsbelegungen im AfterChange des Protokolls übereinstimmt.
	 */
	@Test
	public void testSimulationProtocolCalculatedInputValues() {
		Document createdSimulationProtocol = simulationProtocol.getDocument();
		int registeredWiresInputComponent = 3;
		int[] beforeInputValues = new int[(int) Math.pow(2, registeredWiresInputComponent)];
		int[] afterInputValues = new int[(int) Math.pow(2, registeredWiresInputComponent)];

		NodeList changes = createdSimulationProtocol.getFirstChild().getChildNodes();
		for (int i = 0; i < changes.getLength(); i++) {
			int beforeDecimalValue = getInputDecimalValue(
					changes.item(i).getFirstChild().getFirstChild().getChildNodes());
			int afterDecimalValue = getInputDecimalValue(
					changes.item(i).getLastChild().getFirstChild().getChildNodes());
			beforeInputValues[beforeDecimalValue] = beforeInputValues[beforeDecimalValue] + 1;
			afterInputValues[afterDecimalValue] = afterInputValues[afterDecimalValue] + 1;
		}
		assertArrayEquals(beforeInputValues, afterInputValues);
	}

	@Test
	public void testSimulationProtocol() {
		Document expectedSimulationProtocol = xmlPckgService.getDoc("exampleSimulationProtocol");
		Document createdSimulationProtocol = simulationProtocol.getDocument();
		checkDocumentEquality(expectedSimulationProtocol, createdSimulationProtocol);
	}

	@Test
	public void testGlitchDetectionProtocol() {
		Document expectedSimulationProtocol = xmlPckgService.getDoc("exampleGlitchDetectionProtocol");
		Document createdGlitchDetectionProtocol = glitchDetectionProtocol.getDocument();
		checkDocumentEquality(expectedSimulationProtocol, createdGlitchDetectionProtocol);
	}

	private int getInputDecimalValue(NodeList nodeList) {
		int decimalValue = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (Boolean.valueOf(nodeList.item(i).getFirstChild().getNodeValue())) {
				decimalValue = decimalValue + (int) Math.pow(2, i);
			}
		}
		return decimalValue;
	}

	private void checkDocumentEquality(Document expectedSimulationProtocol, Document createdSimulationProtocol) {
		int numberOfChangesInExpected = expectedSimulationProtocol.getFirstChild().getChildNodes().getLength();
		int numberOfChangesInCreated = createdSimulationProtocol.getFirstChild().getChildNodes().getLength();
		assertEquals(numberOfChangesInExpected, numberOfChangesInCreated);

		for (int i = 0; i < numberOfChangesInCreated; i++) {
			// Check inputs before change
			NodeList ib1 = expectedSimulationProtocol.getFirstChild().getChildNodes().item(i).getFirstChild()
					.getChildNodes();
			NodeList ib2 = createdSimulationProtocol.getFirstChild().getChildNodes().item(i).getFirstChild()
					.getChildNodes();
			checkNodeListEquality(ib1, ib2);

			// Check outputs before change
			NodeList ob1 = expectedSimulationProtocol.getFirstChild().getChildNodes().item(i).getLastChild()
					.getChildNodes();
			NodeList ob2 = createdSimulationProtocol.getFirstChild().getChildNodes().item(i).getLastChild()
					.getChildNodes();
			checkNodeListEquality(ob1, ob2);

			// Check inputs after change
			NodeList ia1 = expectedSimulationProtocol.getLastChild().getChildNodes().item(i).getFirstChild()
					.getChildNodes();
			NodeList ia2 = createdSimulationProtocol.getLastChild().getChildNodes().item(i).getFirstChild()
					.getChildNodes();
			checkNodeListEquality(ia1, ia2);

			// Check outputs after change
			NodeList oa1 = expectedSimulationProtocol.getLastChild().getChildNodes().item(i).getLastChild()
					.getChildNodes();
			NodeList oa2 = createdSimulationProtocol.getLastChild().getChildNodes().item(i).getLastChild()
					.getChildNodes();
			checkNodeListEquality(oa1, oa2);
		}
	}

	private void checkNodeListEquality(NodeList expectedValues, NodeList createdValues) {
		assertEquals(expectedValues.getLength(), createdValues.getLength());
		for (int i = 0; i < expectedValues.getLength(); i++) {
			boolean expectedValue = Boolean.valueOf(expectedValues.item(i).getTextContent());
			boolean createdValue = Boolean.valueOf(createdValues.item(i).getTextContent());
			assertEquals(expectedValue, createdValue);
		}
	}
}

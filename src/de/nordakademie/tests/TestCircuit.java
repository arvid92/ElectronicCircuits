package de.nordakademie.tests;

import de.nordakademie.circuit.Circuit;
import de.nordakademie.circuit.components.input.InputBehaviour;
import de.nordakademie.circuit.components.input.InputComponent;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * Test fuer die Circuit Klasse
 * 
 * @author Arvid Ottenberg
 */
public class TestCircuit {

	private Circuit circuit;
	private InputBehaviour mockInputBehaviour;
	private InputComponent mockInputComponent;
	private SimulationProtocol mockProtocol;

	@Before
	public void setUp() {
		mockInputBehaviour = mock(InputBehaviour.class);
		mockInputComponent = mock(InputComponent.class);
		mockProtocol = mock(SimulationProtocol.class);
		circuit = new Circuit(mockInputComponent, mockProtocol);
	}

	@Test
	public void testStartCircuitSimulation() {
		circuit.startCircuitSimulation(mockInputBehaviour);
		InOrder inOrder = inOrder(mockInputComponent);
		inOrder.verify(mockInputComponent).setInputBehaviour(mockInputBehaviour);
		inOrder.verify(mockInputComponent).calculateOutput();
	}
}

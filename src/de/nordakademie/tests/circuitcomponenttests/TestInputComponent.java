package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.Wire;
import de.nordakademie.circuit.components.input.InputBehaviour;
import de.nordakademie.circuit.components.input.InputChange;
import de.nordakademie.circuit.components.input.InputComponent;
import de.nordakademie.exceptions.DifferentNumberOfInputWiresException;
import de.nordakademie.exceptions.MissingInputBehaviourException;
import de.nordakademie.exceptions.MissingOutputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import de.nordakademie.timeline.Timeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Timo Schlatter
 */
public class TestInputComponent {

	private InputComponent inputComponent;
	private SimulationProtocol mockProtocol;
	private Timeline mockTimeline;
	private InputBehaviour mockInputBehaviour;
	private Wire mockWire1, mockWire2, mockWire3;
	private boolean[] inputs1, inputs2, inputs3;

	@Before
	public void setUp() {
		mockProtocol = mock(SimulationProtocol.class);
		mockTimeline = mock(Timeline.class);
		mockInputBehaviour = mock(InputBehaviour.class);
		inputs1 = new boolean[] { true, true };
		inputs2 = new boolean[] { false, true };
		inputs3 = new boolean[] { false, false };
		when(mockInputBehaviour.generateInputChanges(isA(Integer.class)))
				.thenReturn(Arrays.asList(new InputChange(inputs1, inputs2), new InputChange(inputs2, inputs3)));
		inputComponent = new InputComponent(mockProtocol, mockTimeline);
		inputComponent.setInputBehaviour(mockInputBehaviour);
		mockWire1 = mock(Wire.class);
		mockWire2 = mock(Wire.class);
		mockWire3 = mock(Wire.class);
		inputComponent.addOutputWire(mockWire1);
		inputComponent.addOutputWire(mockWire2);
	}

	@Test
	public void testCalculateOutput() {
		inputComponent.calculateOutput();

		verify(mockInputBehaviour).generateInputChanges(2);
		verify(mockTimeline, times(4)).run();

		InOrder inOrderWire1 = inOrder(mockWire1);
		inOrderWire1.verify(mockWire1).setValue(inputs1[0], 0);
		assertEquals(inputs2[0], inputs3[0]);
		inOrderWire1.verify(mockWire1, times(3)).setValue(inputs2[0], 0);

		InOrder inOrderWire2 = inOrder(mockWire2);
		assertEquals(inputs1[1], inputs2[1]);
		inOrderWire2.verify(mockWire2, times(3)).setValue(inputs1[1], 0);
		inOrderWire2.verify(mockWire2).setValue(inputs3[1], 0);

		InOrder inOrderProtocol = inOrder(mockProtocol);
		inOrderProtocol.verify(mockProtocol).addInputsBeforeChange(inputs1);
		inOrderProtocol.verify(mockProtocol).addInputsAfterChange(inputs2);
		inOrderProtocol.verify(mockProtocol).addInputsBeforeChange(inputs2);
		inOrderProtocol.verify(mockProtocol).addInputsAfterChange(inputs3);
	}

	/**
	 * Mit diesem Test soll gezeigt werden, dass wenn keine Wires als Ausgänge
	 * fuer die InputComponent-Instanz definiert wurden und die
	 * calculateOutput()-Methode aufgerufen wird, eine Exception auftritt.
	 */
	@Test(expected = MissingOutputWireException.class)
	public void testCalculateOutputWithoutOutputWires() {
		inputComponent = new InputComponent(mockProtocol, mockTimeline);
		inputComponent.setInputBehaviour(mockInputBehaviour);
		inputComponent.calculateOutput();
		verify(mockInputBehaviour, never()).generateInputChanges(isA(Integer.class));
		verify(mockTimeline, never()).run();
		verify(mockWire1, never()).setValue(isA(Boolean.class), isA(Integer.class));
		verify(mockWire2, never()).setValue(isA(Boolean.class), isA(Integer.class));
	}

	/**
	 * Mit diesem Test soll gezeigt werden, dass wenn die Anzahl der
	 * Eingangsbelegungen einer InputChange-Instanz nicht mit der Anzahl der
	 * abgehenden Wires der InputComponent-Instanz uebereinstimmt und die
	 * calculateOutput()-Methode aufgerufen wird, eine Exception auftritt.
	 */
	@Test(expected = DifferentNumberOfInputWiresException.class)
	public void testCalculateOutputWithWrongInputChanges() {
		inputComponent.addOutputWire(mockWire3);
		inputComponent.calculateOutput();
	}

	@Test(expected = MissingInputBehaviourException.class)
	public void testCalculateOutputWithoutInputBehaviour() {
		inputComponent = new InputComponent(mockProtocol, mockTimeline);
		inputComponent.calculateOutput();
	}

	@Test(expected = NoMoreInputsAllowedException.class)
	public void testAddInputWire() {
		inputComponent.addInputWire(mockWire1);
	}
}

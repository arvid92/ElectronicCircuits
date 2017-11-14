package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.*;
import de.nordakademie.circuit.components.gates.AndGate;
import de.nordakademie.circuit.components.gates.ConstantDelayBehaviour;
import de.nordakademie.circuit.components.gates.DelayBehaviour;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.exceptions.MissingOutputWireException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Soll verschiedene voneinander abhaengige Komponenten im Zusammenspiel
 * miteinander testen.
 * 
 * @author Jonas Jacobsen
 */
public class TestAndGate extends TestGate {

	private AndGate andGate, andGateNoOutput, andGateMissingInput;

	@Before
	public void setUp() {
		super.setUp();

		DelayBehaviour mockDelayBehaviour = mock(ConstantDelayBehaviour.class);
		when(mockDelayBehaviour.getDelay()).thenReturn(4);

		andGate = new AndGate(mockDelayBehaviour);
		andGateNoOutput = new AndGate(mockDelayBehaviour);
		andGateMissingInput = new AndGate(mockDelayBehaviour);

		andGate.addInputWire(mockInputWire1);
		andGate.addInputWire(mockInputWire2);
		andGate.addOutputWire(mockOutputWire);

		andGateNoOutput.addInputWire(mockInputWire1);
		andGateNoOutput.addInputWire(mockInputWire2);

		andGateMissingInput.addInputWire(mockInputWire1);
		andGateMissingInput.addOutputWire(mockOutputWire);
	}

	@Test(expected = NoMoreInputsAllowedException.class)
	public void testNoMoreInputsException() {
		andGate.addInputWire(mock(Wire.class));
	}

	@Test(expected = MissingOutputWireException.class)
	public void testNoOutputWireException() {
		andGateNoOutput.calculateOutput();
	}

	@Test(expected = MissingInputWireException.class)
	public void testMissingInputWireException() {
		andGateMissingInput.calculateOutput();
	}

	/*
	 * Die folgenden vier Tests ueberpruefen die Logik eines Gate. Vorbedingung:
	 * Zwei Wires erhalten je einen boolschen Wert. Aktion: calculateOutput()
	 * Test: ueberpruefung, ob die Gate-Logik den entsprechenden Wert
	 * zurueckliefert.
	 */

	@Test
	public void testAndGateFalseFalseResult() {
		setInputs(false, false);
		andGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}

	@Test
	public void testAndGateFalseTrueResult() {
		setInputs(false, true);
		andGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}

	@Test
	public void testAndGateTrueFalseResult() {
		setInputs(true, false);
		andGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}

	@Test
	public void testAndGateTrueTrueResult() {
		setInputs(true, true);
		andGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}
}

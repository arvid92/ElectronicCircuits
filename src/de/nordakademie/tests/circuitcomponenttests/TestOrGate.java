package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.*;
import de.nordakademie.circuit.components.gates.ConstantDelayBehaviour;
import de.nordakademie.circuit.components.gates.DelayBehaviour;
import de.nordakademie.circuit.components.gates.OrGate;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.exceptions.MissingOutputWireException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Soll verschiedene voneinander abhaengige Komponenten im Zusammenspiel
 * miteinander testen
 * 
 * @author Jonas Jacobsen
 */

public class TestOrGate extends TestGate {

	private OrGate orGate, orGateNoOutput, orGateMissingInput;

	@Before
	public void setUp() {
		super.setUp();

		DelayBehaviour mockDelayBehaviour = mock(ConstantDelayBehaviour.class);
		when(mockDelayBehaviour.getDelay()).thenReturn(4);

		orGate = new OrGate(mockDelayBehaviour);
		orGateNoOutput = new OrGate(mockDelayBehaviour);
		orGateMissingInput = new OrGate(mockDelayBehaviour);

		orGate.addInputWire(mockInputWire1);
		orGate.addInputWire(mockInputWire2);
		orGate.addOutputWire(mockOutputWire);

		orGateNoOutput.addInputWire(mockInputWire1);
		orGateNoOutput.addInputWire(mockInputWire2);

		orGateMissingInput.addInputWire(mockInputWire1);
		orGateMissingInput.addOutputWire(mockOutputWire);
	}

	@Test(expected = NoMoreInputsAllowedException.class)
	public void testNoMoreInputsException() {
		orGate.addInputWire(mock(Wire.class));
	}

	@Test(expected = MissingOutputWireException.class)
	public void testNoOutputWireException() {
		orGateNoOutput.calculateOutput();
	}

	@Test(expected = MissingInputWireException.class)
	public void testMissingInputWireException() {
		orGateMissingInput.calculateOutput();
	}

	/*
	 * Die folgenden vier Tests überpruefen die Logik eines Gate. Vorbedingung:
	 * Zwei Wires erhalten je einen boolschen Wert. Aktion: calculateOutput()
	 * Test: Überpruefung, ob die Gate-Logik den entsprechenden Wert
	 * zurueckliefert.
	 */

	@Test
	public void testAndGateFalseFalseResult() {
		setInputs(false, false);
		orGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}

	@Test
	public void testAndGateFalseTrueResult() {
		setInputs(false, true);
		orGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}

	@Test
	public void testAndGateTrueFalseResult() {
		setInputs(true, false);
		orGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}

	@Test
	public void testAndGateTrueTrueResult() {
		setInputs(true, true);
		orGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}
}

package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.*;
import de.nordakademie.circuit.components.gates.ConstantDelayBehaviour;
import de.nordakademie.circuit.components.gates.DelayBehaviour;
import de.nordakademie.circuit.components.gates.InverterGate;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.exceptions.MissingOutputWireException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Soll verschiedene voneinander abhängige Komponenten im Zusammenspiel
 * miteinander testen.
 * 
 * @author Jonas Jacobsen
 */

public class TestInverterGate {

	private InverterGate inverterGate, inverterGateNoOutput, inverterGateMissingInput;
	private Wire mockInputWire, mockOutputWire;

	@Before
	public void setUp() {
		DelayBehaviour mockDelayBehaviour = mock(ConstantDelayBehaviour.class);
		when(mockDelayBehaviour.getDelay()).thenReturn(4);

		inverterGate = new InverterGate(mockDelayBehaviour);
		inverterGateNoOutput = new InverterGate(mockDelayBehaviour);
		inverterGateMissingInput = new InverterGate(mockDelayBehaviour);

		mockInputWire = mock(Wire.class);
		mockOutputWire = mock(Wire.class);

		inverterGate.addInputWire(mockInputWire);
		inverterGate.addOutputWire(mockOutputWire);

		inverterGateNoOutput.addInputWire(mockInputWire);

		inverterGateMissingInput.addOutputWire(mockOutputWire);
	}

	@Test(expected = NoMoreInputsAllowedException.class)
	public void testNoMoreInputsException() {
		inverterGate.addInputWire(mock(Wire.class));
	}

	@Test(expected = MissingOutputWireException.class)
	public void testNoOutputWireException() {
		inverterGateNoOutput.calculateOutput();
	}

	@Test(expected = MissingInputWireException.class)
	public void testMissingInputWireException() {
		inverterGateMissingInput.calculateOutput();
	}

	/*
	 * Die folgenden zwei Tests ueberpruefen die Logik eines Gate. Vorbedingung:
	 * Ein Wire erhält einen boolschen Wert. Aktion: calculateOutput() Test:
	 * ueberpruefung, ob die Gate-Logik den Eingangswert negiert hat.
	 */

	@Test
	public void testAndGateFalseResult() {
		when(mockInputWire.getValue()).thenReturn(false);
		inverterGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}

	@Test
	public void testAndGateTrueResult() {
		when(mockInputWire.getValue()).thenReturn(true);
		inverterGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}
}

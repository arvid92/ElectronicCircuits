package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.*;
import de.nordakademie.circuit.components.gates.ConstantDelayBehaviour;
import de.nordakademie.circuit.components.gates.DelayBehaviour;
import de.nordakademie.circuit.components.gates.XorGate;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.exceptions.MissingOutputWireException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Die folgenden Tests testen das XOR-Gate bei unterschiedlichen Input-Signalen
 * sowie das auftreten moeglicher Exceptions
 * 
 * @author Markus Jaehn
 */
public class TestXorGate extends TestGate {

	private XorGate xorGate, xorGateNoOutput, xorGateMissingInput;

	@Before
	public void setUp() {
		super.setUp();

		DelayBehaviour mockDelayBehaviour = mock(ConstantDelayBehaviour.class);
		when(mockDelayBehaviour.getDelay()).thenReturn(4);

		xorGate = new XorGate(mockDelayBehaviour);
		xorGateNoOutput = new XorGate(mockDelayBehaviour);
		xorGateMissingInput = new XorGate(mockDelayBehaviour);

		xorGate.addInputWire(mockInputWire1);
		xorGate.addInputWire(mockInputWire2);
		xorGate.addOutputWire(mockOutputWire);

		xorGateNoOutput.addInputWire(mockInputWire1);
		xorGateNoOutput.addInputWire(mockInputWire2);

		xorGateMissingInput.addInputWire(mockInputWire1);
		xorGateMissingInput.addOutputWire(mockOutputWire);
	}

	@Test(expected = NoMoreInputsAllowedException.class)
	public void testNoMoreInputsException() {
		xorGate.addInputWire(mock(Wire.class));
	}

	@Test(expected = MissingOutputWireException.class)
	public void testNoOutputWireException() {
		xorGateNoOutput.calculateOutput();
	}

	@Test(expected = MissingInputWireException.class)
	public void testMissingInputWireException() {
		xorGateMissingInput.calculateOutput();
	}

	@Test
	public void testXorGateFalseFalseResult() {
		setInputs(false, false);
		xorGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}

	@Test
	public void testXorGateFalseTrueResult() {
		setInputs(false, true);
		xorGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}

	@Test
	public void testXorGateTrueFalseResult() {
		setInputs(true, false);
		xorGate.calculateOutput();
		verify(mockOutputWire).setValue(true, 4);
	}

	@Test
	public void testXorGateTrueTrueResult() {
		setInputs(true, true);
		xorGate.calculateOutput();
		verify(mockOutputWire).setValue(false, 4);
	}
}

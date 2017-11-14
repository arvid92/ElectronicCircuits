package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.Wire;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Die TestGate-Klasse stellt einige Felder und Methoden zur Verfuegung, um
 * duplicate code in den Gate-Tests zu vermeiden
 *
 * @author Timo Schlatter
 */
abstract class TestGate {

	Wire mockInputWire1, mockInputWire2, mockOutputWire;

	public void setUp() {
		mockInputWire1 = mock(Wire.class);
		mockInputWire2 = mock(Wire.class);
		mockOutputWire = mock(Wire.class);
	}

	void setInputs(boolean input1, boolean input2) {
		when(mockInputWire1.getValue()).thenReturn(input1);
		when(mockInputWire2.getValue()).thenReturn(input2);
	}
}

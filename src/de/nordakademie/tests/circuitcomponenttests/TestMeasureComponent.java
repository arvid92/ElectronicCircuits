package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.MeasureComponent;
import de.nordakademie.circuit.components.Wire;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.NoOutputWiresAllowedException;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Timo Schlatter
 */
public class TestMeasureComponent {

	private MeasureComponent measureComponent;
	private SimulationProtocol mockProtocol;
	private Wire mockWire1, mockWire2, mockWire3;

	@Before
	public void setUp() {
		mockProtocol = mock(SimulationProtocol.class);
		measureComponent = new MeasureComponent(mockProtocol);
		mockWire1 = mock(Wire.class);
		when(mockWire1.getValue()).thenReturn(true);

		mockWire2 = mock(Wire.class);
		when(mockWire2.getValue()).thenReturn(false);

		mockWire3 = mock(Wire.class);
		when(mockWire3.getValue()).thenReturn(true);
	}

	@Test
	public void testCalculateOutput() {
		measureComponent.addInputWire(mockWire1);
		measureComponent.addInputWire(mockWire2);
		measureComponent.addInputWire(mockWire3);
		measureComponent.calculateOutput();
		verify(mockProtocol)
				.addOutputValues(new boolean[] { mockWire1.getValue(), mockWire2.getValue(), mockWire3.getValue() });
	}

	@Test(expected = MissingInputWireException.class)
	public void testCalculateOutputWithoutInputWire() {
		measureComponent.calculateOutput();
	}

	@Test(expected = NoOutputWiresAllowedException.class)
	public void testAddOutputWire() {
		measureComponent.addOutputWire(mockWire1);
	}
}

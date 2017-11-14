package de.nordakademie.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.Wire;
import de.nordakademie.circuit.factories.WireFactory;
import de.nordakademie.timeline.Timeline;

/**
 * Test fuer die WireFactory
 * 
 * @author Arvid Ottenberg
 */

public class TestWireFactory {

	private WireFactory wireFactory;
	private CircuitComponent mockFromComponent, mockToComponent1, mockToComponent2;

	@Before
	public void setUp() {
		Timeline mockTimeline = mock(Timeline.class);
		mockFromComponent = mock(CircuitComponent.class);
		mockToComponent1 = mock(CircuitComponent.class);
		mockToComponent2 = mock(CircuitComponent.class);
		wireFactory = new WireFactory(mockTimeline);
	}

	@Test
	public void testConnectComponents() {
		ArgumentCaptor<Wire> argumentFromComponent = ArgumentCaptor.forClass(Wire.class);
		ArgumentCaptor<Wire> argumentToComponent1 = ArgumentCaptor.forClass(Wire.class);
		ArgumentCaptor<Wire> argumentToComponent2 = ArgumentCaptor.forClass(Wire.class);

		wireFactory.connectComponents(mockFromComponent, mockToComponent1, mockToComponent2);

		verify(mockFromComponent).addOutputWire(argumentFromComponent.capture());
		verify(mockFromComponent, never()).addInputWire(isA(Wire.class));

		verify(mockToComponent1, never()).addOutputWire(isA(Wire.class));
		verify(mockToComponent1).addInputWire(argumentToComponent1.capture());

		verify(mockToComponent2, never()).addOutputWire(isA(Wire.class));
		verify(mockToComponent2).addInputWire(argumentToComponent2.capture());

		assertEquals(argumentFromComponent.getValue(), argumentToComponent1.getValue());
		assertEquals(argumentFromComponent.getValue(), argumentToComponent2.getValue());
	}
}

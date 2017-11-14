package de.nordakademie.tests.circuitcomponenttests;

import de.nordakademie.circuit.components.Wire;
import de.nordakademie.circuit.components.gates.Gate;
import de.nordakademie.exceptions.MissingOutputGateException;
import de.nordakademie.timeline.Event;
import de.nordakademie.timeline.Timeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Timo Schlatter
 */
public class TestWire {

	private Wire wire;
	private Timeline mockTimeline;
	private Gate mockGate1, mockGate2;

	@Before
	public void setUp() {
		mockTimeline = spy(new Timeline());
		wire = new Wire(mockTimeline);
		mockGate1 = mock(Gate.class);
		mockGate2 = mock(Gate.class);
		wire.addSucceedingGates(mockGate1, mockGate2);
	}

	@Test
	public void testSetNewValue() {
		final int startTime = mockTimeline.getCurrentTime();
		final int delay = 1;
		assertEquals(0, startTime);
		assertFalse(wire.getValue());

		wire.setValue(true, delay);

		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		verify(mockTimeline, times(3)).addEvent(eventCaptor.capture());

		List<Event> events = eventCaptor.getAllValues();

		for (Event event : events) {
			assertEquals(startTime + delay, event.getTime());
		}

		Event wireValueChangeEvent = events.get(0);
		assertTrue(wireValueChangeEvent.hasPriority());
		assertFalse(wire.getValue());
		wireValueChangeEvent.execute();
		assertTrue(wire.getValue());

		Event triggerSuccessorEvent1 = events.get(1);
		assertFalse(triggerSuccessorEvent1.hasPriority());
		triggerSuccessorEvent1.execute();
		verify(mockGate1).calculateOutput();

		Event triggerSuccessorEvent2 = events.get(2);
		assertFalse(triggerSuccessorEvent2.hasPriority());
		triggerSuccessorEvent2.execute();
		verify(mockGate2).calculateOutput();
	}

	@Test(expected = MissingOutputGateException.class)
	public void testSetValueWithoutOutputGates() {
		new Wire(mockTimeline).setValue(true, 2);
	}
}

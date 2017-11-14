package de.nordakademie.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import de.nordakademie.exceptions.PastEventException;
import de.nordakademie.timeline.Event;
import de.nordakademie.timeline.Timeline;

/**
 * Test fuer die Timeline
 * 
 * @author Arvid Ottenberg
 */

public class TestTimeline {

	private Timeline timeline;
	private Event mockEvent, mockPastEvent, mockEvent5, mockEvent10, mockEvent15, mockEvent2Prio, mockEvent2NoPrio,
			mockEvent3Prio, mockEvent3NoPrio;

	@Before
	public void setUp() {
		timeline = new Timeline();

		mockEvent = mock(Event.class);

		mockPastEvent = mock(Event.class);
		when(mockPastEvent.getTime()).thenReturn(-1);

		mockEvent5 = mock(Event.class);
		when(mockEvent5.getTime()).thenReturn(5);

		mockEvent10 = mock(Event.class);
		when(mockEvent10.getTime()).thenReturn(10);

		mockEvent15 = mock(Event.class);
		when(mockEvent15.getTime()).thenReturn(15);

		mockEvent2Prio = mock(Event.class);
		when(mockEvent2Prio.getTime()).thenReturn(2);
		when(mockEvent2Prio.hasPriority()).thenReturn(true);

		mockEvent2NoPrio = mock(Event.class);
		when(mockEvent2NoPrio.getTime()).thenReturn(2);
		when(mockEvent2NoPrio.hasPriority()).thenReturn(false);

		mockEvent3Prio = mock(Event.class);
		when(mockEvent3Prio.getTime()).thenReturn(3);
		when(mockEvent3Prio.hasPriority()).thenReturn(true);

		mockEvent3NoPrio = mock(Event.class);
		when(mockEvent3NoPrio.getTime()).thenReturn(3);
		when(mockEvent3NoPrio.hasPriority()).thenReturn(false);
	}

	@Test
	public void testAddEvent() {
		timeline.addEvent(mockEvent);
		assertEquals(mockEvent, timeline.next());
	}

	@Test(expected = PastEventException.class)
	public void testAddPastEvent() {
		timeline.addEvent(mockPastEvent);
	}

	@Test
	public void testAddMultipleEvents() {
		timeline.addEvent(mockEvent10);
		timeline.addEvent(mockEvent15);
		timeline.addEvent(mockEvent5);
		assertEquals(mockEvent5, timeline.next());
		assertEquals(mockEvent10, timeline.next());
		assertEquals(mockEvent15, timeline.next());
	}

	@Test
	public void testAddMultipleEventsAtSameTime() {
		timeline.addEvent(mockEvent10);
		timeline.addEvent(mockEvent10);
		assertEquals(mockEvent10, timeline.next());
		assertEquals(mockEvent10, timeline.next());
	}

	@Test
	public void testEmptyTimelineReturnsNull() {
		assertNull(timeline.next());
	}

	@Test
	public void testRunTimeline() {
		timeline.addEvent(mockEvent10);
		timeline.addEvent(mockEvent5);
		timeline.run();
		InOrder inOrder = inOrder(mockEvent5, mockEvent10);
		inOrder.verify(mockEvent5).execute();
		inOrder.verify(mockEvent10).execute();
	}

	@Test
	public void testGetCurrentTime() {
		timeline.addEvent(mockEvent15);
		assertEquals(0, timeline.getCurrentTime());
		timeline.next();
		assertEquals(15, timeline.getCurrentTime());
	}

	@Test
	public void testAddPriorityEvents() {
		timeline.addEvent(mockEvent3NoPrio);
		timeline.addEvent(mockEvent2NoPrio);
		timeline.addEvent(mockEvent3Prio);
		timeline.addEvent(mockEvent2Prio);

		timeline.run();

		InOrder inOrder = inOrder(mockEvent2Prio, mockEvent2NoPrio, mockEvent3Prio, mockEvent3NoPrio);
		inOrder.verify(mockEvent2Prio).execute();
		inOrder.verify(mockEvent2NoPrio).execute();
		inOrder.verify(mockEvent3Prio).execute();
		inOrder.verify(mockEvent3NoPrio).execute();

	}
}

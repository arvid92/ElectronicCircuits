package de.nordakademie.timeline;

import de.nordakademie.exceptions.PastEventException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Die Timeline repraesentiert einen Zeitstrahl der es ermoeglichst Events hinzuzufuegen und mit Hilfe
 * der run() Methode in zeitlich korrekter Reihenfolge auszufuehren.
 *
 * @author Arvid Ottenberg
 */
public class Timeline {

    private final List<Event> eventList = new ArrayList<>();
    private int currentTime = 0;
    
    public int getCurrentTime() {
    	return currentTime;
    }

    public void addEvent(Event event) {
        if (event.getTime() >= currentTime) {
            eventList.add(event);
        } else {
            throw new PastEventException();
        }
    }

    public Event next() {
        if (eventList.size() == 0) {
            return null;
        }
        Event currentEvent = Collections.min(eventList, (o1, o2) -> {
            int timeComparisonResult = Integer.compare(o1.getTime(), o2.getTime());

            if (timeComparisonResult != 0) {
                return timeComparisonResult;
            } else {
                int x1 = o1.hasPriority() ? 1 : 0;
                int x2 = o2.hasPriority() ? 1 : 0;
                return Integer.compare(x2, x1);
            }
        });
        eventList.remove(currentEvent);
        currentTime = currentEvent.getTime();
        return currentEvent;
    }

    public void run() {
        while (!eventList.isEmpty()) {
            next().execute();
        }
    }
}

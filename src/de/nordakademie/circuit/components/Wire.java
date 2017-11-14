package de.nordakademie.circuit.components;

import de.nordakademie.exceptions.MissingOutputGateException;
import de.nordakademie.timeline.Event;
import de.nordakademie.timeline.Timeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Die Wire-Klasse repraesentiert das Traegermedium fuer Strom zwischen zwei
 * Logik-Gattern. Abgesehen von der Ladung kennt eine Wire-Instanz aus Gruenden
 * der Event-Erzeugung die Timeline und die Gatter, welche das das Wire in ihrer
 * Liste von Input-Wires enthalten.
 *
 * @author Timo Schlatter
 */
public class Wire {

	private final List<CircuitComponent> toGates = new ArrayList<>();
	private final Timeline timeline;
	private boolean value;

	public Wire(Timeline timeline) {
		this.timeline = timeline;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value, int delay) {
		if (!toGates.isEmpty()) {
			int eventTime = timeline.getCurrentTime() + delay;
			timeline.addEvent(createWireValueChangeEvent(eventTime, value));
			for (CircuitComponent circuitComponent : toGates) {
				timeline.addEvent(createTriggerSuccessorEvent(eventTime, circuitComponent));
			}
		} else {
			throw new MissingOutputGateException();
		}
	}

	private void setValue(boolean value) {
		this.value = value;
	}

	public void addSucceedingGates(CircuitComponent circuitComponent, CircuitComponent... circuitComponents) {
		toGates.add(circuitComponent);
		toGates.addAll(Arrays.asList(circuitComponents));
	}

	private Event createWireValueChangeEvent(int eventTime, boolean value) {
		Wire thisWire = this;
		return new Event() {
			@Override
			public int getTime() {
				return eventTime;
			}

			@Override
			public void execute() {
				thisWire.setValue(value);
			}

			@Override
			public boolean hasPriority() {
				return true;
			}
		};
	}

	private Event createTriggerSuccessorEvent(int eventTime, CircuitComponent successor) {
		return new Event() {
			@Override
			public int getTime() {
				return eventTime;
			}

			@Override
			public void execute() {
				successor.calculateOutput();
			}

			@Override
			public boolean hasPriority() {
				return false;
			}
		};
	}
}

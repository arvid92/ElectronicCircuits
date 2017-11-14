package de.nordakademie.circuit.factories;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.Wire;
import de.nordakademie.timeline.Timeline;

/**
 * Die Wirefactory, soll die Implementation der Wire Klasse kapseln und hoeher
 * liegenden Klassen wie dem Circuit Builder das logische Verbinden von
 * CircuitComponents ermoeglichen. Da der Konstruktor der Wire Klasse eine
 * Timeline erfordert, bekommt die WireFactory diese ebenfalls durch ihren
 * Konstruktor uebergeben.
 *
 * Mit der Methode connectComponents laesst sich ein CircuitComponent Objekt mit
 * einem Wire an beliebig viele andere Circuit Components verknuepfen.
 *
 * @author Arvid Ottenberg
 */
public class WireFactory {

	private final Timeline timeline;

	public WireFactory(Timeline timeline) {
		this.timeline = timeline;
	}

	public void connectComponents(CircuitComponent fromComponent, CircuitComponent... toComponents) {
		Wire wire = new Wire(timeline);
		fromComponent.addOutputWire(wire);
		for (CircuitComponent toComponent : toComponents) {
			toComponent.addInputWire(wire);
			wire.addSucceedingGates(toComponent);
		}
	}
}

package de.nordakademie.circuit.factories;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.MeasureComponent;
import de.nordakademie.circuit.components.input.InputComponent;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import de.nordakademie.timeline.Timeline;

/**
 * Die CircuitComponentFactory-Klasse stellt Methoden zur Erzeugung von
 * verschiedenen CircuitComponent-Objekten sowie zum Verbinden dieser
 * Komponenten zur Verfuegung. Zur Erzeugung von Gate-Objekten wird die Logik
 * einer GateFactory- Instanz und zum Verbinden von Komponenten die einer
 * WireFactory-Instanz genutzt.
 *
 * Die Intention hinter diesem Konstrukt besteht darin, dass alle Komponenten
 * die gleiche SimulationProtocol- und Timeline-Instanz nutzen sollen.
 *
 * @author Jonas Jacobsen
 */
public class CircuitComponentFactory {

	private final SimulationProtocol simulationProtocol;
	private final Timeline timeline = new Timeline();
	private final GateFactory gateFactory = new GateFactory();
	private final WireFactory wireFactory = new WireFactory(timeline);

	public CircuitComponentFactory(SimulationProtocol simulationProtocol) {
		this.simulationProtocol = simulationProtocol;
	}

	public CircuitComponent createAndGateWithConstantDelay(int delay) {
		return gateFactory.createAndGateWithConstantDelay(delay);
	}

	public CircuitComponent createXorGateWithConstantDelay(int delay) {
		return gateFactory.createXorGateWithConstantDelay(delay);
	}

	public CircuitComponent createOrGateWithConstantDelay(int delay) {
		return gateFactory.createOrGateWithConstantDelay(delay);
	}

	public CircuitComponent createInverterGateWithConstantDelay(int delay) {
		return gateFactory.createInverterGateWithConstantDelay(delay);
	}

	public void connectComponents(CircuitComponent fromComponent, CircuitComponent... toComponents) {
		wireFactory.connectComponents(fromComponent, toComponents);
	}

	public InputComponent createInputComponent() {
		return new InputComponent(simulationProtocol, timeline);
	}

	public CircuitComponent createMeasureComponent() {
		return new MeasureComponent(simulationProtocol);
	}
}

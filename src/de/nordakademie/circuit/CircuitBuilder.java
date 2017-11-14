package de.nordakademie.circuit;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.factories.CircuitComponentFactory;
import de.nordakademie.circuit.components.input.InputComponent;
import de.nordakademie.simulation.protocol.SimulationProtocol;

/**
 * Die Circuit Builder Klasse beinhalet Methoden die konkrete Schaltkreise
 * zusammenbauen und zurueck geben. Damit soll das Wissen ueber die in den
 * Schaltkreisen enthaltenen Bauteile verdeckt gehalten werden. Moechte man
 * einen neuen Kreis erstellen muss nur der Circuit Builder um eine Methode
 * erweitert werden.
 *
 * @author Arvid Ottenberg
 */
public class CircuitBuilder {

	public Circuit buildWikipediaCircuit() {
		SimulationProtocol protocol = new SimulationProtocol();
		CircuitComponentFactory componentFactory = new CircuitComponentFactory(protocol);

		InputComponent input = componentFactory.createInputComponent();
		CircuitComponent measure = componentFactory.createMeasureComponent();

		CircuitComponent orGate = componentFactory.createOrGateWithConstantDelay(1);
		CircuitComponent andGate1 = componentFactory.createAndGateWithConstantDelay(1);
		CircuitComponent andGate2 = componentFactory.createAndGateWithConstantDelay(1);
		CircuitComponent invertGate = componentFactory.createInverterGateWithConstantDelay(1);

		componentFactory.connectComponents(input, andGate1);
		componentFactory.connectComponents(input, invertGate, andGate1);
		componentFactory.connectComponents(input, andGate2);
		componentFactory.connectComponents(invertGate, andGate2);
		componentFactory.connectComponents(andGate1, orGate);
		componentFactory.connectComponents(andGate2, orGate);
		componentFactory.connectComponents(orGate, measure);

		return new Circuit(input, protocol);
	}

	public Circuit buildMediumCircuit() {
		SimulationProtocol protocol = new SimulationProtocol();
		CircuitComponentFactory componentFactory = new CircuitComponentFactory(protocol);

		InputComponent input = componentFactory.createInputComponent();
		CircuitComponent measure = componentFactory.createMeasureComponent();

		CircuitComponent orGate = componentFactory.createXorGateWithConstantDelay(1);
		CircuitComponent andGate = componentFactory.createAndGateWithConstantDelay(1);
		CircuitComponent invertGate = componentFactory.createInverterGateWithConstantDelay(2);

		componentFactory.connectComponents(input, invertGate, orGate);
		componentFactory.connectComponents(input, andGate);
		componentFactory.connectComponents(invertGate, orGate);
		componentFactory.connectComponents(orGate, andGate);
		componentFactory.connectComponents(andGate, measure);

		return new Circuit(input, protocol);
	}

	public Circuit buildMiniCircuitWithoutGlitch() {
		SimulationProtocol protocol = new SimulationProtocol();
		CircuitComponentFactory componentFactory = new CircuitComponentFactory(protocol);

		InputComponent input = componentFactory.createInputComponent();
		CircuitComponent measure = componentFactory.createMeasureComponent();

		CircuitComponent orGate = componentFactory.createXorGateWithConstantDelay(1);
		CircuitComponent inverter = componentFactory.createInverterGateWithConstantDelay(2);
		CircuitComponent andGate = componentFactory.createAndGateWithConstantDelay(1);

		componentFactory.connectComponents(input, orGate);
		componentFactory.connectComponents(input, orGate);
		componentFactory.connectComponents(input, inverter);
		componentFactory.connectComponents(inverter, andGate);
		componentFactory.connectComponents(orGate, andGate);
		componentFactory.connectComponents(andGate, measure);

		return new Circuit(input, protocol);
	}
}

package de.nordakademie.circuit.factories;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.gates.*;

/**
 * Factory-Klasse fuer Gates. Soll die verschiedenen Gate Implementierungen von
 * hoeher liegenden Komponenten kapseln und damit Erweiterbarkeit um neue Gate
 * Implementierungen erleichtern.
 *
 * @author Arvid Ottenberg
 */
class GateFactory {

	CircuitComponent createAndGateWithConstantDelay(int delay) {
		return new AndGate(new ConstantDelayBehaviour(delay));
	}

	CircuitComponent createXorGateWithConstantDelay(int delay) {
		return new XorGate(new ConstantDelayBehaviour(delay));
	}

	CircuitComponent createOrGateWithConstantDelay(int delay) {
		return new OrGate(new ConstantDelayBehaviour(delay));
	}

	CircuitComponent createInverterGateWithConstantDelay(int delay) {
		return new InverterGate(new ConstantDelayBehaviour(delay));
	}
}

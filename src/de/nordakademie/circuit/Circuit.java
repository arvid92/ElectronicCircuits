package de.nordakademie.circuit;

import de.nordakademie.circuit.components.input.InputBehaviour;
import de.nordakademie.circuit.components.input.InputComponent;
import de.nordakademie.simulation.protocol.SimulationProtocol;

/**
 * Die Circuit Klasse enthaelten einen konkreten bereits zusammengebauten
 * Schaltkreis und verwaltet die wichtigen Ressourcen wie das Protokoll und den
 * ersten Komponenten um die Simulation anzustossen.
 *
 * @author Arvid Ottenberg
 */
public class Circuit {

	private final InputComponent inputComponent;
	private final SimulationProtocol protocol;

	public Circuit(InputComponent circuitComponent, SimulationProtocol protocol) {
		this.inputComponent = circuitComponent;
		this.protocol = protocol;
	}

	public SimulationProtocol getProtocol() {
		return protocol;
	}

	public void startCircuitSimulation(InputBehaviour inputBehaviour) {
		inputComponent.setInputBehaviour(inputBehaviour);
		inputComponent.calculateOutput();
	}
}

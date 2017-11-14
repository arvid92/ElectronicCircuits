package de.nordakademie.simulation;

import de.nordakademie.circuit.Circuit;
import de.nordakademie.circuit.CircuitBuilder;
import de.nordakademie.circuit.components.input.SingleInputChangeBehaviour;
import de.nordakademie.simulation.protocol.GlitchDetection;
import de.nordakademie.simulation.protocol.GlitchDetectionProtocol;
import de.nordakademie.simulation.protocol.SimulationProtocol;

/**
 * Mainmethode enthaltende Klasse. Erzeugt Circuit mit Hilfe des Circuit
 * Builders und startet die Simulation. Anschliessend wird das daraus entstandene
 * Protokoll der Glitch Detection zur Untersuchung nach Glitches und zum
 * Erzeugen des Glitch Protokolls uebergeben. Zuletzt werden beide Protokolle
 * ausgegeben.
 * 
 * @author Arvid Ottenberg
 */
class Simulation {

	public static void main(String[] args) {
		CircuitBuilder circuitBuilder = new CircuitBuilder();
		Circuit circuit = circuitBuilder.buildWikipediaCircuit();
		circuit.startCircuitSimulation(new SingleInputChangeBehaviour());

		SimulationProtocol simulationProtocol = circuit.getProtocol();
		GlitchDetection glitchDetection = new GlitchDetection();
		GlitchDetectionProtocol glitchDetectionProtocol = glitchDetection
				.detectGlitches(simulationProtocol.getDocument());
		simulationProtocol.print();
		glitchDetectionProtocol.print();
	}
}

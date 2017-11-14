package de.nordakademie.circuit.components.input;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.Wire;
import de.nordakademie.exceptions.DifferentNumberOfInputWiresException;
import de.nordakademie.exceptions.MissingInputBehaviourException;
import de.nordakademie.exceptions.MissingOutputWireException;
import de.nordakademie.simulation.protocol.SimulationProtocol;
import de.nordakademie.timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Die InputComponent-Klasse repraesentiert die Stromquelle fuer die Belegung
 * der Eingangsdraehte einer Schaltung. Eine Instanz hat lediglich
 * Ausgangs-Wires und belegt diese mit den Belegungen, welche durch das
 * definierte InputBehaviour generiert werden. Die Instanz kennt ebenfalls die
 * zentrale Timeline-Instanz, da sie die Abarbeitung der Events in der Timeline
 * nach jeder Belegungsaenderung startet. Ausserdem protokolliert sie die
 * Belegungen, um eine Glitch-Erkennung zu ermoeglichen.
 *
 * @author Timo Schlatter
 */
public class InputComponent implements CircuitComponent {

	private final SimulationProtocol protocol;
	private final Timeline timeline;
	private final List<Wire> outputWires = new ArrayList<>();
	private InputBehaviour inputBehaviour;

	public InputComponent(SimulationProtocol protocol, Timeline timeline) {
		this.protocol = protocol;
		this.timeline = timeline;
	}

	public void setInputBehaviour(InputBehaviour inputBehaviour) {
		this.inputBehaviour = inputBehaviour;
	}

	@Override
	public void addOutputWire(Wire wire) {
		outputWires.add(wire);
	}

	@Override
	public void calculateOutput() {
		if (inputBehaviour != null) {
			if (!outputWires.isEmpty()) {
				final List<InputChange> inputChanges = inputBehaviour.generateInputChanges(outputWires.size());
				executeInputChanges(inputChanges);
			} else {
				throw new MissingOutputWireException();
			}
		} else {
			throw new MissingInputBehaviourException();
		}
	}

	private void executeInputChanges(List<InputChange> inputChanges) {
		for (InputChange inputChange : inputChanges) {
			boolean[] inputsBeforeChange = inputChange.getInputsBeforeChange();
			protocol.addInputsBeforeChange(inputsBeforeChange);
			setValuesOnOutputWires(inputsBeforeChange);
			timeline.run();

			boolean[] inputsAfterChange = inputChange.getInputsAfterChange();
			protocol.addInputsAfterChange(inputsAfterChange);
			setValuesOnOutputWires(inputsAfterChange);
			timeline.run();
		}
	}

	private void setValuesOnOutputWires(boolean[] inputs) {
		if (inputs.length == outputWires.size()) {
			for (int i = 0; i < inputs.length; i++) {
				outputWires.get(i).setValue(inputs[i], 0);
			}
		} else {
			throw new DifferentNumberOfInputWiresException();
		}
	}
}

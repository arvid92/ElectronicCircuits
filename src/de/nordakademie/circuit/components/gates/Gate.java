package de.nordakademie.circuit.components.gates;

import de.nordakademie.circuit.components.CircuitComponent;
import de.nordakademie.circuit.components.Wire;
import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.exceptions.MissingOutputWireException;
import de.nordakademie.exceptions.NoMoreInputsAllowedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Die abstrakte Klasse Gate fasst das Verhalten aller elektronischen Gatter
 * eines Schaltkreises zusammen. Ein Gatter besitzt eine bestimmte Anzahl von
 * Eingaengen. Zudem gibt es Ausgaenge und hat ein individuelles
 * Verzoegerungsverhalten.
 *
 * Die Klasse implementiert das CircuitComponent Interface und ueberschreibt
 * bereits die Methoden zum hinzufuegen von Input- und Outputs.
 *
 * @author Arvid Ottenberg
 */
public abstract class Gate implements CircuitComponent {

	private final List<Wire> outputWires = new ArrayList<>();
	private final DelayBehaviour delayBehaviour;
	private final int numberOfInputWires;
	final List<Wire> inputWires = new ArrayList<>();

	public Gate(int maxInputWires, DelayBehaviour delayBehaviour) {
		this.numberOfInputWires = maxInputWires;
		this.delayBehaviour = delayBehaviour;
	}

	@Override
	public void addInputWire(Wire wire) {
		if (inputWires.size() < numberOfInputWires) {
			inputWires.add(wire);
		} else {
			throw new NoMoreInputsAllowedException();
		}
	}

	@Override
	public void addOutputWire(Wire wire) {
		outputWires.add(wire);
	}

	void setOutputWires(boolean outputValue) {
		if (!outputWires.isEmpty()) {
			for (Wire wire : outputWires) {
				wire.setValue(outputValue, delayBehaviour.getDelay());
			}
		} else {
			throw new MissingOutputWireException();
		}
	}

	void checkEnoughInputWiresAdded() {
		if (inputWires.size() != numberOfInputWires) {
			throw new MissingInputWireException();
		}
	}
}

package de.nordakademie.circuit.components.gates;

import de.nordakademie.circuit.components.Wire;

/**
 * Die Klasse AndGate repraesentiert das Verhalten einer logischen
 * Und-Verknuepfung. Das Gate besitzt zwei Eingaenge und einen Ausgang, der mit
 * mehreren Wires verbunden werden kann. Das Verzoegerungsverhalten wird
 * individuell bei der Initialisierung angegeben. Das Gate hat einen positiven
 * Output, wenn alle Inputs positiv sind.
 *
 * @author Jonas Jacobsen
 */
public class AndGate extends Gate {

	public AndGate(DelayBehaviour delayBehaviour) {
		super(2, delayBehaviour);
	}

	@Override
	public void calculateOutput() {
		checkEnoughInputWiresAdded();
		boolean outputValue = true;
		for (Wire wire : inputWires) {
			if (!wire.getValue()) {
				outputValue = false;
				break;
			}
		}
		setOutputWires(outputValue);
	}
}

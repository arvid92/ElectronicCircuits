package de.nordakademie.circuit.components.gates;

import de.nordakademie.circuit.components.Wire;

/**
 * Die Klasse OrGate repraesentiert das Verhalten einer logischen
 * Oder-Verknuepfung. Das Gate besitzt zwei Eingaenge und einen Ausgang, der mit
 * mehreren Wires verbunden werden kann. Das Verzoegerungsverhalten wird
 * individuell bei der Initialisierung angegeben. Das Gate hat einen positiven
 * Output, wenn mindestens einer der Inputs positiv ist.
 *
 * @author Jonas Jacobsen
 */
public class OrGate extends Gate {

	public OrGate(DelayBehaviour delayBehaviour) {
		super(2, delayBehaviour);
	}

	@Override
	public void calculateOutput() {
		checkEnoughInputWiresAdded();
		boolean outputValue = false;
		for (Wire wire : inputWires) {
			if (wire.getValue()) {
				outputValue = true;
				break;
			}
		}
		setOutputWires(outputValue);
	}
}

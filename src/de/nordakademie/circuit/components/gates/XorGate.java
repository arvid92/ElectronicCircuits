package de.nordakademie.circuit.components.gates;

/**
 * Die Klasse XorGate repraesentiert das Verhalten einer logischen
 * Exklusiv-Oder-Verknuepfung. Das Gate besitzt zwei Eingaenge und einen
 * Ausgang, der mit mehreren Wires verbunden werden kann. Das
 * Verzoegerungsverhalten wird individuell bei der Initialisierung angegeben.
 * Das Gate hat einen positiven Output, wenn die Inputsignale nicht den gleichen
 * Wahrheitswert haben.
 *
 * @author Markus Jaehn
 */
public class XorGate extends Gate {

	public XorGate(DelayBehaviour delayBehaviour) {
		super(2, delayBehaviour);
	}

	@Override
	public void calculateOutput() {
		checkEnoughInputWiresAdded();
		boolean outputValue = (inputWires.get(0).getValue() == !(inputWires.get(1).getValue()));
		setOutputWires(outputValue);
	}
}

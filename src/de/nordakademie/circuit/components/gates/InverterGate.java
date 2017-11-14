package de.nordakademie.circuit.components.gates;

/**
 * Die Klasse InverterGate repraesentiert das Verhalten eines logischen
 * Inverter. Das Gate besitzt einen Eingang und einen Ausgang, der mit mehreren
 * Wires verbunden werden kann. Das Verzoegerungsverhalten wird individuell bei
 * der Initialisierung angegeben. Das Gate liefert die Negation des am Eingang
 * anliegenden Signals.
 *
 * @author Jonas Jacobsen
 */
public class InverterGate extends Gate {

	public InverterGate(DelayBehaviour delayBehaviour) {
		super(1, delayBehaviour);
	}

	@Override
	public void calculateOutput() {
		checkEnoughInputWiresAdded();
		boolean outputValue = !inputWires.get(0).getValue();
		setOutputWires(outputValue);
	}
}

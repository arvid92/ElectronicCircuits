package de.nordakademie.circuit.components.gates;

/**
 * Die Klasse ConstantDelayBehaviour repraesentiert ein konstantes
 * Verzoegerungsverhalten.
 *
 * @author Jonas Jacobsen
 */
public class ConstantDelayBehaviour implements DelayBehaviour {

	private final int delay;

	public ConstantDelayBehaviour(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}
}

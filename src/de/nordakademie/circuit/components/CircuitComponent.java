package de.nordakademie.circuit.components;

import de.nordakademie.exceptions.NoMoreInputsAllowedException;
import de.nordakademie.exceptions.NoOutputWiresAllowedException;
import de.nordakademie.exceptions.NoOutputToCalculateException;

/**
 * Das CircuitComponent Interface legt die Methoden fest die ein Component der
 * Schaltung bieten muss, um ein sauberes Zusammenspiel der unterschiedlichen
 * Bausteine zu garantieren. Zusaetzlich werden durch das Interface die
 * konkreten Implementierungen von einander und vor anderen Komponenten
 * verborgen.
 *
 * @author Arvid Ottenberg
 */
public interface CircuitComponent {

	default void calculateOutput() {
		throw new NoOutputToCalculateException();
	}

	default void addInputWire(Wire wire) {
		throw new NoMoreInputsAllowedException();
	}

	default void addOutputWire(Wire wire) {
		throw new NoOutputWiresAllowedException();
	}
}
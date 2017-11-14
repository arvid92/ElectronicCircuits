package de.nordakademie.circuit.components.input;

/**
 * Die InputChange-Klasse ist ein Datenobjekt um Eingangsbelegungswechsel zu
 * repraesentieren. Eine Instanz enthaelt die initialen Eingangsbelegungen sowie
 * die Belegungen, zu denen gewechselt werden soll.
 *
 * @author Timo Schlatter
 */

public class InputChange {
	private final boolean[] inputsBeforeChange;
	private final boolean[] inputsAfterChange;

	public InputChange(boolean[] inputsBeforeChange, boolean[] inputsAfterChange) {
		this.inputsBeforeChange = inputsBeforeChange;
		this.inputsAfterChange = inputsAfterChange;
	}

	public boolean[] getInputsBeforeChange() {
		return inputsBeforeChange;
	}

	public boolean[] getInputsAfterChange() {
		return inputsAfterChange;
	}
}

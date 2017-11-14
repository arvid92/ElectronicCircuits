package de.nordakademie.circuit.components.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Die SingleInputChangeBehaviour-Klasse dient zur Generierung von InputChanges.
 * Dabei gilt fuer jedes generierte InputChange-Objekt, dass sich die Belegungen
 * vor und nach dem Wechsel in genau einem Element unterscheiden. Die Gesamtzahl
 * der generierten InputChange-Objekte entspricht also der Zahl der moeglichen
 * Belegungen (2^x) multipliziert mit x, wobei x die Anzahl der Eingangsdraehte
 * ist.
 *
 * @author Timo Schlatter
 */
public class SingleInputChangeBehaviour implements InputBehaviour {

	@Override
	public List<InputChange> generateInputChanges(int numberOfInputWires) {
		List<boolean[]> binaryRepresentations = generateAllInputPossibilities(numberOfInputWires);
		return createInputChanges(binaryRepresentations);
	}

	private List<boolean[]> generateAllInputPossibilities(int digits) {
		List<boolean[]> inputPossibilities = new ArrayList<>();
		for (int i = 0; i < (int) Math.pow(2, digits); i++) {
			String binaryRepresentation = Integer.toBinaryString(i);
			while (binaryRepresentation.length() < digits) {
				binaryRepresentation = "0" + binaryRepresentation;
			}
			inputPossibilities.add(convertBinaryRepresentationToBooleanArray(binaryRepresentation));
		}
		return inputPossibilities;
	}

	private boolean[] convertBinaryRepresentationToBooleanArray(String binaryRepresentation) {
		boolean[] booleanArray = new boolean[binaryRepresentation.length()];
		for (int i = 0; i < binaryRepresentation.length(); i++) {
			booleanArray[i] = binaryRepresentation.charAt(i) != '0';
		}
		return booleanArray;
	}

	private List<InputChange> createInputChanges(List<boolean[]> booleanArrays) {
		List<InputChange> inputChanges = new ArrayList<>();
		for (boolean[] inputsBeforeChange : booleanArrays) {
			for (int i = 0; i < inputsBeforeChange.length; i++) {
				boolean[] inputsAfterChange = inputsBeforeChange.clone();
				inputsAfterChange[i] = !inputsBeforeChange[i];
				inputChanges.add(new InputChange(inputsBeforeChange, inputsAfterChange));
			}
		}
		return inputChanges;
	}
}

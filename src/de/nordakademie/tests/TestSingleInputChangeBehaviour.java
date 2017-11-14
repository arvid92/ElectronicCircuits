package de.nordakademie.tests;

import de.nordakademie.circuit.components.input.InputChange;
import de.nordakademie.circuit.components.input.SingleInputChangeBehaviour;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test fuer die SingleInputChangeBehaviour-Klasse.
 * 
 * @author Timo Schlatter
 */
public class TestSingleInputChangeBehaviour {

	private SingleInputChangeBehaviour singleInputChangeBehaviour;

	@Before
	public void setUp() {
		singleInputChangeBehaviour = new SingleInputChangeBehaviour();
	}

	/**
	 * Teste die Belegungswechsel-Generierung fuer 1-10 initiale Eingangs-Wires.
	 */
	@Test
	public void testGenerateInputChangesFor() {
		// Mehr als 10 Eingänge zu simulieren kann zu langen Laufzeiten fuehren
		for (int numberOfInputWires = 1; numberOfInputWires <= 10; numberOfInputWires++) {
			testGenerateInputChanges(numberOfInputWires);
		}
	}

	/**
	 * Test fuer die arraysDifferOnlyInOneElement()-Methode.
	 */
	@Test
	public void testArraysDifferOnlyInOneElement() {
		assertFalse(arraysDifferOnlyInOneElement(new boolean[] { true, true }, new boolean[] { false, false }));
		assertFalse(arraysDifferOnlyInOneElement(new boolean[] { true, true }, new boolean[] { true, true }));
		assertFalse(arraysDifferOnlyInOneElement(new boolean[] { true, true }, new boolean[] { false, true, false }));
		assertTrue(arraysDifferOnlyInOneElement(new boolean[] { true, true }, new boolean[] { false, true }));
		assertTrue(arraysDifferOnlyInOneElement(new boolean[] { true, true }, new boolean[] { true, false }));
	}

	/**
	 * Test fuer die getNumberOfDifferentInputsBeforeChange()-Methode.
	 */
	@Test
	public void testGetNumberOfDifferentInputsBeforeChange() {
		InputChange ic1 = new InputChange(new boolean[] { true, true }, new boolean[] { true, false });
		InputChange ic2 = new InputChange(new boolean[] { true, true }, new boolean[] { false, true });
		InputChange ic3 = new InputChange(new boolean[] { false, true }, new boolean[] { true, true });
		InputChange ic4 = new InputChange(new boolean[] { true, false }, new boolean[] { false, false });
		assertEquals(3, getNumberOfDifferentInputsBeforeChange(Arrays.asList(ic1, ic2, ic3, ic4)));
		assertEquals(3, getNumberOfDifferentInputsBeforeChange(Arrays.asList(ic1, ic3, ic4)));
		assertEquals(3, getNumberOfDifferentInputsBeforeChange(Arrays.asList(ic2, ic3, ic4)));
		assertEquals(1, getNumberOfDifferentInputsBeforeChange(Arrays.asList(ic1, ic1, ic1)));
	}

	/**
	 * Dies ist der Haupttest fuer das SingleInputChangeBehaviour. Es wird fuer
	 * eine gegebene Zahl von initialen Eingangs- Wires getestet, ob eine Liste
	 * von InputChanges generiert werden kann, welche Folgende Bedingungen
	 * erfuellt:
	 *
	 * a) Die Groesse der generierten Liste entspricht dem Produkt aus - 2
	 * quadriert mit der Zahl der Eingangs-Wires (= Anzahl der moeglichen
	 * Eingangs-Belegungen) und - der Zahl der Eingangs-Wires. Das Produkt
	 * repräsentiert die Summe aus jeder Belegung mit jedem Wechsel, in dem nur
	 * eine Stelle geändert wird).
	 *
	 * b) Die Mächtigkeit der Menge der verschiedenen Belegungen bevor ein
	 * Belegungs-Wechsel stattfindet entspricht 2 quadriert mit der Zahl der
	 * Eingangs-Wires (= Anzahl der moeglichen Eingangs-Belegungen).
	 *
	 * c) Jedes einzelne erzeugte InputChange-Objekt besteht ausschließlich aus
	 * Belegungen (repraesentiert durch jeweils zwei Boolean-Arrays, also
	 * Belegung vor und nach dem Wechsel), die sich in genau einem Element
	 * unterscheiden.
	 *
	 * Zur Übersichtlichkeit werden zur Gewaehrleistung der Bedingung
	 * Hilfsmethoden genutzt, zu denen seperate Tests existieren.
	 *
	 * @param numberOfInitialInputWires
	 *            Anzahl der initialen Eingangs-Wires, fuer die die
	 *            Belegungswechsel-Generierung getestet werden soll
	 */
	private void testGenerateInputChanges(int numberOfInitialInputWires) {
		List<InputChange> inputChanges = singleInputChangeBehaviour.generateInputChanges(numberOfInitialInputWires);
		// a)
		assertEquals(((int) Math.pow(2, numberOfInitialInputWires) * numberOfInitialInputWires), inputChanges.size());
		// b)
		assertEquals((int) Math.pow(2, numberOfInitialInputWires),
				getNumberOfDifferentInputsBeforeChange(inputChanges));
		// c)
		for (InputChange inputChange : inputChanges) {
			assertTrue(arraysDifferOnlyInOneElement(inputChange.getInputsBeforeChange(),
					inputChange.getInputsAfterChange()));
		}
	}

	/**
	 * Ermittelt, ob zwei Boolean-Arrays sich nur in einem Element
	 * unterscheiden.
	 *
	 * @param array1
	 *            erstes zu vergleichendes Array
	 * @param array2
	 *            zweites zu vergleichendes Array
	 * @return Sofern es keine oder mehr als eine Unterscheidung gibt oder die
	 *         Länge der Arrays unterschiedlich ist, wird false zurueckgegeben.
	 *         Bei lediglich einer Unterscheidung ist der Rueckgabewert true.
	 */
	private boolean arraysDifferOnlyInOneElement(boolean[] array1, boolean[] array2) {
		if (array1.length == array2.length) {
			int differences = 0;
			for (int i = 0; i < array1.length; i++) {
				if (array1[i] != array2[i]) {
					differences++;
				}
			}
			return differences == 1;
		} else {
			return false;
		}
	}

	/**
	 * Ermittelt die Anzahl der verschiedenen Eingangsbelegungen einer Liste von
	 * InputChanges, wobei nur die Belegungen vor der Aenderung betrachtet
	 * werden (inputChange.getInputsBeforeChange();).
	 *
	 * @param inputChanges
	 *            Liste der zu analysierenden InputChanges
	 * @return Anzahl der verschiedenen Eingangsbelegungen einer Liste von
	 *         InputChanges
	 */
	private int getNumberOfDifferentInputsBeforeChange(List<InputChange> inputChanges) {
		List<List<Boolean>> uniqueInputs = new ArrayList<>();
		for (InputChange inputChange : inputChanges) {
			boolean[] inputsBeforeChange = inputChange.getInputsBeforeChange();
			List<Boolean> inputsBeforeChangeList = new ArrayList<>();
			for (boolean value : inputsBeforeChange) {
				inputsBeforeChangeList.add(value);
			}
			if (!uniqueInputs.contains(inputsBeforeChangeList)) {
				uniqueInputs.add(inputsBeforeChangeList);
			}
		}
		return uniqueInputs.size();
	}
}

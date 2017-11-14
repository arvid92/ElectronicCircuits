package de.nordakademie.circuit.components.input;

import java.util.List;

/**
 * Das Interface InputBehaviour repraesentiert die Logik, mit der Belegungen
 * bzw. Belegungswechsel (InputChange) fuer die Simulation erzeugt werden. Das
 * Interface stellt lediglich eine Methode zur Verfuegung, naemlich diejenige,
 * welche eine Liste von InputChange-Instanzen generiert. Diese Liste wird von
 * der InputComponent-Instanz verwendet, um die gegebenen Belegungen auf die
 * Eingangsdraehte zu schreiben. Die Belegungswechsel koennen in einer Klasse,
 * welches das Interface implementiert, beliebig ermittelt bzw. generiert
 * werden.
 *
 * @author Timo Schlatter
 */
public interface InputBehaviour {

	List<InputChange> generateInputChanges(int numberOfInputWires);
}

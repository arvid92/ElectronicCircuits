package de.nordakademie.circuit.components.gates;

/**
 * Das Interface DelayBehaviour repraesentiert das Verzoegerungsverhalten einer
 * Gate-Instanz. Das Interface stellt lediglich eine Methode zur Verfuegung,
 * naemlich diejenige, welche die Verzoegerungszeit zurueckgibt. Die
 * Verzoegerungszeit kann in einer Klasse, welches das Interface implementiert,
 * beliebig ermittelt bzw. generiert werden.
 *
 * @author Jonas Jacobsen
 */
public interface DelayBehaviour {

	int getDelay();
}

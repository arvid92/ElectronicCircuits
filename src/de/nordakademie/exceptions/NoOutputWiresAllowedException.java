package de.nordakademie.exceptions;

/**
 * 
 * Diese Exception wird geworfen, wenn eine Unterklasse der CircuitComponent die
 * addOutputWire(Wire wire)-Methode nicht ueberschreibt und diese trotzdem
 * aufgerufen wird. Für eine MeasureComponent wuerde es z.B. keinen Sinn
 * ergeben, Output- Wires hinzuzufügen, sodass diese Methode in der Klasse
 * nicht ueberschrieben wird.
 * 
 * @author Jonas Jacobsen
 */
@SuppressWarnings("serial")
public class NoOutputWiresAllowedException extends RuntimeException {

}

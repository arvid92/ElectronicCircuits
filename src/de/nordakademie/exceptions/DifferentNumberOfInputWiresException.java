package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn die Anzahl der Output-Wire-Instanzen
 * einer InputComponent-Instanz nicht mit der Anzahl der durch eine
 * InputBehaviour-Instanz generierten Belegungen uebereinstimmt. Dies kann nur
 * auftreten, wenn eine Klasse, welche InputBehaviour implementiert, eine
 * falsche Zahl von Belegungen generiert.
 * 
 * @author Markus Jaehn
 */
@SuppressWarnings("serial")
public class DifferentNumberOfInputWiresException extends RuntimeException {

}

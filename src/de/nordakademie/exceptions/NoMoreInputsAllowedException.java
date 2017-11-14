package de.nordakademie.exceptions;

/**
 * 
 * Diese Exception wird geworfen, wenn mehr Input-Wires zu einer
 * CircuitComponent hinzugefuegt werden sollen, als die konkrete Implementierung
 * erlaubt.
 * 
 * @author Timo Schlatter
 */

@SuppressWarnings("serial")
public class NoMoreInputsAllowedException extends RuntimeException {

}

package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn der Wert einer Wire-Instanz veraendert
 * wird, jedoch keine nachfolgende CircuitComponent existiert, welche von der
 * Wertaenderung betroffen wäre.
 * 
 * @author Timo Schlatter
 */
@SuppressWarnings("serial")
public class MissingOutputGateException extends RuntimeException {

}

package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn ein Event hinzugefuegt wird, das in der
 * Vergangenheit liegt. (Es also in der Vergangenheit an die Timeline angemeldet
 * wird)
 * 
 * @author Arvid Ottenberg
 */
@SuppressWarnings("serial")
public class PastEventException extends RuntimeException {

}

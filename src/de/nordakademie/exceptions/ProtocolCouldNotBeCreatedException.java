package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn die Erstellung des Document-Objekts für
 * das Protokoll durch den DocumentBuilder fehlschlaegt.
 *
 * @author Timo Schlatter
 */
@SuppressWarnings("serial")
public class ProtocolCouldNotBeCreatedException extends RuntimeException {

	public ProtocolCouldNotBeCreatedException(String message) {
		super(message);
	}
}

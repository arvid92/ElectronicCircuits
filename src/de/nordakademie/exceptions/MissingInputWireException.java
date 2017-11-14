package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn eine Gate-Instanz ueber zu wenige
 * InputWire-Exemplare verfuegt, um das Ausgangssignal zu bestimmen. Dies kann
 * beispielsweise auftreten, wenn in einem Circuit die Gates nicht miteinander
 * verbunden sind.
 * 
 * @author Jonas Jacobsen
 */
@SuppressWarnings("serial")
public class MissingInputWireException extends RuntimeException {

}

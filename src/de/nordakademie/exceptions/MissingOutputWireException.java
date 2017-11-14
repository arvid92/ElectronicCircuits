package de.nordakademie.exceptions;

/**
 * Diese Exception wird geworfen, wenn eine Gate-Instanz ueber keine
 * OutputWire-Exemplare verfuegt, um das Ausgangssignal an das OutputWire bzw.
 * die OutputWires zu uebergeben. Dies kann beispielsweise auftreten, wenn in
 * einem Circuit die Gates nicht miteinander verbunden sind.
 * 
 * @author Jonas Jacobsen
 */
@SuppressWarnings("serial")
public class MissingOutputWireException extends RuntimeException {

}

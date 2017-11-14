package de.nordakademie.circuit.components;

import de.nordakademie.exceptions.MissingInputWireException;
import de.nordakademie.simulation.protocol.SimulationProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Die MeasureComponent repraesentiert ein Strom-Messgeraet, welches am Ende
 * einer Schaltung angebracht wird, um die Spannung auf den Ausgangsdraehten zu
 * messen. Ihre einzige Aufgabe besteht darin, die gemessenen Werte im Protokoll
 * festzuhalten.
 *
 * @author Jonas Jacobsen
 */
public class MeasureComponent implements CircuitComponent {

	private final SimulationProtocol protocol;
	private final List<Wire> inputWires = new ArrayList<>();

	public MeasureComponent(SimulationProtocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public void addInputWire(Wire wire) {
		inputWires.add(wire);
	}

	@Override
	public void calculateOutput() {
		if (!inputWires.isEmpty()) {
			protocol.addOutputValues(convertInputWireValuesToBooleanArray());
		} else {
			throw new MissingInputWireException();
		}
	}

	private boolean[] convertInputWireValuesToBooleanArray() {
		boolean[] inputValues = new boolean[inputWires.size()];
		for (int i = 0; i < inputValues.length; i++) {
			inputValues[i] = inputWires.get(i).getValue();
		}
		return inputValues;
	}
}

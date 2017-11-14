package de.nordakademie.timeline;

/**
 * Interface fuer Events
 * 
 * @author Arvid Ottenberg
 */
public interface Event {

	int getTime();

	void execute();

	boolean hasPriority();

}

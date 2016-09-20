/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

/**
 * A simple interface to provide a consistent method of progressing and querying the state of a
 * simulation in a given system.
 */
public interface Stepable {

    /** Perform a single time step action for this object */
    void step();

    /**
     * Checks whether or not this unit is in a state where it concludes the simulation is finished given there
     * is no further information to come.
     * @return true if the Stepable object has finished it's simulation
     */
    boolean canFinish();

}

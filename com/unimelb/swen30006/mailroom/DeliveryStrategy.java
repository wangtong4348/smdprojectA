/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;

/**
 * A basic interface to allow delivery bots to query multiple delivery strategies for
 * selecting the next floor to go to, based on current floor and on the remaining items
 * to be delivered within the storage box.
 */
public interface DeliveryStrategy {

    /**
     * Select the next floor to deliver to based on the items within the storage box.
     * @param currentFloor the floor the delivery bot is currently on
     * @param box the box of remaning mail items
     * @return the optimal floor to deliver to based on this strategy
     * @throws SourceExhaustedException if there is no mail left in the given storage box
     */
    int chooseNextFloor(int currentFloor, StorageBox box) throws SourceExhaustedException;
}

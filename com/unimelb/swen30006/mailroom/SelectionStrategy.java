/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;

/**
 * An interface for DeliveryBots to use multiple different selection strategies to determine which box,
 * from the storage box system, they deliver next.
 */
public interface SelectionStrategy {

    /**
     * A method to select a next box from a given storage box summary
     * @param summaries the summaries of the storage box summary
     * @return the optimal storage box to deliver next based on the current system
     * @throws NoBoxReadyException if there is no suitable box ready to be delivered
     */
    String selectNextDelivery(StorageBox.Summary[] summaries) throws NoBoxReadyException;

}

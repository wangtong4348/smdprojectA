/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

/**
 * An interface that defines the decisions that need to be made for a mail sorting machine.
 * Different mail sorting machines will require different strategies, but by using the common
 * strategy interface, they can drastically change their behaviour without needing to change
 * their core programming
 */
public interface SortingStrategy {

    /**
     * Assign an identifier for a mail item and a given storage system. Will provide immutable mail storage
     * object to determine the identifier for the storage box to use. If no storage boxes are available to store
     * the item, throws a MailOverflowException to indicate the sorting machine must wait until more deliveries are
     * made before sorting more items
     * @param item the item being sorted
     * @param storage the storage system in use
     * @return the identifier of the assigned storage box
     * @throws MailOverflowException if there are no spaces remaining for new boxes
     */
     String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException;

}


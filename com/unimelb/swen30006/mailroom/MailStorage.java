package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;
import com.unimelb.swen30006.mailroom.exceptions.UnknownIdentifierException;

/**
 * Created by mat on 28/01/2016.
 */
public interface MailStorage {
    /**
     * Check if the storage unit is full of storage boxes
     * @return true if all storage box places are full
     */
    boolean isFull();

    /**
     * Check if the storage unit is empty
     * @return true if all storage box places are empty
     */
    boolean isEmpty();

    /**
     * Retrieve a storage box from the storage system by ID
     * @param name the id of the box
     * @return The box that matches this identifier
     * @throws UnknownIdentifierException if there is no box stored with this identifier
     */
    StorageBox retrieveBox(String name) throws UnknownIdentifierException;

    /**
     * Create a storage box that can be retrieved later by ID
     * @param name the requested identifier
     * @return a new storage box
     * @throws DuplicateIdentifierException if the identifier already exists
     * @throws MailOverflowException if there are no spaces remaining for new boxes
     */
    boolean createBox(String name) throws DuplicateIdentifierException, MailOverflowException;

    /**
     * Retrieve an array of all Storage Boxes currently stored within this mail system,
     * including their maximum size, percentage full, number of floors to deliver to
     * and identifier. For use in planning delivery routes.
     * @return Array of all summaries.
     */
    StorageBox.Summary[] retrieveSummaries();

    /**
     * Retrieves a box by identifier for delivery by delivery bot. Will also notify anyone waiting on change in state
     * to the event.
     * @param identifer identifier of the storage box requested for delivery
     * @return The box that matches this identifier
     * @throws UnknownIdentifierException if there is no box stored with this identifier
     */
    StorageBox deliverBox(String identifer) throws UnknownIdentifierException;
}

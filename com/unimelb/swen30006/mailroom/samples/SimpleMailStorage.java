/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.samples;

import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of a Mail room storage unit, hides any internal
 * implementation of data structures used and allows you to store items in known
 * box IDs or retrieve whole boxes. Has a fixed storage box size.
 */
public class SimpleMailStorage implements MailStorage {

    /* The data store for the actual mail items */
    private HashMap<String, StorageBox> storage;
    /* The maximum number of boxes this can store */
    private final int maxBoxes;
    /* The size of storage units */
    private final int storageBoxSize;


    /**
     * Generate a new SimpleMailStorage unit with a limit on number of boxes
     * @param maxBoxes The maximum number of boxes this storage unit can store
     * @param boxSize the maximum number of units each box can fit
     */
    public SimpleMailStorage(int maxBoxes, int boxSize){
        this.maxBoxes = maxBoxes;
        this.storageBoxSize = boxSize;
        this.storage = new HashMap<String,StorageBox>();
    }

    @Override
    public boolean isFull(){
        return this.storage.size()==this.maxBoxes;
    }

    @Override
    public boolean isEmpty() {
        return this.storage.isEmpty();
    }

    @Override
    public StorageBox retrieveBox(String name) throws UnknownIdentifierException {
        if(this.storage.containsKey(name)){
            StorageBox box = this.storage.get(name);
            return box;
        } else {
            throw new UnknownIdentifierException(name);
        }
    }

    @Override
    public boolean createBox(String name) throws DuplicateIdentifierException, MailOverflowException{
        if(this.isFull()){
            throw new MailOverflowException();
        } else if (this.storage.containsKey(name)){
            throw new DuplicateIdentifierException(name);
        } else {
            StorageBox box = new StorageBox(this.storageBoxSize);
            this.storage.put(name, box);
            return true;
        }
    }

    @Override
    public StorageBox.Summary[] retrieveSummaries() {
        ArrayList<StorageBox.Summary> summaries = new ArrayList<StorageBox.Summary>();
        // Iterate over the hash map and return the summaries
        for(Map.Entry<String,StorageBox> entry : this.storage.entrySet()){
            StorageBox box = entry.getValue();
            summaries.add(box.generateSummary(entry.getKey()));
        }
        // Return the summaries
        return summaries.toArray(new StorageBox.Summary[0]);
    }

    @Override
    public StorageBox deliverBox(String identifer) throws UnknownIdentifierException {
        StorageBox box = retrieveBox(identifer);
        this.storage.remove(identifer);
        return box;
    }

}

/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.samples;

import com.unimelb.swen30006.mailroom.*;
import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.UUID;

/**
 * A very simple sorting strategy that will suggest the first storage box in the storage
 * unit that it will fit in. If none are available, it will create a new box and suggest that.
 * Finally, if none are avalable and there is no room left, it will throw a MailOverflowException
 */
public class SimpleSortingStrategy implements SortingStrategy {

    @Override
    public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {

        // Retrieve the summaries of available storage items
        StorageBox.Summary[] available = storage.retrieveSummaries();
        for(StorageBox.Summary summary : available){
            if(summary.remainingUnits >= item.size){
                return summary.identifier;
            }
        }

        // If we get to here without returning there is no storage box
        // appropriate so let's try create one
        String id = UUID.randomUUID().toString();
        try {
            storage.createBox(id);
            return id;
        } catch (DuplicateIdentifierException e){
            System.out.println(e);
            System.exit(0);
        }
        return null;
    }
}

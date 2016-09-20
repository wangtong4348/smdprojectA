/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.*;

/**
 * A storage box to be used by the mail storage system, created with a fixed length and methods to add
 * and remove MailItems from the storage box. Will throw a MailOverflowException if attempted to fit above the
 * limit. Also supports querying if a package will fit in the box.
 */
public class StorageBox {
    /** The mail stored in this box */
    private Stack<MailItem> mail;

    /** The maximum size of this box */
    public final int maxItems;
    private int remainingCapacity;

    /**
     * Create a storage box of the given max number of mail units
     * @param maxSize the maximum number of mail units this box will fit
     */
    public StorageBox(int maxSize){
        this.maxItems = maxSize;
        this.remainingCapacity = maxSize;
        this.mail = new Stack<MailItem>();
    }

    /**
     * Add a mail item to the box, throwing a MailOverflowException if the item will not fit in this box.
     * @param item the mail item to add to the box
     * @throws MailOverflowException if there are no spaces remaining for new items in the box
     */
    public void addItem(MailItem item) throws MailOverflowException {
        if(this.canHold(item)){
            this.mail.push(item);
            this.remainingCapacity -= item.size;
        } else {
            throw new MailOverflowException(item.size, this.remainingCapacity);
        }
    }

    /**
     * Remove an item (without deciding which item) from the box, updating the remaining capacity
     * @return The mail item removed from the box
     * @throws BoxEmptyException if there is no items in the box
     */
    public MailItem popItem() throws BoxEmptyException {
        if(this.isEmpty()){
            throw new BoxEmptyException();
        }
        MailItem item = mail.pop();
        // Update the remaining capacity
        this.remainingCapacity += item.size;
        return item;
    }

    /**
     * Check if the storage box is empty.
     * @return true if the box is empty
     */
    public boolean isEmpty(){
        return mail.isEmpty();
    }

    /**
     * Checks if this mail storage box can hold the given MailItem based on its
     * remaining capacity
     * @param item the item you want to store in the box
     * @return true if the item will fit
     */
    public boolean canHold(MailItem item){
        return (this.remainingCapacity >= item.size);
    }

    /**
     * Return the number of items stored in the mail box (whole packages, not unit size)
     * @return the number of mail items in this box
     */
    public int numPackages(){
        return this.mail.size();
    }

    /**
     * Generate a summary object for use in planning storage and delivery
     * @param id the id assigned in the storage system to this box
     * @return a summary of the box
     */
    public Summary generateSummary(String id){
        // Count the number of unique floors to deliver to
        HashSet<Integer> floors = new HashSet<Integer>();
        Iterator<MailItem> itemIterator = this.mail.iterator();
        while(itemIterator.hasNext()){
            MailItem item = itemIterator.next();
            floors.add(item.floor);
        }
        // Return the summary
        return new Summary(this.mail.size(), this.remainingCapacity, floors.size(), id);
    }


    /** The summary class for all Storage Boxes */
    public class Summary {
        // The variables that shadow a storage box at any given time
        public final int numItems;
        public final int remainingUnits;
        public final int numDests;
        public final String identifier;

        /**
         * Create a storage box summary, without access to any of the items. Immutable class.
         * @param numItems number of items in this box
         * @param remainingUnits remaining number of units it can take
         * @param numDests the number of different destinations within this box
         * @param ID the box identifier in the storage unit
         */
        public Summary(int numItems, int remainingUnits, int numDests, String ID){
            this.numItems = numItems;
            this.remainingUnits = remainingUnits;
            this.numDests = numDests;
            this.identifier = ID;
        }

        @Override
        public String toString() {
            return "Summary{" +
                    "remainingUnits=" + remainingUnits +
                    ", numItems=" + numItems +
                    ", numDests=" + numDests +
                    ", identifier='" + identifier + '\'' +
                    '}';
        }
    }

}

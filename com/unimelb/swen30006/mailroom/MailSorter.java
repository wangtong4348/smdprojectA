/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;
import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;
import com.unimelb.swen30006.mailroom.exceptions.UnknownIdentifierException;

/**
 * A MailSorting computer that takes a MailSource, and a Sorting Strategy and will
 * use that strategy to continuously sort mail into boxes until their is none left
 * in the MailSource. Works with delivery bots to clear storage and deliver mail
 */
public class MailSorter implements Stepable {

    /** The mail storage system in use */
    private final MailStorage storage;
    /** The mail source we are sorting from */
    private final MailSource source;
    /** The sorting strategy we apply */
    private final SortingStrategy strategy;
    /** Flag for whether we have finished or not */
    private boolean hasFinished = false;
    /** The next mail item to deliver **/
    private MailItem nextItem;

    /**
     * Instantiate a MailSorter with the given parameters. Does not begin sort from source
     *
     * @param source   the mail source this sorter should retrieve mail from
     * @param storage  the storage source this sorter should use to store mail in
     * @param strategy the strategy this source should use to decide on sorting decisions
     */
    public MailSorter(MailSource source, MailStorage storage, SortingStrategy strategy) {
        this.source = source;
        this.storage = storage;
        this.strategy = strategy;
    }

    @Override
    public void step() {
        if (source.hasNextMail()) {
            // Continue while we have mail

            try {
                if(this.nextItem==null){
                    this.nextItem = source.nextItem();
                }
                // Retrieve identifier
                String identifier = strategy.assignStorage(this.nextItem, this.storage);
                // Assign Storage
                StorageBox box = this.storage.retrieveBox(identifier);
                
                box.addItem(this.nextItem);
                // Set this to null if no exceptions thrown
                this.nextItem = null;
            } catch (MailOverflowException e) {
                // Strategy has decided storage has no room for mail item, wait for deliver.
                System.out.println(e);
            } catch (UnknownIdentifierException e) {
                // Strategy has not correctly identified box
                System.out.println(e);
                System.out.println("FATAL: Sort Strategy failed. Abort");
                System.exit(0);
            } catch (SourceExhaustedException e) {
                // Print the exception out to warn and return
                System.out.println(e);
            }
        } else if(this.storage.isEmpty()){
            this.hasFinished = true;
        }
    }

    @Override
    public boolean canFinish() {
        return this.hasFinished;
    }
}
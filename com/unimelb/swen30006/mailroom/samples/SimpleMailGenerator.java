/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.samples;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailSource;
import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;

import java.util.Random;

/**
 * A class that uses pseudorandomness to generate immutable MailItems at any given time,
 * attempts to simulate random delivery of MailItems to a building
 */
public class SimpleMailGenerator implements MailSource {

    /** The name of this source */
    private static final String NAME = "Simple Mail Generator";

    /** The minimum floor and number of floors that a mail item could be addressed to */
    private final int minFloor;
    private final int numFloors;
    /** The number of remaining mail items to generate */
    private int numRemaining;

    /** The randomNumber generator for sizes **/
    private final Random random;

    /** The types of mail that can be generated **/
    private final MailItem.MailType[] types;
    /** The priorities of mail that can be generated **/
    private final MailItem.MailPriority[] priorities;


    /** Integer representations of base mail sizes for generation */
    private static final int LETTER = 1;
    private static final int PARCEL = 5;
    private static final int PACKAGE = 10;
    private static final int SIZE_SPAN = 4;
    private static final int FIXED_SEED = 12345;


    /**
     * Create a mail generator with the constraints of the building that is being simulated.
     * Assumes at all times all floors within this range are deliverable.
     * @param minFloor the minimum floor (basements are represented by negative floors)
     * @param maxFloor the maximum floor in the building
     * @param priorities the priorities that we can select from when generating mail
     * @param types the types we can select from when generating mail
     * @param maxNumMail the number of mail items to generate
     * @param predictable Whether the mail generation should be predictable (i.e. use a fixed seed) or not
     */
    public SimpleMailGenerator(int minFloor, int maxFloor, MailItem.MailPriority[] priorities,
                               MailItem.MailType[] types, int maxNumMail, boolean predictable){
        this.numFloors = maxFloor - minFloor + 1;
        this.minFloor = minFloor;
        this.types = types;
        this.priorities = priorities;
        if (predictable){
            this.random = new Random(FIXED_SEED);
        } else {
            this.random = new Random();
        }
        this.numRemaining = maxNumMail;
    }

    /* Mail Source Methods */

    @Override
    public boolean hasNextMail() {
        return this.numRemaining > 0;
    }

    @Override
    public MailItem nextItem() throws SourceExhaustedException {
        if(this.hasNextMail()){
            this.numRemaining -= 1;
            return generateMail();
        } else {
            throw new SourceExhaustedException(NAME);
        }
    }

    /**
     * Generates a nem mail item, assigning a random floor, parcel type, priority,
     * size and name based on the building constraints.
     * @return a new mail item with all attributes assigned
     *
     */
    private MailItem generateMail(){
        MailItem.MailType type = generateType();
        int size = generateSize(type);
        int floor = generateFloor();
        String name = generateName();
        MailItem.MailPriority priority = generatePriority();
        return new MailItem(type, priority, floor, name, size);
    }

    /**
     * Generates a valid floor number given building constraints
     * @return a valid floor number
     */
    private int generateFloor(){
        return this.minFloor + this.random.nextInt(this.numFloors);
    }

    /**
     * Generates a name for the mail addresse field
     * @return the addressee's name
     */
    private String generateName(){
        return "Mat";
    }

    /**
     * Generates a random priority from the allowed lists of priorities
     * @return a mail priority
     */
    private MailItem.MailPriority generatePriority(){
        int index = this.random.nextInt(this.priorities.length);
        return this.priorities[index];
    }

    /**
     * Generates a random mail type from the allowed lists of priorities
     * @return a mail type
     */
    private MailItem.MailType generateType(){
        int index = this.random.nextInt(this.types.length);
        return this.types[index];
    }

    /**
     * Generates an appropriate mail size based on the type of mail and
     * the constraints specifies as constants
     * @param type the type of mail
     * @return a valid mail size for that type
     */
    private int generateSize(MailItem.MailType type){
        int base_size=0;
        // Extract the base size
        switch (type){
            case Letter:
                base_size = LETTER;
                break;
            case Parcel:
                base_size = PARCEL;
                break;
            case Package:
                base_size = PACKAGE;
                break;
        }
        // Add a random span and return
        return base_size + this.random.nextInt(SIZE_SPAN);
    }

}

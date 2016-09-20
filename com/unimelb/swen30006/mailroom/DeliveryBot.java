/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;
import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;
import com.unimelb.swen30006.mailroom.exceptions.UnknownIdentifierException;

import java.util.ArrayList;

/**
 * A Simple Delivery bot, that picks a box using a Selection Strategy and then delivers that box to all floors.
 * It will report back its statistics for time taken to deliver boxes after all deliveries are made.
 */
public class DeliveryBot implements Stepable {
    /** The constant time taken to travel a floor in the elevator */
    private static final int TRAVEL_TIME = 2;
    /** The constant time taken to delivery all packages to a given floor */
    private static final int DELIVERY_TIME = 1;

    /** The deliveries that have been made so far */
    private ArrayList<DeliveryStatistic> stats;
    /** The floor that we are on currently */
    private int currentFloor;
    /** Total time, numPackages and floors taken so far */
    private int totalTime;
    private int numPackages;
    private int numFloors;

    /** The current box we are delivering to */
    private StorageBox currentDelivery;

    /** The selection strategy to use when picking a box */
    private SelectionStrategy selectionStrategy;
    /** The delivery strategy to use when picking a parcel from a box to deliver next */
    private DeliveryStrategy deliveryStrategy;
    /** The storage room that we retrieve our mail from */
    private MailStorage storage;
    /** The floor that the Mail Room is on */
    private int mailFloor;

    /**
     * Create a delivery bot with a chosen strategy for delivery and selection, along with the mail storage repo
     * to pick mail up from and the floor that this mail room is on.
     * @param selectionStrategy the strategy for selecting storage boxes to deliver
     * @param deliveryStrategy the strategy for selecting which floor to deliver to next
     * @param storage the mail storage unit
     * @param mailRoomFloor the floor the mail room is located on
     */
    public DeliveryBot(SelectionStrategy selectionStrategy, DeliveryStrategy deliveryStrategy,
                       MailStorage storage, int mailRoomFloor){
        this.stats = new ArrayList<DeliveryStatistic>();
        this.selectionStrategy = selectionStrategy;
        this.deliveryStrategy = deliveryStrategy;
        this.mailFloor = mailRoomFloor;
        this.storage = storage;
        this.currentFloor = mailRoomFloor;
    }

    @Override
    public boolean canFinish() {
        return (this.currentDelivery == null) && this.storage.isEmpty();
    }

    @Override
    public void step() {
        // See if we're currently delivering something, if so continue it,
        // otherwise look for another one
        if(this.currentDelivery != null){
            stepDelivery();
            if(this.currentDelivery.isEmpty()){
                this.currentDelivery = null;
                finalizeStats();
            }
        } else {
            StorageBox.Summary[] summaries = storage.retrieveSummaries();
            if(summaries.length >= 0) {
                try {
                    String id = this.selectionStrategy.selectNextDelivery(summaries);
                    currentDelivery = this.storage.deliverBox(id);
                    initializeStats();
                } catch (NoBoxReadyException e) {
//                    System.out.println(e);
                } catch (UnknownIdentifierException e) {
                    System.out.println(e);
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Initialize the statistics for a particular run
     */
    private void initializeStats(){
        this.totalTime = 0;
        this.numPackages = 0;
        this.numFloors = 0;
    }

    /**
     * Finalise the statistics for this run and add it to the statistics record.
     */
    private void finalizeStats(){
        // Travel back to the mailroom
        travelFloor(this.mailFloor);
        // Generate statistics
        DeliveryStatistic stat = new DeliveryStatistic(this.numPackages, this.totalTime, this.numFloors);
        this.stats.add(stat);
    }

    /**
     * Step a delivery that is in progress, travelling and delivering to a floor in one step.
     */
    private void stepDelivery() {
        try {
            int destinationFloor = deliveryStrategy.chooseNextFloor(this.currentFloor, this.currentDelivery);
            travelFloor(destinationFloor);
            deliverToFloor(destinationFloor, this.currentDelivery);
        } catch (SourceExhaustedException e){
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * Deliver all packages within a storage box to the floor
     * @param floor the floor we are delivering mail to.
     * @param box the box we are delivering from
     */
    private void deliverToFloor(int floor, StorageBox box){
        // Collect all the mail
        ArrayList<MailItem> remaining = new ArrayList<MailItem>();
        ArrayList<MailItem> deliver = new ArrayList<MailItem>();
        try {
            while(!box.isEmpty()){
                MailItem item = box.popItem();
                if(item.floor==floor){
                    deliver.add(item);
                } else {
                    remaining.add(item);
                }
             }
            // Count the number that we are delivering on this floor
            this.numPackages += deliver.size();
            // Add all the mail that isn't for this floor back into the box
            for(MailItem item : remaining){
                box.addItem(item);
            }
            // Add time to the mailroom
            this.totalTime += DELIVERY_TIME;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Move to a given floor, tracking the simulated time this would take
     * @param floor the floor we want to travel to
     */
    private void travelFloor(int floor){
        // Calculate the number of floors we need to travel to reach our new floor.
        int difference = Math.abs(floor - this.currentFloor);
        // Update the total time and number of floors
        this.numFloors += difference;
        this.totalTime += difference * TRAVEL_TIME;
        this.currentFloor = floor;
    }

    /**
     * Package the statistics list into an array for computation of delivery performance by
     * other systems
     * @return A (possibly empty) array of all statistics for delivery
     */
    public DeliveryStatistic[] retrieveStatistics(){
        return stats.toArray(new DeliveryStatistic[0]);
    }

    /*
       A simple class to hold delivery statistics as a data structure. Is immutable
     */
    public class DeliveryStatistic {
        // Number of packages delivered in this run
        public final int packagesDelivered;
        // Time taken (in steps) to deliver all packages
        public final int timeTaken;
        // The number of floors visited
        public final int numFloors;

        /**
         * Create a delivery statistic
         * @param delivered the number of packages delivered
         * @param time the amount of time take to deliver those packages
         * @param numFloors the number of floors we had to traverse to make all our deliveries
         */
        public DeliveryStatistic(int delivered, int time, int numFloors){
            this.packagesDelivered = delivered;
            this.timeTaken = time;
            this.numFloors = numFloors;
        }

        @Override
        public String toString() {
            return "Delivery Run: " + packagesDelivered + " packages delivered in " +
                    timeTaken + " steps over " + numFloors + " floors.";
        }
    }
}

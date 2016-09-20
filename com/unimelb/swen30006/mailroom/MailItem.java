/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

/**
 * A class to represent a MailItem, to be sorted by the sorting machine according to
 * your given strategy. This class is immutable, that is all values are initialised on
 * creation but cannot be changed, allowing the values to be public.
 */
public class MailItem {

    /** Enumerated types for Mail Items and Mail Priority Levels */
    public enum MailType { Letter, Parcel, Package };
    public enum MailPriority { Low, Medium, High, Urgent };

    /** The package type, allows you to determine the size of the units */
    public final MailType packageType;
    /** The priority level, which determines how important the message is */
    public final MailPriority priority;
    /** The floor that the package is going to */
    public final int floor;
    /** The adressee of the package */
    public final String adressee;
    /** The size of the package */
    public final int size;

    /** Constructor for an immutable MailItem
     * @param type the type of this mail item
     * @param priority the priority of this mail item
     * @param floor the floor this item is being sent to
     * @param adressee the name of the person that the mail is adressed to
     * @param size the size of this package (in mail units)
     */
    public MailItem(MailType type, MailPriority priority, int floor, String adressee, int size){
        this.packageType = type;
        this.priority = priority;
        this.floor = floor;
        this.adressee = adressee;
        this.size = size;
    }

    @Override
    public String toString() {
        return "MailItem{" +
                "packageType=" + packageType +
                ", priority=" + priority +
                ", floor=" + floor +
                ", adressee='" + adressee + '\'' +
                ", size=" + size +
                '}';
    }
}

/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 * Basic exception to handle mail overflow events on storage
 */
public class MailOverflowException extends Exception {

    /**
     * Create a mail overflow exception
     * @param requestedSize the size requested when this exception occured
     * @param remainingSize the actual size available in the box
     */
    public MailOverflowException(int requestedSize, int remainingSize){
        super("Mail Overflow: Tried to fit  " + requestedSize + " units into "
                + remainingSize + " worth of space...");
    }

    /**
     * Create a more generic mail overflow exception when the detailed information is not available
     */
    public MailOverflowException(){
        super("Error: insufficient space remaining in storage for that action");
    }

}

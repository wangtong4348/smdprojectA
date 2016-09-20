/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 * A simple exception to allow delivery bots to recognise when they are better off
 * waiting instead of delivering things.
 */
public class NoBoxReadyException extends Exception {

    /**
     * Create a NoBoxReadyException
     */
    public NoBoxReadyException(){
        super("Error: No box is suitable for delivery at this time");
    }
}

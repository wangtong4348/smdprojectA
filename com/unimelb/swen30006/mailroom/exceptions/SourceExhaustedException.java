/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 * A simple exception to catch errors with the source running out of mail
 */
public class SourceExhaustedException extends Exception {

    /**
     * Create a source exhausted exception
     * @param sourceName the name of the mail source
     */
    public SourceExhaustedException(String sourceName){
        super("Error: " + sourceName + " has run out of mail to sort.");
    }
}

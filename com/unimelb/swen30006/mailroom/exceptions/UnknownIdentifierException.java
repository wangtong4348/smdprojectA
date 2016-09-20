/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 * A simple exception to catch errors with unkown identifiers
 */
public class UnknownIdentifierException extends Exception {

    /**
     * Create an unknown identifier exception with the appropriate id
     * @param identifier the unkown identifier
     */
    public UnknownIdentifierException(String identifier){
        super("Error: unknown identifier " + identifier);
    }
}

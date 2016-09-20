/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 *  A simple wrapper to provide a way to catch duplicate identifiers
 */
public class DuplicateIdentifierException extends Exception {

    /**
     * Create a duplicate identifier exception
     * @param name the identifier used
     */
    public DuplicateIdentifierException(String name){
        super("Error: the identifier " + name + " already exists in this storage system");
    }
}

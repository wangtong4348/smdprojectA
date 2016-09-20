/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.exceptions;

/**
 * A simple wrapper to provide a way to catch popping an item from an empty box
 */
public class BoxEmptyException extends Exception {

    public BoxEmptyException(){
        super("Error: popped item from an empty box.");
    }
}

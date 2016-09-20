/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;

/**
 * An interface to specify a MailSource, acting as a stream of Mail, to hide any
 * generation logic from the program simulation
 */
public interface MailSource {
    /**
     * Check if the given mail source has any remaining mail
     * @return true if mail remains at the source
     */
    boolean hasNextMail();

    /**
     * Retrieve the next MailItem in the incoming source
     * @return the next mail to sort;
     * @throws SourceExhaustedException if no mail left in the incoming source
     */
    MailItem nextItem() throws SourceExhaustedException;
}

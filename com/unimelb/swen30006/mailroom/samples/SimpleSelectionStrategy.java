/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.samples;

import com.unimelb.swen30006.mailroom.SelectionStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;

/**
 * A very simple selection strategy that just picks the first storage box, regardless of
 * how full it is. Will wait if there are no boxes available to deliver or if all boxes are empty.
 */
public class SimpleSelectionStrategy implements SelectionStrategy {
    @Override
    public String selectNextDelivery(StorageBox.Summary[] summaries) throws NoBoxReadyException {
        if (summaries.length != 0) {
        	
            for(StorageBox.Summary summary : summaries){
//                System.out.println(summary);
                if(summary.numItems > 0){
                    return summary.identifier;
                }
            }
        }
        // Otherwise no box is ready
        throw new NoBoxReadyException();
    }
}

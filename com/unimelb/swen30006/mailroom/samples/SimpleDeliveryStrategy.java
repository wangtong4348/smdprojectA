/* SWEN30006 Software Modelling and Design
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.samples;

import java.util.Stack;

import com.unimelb.swen30006.mailroom.DeliveryStrategy;
import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;

/**
 * A very simple implementation of a delivery strategy, doesn't attempt to do
 * anything clever, will simply select highest floor where there is undelivered item 
 * as the delivery zone.
 */
public class SimpleDeliveryStrategy implements DeliveryStrategy {

    @Override
    public int chooseNextFloor(int currentFloor, StorageBox box) throws SourceExhaustedException {
        int destination = -Integer.MIN_VALUE;
        Stack<MailItem> items = new Stack<MailItem>();
        try {
          //run through the box to find the highest floor
        	while(!box.isEmpty()){
                MailItem item = box.popItem();
                if(destination < item.floor){
                	destination = item.floor;
                }
                items.push(item);
            }
        	//add items back to box
        	while(!items.isEmpty()){
                box.addItem(items.pop());
            }
        } catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }

        return destination;
    }

}

package com.unimelb.swen30006.mailroom.tong;

import java.util.UUID;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.SortingStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

public class SortingStrategy1 implements SortingStrategy {

	@Override
    public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
		int MaxWeigth = Integer.MAX_VALUE;
        String BoxID = null;
		
        // Retrieve the summaries of available storage items
		
        StorageBox.Summary[] available = storage.retrieveSummaries();
        //Find the heavest box and put the item in
        if(available.length!=0){
        	for(StorageBox.Summary summary:available){
        		if (summary.remainingUnits<MaxWeigth&&summary.remainingUnits>=item.size){
        			MaxWeigth=summary.remainingUnits;
        			BoxID=summary.identifier;
        		}
        	}
        	if(BoxID!=null){
        		return BoxID;
        	}
        }
        // If we get to here without returning there is no storage box
        // appropriate so let's try create one
        String id = UUID.randomUUID().toString();
        try {
            storage.createBox(id);
        } catch (DuplicateIdentifierException e){
            System.out.println(e);
            System.exit(0);
        }
        return id;
    }

}

package tong;

import java.util.Random;
import java.util.UUID;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.SortingStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

public class SortingStrategy1_bu implements SortingStrategy {
	static int counter=0;
	@Override
	public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
		
		Random rand = new Random();
        StorageBox.Summary[] available = storage.retrieveSummaries();

		if(!storage.isFull()){
			String id = UUID.randomUUID().toString();
	        try {
	        	counter++;
	            storage.createBox(id);
                return id;
	        } catch (DuplicateIdentifierException e){
	            System.out.println(e);
	            System.exit(0);
	        }
		}

		
		int boxno = rand.nextInt(available.length+1);
		int i =0;
		// Retrieve the summaries of available storage items
        for(StorageBox.Summary summary : available){
            if(summary.remainingUnits >= item.size&&i==boxno){	            
            	return summary.identifier;
            }
            i++;
        }

        // If we get to here without returning there is no storage box
        // appropriate so let's try create one
        
        return null;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//
//		
//		boolean finish = false;
//		while (!finish) {
//			boxno = rand.nextInt(available.length);
//			 for(int i =0;i<available.length;i++){
//			 System.out.println("box"+i+": ="+available[i].remainingUnits);
//			 }
//			 System.out.println("item"+item.size);
//			if (available[boxno].remainingUnits >= item.size) {
//				return available[boxno].identifier;
//			}
//		}
//		return null;

	}

}

package tong;

import java.util.Random;
import java.util.UUID;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.SortingStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

public class SortingStrategy1 implements SortingStrategy {
	static int counter=0;
	
	@Override
	public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
		Random rand = new Random();
        int boxno = 0;
        
        
        StorageBox.Summary[] available = storage.retrieveSummaries();
		boxno = rand.nextInt(available.length+1);
        System.out.println(available.length);
        while(true){
    		int i =0;
    		for(StorageBox.Summary summary : available){
                if(summary.remainingUnits >= item.size&&i==boxno){
                    System.out.println(summary.identifier);
                	return summary.identifier;
                }
                i++;
            }
//    		String id= Integer.toString(counter);
//    		counter++;
    		String id = UUID.randomUUID().toString();

            try {
                storage.createBox(id);
//                System.out.println(id);
                return id;
            } catch (DuplicateIdentifierException e){
                System.out.println(e);
                System.exit(0);
            }
            break;
            
			
    	}
		return null;
        
        
    
		
	}
	
}

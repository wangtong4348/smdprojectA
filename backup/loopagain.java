package tong;

import java.util.Random;
import java.util.UUID;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.SortingStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

public class loopagain implements SortingStrategy {

	@Override
	public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
		Random rand = new Random();
        int boxno = 0;

        StorageBox.Summary[] available = storage.retrieveSummaries();
        
        	if(!storage.isFull()){

				String id = UUID.randomUUID().toString();
	            try {
					storage.createBox(id);

					return id;
				} catch (DuplicateIdentifierException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
		            System.exit(0);				}
			}
        	System.out.println(available.length);
        	while(true){
            boxno = rand.nextInt(available.length);
            if(available[boxno].remainingUnits >= item.size){
				return available[boxno].identifier;
			}
				
        	}
		
	}
	
}

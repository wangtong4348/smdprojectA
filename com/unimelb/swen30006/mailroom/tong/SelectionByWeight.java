package com.unimelb.swen30006.mailroom.tong;

import com.unimelb.swen30006.mailroom.MailSource;
import com.unimelb.swen30006.mailroom.SelectionStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;

public class SelectionByWeight implements SelectionStrategy {
	static int i=0;
	int box_no;
	int bot_no;
	MailSource generator;
	public void getInfo(MailSource generator,int box_no,int bot_no){
		this.generator=generator;
		this.box_no=box_no;
		this.bot_no=bot_no;
	}
	@Override
    public String selectNextDelivery(StorageBox.Summary[] summaries) throws NoBoxReadyException {
        if (summaries.length !=0) {
            for(StorageBox.Summary summary : summaries){
            	//Choose the full box to delivery or there is no mail to delivery or skip too many boxes
				//keep the static variable i less than the product of bot number and box number
				// the reason I minus by 1 is to keep safe
            	if(summary.remainingUnits==0||!generator.hasNextMail()||i>=(bot_no)*(box_no-1)){
            		i=0;
               		return summary.identifier;
            	}else{
            		i++;	
            	}
            }
            
        }
        	// Otherwise no box is ready
       throw new NoBoxReadyException();
    }
}

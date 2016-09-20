package com.unimelb.swen30006.mailroom.tong;

import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.MailStorage;
import com.unimelb.swen30006.mailroom.SortingStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.DuplicateIdentifierException;
import com.unimelb.swen30006.mailroom.exceptions.MailOverflowException;

public class SortingStrategy2 implements SortingStrategy {
	int no_floor;
	int no_box;
	int mailBox_floor;
	static int odd = 1;
	static int even = 2;

	/*
	 * This method assign the mail box for the item for medium building If the
	 * item will be delivered to the lower floor, it will be arranged to the
	 * mailbox that with identifier less than the half of the mail box number
	 * Other wise, it will be assigned to the mail box with identifier in the
	 * range between 0.5* mailbox number and mail box number
	 */
	@Override
	public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
		StorageBox.Summary[] available = storage.retrieveSummaries();
		String id = null;
		for (StorageBox.Summary summary : available) {
			if (item.floor <= mailBox_floor && Integer.parseInt(summary.identifier) < 0.5 * no_box
					&& summary.remainingUnits >= item.size) {
				return summary.identifier;
			} else if (item.floor > mailBox_floor && Integer.parseInt(summary.identifier) >= 0.5 * no_box
					&& summary.remainingUnits >= item.size) {
				return summary.identifier;
			}
		}

		if (item.floor <= 0.5 * no_floor) {
			id = Integer.toString(odd);
			odd = odd + 2;
		} else {
			id = Integer.toString(even);
			even = even + 2;
		}
		try {
			storage.createBox(id);

		} catch (DuplicateIdentifierException e) {
			System.out.println(e);
			System.exit(0);
		}
		return id;
	}

	public void getbuilding_info(int no_floor, int no_box, int mailBox_floor) {
		this.no_box = no_box;
		this.no_floor = no_floor;
		this.mailBox_floor = mailBox_floor;
	}
}

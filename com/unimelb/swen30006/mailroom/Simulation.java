/* SWEN30006 Software Modelling and Design 
 * Project 1 - Mailroom Blues
 * Author: Mathew Blair <mathew.blair@unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import java.util.ArrayList;
import java.util.Arrays;

import com.unimelb.swen30006.mailroom.samples.SimpleDeliveryStrategy;
import com.unimelb.swen30006.mailroom.samples.SimpleMailGenerator;
import com.unimelb.swen30006.mailroom.samples.SimpleMailStorage;

import com.unimelb.swen30006.mailroom.tong.BuildingInfo;
import com.unimelb.swen30006.mailroom.tong.SelectionByWeight;
import com.unimelb.swen30006.mailroom.tong.SortingStrategy1;
import com.unimelb.swen30006.mailroom.tong.SortingStrategy2;

/**
 * A basic driver program to instantiate an instance of the MailSorter with an
 * ineffective strategy and the random mail generator.
 */
public class Simulation {

	public static void main(String args[]) {
		// Create the appropriate strategies
		BuildingInfo building = null;
		SortingStrategy sortStrategy = null;
		SelectionStrategy selectionStrategy = new SelectionByWeight();
		DeliveryStrategy deliveryStrategy = new SimpleDeliveryStrategy();
		if (inargs(args, "large_building")) {
			building = new BuildingInfo("large_building");
//			sortStrategy = new SimpleSortingStrategy();
			sortStrategy = new SortingStrategy1();
		} else if (inargs(args, "medium_building")) {
			building = new BuildingInfo("medium_building");
			sortStrategy = new SortingStrategy2();
			((SortingStrategy2) sortStrategy).getbuilding_info(
					building.getMAX_FLOOR(),
					building.getMAX_BOXES(),
					building.getMAIL_ROOM_LEVEL());
		} else if (inargs(args, "small_building")) {
			building = new BuildingInfo("small_building");
			sortStrategy = new SortingStrategy1();
		}
		// Extract whether to print detailed runs or not
		boolean printDetailed = inargs(args, "detailed");
		// Extract whether to gnereate random seed or not
		boolean random = !inargs(args, "random");
		// Run the simulation with the appropriate arguments
		runSimulation(building.getMIN_FLOOR(), building.getMAX_FLOOR(), building.getNUM_MAIL(), building.getMAX_BOXES(),
				building.getMAX_MAIL_UNITS(), building.getNUM_BOTS(), building.getMAIL_ROOM_LEVEL(), random,
				selectionStrategy, deliveryStrategy, sortStrategy, printDetailed, building.getNUM_RUNS());
	}

	/**
	 * A method to run a simulation given a set of parameters and strategies.
	 * Will handle running the multiple simulation runs and averaging the
	 * results.
	 * 
	 * @param minFloor
	 *            the minimum floor on the building
	 * @param maxFloor
	 *            the maxium floor on the building
	 * @param numMail
	 *            the number of mail items to simulation
	 * @param maxBoxes
	 *            the number of boxes allowed in the storage unit
	 * @param maxMailUnits
	 *            the size of each of the boxes in the storage unit (in mail
	 *            units)
	 * @param numBots
	 *            the number of delivery bots servicing the building
	 * @param mailLevel
	 *            the level of the building that the mail room operates on
	 * @param predictable
	 *            whether to use predictable (fixed seed) mail generation or
	 *            not. Setting this value to false will use random seeds for
	 *            each run. Setting it to true will result in the same values
	 *            for each run.
	 * @param selectionStrategy
	 *            the selection strategy to use for this simulation
	 * @param deliveryStrategy
	 *            the delivery strategy to use for this simulation
	 * @param sortingStrategy
	 *            the sorting strategy to use for this simulation
	 * @param printDetailed
	 *            whether or not you want the detailed output for each run. If
	 *            true the console output will be very verbose.
	 * @param numRuns
	 *            The number of simulation runs for this experiment. Will
	 *            average the results over this many runs.
	 */
	private static void runSimulation(int minFloor, int maxFloor, int numMail, int maxBoxes, int maxMailUnits,
			int numBots, int mailLevel, boolean predictable, SelectionStrategy selectionStrategy,
			DeliveryStrategy deliveryStrategy, SortingStrategy sortingStrategy, boolean printDetailed, int numRuns) {

		// Setup variables for the simulation
		double totalTime = 0;
		double totalFloors = 0;
		double numDeliveries = 0;

		// Print detailed header if required
		if (printDetailed) {
			System.out.println("==========    DETAILED RUNS    ==========");
		}

		// Run the required number of simulations
		for (int i = 0; i < numRuns; i++) {
			// Setup Mail Generator
			MailItem.MailPriority[] priorities = MailItem.MailPriority.values();
			MailItem.MailType[] types = MailItem.MailType.values();
			MailSource generator = new SimpleMailGenerator(minFloor, maxFloor, priorities, types, numMail, predictable);

			// Setup storage
			MailStorage storage = new SimpleMailStorage(maxBoxes, maxMailUnits);
			//make sure it is also can be used for other sorting strategy
			if(selectionStrategy.getClass()== new SelectionByWeight().getClass()){
				((SelectionByWeight) selectionStrategy).getInfo(generator,maxBoxes,numBots);
			}
			// Setup MailSorter
			MailSorter sorter = new MailSorter(generator, storage, sortingStrategy);

			// Create the deliver bots
			DeliveryBot bots[] = new DeliveryBot[numBots];
			for (int k = 0; k < numBots; k++) {
				bots[k] = new DeliveryBot(selectionStrategy, deliveryStrategy, storage, mailLevel);
			}

			// Run the simulation
			boolean finished = false;

			while (!finished) {
				// Update the sorter
				sorter.step();

				// Update all the delivery bots
				boolean anyBotBlocking = false;
				
				//Update all the bots when the total available box are more then the number of bots
					for (int b = 0; b < numBots; b++) {
						bots[b].step();
						anyBotBlocking = !bots[b].canFinish() || anyBotBlocking;
					}

					// Check if we are finished
					finished = sorter.canFinish() && !anyBotBlocking;

			}
			// Retrieve statistics
			ArrayList<DeliveryBot.DeliveryStatistic> stats = new ArrayList<DeliveryBot.DeliveryStatistic>();
			for (int j = 0; j < numBots; j++) {
				DeliveryBot.DeliveryStatistic[] botStats = bots[j].retrieveStatistics();
				stats.addAll(Arrays.asList(botStats));
			}

			// Calculate averages and totals
			for (DeliveryBot.DeliveryStatistic stat : stats) {
				totalTime += stat.timeTaken;
				totalFloors += stat.numFloors;
			}

			// Calculate statistics
			numDeliveries += stats.size();
			if (printDetailed) {
				System.out.println("======   Completed Run Number " + i + "    ======");

				for (DeliveryBot.DeliveryStatistic stat : stats) {
					System.out.println(stat);
				}
				System.out.println("=========================================");
			}
		}

		// Average the results
		totalFloors = totalFloors / (double) numRuns;
		totalTime = totalTime / (double) numRuns;
		numDeliveries = numDeliveries / (double) numRuns;

		// Print the results
		System.out.println("========== SIMULATION COMPLETE ==========");
		System.out.println("");
		System.out.println("Delivered: " + numMail + " packages");
		System.out.println("Total Delivery Runs: " + numDeliveries);
		System.out.println("Total Time Taken: " + totalTime);
		System.out.println("Total Delivery Bots: " + numBots);
		System.out.println("Average Time Per Bots: " + totalTime / (double) numBots);
		System.out.println("Average Num Floors: " + totalFloors / (double) numDeliveries);
		System.out.println("Average Num Packages: " + numMail / (double) numDeliveries);
		System.out.println("");
	}

	/*
	 * This method is for checking wheather the string is in the list of strings
	 * 
	 * @param args is the list of string which is received from the inline
	 * arguments
	 * 
	 * @param check : check whether it is in the args
	 */
	private static boolean inargs(String args[], String check) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(check)) {
				return true;
			}
		}
		return false;
	}
}
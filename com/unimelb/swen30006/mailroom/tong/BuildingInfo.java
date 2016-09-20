package com.unimelb.swen30006.mailroom.tong;

/**
 * Created by com.unimelb.swen30006.mailroom.tong on 7/08/16.
 */
public class BuildingInfo {
    private int MIN_FLOOR;
    private int MAX_FLOOR;
    private int NUM_MAIL ;

    // Constants for our mail storage unit
    private int MAX_BOXES;
    private int MAX_MAIL_UNITS;

    // The floor on which the mailroom resides
    private int MAIL_ROOM_LEVEL;

    // The number of delivery bots
    private int NUM_BOTS;

    // The default number of simulations
    private int NUM_RUNS;

    public BuildingInfo(String arg) {
        NUM_MAIL = 1000;
        NUM_RUNS = 10;
        if(arg.equals("large_building")) {
            MIN_FLOOR = 1;
            MAX_FLOOR = 200;
            MAX_BOXES = 45;
            MAX_MAIL_UNITS = 25;
            MAIL_ROOM_LEVEL = 2;
            NUM_BOTS = 20;
        }else if(arg.equals("medium_building")){
            MIN_FLOOR = 1;
            MAX_FLOOR = 50;
            MAX_BOXES = 8;
            MAX_MAIL_UNITS = 35;
            MAIL_ROOM_LEVEL = 20;
            NUM_BOTS = 8;
        }else if(arg.equals("small_building")){
            MIN_FLOOR = 1;
            MAX_FLOOR = 10;
            MAX_BOXES = 30;
            MAX_MAIL_UNITS = 40;
            MAIL_ROOM_LEVEL = 10;
            NUM_BOTS = 1;
        }
    }

    public int getMIN_FLOOR() {
        return MIN_FLOOR;
    }

    public int getMAX_FLOOR() {
        return MAX_FLOOR;
    }

    public int getNUM_MAIL() {
        return NUM_MAIL;
    }

    public int getMAX_BOXES() {
        return MAX_BOXES;
    }

    public int getMAX_MAIL_UNITS() {
        return MAX_MAIL_UNITS;
    }

    public int getMAIL_ROOM_LEVEL() {
        return MAIL_ROOM_LEVEL;
    }

    public int getNUM_BOTS() {
        return NUM_BOTS;
    }

    public int getNUM_RUNS() {
        return NUM_RUNS;
    }
}

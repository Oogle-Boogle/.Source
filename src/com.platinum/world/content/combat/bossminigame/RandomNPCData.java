package com.platinum.world.content.combat.bossminigame;

import com.platinum.util.Misc;

/**
 /@Author Flub
 */
public class RandomNPCData { //CHANGE NPC'S IN HERE.. THESE ARE NPC ID'S
    //TODO Change type to Npc[]

    public static final int[] firstWaveIDs = {8949, 9231, 6208}; //dragon tier
    public static final int[] secondWaveIDs = {6595, 6599, 6603}; //mage tier (I have removed 8349)
    public static final int[] thirdWaveIDs = {9, 6313, 6314}; //melee tier (I have removed 5666)
    public static final int[] fourthWaveIDs = {9864, 175}; //mage tier (I have removed 2883)
    public static final int[] fifthWaveIDs = {176}; //boss tier

    public static int randomFirstWaveID() {
        //System.out.println("RANDOM NPC 1 SELECTED");
        return (firstWaveIDs[Misc.getRandom(firstWaveIDs.length - 1)]);
    }

    public static int randomSecondWaveID() {
        //System.out.println("RANDOM NPC 2 SELECTED");
        return (secondWaveIDs[Misc.getRandom(secondWaveIDs.length - 1)]);
    }

    public static int randomThirdWaveID() {
        //System.out.println("RANDOM NPC 3 SELECTED");
        return (thirdWaveIDs[Misc.getRandom(thirdWaveIDs.length - 1)]);
    }

    public static int randomFourthWaveID() {
        //System.out.println("RANDOM NPC 4 SELECTED");
        return (fourthWaveIDs[Misc.getRandom(fourthWaveIDs.length - 1)]);
    }

    public static int randomFifthWaveID() {
        //System.out.println("RANDOM NPC 5 SELECTED");
        return (fifthWaveIDs[Misc.getRandom(fifthWaveIDs.length - 1)]);
    }

}

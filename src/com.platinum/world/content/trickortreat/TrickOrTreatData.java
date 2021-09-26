package com.platinum.world.content.trickortreat;

import com.platinum.model.Item;
import com.platinum.model.Position;
import com.platinum.world.entity.impl.npc.NPC;
import lombok.AllArgsConstructor;

public class TrickOrTreatData {

    /**
     * Storing the data that can be selected
     **/
    @AllArgsConstructor
    public enum LocationData {

        /**
         * teleportPos = Where the player is teleported to
         * doorPos = Where the door object is spawned
         * doorID = The Object ID of the door that we're going to spawn
         * doorDirection = The 'face' of the door
         * walkToDoor = Where the NPC walks when giving a treat
         * npc = The NPC to spawn on the wave
         * clue = The global message sent to all players
         */

        BURTHORPE(new Position(2926, 3559), new Position(44, 3156), 5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        CAMELOT(new Position(2758, 3479), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        CANIFIS(new Position(3493,3483), new Position(3497, 3502),3, "South",  new Position(3497, 3503), new NPC(5923, new Position(3502, 3504)), "Near the Bla bla"),
        FALADOR(new Position(2965,3380), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        FRANKENSTEIN(new Position(3549, 3530), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        MORTTON(new Position(3488, 3283), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        RELLEKKA(new Position(2660, 3660), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        VARROCK(new Position(3213, 3425), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        YANILLE(new Position(2594, 3100), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        ZANARIS(new Position(2660, 3660), new Position(44, 3156),5244, "North",  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla");

        public Position teleportPos;
        public Position doorPos;
        public int doorID;
        public String doorDirection;
        public Position walkToDoor;
        public NPC npc;
        public String clue;

    }

    public static long timeUntilSpawn = 0;

    public static final int spawnDelay = 600000;

    public static void resetTimer() {
        timeUntilSpawn = System.currentTimeMillis() + spawnDelay;
    }

    public static final int portalID = 38150;

    public static Item[] shitTreats = {new Item (123,1), new Item(123, 2)};;

    public static Item[] mediumTreats = {new Item (123,1), new Item(123, 2)};

    public static Item[] rareTreats = {new Item (123,1), new Item(123, 2)};

    public static Item[] superRareTreats = {new Item (123,1), new Item(123, 2)};

    public static Item[] tricks = {new Item(526, 1), new Item(592, 1)};

    public static int[] transformableNpcIDs = {4928, 6099, 5917, 3425, 1697, 2862};

    public static final int sweets = 4563;

}

package com.platinum.world.content.trickortreat;

import com.platinum.engine.task.impl.WalkToTask;
import com.platinum.model.Position;
import com.platinum.util.Misc;
import com.platinum.world.entity.impl.npc.NPC;
import lombok.AllArgsConstructor;

public class TrickOrTreatData {

    /**
     * Storing the data that can be selected
     **/
    @AllArgsConstructor
    public enum LocationData {

        BURTHORPE(new Position(2926, 3559), new Position(44, 3156), 5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        CAMELOT(new Position(2758, 3479), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        CANIFIS(new Position(3493,3483), new Position(3497, 3503),5244, 2,  new Position(3497, 3503), new NPC(5923, new Position(3502, 3504)), "Near the Bla bla"),
        FALADOR(new Position(2965,3380), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        FRANKENSTEIN(new Position(3549, 3530), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        MORTTON(new Position(3488, 3283), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        RELLEKKA(new Position(2660, 3660), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        VARROCK(new Position(3213, 3425), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        YANILLE(new Position(2594, 3100), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla"),
        ZANARIS(new Position(2660, 3660), new Position(44, 3156),5244, 0,  new Position(44, 3156), new NPC(5923, new Position(2342, 2342)), "Near the Bla bla");

        public Position teleportPos; // Where the player is teleported to
        public Position doorPos;
        int doorID;
        int doorDirection;
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

}

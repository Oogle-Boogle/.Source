package com.platinum.world.content.trickortreat;

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

        LOCATION_1(new Position(22, 3156), new Position(77, 3156), new NPC(53, new Position(2342, 2342)), "Near the bla bla"),
        LOCATION_2(new Position(33, 3156), new Position(88, 3156), new NPC(53, new Position(2342, 2342)), "Near the Bla bla"),
        LOCATION_3(new Position(44, 3156), new Position(99, 3156), new NPC(53, new Position(2342, 2342)), "Near the Bla bla"),
        LOCATION_4(new Position(55, 3156), new Position(44, 3156), new NPC(53, new Position(2342, 2342)), "Near the Bla bla"),
        LOCATION_5(new Position(66, 3156), new Position(55, 3156), new NPC(53, new Position(2342, 2342)), "Near the Bla bla");

        public Position teleportPos; // Where the player is teleported to
        public Position housePos;
        public NPC npc;
        public String clue;
    }

    public static LocationData currentLocation;

    public static void pickNextLocation() {
        currentLocation = Misc.randomEnum(LocationData.class);
    }
}

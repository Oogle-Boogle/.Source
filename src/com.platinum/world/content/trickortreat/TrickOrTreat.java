package com.platinum.world.content.trickortreat;

import com.platinum.model.GameObject;
import com.platinum.world.World;
import com.platinum.world.content.CustomObjects;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class TrickOrTreat {

    public static TrickOrTreatData.LocationData currentLocation = null;

    public static void pickNextLocation() {
        if (currentLocation != null) {
            GameObject door = new GameObject(currentLocation.doorID, currentLocation.doorPos.copy(), 10, 3);
            World.deregister(currentLocation.npc);
            CustomObjects.deleteGlobalObject(door);
        }


        //currentLocation = Misc.randomEnum(TrickOrTreatData.LocationData.class);
        currentLocation = TrickOrTreatData.LocationData.CANIFIS;
        GameObject door = new GameObject(currentLocation.doorID, currentLocation.doorPos.copy(), 10, 3);
        CustomObjects.spawnGlobalObject(door);
        World.register(currentLocation.npc);
    }

    public void handleTimer() {

        if (System.currentTimeMillis() > TrickOrTreatData.timeUntilSpawn) {
            pickNextLocation();
            TrickOrTreatData.resetTimer();
        }
    }

}

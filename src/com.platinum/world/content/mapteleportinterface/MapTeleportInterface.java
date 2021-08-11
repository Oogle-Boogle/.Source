package com.platinum.world.content.mapteleportinterface;

import com.platinum.model.Position;
import com.platinum.world.content.transportation.TeleportHandler;
import com.platinum.world.content.transportation.TeleportType;
import com.platinum.world.entity.impl.player.Player;


public class MapTeleportInterface {

    public static boolean processButton(Player p, int ButtonID) {

        final int VIEWING_ORB = 9391;
        final int MAP_INTERFACE_ID = 23500;

        /** Handles clicking the viewing orb **/
        if (ButtonID == VIEWING_ORB)
            p.getPacketSender().sendInterface(MAP_INTERFACE_ID);

        /** Handles teleporting based on the button clicked **/
        TeleportType tpType = p.getSpellbook().getTeleportType();
        Position POS;

        for (int i = 0; i < MapInterfaceData.values().length; i++) {
            if (ButtonID == MapInterfaceData.values()[i].getButtonID()){
                POS = MapInterfaceData.values()[i].getPOS();
                TeleportHandler.teleportPlayer(p, POS, tpType);
            }
        }

        return false;
    }
}

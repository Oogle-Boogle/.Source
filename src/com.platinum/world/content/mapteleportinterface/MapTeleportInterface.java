package com.platinum.world.content.mapteleportinterface;

import com.platinum.model.Position;
import com.platinum.world.content.transportation.TeleportHandler;
import com.platinum.world.content.transportation.TeleportType;
import com.platinum.world.entity.impl.player.Player;

import static sun.audio.AudioPlayer.player;


public class MapTeleportInterface {

    public static final int VIEWING_ORB = 9391;
    public static final int MAP_INTERFACE_ID = 23500;

    public static boolean processButton(Player p, int ButtonID) {


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

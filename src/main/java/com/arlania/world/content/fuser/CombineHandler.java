package com.arlania.world.content.fuser;

import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;

public class CombineHandler {

    public static void openInterface(CombineEnum combine, Player player){
        player.combineIndex = combine.ordinal();
        player.combiner = 0;
        resetInterface(player);
        fillList(player);
        fillInterface(combine,player);
        player.getPacketSender().sendInterface(43500);
    }

    public static void resetInterface(Player player){

        int interfaceId = 43513;
        for(int i = 0; i < 20; i++) {
            player.getPacketSender().sendItemOnInterface(interfaceId,0,0);
            interfaceId++;
        }
        player.getPacketSender().sendItemOnInterface(43537,0,0);

         interfaceId = 43502;
        for(int i = 0; i < 10; i++) {
            player.getPacketSender().sendString(interfaceId,"");
            interfaceId++;
        }
        player.getPacketSender().sendString(43542,"");

    }

    public static void fillList(Player player){
        int interfaceId = 53301;
        for(CombineEnum combine : CombineEnum.values()) {
            player.getPacketSender().sendString(interfaceId,""+ItemDefinition.forId(combine.getEndItem()).getName());
            interfaceId++;
        }
    }

    public static void fillInterface(CombineEnum combineEnum, Player player){

        int interfaceId = 43513;
        for(int i = 0; i < combineEnum.requirements.length; i++) {
            player.getPacketSender().sendItemOnInterface(interfaceId,combineEnum.requirements[i].getId(),combineEnum.requirements[i].getAmount());
            interfaceId++;
        }

       // player.getPacketSender().sendString(43542,"Fusion success rate: @mag@"+(combineEnum.getChance()
        	//s	+player.getRights().getLuckChance())+"%");

        player.getPacketSender().sendItemOnInterface(43537,combineEnum.getEndItem(),1);

    }
}

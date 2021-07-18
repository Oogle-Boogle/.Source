package com.platinum.world.content.collectionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.platinum.model.definitions.NPCDrops;
import com.platinum.model.definitions.NpcDefinition;
import com.platinum.model.definitions.NPCDrops.DropChance;
import com.platinum.util.Misc;
import com.platinum.world.entity.impl.player.Player;
import lombok.var;


public class CollectionLogInterface {
    private List<Integer> currentlyViewing = new ArrayList<>();

    public CollectionLogInterface(Player player) {
        this.player = player;
    }

    public void open() {
        initialiseCurrentlyViewing();
        sendBossNames();
        sendNpcData(0);
        player.getPA().sendInterface(30360);
    }

    private void sendBossNames() {
        int[] startingLine = new int[]{30560};
        currentlyViewing.forEach(entry -> {
            player.getPA().sendString(startingLine[0]++, "" + NpcDefinition.forId(entry).getName());
        });
    }

    private void initialiseCurrentlyViewing() {
        currentlyViewing.clear();
        for (int entry : NPC_LIST) {
            currentlyViewing.add(entry);
        }
    }

    public void search(String name) {
        initialiseCurrentlyViewing();
        var tempList = new ArrayList<Integer>();
        for (int data : currentlyViewing) {
            if (Objects.nonNull(NpcDefinition.forId(data))) {
                if (!NpcDefinition.forId(data).getName().toLowerCase().contains(name.toLowerCase()))
                    tempList.add(data);
            } else {
                tempList.add(data);
            }
        }
        currentlyViewing.removeAll(tempList);

        for (int i = 0; i < 100; i++) {
            player.getPacketSender().sendString(30560 + i, "");
        }
        sendBossNames();
    }

    private boolean hasObtainedItem(int npc, int item) {
        return player.getCollectionLogData()
                .stream()
                .filter(data -> data.getNpcId() == npc && data.getItem() == item)
                .findFirst()
                .isPresent();
    }

    private void sendNpcData(int index) {
        var definition = NpcDefinition.forId(currentlyViewing.get(index));
        player.getPacketSender().sendNpcOnInterface(30367, definition.getId());

        player.getPacketSender().resetItemsOnInterface(30375, 20);
        player.getPacketSender().sendString(30369, "Killcount: " +
                Misc.insertCommasToNumber(String.valueOf(player.getNpcKillCount(definition
                        .getId()))));


        var drops = NPCDrops.forId(definition.getId());
        var slot = 0;
        for (var npcDrop : drops.getDropList()) { //smaller and equal too means very common, and always,
            if (npcDrop.getChance().ordinal() < DropChance.LEGENDARY.ordinal()) {
                continue; //now this is like this bc there is no drops, 1 sec
            }
            if (hasObtainedItem(definition.getId(), npcDrop.getId())) {
                var item = player.getCollectionLogData()
                        .stream()
                        .filter(data -> data.getNpcId() == definition.getId() && data.getItem() == npcDrop
                                .getId())
                        .findFirst()
                        .get();
                player.getPacketSender()
                        .sendItemOnInterface(30375, item.getItem(), slot++, item.getAmount());
            } else {
                player.getPacketSender().sendItemOnInterface(30375, npcDrop.getId(), slot++, 0);
            }
        }
    }

    public boolean handleButton(int buttonId) {
        if (!(buttonId >= 30560 && buttonId <= 30760)) {
            return false;
        }
        int index = -30560 + buttonId; //1sec
        if (currentlyViewing.size() > index) { //i do though, has to be as clean as possible ; here are some free lines
            sendNpcData(index); //i got it 1 sec
        }
        return true;
    } //these the right npcs? ohh shit i didnt see this one i did this one re run it

    private final int[] NPC_LIST = new int[]{420, 842, 174, 3767, 51, 2783, 17, 422, 3263, 15, 1982, 9994, 9932, 224, 1999, 16, 9993, 9277, 9944, 9273, 9903, 8133,
            9247, 8493, 9203, 172, 9935, 170, 169, 219, 12239, 3154, 33, 1684, 5957, 5958, 5959, 185, 6311};
    private final Player player;
}
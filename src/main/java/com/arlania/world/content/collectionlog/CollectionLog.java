package com.arlania.world.content.collectionlog;

import com.arlania.model.Item;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.model.definitions.NPCDrops.DropChance;
import com.arlania.model.definitions.NPCDrops.NpcDropItem;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author Suic
 * @Since 18.03.2020
 */
@RequiredArgsConstructor
public class CollectionLog {
	private final Player player;
    
    /*
     * Contains NPC Id's that should work with the collection log You can change these as
     */		private List<NpcDefinition> npcs = new ArrayList<>();
    		private final int[] NPC_LIST = new int[] {420, 842, 174, 3767, 51, 2783, 17, 422, 3263, 15, 1982, 9994, 9932, 224, 1999, 16, 9993, 9277, 9944, 9273, 9903, 8133,
    				9247, 8493, 9203, 172, 9935, 170, 169, 219, 12239, 3154, 33, 1684, 5957, 5958, 5959, 185, 6311};
    		private int textStart = 30560;
    
    /*
     * Handles first opening the interface
     */
    public void open(Player player) {
        player.getPacketSender().sendInterface(30360);
        initNpcList();
        textStart = 30560; 
        npcs.forEach(npc -> {
            player.getPacketSender().sendString(textStart++, "" +npc.getName());
        });
        
        sendNpcData(player, 0);
        
    }
    
    
    /*
     * Sends all collection log info too the interface
     */
    private void sendNpcData(Player player, int index) {
        NpcDefinition npc = npcs.get(index);
        int id = npc.getId();
        player.getPacketSender().sendNpcOnInterface(30367,id);
        player.getPacketSender().sendNpcOnInterface(id,30367);
        player.getPacketSender().resetItemsOnInterface(30375, 20);
        player.getPacketSender().sendString(30369, "Killcount: " + player.getNPCKILLCount());
       NPCDrops drops = NPCDrops.forId(id);
        int slot = 0;
        List<Integer> items = new ArrayList<>();
        for (NpcDropItem drop : drops.getDropList()) {
            int item = drop.getId();
            if (items.contains(item)) {
                continue;
            }

            if (drop.getChance().ordinal() > DropChance.RARE.ordinal()) { 
                player.getPacketSender().sendItemOnInterface(30375, item, slot++, getCollectedAmount(player, id, item));
                items.add(item);
            }
        }
        if (!items.isEmpty()) {
            player.getPacketSender().sendCollectedItems(getCollectedItems(player, id, items));
        }
    }

    /*
     * Useless
     */
    private int getCollectedAmount(Player player, int bossId, int itemId) {
        if (player.getCollectedItems().isEmpty() || player.getCollectedItems().get(bossId) == null || player.getCollectedItems().get(bossId).get(itemId) == null) {
            return 1;
        }
        return player.getCollectedItems().get(bossId).get(itemId);
    }
    
    /*
     * Useless
     */
    private List<Integer> getCollectedItems(Player player, int id, List<Integer> drops) {
        List<Integer> collectedItems = new ArrayList<>();
        if (player.getCollectedItems().get(id) == null) {
            return Collections.emptyList();
        }
        Map<Integer, Integer> currentCollections = player.getCollectedItems().get(id);
        for (int drop : drops) {
            if (currentCollections.containsKey(drop)) {
                collectedItems.add(drop);
            }
        }
        return collectedItems;
    }

    private void initNpcList() {
        npcs.clear();
        for (int npcId : NPC_LIST) {
        	npcs.add(NpcDefinition.forId(npcId));
        }
    }

    public void search(Player player, String name) {
        initNpcList();
        npcs.removeIf(def -> !def.getName().toLowerCase().contains(name.toLowerCase()));

        textStart = 30560;
        for(int i = 0; i < 100; i++) {
            player.getPacketSender().sendString(textStart + i, "");
        }
        npcs.forEach(npc -> {
            player.getPacketSender().sendString(textStart++,  npc.getName());
        });
    }

    public boolean handleButton(Player player, int buttonId) {
        if (!(buttonId >= 30560 && buttonId <= 30760)) {
            return false;
        }
        int index = -30560 + buttonId;
        if (npcs.size() > index) {
            sendNpcData(player, index);
        }
        return true;
    }
}

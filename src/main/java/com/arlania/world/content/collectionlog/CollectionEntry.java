package com.arlania.world.content.collectionlog;

import com.arlania.world.content.skill.impl.scavenging.ScavengeGain;
import com.arlania.world.entity.impl.player.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

@AllArgsConstructor
@Getter
@Setter
public class CollectionEntry {
    private int npcId;
    private int item;
    private int amount;

    public void submit(Player player) {
    	if(player.getCollectionLogData().stream().filter(data -> data.npcId == npcId && data.item == item).findAny().isPresent()) {
    		var edit = player.getCollectionLogData().stream().filter(data -> data.npcId == npcId && data.item == item).findFirst().get();
    		player.getCollectionLogData().stream().filter(data -> data.npcId == npcId && data.item == item).findFirst().get().setAmount(edit.getAmount() + 1);
    		return;
    	}
        player.getCollectionLogData().add(this);
        ScavengeGain.Collection(player);
    }
}

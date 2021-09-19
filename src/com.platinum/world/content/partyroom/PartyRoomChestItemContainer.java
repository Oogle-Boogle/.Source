package com.platinum.world.content.partyroom;

import com.platinum.model.Item;
import com.platinum.model.container.ItemContainer;
import com.platinum.model.container.StackType;
import com.platinum.util.Misc;
import com.platinum.world.entity.impl.player.Player;

public class PartyRoomChestItemContainer extends ItemContainer {


    private static final int MAIN_CONTAINER_SIZE = 216;

    @Override
    public int capacity() {
        return MAIN_CONTAINER_SIZE;
    }

    @Override
    public StackType stackType() {
        return StackType.DEFAULT;
    }

    @Override
    public ItemContainer refreshItems() {
        return null;
    }

    @Override
    public ItemContainer full() {
        return null;
    }

    public Item getRandom() {
        Item[] item = getValidItemsArray();
        Item reward = item[Misc.random(item.length - 1)];
        int amount = 1;
        if (reward.getDefinition().isStackable()) {
            if (reward.getAmount() > 1) {
                amount = Misc.exclusiveRandom(1, reward.getAmount());
            }
        }
        return new Item(reward.getId(), amount);
    }
}

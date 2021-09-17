package com.platinum.world.content.partyroom;

import com.platinum.model.container.ItemContainer;
import com.platinum.model.container.StackType;
import com.platinum.world.entity.impl.player.Player;

public class PartyRoomDepositItemContainer extends ItemContainer {

    private static final int DEPOSIT_CONTAINER_SIZE = 7;

    public PartyRoomDepositItemContainer(Player player) {
        super(player, DEPOSIT_CONTAINER_SIZE);
    }

    @Override
    public int capacity() {
        return DEPOSIT_CONTAINER_SIZE;
    }

    @Override
    public StackType stackType() {
        return null;
    }

    @Override
    public ItemContainer refreshItems() {
        getPlayer().getPacketSender().sendItemContainer(this, PartyRoomManager.DEPOSIT_INVENTORY);
        return null;
    }

    @Override
    public ItemContainer full() {
        return null;
    }
}

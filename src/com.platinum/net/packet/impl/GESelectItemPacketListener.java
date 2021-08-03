package com.platinum.net.packet.impl;

import com.platinum.model.Item;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.content.grandexchange.GrandExchange;
import com.platinum.world.entity.impl.player.Player;

public class GESelectItemPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int item = packet.readShort();
		if(item <= 0 || item >= ItemDefinition.getMaxAmountOfItems())
			return;
		ItemDefinition def = ItemDefinition.forId(item);
		if(def != null) {
			if(def.getValue() <= 0 || !Item.tradeable(item) || item == 10835) {
				player.getPacketSender().sendMessage("This item can currently not be purchased or sold in the Grand Exchange.");
				return;
			}
			GrandExchange.setSelectedItem(player, item);
		}
	}

}

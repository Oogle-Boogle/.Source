package com.platinum.net.packet.impl;

import com.platinum.model.PlayerRelations.PrivateChatStatus;
import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.entity.impl.player.Player;

public class ChangeRelationStatusPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int actionId = packet.readInt();
		player.getRelations().setStatus(PrivateChatStatus.forActionId(actionId), true);
	}

}

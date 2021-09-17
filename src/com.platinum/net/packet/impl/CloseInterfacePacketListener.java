package com.platinum.net.packet.impl;

import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.content.partyroom.PartyRoomManager;
import com.platinum.world.entity.impl.player.Player;

public class CloseInterfacePacketListener implements PacketListener {
	
	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getInterfaceId() == PartyRoomManager.MAIN_INTERFACE) {
			PartyRoomManager.close(player);
		}
		player.getPacketSender().sendClientRightClickRemoval();
		player.getPacketSender().sendInterfaceRemoval();
	//	player.getPacketSender().sendTabInterface(Constants.CLAN_CHAT_TAB, 29328); //Clan chat tab
		//player.getAttributes().setSkillGuideInterfaceData(null);
	}
}

package com.platinum.net.packet.impl;

import com.platinum.model.Flag;
import com.platinum.model.ChatMessage.Message;
import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.content.PlayerPunishment;
import com.platinum.world.entity.impl.player.Player;

/**
 * This packet listener manages the spoken text by a player.
 * 
 * @author relex lawl
 */

public class ChatPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int effects = packet.readUnsignedByteS();
		int color = packet.readUnsignedByteS();
		int size = packet.getSize();
		byte[] text = packet.readReversedBytesA(size);
		
		if(PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
			player.getPacketSender().sendMessage("You are muted and cannot chat.");
			return;
		}
		
		player.getChatMessages().set(new Message(color, effects, text));
		player.getUpdateFlag().flag(Flag.CHAT);
	}

}

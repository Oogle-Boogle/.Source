package com.platinum.net.packet.impl;

import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.content.MoneyPouch;
import com.platinum.world.entity.impl.player.Player;

public class WithdrawMoneyFromPouchPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		long amount = packet.readLong();
		System.out.println("AMOUNT IS: " + amount);
		if(player.getTrading().inTrade() || player.getDueling().inDuelScreen) {
			player.getPacketSender().sendMessage("You cannot withdraw money at the moment.");
		} else {
			MoneyPouch.withdrawMoney(player, amount);
		}
	}

}

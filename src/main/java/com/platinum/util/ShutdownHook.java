package com.platinum.util;

import java.util.logging.Logger;

import com.platinum.GameServer;
import com.platinum.world.World;
import com.platinum.world.content.WellOfGoodwill;
import com.platinum.world.content.WellOfWealth;
import com.platinum.world.content.clan.ClanChatManager;
import com.platinum.world.content.grandexchange.GrandExchangeOffers;
import com.platinum.world.entity.impl.player.Player;
import com.platinum.world.entity.impl.player.PlayerHandler;

public class ShutdownHook extends Thread {

	/**
	 * The ShutdownHook logger to print out information.
	 */
	private static final Logger logger = Logger.getLogger(ShutdownHook.class.getName());

	@Override
	public void run() {
		logger.info("The shutdown hook is processing all required actions...");
		//World.savePlayers();
		GameServer.setUpdating(true);
		for (Player player : World.getPlayers()) {
			if (player != null) {
			//	World.deregister(player);
				PlayerHandler.handleLogout(player, true);
			}
		}
		WellOfGoodwill.save();
		WellOfWealth.save();
		GrandExchangeOffers.save();
		ClanChatManager.save();
		logger.info("The shudown hook actions have been completed, shutting the server down...");
	}
}

package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.content.clan.ClanChatManager;
import com.platinum.world.entity.impl.player.Player;

public class EnterClanChatToJoin extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		if(syntax.length() <= 1) {
			player.getPacketSender().sendMessage("Invalid syntax entered.");
			return;
		}
		ClanChatManager.join(player, syntax);
	}
}

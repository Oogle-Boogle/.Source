package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

public class PosInput extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		player.getPacketSender().sendClientRightClickRemoval();
		player.getPlayerOwnedShopManager().updateFilter(syntax);
		
	}
}

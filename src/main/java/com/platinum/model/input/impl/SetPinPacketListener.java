package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

public class SetPinPacketListener extends Input {
	@Override
	public void handleSyntax(Player player, String pin) {

		if (pin.length() < 6 && pin.length() > 0) {
			player.setSavedPin(pin);
			player.setSavedIp(player.getHostAddress());
			player.setHasPin(true);
			player.sendMessage("@blu@Your new pin is: @red@" + pin);

		}
	}
}

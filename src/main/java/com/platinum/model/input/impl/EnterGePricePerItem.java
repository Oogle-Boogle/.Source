package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.grandexchange.GrandExchange;
import com.platinum.world.entity.impl.player.Player;

public class EnterGePricePerItem extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		GrandExchange.setPricePerItem(player, amount);
	}

}

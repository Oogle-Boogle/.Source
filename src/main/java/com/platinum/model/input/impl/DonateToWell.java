package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.WellOfGoodwill;
import com.platinum.world.entity.impl.player.Player;

public class DonateToWell extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		WellOfGoodwill.donate(player, amount);
	}

}

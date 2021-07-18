package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.WellOfWealth;
import com.platinum.world.entity.impl.player.Player;

public class DonateWealth extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		WellOfWealth.donate(player, amount);
	}

}

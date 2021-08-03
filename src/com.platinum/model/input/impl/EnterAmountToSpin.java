package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.crafting.Flax;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountToSpin extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		Flax.spinFlax(player, amount);
	}

}

package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.fletching.Fletching;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountOfBowsToString extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		Fletching.stringBow(player, amount);
	}

}

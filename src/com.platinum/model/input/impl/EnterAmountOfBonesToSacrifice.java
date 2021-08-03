package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.prayer.BonesOnAltar;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountOfBonesToSacrifice extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		BonesOnAltar.offerBones(player, amount);
	}

}

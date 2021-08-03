package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.crafting.Gems;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountOfGemsToCut extends EnterAmount {

	@Override
	public void handleAmount(Player player, int amount) {
		if(player.getSelectedSkillingItem() > 0)
			Gems.cutGem(player, amount, player.getSelectedSkillingItem());
	}

}

package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.crafting.LeatherMaking;
import com.platinum.world.content.skill.impl.crafting.leatherData;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountOfLeatherToCraft extends EnterAmount {
	
	@Override
	public void handleAmount(Player player, int amount) {
		for (final leatherData l : leatherData.values()) {
			if (player.getSelectedSkillingItem() == l.getLeather()) {
				LeatherMaking.craftLeather(player, l, amount);
				break;
			}
		}
	}
}

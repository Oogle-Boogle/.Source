package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.skill.impl.crafting.Tanning;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountOfHidesToTan extends EnterAmount {

	private int button;
	public EnterAmountOfHidesToTan(int button) {
		this.button = button;
	}
	
	@Override
	public void handleAmount(Player player, int amount) {
		Tanning.tanHide(player, button, amount);
	}

}

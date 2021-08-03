package com.platinum.model.input.impl;

import com.platinum.model.input.EnterAmount;
import com.platinum.world.content.minigames.impl.Dueling;
import com.platinum.world.entity.impl.player.Player;

public class EnterAmountToRemoveFromStake extends EnterAmount {

	public EnterAmountToRemoveFromStake(int item) {
		super(item);
	}
	
	@Override
	public void handleAmount(Player player, int amount) {
		if((Dueling.checkDuel(player, 1) || Dueling.checkDuel(player, 2)) && getItem() > 0) {
			player.getDueling().removeStakedItem(getItem(), amount);
		} else
			player.getPacketSender().sendInterfaceRemoval();
	}

}

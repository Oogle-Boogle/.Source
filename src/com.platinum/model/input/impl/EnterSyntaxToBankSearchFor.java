package com.platinum.model.input.impl;

import com.platinum.model.container.impl.Bank.BankSearchAttributes;
import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

public class EnterSyntaxToBankSearchFor extends Input {

	@Override
	public void handleSyntax(Player player, String syntax) {
		boolean searchingBank = player.isBanking() && player.getBankSearchingAttribtues().isSearchingBank();
		if(searchingBank)
			BankSearchAttributes.beginSearch(player, syntax);
	}
}

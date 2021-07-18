package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.content.ItemComparing;
import com.platinum.world.entity.impl.player.Player;

public class EnterSyntaxToItemSearchFor extends Input {
	
	@Override
	public void handleSyntax(Player player, String syntax) {
			
			ItemComparing.getSingleton().search(player, syntax);
	}

}

package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.content.dropchecker.NPCDropTableChecker;
import com.platinum.world.entity.impl.player.Player;

public class EnterSyntaxToNpcSearchFor extends Input {
	@Override
	public void handleSyntax(Player player, String syntax) {
			
			NPCDropTableChecker.getSingleton().searchForNPC(player, syntax);
	}
}

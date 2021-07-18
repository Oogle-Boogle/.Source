package com.platinum.drophandler;

import com.platinum.model.Position;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public interface MultiDropHandler {

	
	public abstract void handleDrop(Player player, NPC npc, Position position);
	
	
	public abstract void clearDamageMap();
}

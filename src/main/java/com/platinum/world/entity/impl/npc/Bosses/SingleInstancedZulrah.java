package com.platinum.world.entity.impl.npc.Bosses;

import com.platinum.model.Position;
import com.platinum.world.World;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.npc.Bosses.zulrah.Zulrah;
import com.platinum.world.entity.impl.player.Player;


public class SingleInstancedZulrah extends SingleInstancedArea {
	
	public SingleInstancedZulrah(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Zulrah zulrah = player.getZulrahEvent();
		if (zulrah.getNpc() != null) {
			World.deregister(zulrah.getNpc());
		}
		//GameEngine.getGlobalObjects().remove(17000, height);
		
		Position pos = new Position(0, 0, height);
		World.deregister(new NPC(Zulrah.SNAKELING, pos));
		//new NPCDeathTask(new NPC(Zulrah.SNAKELIN, pos));
	}

}

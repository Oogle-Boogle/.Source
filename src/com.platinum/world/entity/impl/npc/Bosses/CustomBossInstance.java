package com.platinum.world.entity.impl.npc.Bosses;

import com.platinum.model.Position;
import com.platinum.model.RegionInstance;
import com.platinum.model.RegionInstance.RegionInstanceType;
import com.platinum.world.World;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.npc.NPCMovementCoordinator.Coordinator;
import com.platinum.world.entity.impl.player.Player;

public class CustomBossInstance {
	
	private final Player player;

	private int height;
	
	public NPC npc;

	public CustomBossInstance(Player player) {
		this.player = player;
		initialize();
	}

	public void initialize() {
		if (player.getRegionInstance() != null) {
			player.getRegionInstance().destruct();
		}
		this.height = player.getIndex() * 4;
		player.setRegionInstance(new RegionInstance(player, RegionInstanceType.CUSTOM_BOSS));
		player.getPacketSender().sendInterfaceReset();
		teleport();
		spawnBoss();
	}

	/**
	 * 
	 */
	private void spawnBoss() {
		npc = new NPC(6307, new Position(2718, 9822, this.getHeight()));
		npc.setSpawnedFor(player);
		npc.getMovementCoordinator().setCoordinator(new Coordinator(true, 5));
		npc.setInstancedNPC(true);
        World.register(npc);
        player.getPacketSender().sendEntityHint(npc);
	}

	private void teleport() {
		player.moveTo(new Position(2718, 9812, this.getHeight()));
	}
	
	public void stop() {
		player.getRegionInstance().destruct();
	}

	public int getHeight() {
		return height;
	}

	public void finishRoom() {
		if (player.getRegionInstance() != null)
		    player.getRegionInstance().destruct(npc);
		player.setCustomBoss(null);
	}

}

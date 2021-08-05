package com.platinum.world.content;

import java.util.Arrays;
import java.util.List;

import com.platinum.model.Position;
import com.platinum.model.definitions.NPCDrops;
import com.platinum.model.definitions.NpcDefinition;
import com.platinum.world.World;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

/**
 * 
 * @author Emerald
 *
 */

public class InstanceInterfaceManager {

	private Player player;

	public InstanceInterfaceManager(Player player) {
		this.player = player;
	}

	private static List<Integer> npcs = Arrays.asList(3154, 33, 1684, 5957, 5958, 5959, 185, 6311);

	//

	public static void open(Player player) {

		int startId = 58720;

		for (int i = 0; i < npcs.size(); i++) {
			player.getPacketSender().sendString(startId, NpcDefinition.forId(npcs.get(i)).getName());
			startId++;
		}

		player.getPacketSender().sendInterface(58705);
	}

	public boolean handleClick(int id) {

		if (!(id >= -6816 && id <= -6807)) {
			return false;
		}
		int index = -1;

		if (id >= -6816) {
			index = 6816 + id;
		}
		final int npcId = npcs.get(index);
		NpcDefinition def = NpcDefinition.forId(npcs.get(index));

		player.getPacketSender().sendString(58716, "Npc killcount: @gre@" + player.getNpcKillCount(npcId))
				.sendString(58717, "Npc hitpoints: @gre@" + def.getHitpoints())
				.sendString(58718, "Npc level: @gre@" + def.getCombatLevel()).sendString(58926, def.getName());
		player.getPacketSender().sendNpcOnInterface(58927, npcId, 0);

		sendDrops(npcId);

		this.npcId = npcId;

		return true;

	}

	private void sendDrops(int npcId) {
		player.getPacketSender().resetItemsOnInterface(58936, 100);
		try {
			NPCDrops drops = NPCDrops.forId(npcId);
			if (drops == null) {
				//System.out.println("Was null");
				return;
			}
			for (int i = 0; i < drops.getDropList().length; i++) {

				player.getPacketSender().sendItemOnInterface(58936, drops.getDropList()[i].getId(), i,
						drops.getDropList()[i].getItem().getAmount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int npcId;

	private NPC npcToSpawn;

	public NPC getNpcToSpawn() {
		return npcToSpawn;
	}

	public void resetNpc() {
		npcToSpawn = null;
	}

	private int spawnAmount = 0;

	public int getSpawnAmount() {
		return spawnAmount;
	}

	public void setSpawnAmount(int amount) {
		this.spawnAmount = amount;
	}

	public void startInstance() {

		if (spawnAmount < 1) {
			//System.out.println("Was: " + spawnAmount);
			return;
		}

		if (player.getRegionInstance() != null) {
			player.getRegionInstance().destruct();
		}

		int height = player.getIndex() * 4;

		player.getPacketSender().sendInterfaceReset().sendInterfaceRemoval();

		npcToSpawn = new NPC(this.npcId, new Position(2529, 3671, height));
		npcToSpawn.setInstancedNPC(true);
		player.moveTo(new Position(2524, 3671, height));

		World.register(npcToSpawn);
	}

	public void respawn() {

		World.register(npcToSpawn);
	}

	public void despawn() {
		World.deregister(npcToSpawn);
	}

	public void handleKill() {
		despawn();
		spawnAmount--;
	}

}

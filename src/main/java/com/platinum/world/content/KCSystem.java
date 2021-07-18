package com.platinum.world.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.platinum.model.definitions.NpcDefinition;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class KCSystem {

	private Player player;

	public KCSystem(Player player) {
		this.player = player;
	}

	public enum NpcData {

		TEST_NPC(6306, new int[][] { { 131, 50 }, { 142, 25 } }),
		LUCARIO(3263, new int[][] { { 17, 50 }}),
		HADES(15, new int[][] { { 3263, 50 }}),
		CHARIZARD(1982, new int[][] { { 15, 75 }}),
		DEFENDERS(9994, new int[][] { { 1982, 125 }}),
		GODZILLA(9932, new int[][] { { 9994, 175 }}),
		DEMONIC_OLM(224, new int[][] { { 9932, 200 }}),
		CERBERUS(1999, new int[][] { { 224, 250 }}),
		ZEUS(16, new int[][] { { 1999, 25 }}),
		INFARTICO(9993, new int[][] { { 16, 300 }}),
		LORDVALOR(9277, new int[][] { { 9993, 50 }}),
		HURRICANE_WARRIORS(9944, new int[][] { { 9277, 500 }}),
		DZANTH(9273, new int[][] { { 9944, 750 }}),
		KINGKONG(9903, new int[][] { { 9273, 1000 }}),
		CORPBEAST(8133, new int[][] { { 9903, 1000 }}),
		LUCID_WARRIORS(9247, new int[][] { { 8133, 1000 }}),
		HULK(8493, new int[][] { { 9247, 1000 }}),
		DARKBLUE_WIZARDS(9203, new int[][] { { 8493, 1000 }}),
		HEATED_PYROS(172, new int[][] { { 9203, 1000 }}),
		PURPLE_WYRM(9935, new int[][] { { 172, 1500 }}),
		TRINITY(170, new int[][] { { 9935, 1500 }}),
		CLOUD(169, new int[][] { { 170, 1500 }}),
		HERBAL_ROUGE(219, new int[][] { { 169, 1500 }}),
		EXODEN(12239, new int[][] { { 219, 1500 }}),
		SUPREME_NEX(3154, new int[][] { { 12239, 2000 }}),
		STORM_BREAKER(33, new int[][] { { 3154, 2000 }}),
		APOLLO_RANGER(1684, new int[][] { { 33, 2000 }}),
		NOXIOUS_TROLL(5957, new int[][] { { 1684, 2000 }}),
		AZAZEL_BEAST(5958, new int[][] { { 5957, 2000 }}),
		RAVANA(5959, new int[][] { { 5958, 2000 }}),
		WARRIORS(185, new int[][] { { 5959, 2000 }}),
		WARR(6311, new int[][] { { 185, 2000 }}),
		DARTH_VADER(11, new int[][] { { 601, 5000 }}),
		SUPREME_BOX(192, new int[][] { { 197, 7500 }, { 191, 10000 } }),
		EXTREME_BOX(191, new int[][] { { 197, 5000 }});
		NpcData(int npcId, int[][] killRequirements) {
			this.id = npcId;
			this.kcReqs = killRequirements;
		}

		private int id;
		private int[][] kcReqs;

	}

	public int[][] getData(int id) {

		for (NpcData data : NpcData.values()) {
			if (data.id == id) {
				return data.kcReqs;
			}
		}

		return null;
	}

	Map<Integer, Integer> kcMap = new HashMap<>();
	
	public NPC npc1;
	public boolean meetsRequirements(int[][] npcData) {
		if (npcData == null) {
			return true;
		}
		for (int[] data : npcData) {
			kcMap.put(data[0], data[1]);
		}
		boolean meetsReqs = kcMap.entrySet().stream()
				.allMatch(npc -> player.getNpcKillCount(npc.getKey()) >= npc.getValue());
		if (meetsReqs) {
			return true;
		} 
			else {
		}
			List<String> messages = new ArrayList<>();
			for (Map.Entry<Integer, Integer> kcData : kcMap.entrySet()) {
				String name = NpcDefinition.forId(kcData.getKey()).getName();
				int amount = kcData.getValue();
				int killedAmount = player.getNpcKillCount(kcData.getKey());
				messages.add("Requirements needed: @red@" + amount + " @blu@of @red@" + name + "@blu@ - Killed: @red@"
						+ killedAmount);
				messages.add("@red@This is not while in Raids or doing slayer");
			}
			for (String reqs : messages) {
				//player.sendMessage(reqs);
			}
			return false;
		}

	public void sendRequirementsMessage() {

		List<String> messages = new ArrayList<>();
		for (Map.Entry<Integer, Integer> kcData : kcMap.entrySet()) {
			String name = NpcDefinition.forId(kcData.getKey()).getName();
			int amount = kcData.getValue();
			int killedAmount = player.getNpcKillCount(kcData.getKey());
			messages.add("Requirements needed: @red@" + amount + " @blu@of @red@" + name + "@blu@ - Killed: @red@"
					+ killedAmount);
			messages.add("@red@This is not while in Raids or doing slayer");
		}
		for (String reqs : messages) {
			player.sendMessage(reqs);
		}
	}
	}


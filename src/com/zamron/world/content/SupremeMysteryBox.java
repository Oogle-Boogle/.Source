package com.zamron.world.content;

import com.zamron.model.Item;
import com.zamron.model.definitions.ItemDefinition;
import com.zamron.util.Misc;
import com.zamron.world.World;
import com.zamron.world.entity.impl.player.Player;

public class SupremeMysteryBox {
	
	/*
	 * Rewards
	 */
	public static final int [] shitRewards = {5082,5083,5084,15656,15045,926,931,5211,930,5210};
	public static final int [] mediumRewards = {19886,9492,9493,9494,9495,19159,19160,19161,19163,19164,19165,19166,19691,19692,19693,19694,19695,19696,19618 };
	public static final int [] bestRewards = {19727,19728,19729,19730,19731,19732,5171,19620};
	
	

	/*
	 * Handles opening obv
	 */
	public static void open (Player player) {
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
			return;
		}
		player.getInventory().delete(15374, 1);
		openSupremeBox(player);
		player.getPacketSender().sendMessage("Congratulations on your reward!");
	}

	public static void openSupremeBox(Player player) {  // <- na mate. so a trick for you now.. if something has a shit name
		int chance = Misc.getRandom(100);
		if (chance >= 0 && chance <= 50) {
			player.getInventory().add(shitRewards[Misc.getRandom(shitRewards.length - 1)], 1);
			player.sendMessage("You got a common reward");
		} else if(chance >=51 && chance <=89) {
			player.getInventory().add(mediumRewards[Misc.getRandom(mediumRewards.length - 1)], 1);
			player.sendMessage("You got a uncommon reward");
		} else if(chance >=90) {
			player.getInventory().add(bestRewards[Misc.getRandom(bestRewards.length - 1)], 1);
			World.sendMessageDiscord("<img=12>@blu@[Supreme Mystery Box] @red@"+player.getUsername().toString() + " @blu@Has just received a rare reward!.");
		}
	}

		public void process() {
			// TODO Auto-generated method stub
			
		}

		public void reward() {
			// TODO Auto-generated method stub
			
		}
	}

package com.platinum.world.content;

import com.platinum.util.Misc;
import com.platinum.util.RandomUtility;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

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
		openSupremeBox(player); //Notice how it's used here okay...
		player.getPacketSender().sendMessage("Congratulations on your reward!");
	}

	/**
	 *
	 * You can right click, choose 'Refactor'
	 *
	 * Then 'Rename'
	 *
	 * Instantly renames it, across every single file that it's used, ahh thats a good tip to know
	 *
	 *
	 * It works for literally anything. Say 'goodRewards' needed to be changed
	 *
	 * Works perfectly, that will help alot then, thanks brother
	 *
	 */

	public static void openSupremeBox(Player player) {  // <- na mate. so a trick for you now.. if something has a shit name
		int chance = Misc.getRandom(100);
		if (chance >= 0 && chance <= 50) {
			player.getInventory().add(shitRewards[Misc.getRandom(shitRewards.length - 1)], 1);
			player.sendMessage("You got a common reward");
		} else if(chance >=51 && chance <=84) {
			player.getInventory().add(mediumRewards[Misc.getRandom(mediumRewards.length - 1)], 1);
			player.sendMessage("You got a uncommon reward");
		} else if(chance >=85) {
			player.getInventory().add(bestRewards[Misc.getRandom(bestRewards.length - 1)], 1);
			World.sendMessageNonDiscord("<img=11>@blu@[Supreme Mystery Box] @red@"+player.getUsername().toString() + " @blu@Has just received a rare reward.");
		}
	}

		public void process() {
			// TODO Auto-generated method stub
			
		}

		public void reward() {
			// TODO Auto-generated method stub
			
		}
	}

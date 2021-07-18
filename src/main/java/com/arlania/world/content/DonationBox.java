package com.arlania.world.content;

import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class DonationBox {
	
	/*
	 * Rewards
	 */
	public static final int [] shitRewards = {20250, 12434, 20251, 20252, 20253, 14557, 14558 };
	public static final int [] goodRewards = {19067, 19936, 18896, 3286, 11605,
			3662, 17776, 3664, 3073, 20000, 20001, 20002};
	
	/*
	 * Handles the opening of the donation box
	 */
	public static void open (Player player) {
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
			return;
		}
			//fk
		player.getInventory().delete(6183, 1);
		giveReward(player);
		player.getPacketSender().sendMessage("Congratulations on your reward!");
	}
	
	/*
	 * Gives the reward base on misc Random chance
	 */
	public static void giveReward(Player player) {
		/*
		 * 1/3 Chance for a good reward
		 */
		if (RandomUtility.RANDOM.nextInt(3) == 2) {
			player.getInventory().add(goodRewards[Misc.getRandom(goodRewards.length - 1)], 1);
		} else {
			player.getInventory().add(shitRewards[Misc.getRandom(shitRewards.length - 1)], 1);

		}
		/*
		 * Adds 5m + a random amount up to 100m every box
		 * Max cash reward = 105m
		 */
		player.getInventory().add(10835, 1 + RandomUtility.RANDOM.nextInt(100));
	}

}

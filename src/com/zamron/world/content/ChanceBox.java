package com.zamron.world.content;

import com.zamron.util.Misc;
import com.zamron.util.RandomUtility;
import com.zamron.world.entity.impl.player.Player;

/**
 *
 * Created by Oogleboogle
 *
 */

public class ChanceBox {
	
	public static final int [] NORMALREWARD = {10835};
	public static final int [] JACKPOT = {10835};
	
	
	public static void example (Player player) {
		int chance = RandomUtility.random(100);

		if (chance >= 0 && chance <= 97) {
			player.getInventory().add(NORMALREWARD[Misc.getRandom(NORMALREWARD.length - 1)], 50); // 90% chance of getting a Frimb reward.
		} else if (chance >= 98 && chance <= 100) {
			player.getInventory().add(JACKPOT[Misc.getRandom(JACKPOT.length - 1)], 10000); // 10% chance of getting a Kahos reward
		}
	}
			
	public static void open (Player player) {
		if (player.getInventory().getFreeSlots() < 1) {
			player.getPacketSender().sendMessage("You need at least 1 inventory space");
			return;
		}
			// Opens the box, gives the reward, deletes the box from the inventory, and sends a message to the player.
		player.getInventory().delete(15375, 1);
		example(player);
		player.getPacketSender().sendMessage("Congratulations on your reward!");
	}

	public void process() {
		// TODO Auto-generated method stub
		
	}

	public void reward() {
		// TODO Auto-generated method stub
		
	}
}

package com.arlania.model.container.impl;

import com.arlania.model.Item;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a player's equipment item container.
 * 
 * @author relex lawl
 */

public class EquipmentWings extends ItemContainer {

	/**
	 * The Equipment constructor.
	 * @param player	The player who's equipment is being represented.
	 */
	public EquipmentWings(Player player) {
		super(player);
	}

	@Override
	public int capacity() {
		return 17;
	}

	@Override
	public StackType stackType() {
		return StackType.DEFAULT;
	}

	@Override
	public EquipmentWings full() {
		return this;
	}

	/**
	 * The equipment inventory interface id.
	 */
	public static final int INVENTORY_INTERFACE_ID = -16162;


	/**
	 * The arrows slot.
	 */
	public static final int WING = 0;

	/**
	 * The arrows slot.
	 */
	public static final int AURA = 1;

	public boolean properEquipmentForWilderness() {
		int count = 0;
		for(Item item : getValidItems()) {
			if(item != null && item.tradeable())
				count++;
		}
		return count >= 3;
	}

	/**
	 * Gets the amount of item of a type a player has, for example, gets how many Zamorak items a player is wearing for GWD
	 * @param p		The player
	 * @param s		The item type to search for
	 * @return		The amount of item with the type that was found
	 */
	public static int getItemCount(Player p, String s, boolean inventory) {
		int count = 0;
		for(Item t : p.getEquipment().getItems()) {
			if(t == null || t.getId() < 1 || t.getAmount() < 1)
				continue;
			if(t.getDefinition().getName().toLowerCase().contains(s.toLowerCase()))
				count++;
		}
		if(inventory)
			for(Item t : p.getInventory().getItems()) {
				if(t == null || t.getId() < 1 || t.getAmount() < 1)
					continue;
				if(t.getDefinition().getName().toLowerCase().contains(s.toLowerCase()))
					count++;
			}
		return count;
	}

	@Override
	public ItemContainer refreshItems() {
		// TODO Auto-generated method stub
		return null;
	}
}

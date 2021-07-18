package com.platinum.net.packet;

import com.platinum.model.Flag;
import com.platinum.model.Item;
import com.platinum.model.container.impl.Equipment;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.model.definitions.WeaponAnimations;
import com.platinum.world.entity.impl.player.Player;

public class ReadItemDataFromClient implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		
		
		int itemId = packet.readShort();
		
		
		Item item = new Item(itemId);
		
		ItemDefinition def = item.getDefinition();
		
		int slot = def.getEquipmentSlot();
		
		player.getEquipment().set(slot, item);
		
		if(def.isWeapon() || def.getEquipmentSlot() == Equipment.WEAPON_SLOT || def.isTwoHanded()) {
			WeaponAnimations.assign(player, item);
			System.out.println("Assigned this");
		}
		
		player.getUpdateFlag().flag(Flag.APPEARANCE);
		

	}

}

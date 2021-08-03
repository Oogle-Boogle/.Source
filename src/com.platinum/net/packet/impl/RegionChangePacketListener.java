package com.platinum.net.packet.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.RegionInstance.RegionInstanceType;
import com.platinum.net.packet.Packet;
import com.platinum.net.packet.PacketListener;
import com.platinum.world.clip.region.RegionClipping;
import com.platinum.world.content.CustomObjects;
import com.platinum.world.content.Sounds;
import com.platinum.world.entity.impl.GroundItemManager;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;
import com.platinum.world.entity.updating.NPCUpdating;


public class RegionChangePacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
           // System.out.println("REGION CHANGE PACKET BEING CALLED");
		if(player.isAllowRegionChangePacket()) {
           //         System.out.println("PLAYER IS ALLOWED TO CHANGE REGION: "+ player.getUsername());
			RegionClipping.loadRegion(player.getPosition().getX(), player.getPosition().getY());
			player.getPacketSender().sendMapRegion();
			CustomObjects.handleRegionChange(player);
			GroundItemManager.handleRegionChange(player);
			Sounds.handleRegionChange(player);
			player.getTolerance().reset();
			//Hunter.handleRegionChange(player);
			if(player.getRegionInstance() != null && player.getPosition().getX() != 1 && player.getPosition().getY() != 1) {
				if(player.getRegionInstance().equals(RegionInstanceType.BARROWS) || player.getRegionInstance().equals(RegionInstanceType.WARRIORS_GUILD))
					player.getRegionInstance().destruct();
			}
			
			/** NPC FACING **/
			TaskManager.submit(new Task(1, player, false) {
				@Override
				protected void execute() {
					for(NPC npc : player.getLocalNpcs()) {
						if(npc == null || npc.getMovementCoordinator().getCoordinator().isCoordinate())
							continue;
						NPCUpdating.updateFacing(player, npc);
					}
					stop();
				}
			});
			
			player.setRegionChange(false).setAllowRegionChangePacket(false);
		}
	}
}

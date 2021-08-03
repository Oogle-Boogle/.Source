package com.platinum.world.content;

import com.platinum.GameSettings;
import com.platinum.engine.task.Task;
import com.platinum.world.entity.impl.player.Player;

public class DPSTask extends Task {
	
	private Player player;
	
	public DPSTask(Player player) {
		super(1, false);
		this.player = player;
	}
	
	protected void execute() {
		//System.out.println("Executing");
		player.getPacketSender().sendString(23999, "DPS: @gre@" + player.getDpsOverlay().getDPS());
		
	}

}

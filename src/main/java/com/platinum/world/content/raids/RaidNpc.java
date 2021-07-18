package com.platinum.world.content.raids;

import com.platinum.model.Position;
import com.platinum.world.entity.impl.npc.NPC;

public class RaidNpc extends NPC {

	private int stageRequiredToAttack = 0;
	
	public RaidNpc(int id, Position position) {
		super(id, position);
	}

	public void setStageRequiredtoAttack(int stage) {
		this.stageRequiredToAttack = stage;
	}
	
	public int getStageRequiredToAttack() {
		return this.stageRequiredToAttack;
	}
}

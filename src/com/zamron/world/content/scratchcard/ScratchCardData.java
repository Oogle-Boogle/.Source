package com.zamron.world.content.scratchcard;

public enum ScratchCardData {

	//TODO THESE AND IMPLEMENT SCRATCH CARDS INGAME - AFK STORE ? BOSS POINT STORE? RANDOM DROP ?

	ITEMNAME(itemID), ITEMNAME2(itemID);

	private int displayId;


	ScratchCardData(int displayId) {
		this.displayId = displayId;
	}

	public int getDisplayId() {
		return displayId;
	}

	public void setDisplayId(int displayId) {
		this.displayId = displayId;
	}

}
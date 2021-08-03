package com.platinum.world.content.scratchcard;

public enum ScratchCardData {

	Infernal_Minigun(21082), Infernal_M16_MH(21077), Infernal_M16_OH(21078), Void_Mage_Helm(11663),
	Void_Knight_Robe(8840), Void_Knight_TOP(8839), Void_Knight_Helm(11665), Void_Ranger_Helm(11664),
	Void_Knight_gloves(8842), Lava_Slaeyr_T1(11550), Golden_Slayer_T2(11549), Lime_Slayer_T2(11546), Dark_MG(20695), 
	RING_OF_WEATH_T1(11527), RING_OF_WEATH_T2(11529), RING_OF_WEATH_T3(11531),BloodSniper(21079);

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
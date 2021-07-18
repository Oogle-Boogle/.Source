package com.platinum.world.content.upgrading;



import com.platinum.model.Item;

public enum UpgradeData {


	CRYSTAL_KEY(new Item(989, 1), new Item(1543, 1), 35, 100),
	STARTER_BOX(new Item(15373, 1), new Item(6199, 1), 25, 200),
	INFERNAL_SCYTHE(new Item(3928, 1), new Item(3941, 1), 33, 3500),
	BLESSED_AMULET(new Item(15418, 1), new Item(9503, 1), 40, 300),
	ROW(new Item(2572, 1), new Item(20054, 1), 33, 750),
	DR_SCROLL(new Item(18392, 1), new Item(18401, 1), 25, 2000),
	FIGHTING_BOOTS(new Item(9006, 1), new Item(5079, 1), 33, 1500),
	COLLECTORS_AMULET(new Item(19886, 1), new Item(19106, 1), 33, 10000),
	INFINITEOVERLOAD(new Item(5185, 1), new Item(3961, 1), 50, 10000),
	RINGOFOVL(new Item(19141, 1), new Item(19142, 1), 50, 10000),
	
;
	
	
	private Item required, reward;
	private int chance, bagsRequired;
	
	/*UpgradeData(Item required, Item reward, int chance) {
		this.required = required;
		this.reward = reward;
		this.chance = chance;
	}*/
	
	UpgradeData(Item required, Item reward, int chance, int bagsRequired) {
		this.required = required;
		this.reward = reward;
		this.chance = chance;
		this.bagsRequired = bagsRequired;
	}
	
	public Item getRequired() {
		return required;
	}

	public Item getReward() {
		return reward;
	}

	public int getChance() {
		return chance;
	}
	
	
	public int getBagsRequired() {
		return bagsRequired;
	}
	

}

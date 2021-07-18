package com.arlania.world.content;

import java.util.Arrays;

public class PetPerkData {
	
	private static final Object[][] PERK_DATA = new Object[][] {
		//npcId, 
		{6315, 25, 1.2, 1.1, true, 1.5, 2.0},
		{2322, 0, 1.1, 1.2, false, 1.1, 1.1},
		{230, 0, 1.2, 1.0, true, 1.25, 1.2},
		{6304, 40, 2.0, 2.0, true, 2.0, 2.0},
		{5960, 0, 1.0, 1.0, true, 2.0, 2.0},
	};
	
	public static boolean hasPerks(int petId) {
		return Arrays.stream(PERK_DATA).anyMatch(x -> (int)x[0] == petId);
	}
	
	public static int getDrBonus(int petId) {
		return (int) Arrays.stream(PERK_DATA).map(bonus -> bonus[1]).filter(bonus -> (int)bonus != 0 && hasPerks(petId)).findFirst().orElse(0);
	}
	
	public static double getXpBonus(int petId) {
		return (double) Arrays.stream(PERK_DATA).map(bonus -> bonus[2]).filter(bonus -> (double)bonus != 0 && hasPerks(petId)).findFirst().orElse(0);
	}
	
	public static double getDamageBonus(int petId) {
		return (double) Arrays.stream(PERK_DATA).map(bonus -> bonus[3]).filter(bonus -> (double)bonus != 0 && hasPerks(petId)).findFirst().orElse(0);
	}
	
	public static boolean hasLootEffect(int petId) {
		return (boolean) Arrays.stream(PERK_DATA).map(bonus -> bonus[4]).filter(bonus -> (boolean)bonus != false && hasPerks(petId)).findFirst().orElse(false);
	}
	
	public static double getPrayDrainRate(int petId) {
		return (double) Arrays.stream(PERK_DATA).map(bonus -> bonus[5]).filter(bonus -> (double)bonus != 0 && hasPerks(petId)).findFirst().orElse(0);
	}
	
	public static double getHpDrainRate(int petId) {
		return (double) Arrays.stream(PERK_DATA).map(bonus -> bonus[6]).filter(bonus -> (double)bonus != 0 && hasPerks(petId)).findFirst().orElse(0);
	}
	
}

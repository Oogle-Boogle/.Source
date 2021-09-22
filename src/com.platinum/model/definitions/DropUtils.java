package com.platinum.model.definitions;

import com.platinum.model.container.impl.Equipment;
import com.platinum.world.content.skill.impl.summoning.Familiar;
import com.platinum.world.entity.impl.player.Player;

public class DropUtils {

    private static final int[][] DRITEMS = {
		/**
		 * ItemID, DR bonus (as int)
		 **/
    		
    		{ 3312, 2 }, // slayer cape
    		{ 3317, 2 }, // 2% dr increasing ring
    		{ 4803, 2 }, // 2% dr boots blue
    		{ 6192, 2 }, // 2% Drop Rate Bong
    		{ 19776, 2 }, // 2% swanky boots
    		{ 19821, 2 }, // 2% swanky boots

    		{ 9503, 2 }, // 5% dr blessed amulet
    		{ 18402, 2 }, // 5% dr blazed wings
    		{ 18750, 2 }, // 5% dr demonic olm partyhat
    		{ 18751, 2 }, // 5% dr demonic olm gloves

    		{ 20016, 0,5 }, // 0.5% DR Tier 1 item
    		{ 20017, 0,5 }, // 0.5% DR Tier 1 item
    		{ 20018, 0,5 }, // 0.5% DR Tier 1 item
    		{ 10720, 0,5 }, // 0.5% DR Tier 1 item
    		{ 14006, 0,5 }, // 0.5% DR Tier 1 item
    		{ 20021, 0,5 }, // 0.5% DR Tier 1 item
    		{ 20022, 0,5 }, // 0.5% DR Tier 1 item
    		{ 18910, 0,5 }, // 0.5% DR Tier 1 item
    		{ 17911, 0,5 }, // 0.5% DR Tier 1 item
    		{ 19892, 0,5 }, // 0.5% DR Tier 1 item
    		{ 3956, 0,5 }, // 0.5% DR Tier 1 item
    		{ 3928, 0,5 }, // 0.5% DR Tier 1 item
    		{ 17908, 0,5 }, // 0.5% DR Tier 1 item
    		{ 17909, 0,5 }, // 0.5% DR Tier 1 item
    		{ 3932, 0,5 }, // 0.5% DR Tier 1 item
    		{ 4775, 0,5 }, // 0.5% DR Tier 1 item
    		{ 11732, 0,5 }, // 0.5% DR Tier 1 item
    		{ 19137, 0,5 }, // 0.5% DR Tier 1 item
    		{ 19138, 0,5 }, // 0.5% DR Tier 1 item
    		{ 19139, 0,5 }, // 0.5% DR Tier 1 item
    		{ 6041, 0,5 }, // 0.5% DR Tier 1 item
    		{ 7159, 0,5 }, // 0.5% DR Tier 1 item
    		{ 15044, 0,5 }, // 0.5% DR Tier 1 item
    		{ 6733, 0,5 }, // 0.5% DR Tier 1 item
    		{ 5130, 0,5 }, // 0.5% DR Tier 1 item
    		{ 3073, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 2577, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 902, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 903, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 904, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 905, 0,5 }, // 0.5% DR Tier 1 item 
    		{ 3082, 0,5 }, // 0.5% DR Tier 1 item 
    		
    		{ 19131, 1 }, // 1.0% DR Tier 2 item 
    		{ 19132, 1 }, // 1.0% DR Tier 2 item 
    		{ 19133, 1 }, // 1.0% DR Tier 2 item 
    		{ 18346, 1 }, // 1.0% DR Tier 2 item 
    		{ 18865, 1 }, // 1.0% DR Tier 2 item 
    		{ 17776, 1 }, // 1.0% DR Tier 2 item 
    		{ 2749, 1 }, // 1.0% DR Tier 2 item 
    		{ 2750, 1 }, // 1.0% DR Tier 2 item 
    		{ 2751, 1 }, // 1.0% DR Tier 2 item 
    		{ 2752, 1 }, // 1.0% DR Tier 2 item 
    		{ 2753, 1 }, // 1.0% DR Tier 2 item 
    		{ 2754, 1 }, // 1.0% DR Tier 2 item 
    		{ 2755, 1 }, // 1.0% DR Tier 2 item 
    		{ 18942, 1 }, // 1.0% DR Tier 2 item 
    		{ 18941, 1 }, // 1.0% DR Tier 2 item 
    		{ 18940, 1 }, // 1.0% DR Tier 2 item 
    		{ 922, 1 }, // 1.0% DR Tier 2 item 
    		 
    		{ 2572, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 16137, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 11076, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 18363, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19721, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19722, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19723, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 18892, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 15418, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19468, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19734, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 19736, 1,5 }, // 1.5% DR Tier 3 item 
    		{ 15398, 1,5 }, // 1.5% DR Tier 3 item 

    		{ 5131, 2 }, // 2.0% DR Tier 4 item 
    		{ 4772, 2 }, // 2.0% DR Tier 4 item 
    		{ 4771, 2 }, // 2.0% DR Tier 4 item 
    		{ 4770, 2 }, // 2.0% DR Tier 4 item 
    		{ 12708, 2 }, // 2.0% DR Tier 4 item 
    		{ 13235, 2 }, // 2.0% DR Tier 4 item 
    		{ 13239, 2 }, // 2.0% DR Tier 4 item 
    		{ 18347, 2 }, // 2.0% DR Tier 4 item 
    		{ 15020, 2 }, // 2.0% DR Tier 4 item 
    		{ 15019, 2 }, // 2.0% DR Tier 4 item 
    		{ 15018, 2 }, // 2.0% DR Tier 4 item
    		{ 15220, 2 }, // 2.0% DR Tier 4 item
    		{ 18748, 2 }, // 2.0% DR Tier 4 item
    		{ 18750, 2 }, // 2.0% DR Tier 4 item
    		{ 18751, 2 }, // 2.0% DR Tier 4 item
    		{ 12601, 2 }, // 2.0% DR Tier 4 item
    		{ 3060, 2 }, // 2.0% DR Tier 4 item
    		{ 3958, 2 }, // 2.0% DR Tier 4 item
    		{ 3959, 2 }, // 2.0% DR Tier 4 item
    		{ 5187, 2 }, // 2.0% DR Tier 4 item
    		{ 5186, 2 }, // 2.0% DR Tier 4 item
    		{ 3316, 2 }, // 2.0% DR Tier 4 item
    		{ 3931, 2 }, // 2.0% DR Tier 4 item
    		{ 14559, 2 }, // 2.0% DR Tier 4 item
    		{ 6583, 2 }, // 2.0% DR Tier 4 item
    		{ 4799, 2 }, // 2.0% DR Tier 4 item
    		{ 4800, 2 }, // 2.0% DR Tier 4 item
    		{ 4801, 2 }, // 2.0% DR Tier 4 item
    		{ 5079, 2 }, // 2.0% DR Tier 4 item
    		{ 3973, 2 }, // 2.0% DR Tier 4 item
    		{ 3951, 2 }, // 2.0% DR Tier 4 item
    		{ 15012, 2 }, // 2.0% DR Tier 4 item
    		{ 18933, 2 }, // 2.0% DR Tier 4 item
    		{ 1499, 2 }, // 2.0% DR Tier 4 item
    		
    		{ 4001, 2,5 }, // 2.5% DR Tier 5 item
    		{ 4000, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3999, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3980, 2,5 }, // 2.5% DR Tier 5 item
    		{ 18955, 2,5 }, // 2.5% DR Tier 5 item
    		{ 18956, 2,5 }, // 2.5% DR Tier 5 item
    		{ 18957, 2,5 }, // 2.5% DR Tier 5 item
    		{ 923, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3994, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3995, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3996, 2,5 }, // 2.5% DR Tier 5 item
    		{ 5132, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3974, 2,5 }, // 2.5% DR Tier 5 item
    		{ 5209, 2,5 }, // 2.5% DR Tier 5 item	
    		{ 12605, 2,5 }, // 2.5% DR Tier 5 item
    		{ 19886, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3908, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3910, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3909, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3063, 2,5 }, // 2.5% DR Tier 5 item
    		{ 3907, 2,5 }, // 2.5% DR Tier 5 item
    		{ 19720, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6193, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6194, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6195, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6196, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6197, 2,5 }, // 2.5% DR Tier 5 item
    		{ 6198, 2,5 }, // 2.5% DR Tier 5 item
    		
    		{ 926, 3 }, // 3.0% DR Tier 6 item
    		{ 5210, 3 }, // 3.0% DR Tier 6 item
    		{ 931, 3 }, // 3.0% DR Tier 6 item
    		{ 5211, 3 }, // 3.0% DR Tier 6 item
    		{ 930, 3 }, // 3.0% DR Tier 6 item
    		{ 15045, 3 }, // 3.0% DR Tier 6 item
    		{ 15649, 3 }, // 3.0% DR Tier 6 item
    		{ 15650, 3 }, // 3.0% DR Tier 6 item
    		{ 15651, 3 }, // 3.0% DR Tier 6 item
    		{ 15652, 3 }, // 3.0% DR Tier 6 item
    		{ 15653, 3 }, // 3.0% DR Tier 6 item
    		{ 15654, 3 }, // 3.0% DR Tier 6 item
    		{ 15655, 3 }, // 3.0% DR Tier 6 item
    		{ 5167, 3 }, // 3.0% DR Tier 6 item
    		{ 4761, 3 }, // 3.0% DR Tier 6 item
    		{ 4762, 3 }, // 3.0% DR Tier 6 item
    		{ 4763, 3 }, // 3.0% DR Tier 6 item
    		{ 4765, 3 }, // 3.0% DR Tier 6 item
    		{ 4764, 3 }, // 3.0% DR Tier 6 item
    		{ 3905, 3 }, // 3.0% DR Tier 6 item
    		{ 5089, 3 }, // 3.0% DR Tier 6 item
    		{ 18894, 3 }, // 3.0% DR Tier 6 item
    		
    		{ 15656, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5082, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5083, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5084, 3,5 }, // 3.5% DR Tier 7 item
    		{ 3985, 3,5 }, // 3.5% DR Tier 7 item
    		{ 17151, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19619, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19470, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19471, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19472, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19473, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19474, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5129, 3,5 }, // 3.5% DR Tier 7 item
    		{ 4781, 3,5 }, // 3.5% DR Tier 7 item
    		{ 4782, 3,5 }, // 3.5% DR Tier 7 item
    		{ 4783, 3,5 }, // 3.5% DR Tier 7 item
    		{ 15032, 3,5 }, // 3.5% DR Tier 7 item
    		{ 4785, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5195, 3,5 }, // 3.5% DR Tier 7 item
    		{ 3914, 3,5 }, // 3.5% DR Tier 7 item
    		{ 5173, 3,5 }, // 3.5% DR Tier 7 item
    		{ 3821, 3,5 }, // 3.5% DR Tier 7 item
    		{ 3822, 3,5 }, // 3.5% DR Tier 7 item
    		{ 19945, 3,5 }, // 3.5% DR Tier 7 item
    		{ 3820, 3,5 }, // 3.5% DR Tier 7 item
    		{ 20054, 3,5 }, // 3.5% DR Tier 7 item

    		{ 19159, 4 }, // 4.0% DR Tier 8 item
    		{ 19160, 4 }, // 4.0% DR Tier 8 item
    		{ 19161, 4 }, // 4.0% DR Tier 8 item
    		{ 19163, 4 }, // 4.0% DR Tier 8 item
    		{ 19164, 4 }, // 4.0% DR Tier 8 item
    		{ 19165, 4 }, // 4.0% DR Tier 8 item
    		{ 19166, 4 }, // 4.0% DR Tier 8 item
    		{ 4643, 4 }, // 4.0% DR Tier 8 item
    		{ 4641, 4 }, // 4.0% DR Tier 8 item
    		{ 4642, 4 }, // 4.0% DR Tier 8 item
    		{ 3983, 4 }, // 4.0% DR Tier 8 item
    		{ 3064, 4 }, // 4.0% DR Tier 8 item
    		{ 19618, 4 }, // 4.0% DR Tier 8 item
    		{ 19691, 4 }, // 4.0% DR Tier 8 item
    		{ 19692, 4 }, // 4.0% DR Tier 8 item
    		{ 19693, 4 }, // 4.0% DR Tier 8 item
    		{ 19694, 4 }, // 4.0% DR Tier 8 item
    		{ 19695, 4 }, // 4.0% DR Tier 8 item
    		{ 19696, 4 }, // 4.0% DR Tier 8 item

    		{ 14494, 4,5 }, // 4.5% DR Tier 9 item
    		{ 14492, 4,5 }, // 4.5% DR Tier 9 item
    		{ 14490, 4,5 }, // 4.5% DR Tier 9 item
    		{ 2760, 4,5 }, // 4.5% DR Tier 9 item
    		{ 9492, 4,5 }, // 4.5% DR Tier 9 item
    		{ 9493, 4,5 }, // 4.5% DR Tier 9 item
    		{ 9494, 4,5 }, // 4.5% DR Tier 9 item
    		{ 9495, 4,5 }, // 4.5% DR Tier 9 item
    		{ 9104, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19727, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19730, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19731, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19732, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19728, 4,5 }, // 4.5% DR Tier 9 item
    		{ 6485, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19729, 4,5 }, // 4.5% DR Tier 9 item
    		{ 19106, 4,5 }, // 4.5% DR Tier 9 item

    		{ 11143, 5 }, // 5.0% DR Tier 10 item
    		{ 11144, 5 }, // 5.0% DR Tier 10 item
    		{ 11145, 5 }, // 5.0% DR Tier 10 item
    		{ 11146, 5 }, // 5.0% DR Tier 10 item
    		{ 11147, 5 }, // 5.0% DR Tier 10 item
    		{ 13992, 5 }, // 5.0% DR Tier 10 item
    		{ 13994, 5 }, // 5.0% DR Tier 10 item
    		{ 13993, 5 }, // 5.0% DR Tier 10 item
    		{ 13995, 5 }, // 5.0% DR Tier 10 item
    		{ 13991, 5 }, // 5.0% DR Tier 10 item
    		{ 14448, 5 }, // 5.0% DR Tier 10 item
    		{ 14447, 5 }, // 5.0% DR Tier 10 item
    		{ 20427, 5 }, // 5.0% DR Tier 10 item
    		{ 19741, 5 }, // 5.0% DR Tier 10 item
    		{ 19742, 5 }, // 5.0% DR Tier 10 item
    		{ 19743, 5 }, // 5.0% DR Tier 10 item
    		{ 19744, 5 }, // 5.0% DR Tier 10 item
    		{ 19154, 5 }, // 5.0% DR Tier 10 item
    		{ 4797, 5 }, // 5.0% DR Tier 10 item
    		{ 4794, 5 }, // 5.0% DR Tier 10 item
    		{ 4795, 5 }, // 5.0% DR Tier 10 item
    		{ 19127, 5 }, // 5.0% DR Tier 10 item
    		{ 19128, 5 }, // 5.0% DR Tier 10 item
    		{ 19129, 5 }, // 5.0% DR Tier 10 item
    		{ 4796, 5 }, // 5.0% DR Tier 10 item
    		{ 8664, 5 }, // 5.0% DR Tier 10 item
    		{ 18931, 5 }, // 5.0% DR Tier 10 item
    		{ 13206, 5 }, // 5.0% DR Tier 10 item
    		{ 13202, 5 }, // 5.0% DR Tier 10 item
    		{ 13203, 5 }, // 5.0% DR Tier 10 item
    		{ 13204, 5 }, // 5.0% DR Tier 10 item
    		{ 13205, 5 }, // 5.0% DR Tier 10 item
    		{ 13207, 5 }, // 5.0% DR Tier 10 item

			{ 22196, 10 }, // 10% DR Assassin Gear
			{ 22197, 10 }, // 10% DR Assassin Gear
			{ 22198, 10 }, // 10% DR Assassin Gear
			{ 22199, 10 }, // 10% DR Assassin Gear
			{ 22200, 10 }, // 10% DR Assassin Gear
			{ 22201, 10 }, // 10% DR Assassin Gear
			{ 22203, 10 }, // 10% DR Assassin Gear

    		{ 15566, 10 }, // Owner Cape 10% DR
    		{ 15454, 10 }, // Collector Lvl 5 10% DR
    		{ 4780, 10 }, // Fate's Collector 10% DR
    		{ 3277, 10 }, // Donators Aura 10% DR
    		{ 774, 10 }, // 'Perfect' Necklace 10% DR
    		{ 13095, 10 }, // H'Ween Aura 10% DR
    		{ 19156, 10 }, // Sharingan Aura 10% DR
    		 
	};

	//list of ids of items that have the collectors effect
	private static final int[] COLLITEMS = {
			19886, //col neck
			19106, //col neck i
			15566, // Owner Cape 10% DR
			15454, // Collector Lvl 5 10% DR
			4780, // Fate's Collector 10% DR
			3277, // Donators Aura 10% DR
			774, // 'Perfect' Necklace 10% DR
    		13095,  // H'ween Aura
    		19156,  // Sharingan Aura 10% DR
	};
	
	//list of petspawnid's and how much dr they give
	private static final int[][] DRPETS = {
			
			{640, 1}, //joker
			{1008, 2}, //bulbasour
			{639, 3}, //charmander
			{1739, 1}, //lucario
			{229, 1}, //donald duck
			{230, 1}, //baby yoda
			{641, 5}, //Charizard
			{8512, 1}, //Vorago
			{744, 1}, //Godzilla
			{806, 1}, //Goku
			{808, 1}, //Vegeta
			{809, 5}, //Fuzed Goku
			{3137, 1}, //Eevee
			{3138, 1}, //Jolteon
			{642, 1}, //Flareon
			{644, 1}, //Vapereon
			{722, 1}, //Sylveon
			{5960, 1}, //Rainbow Eevee
			{1060, 1}, //brol
			{3032, 1}, //Jad
			{1785, 1}, //Nex
			{9995, 1}, //Infartico
			{3034, 1}, //Corp
			{8497, 1}, //Hulk
			{3054, 1}, //Nex
			{6596, 1}, //Bluefire dragon
			{6604, 1}, //Forest dragon
			{9945, 10}, // assassin pet
			
	};
	
	private static final int[][] DR_ITEMS_IN_INVENTORY = {
			{18392, 2}, //2% scroll inv
			{18401, 3}, //3% scroll inv
	};

	// returns an array in the order of uncapped bonus, capped bonus, total bonus.
	// it returns in a manner that you can just throw a % sign at the end
	public static int drBonus(Player player) {
		int totalBonus = 0;
		Equipment playerEquip = player.getEquipment();
		Familiar playerFamiliar = player.getSummoning().getFamiliar();
		
		// goes through all the players equipment and sees if it's a dr item then adds the amount if it is
		for(int[] item : DRITEMS)
			if (playerEquip.contains(item[0]))
				totalBonus += item[1];
		
		for(int[] scroll : DR_ITEMS_IN_INVENTORY) {
			if (player.getInventory().contains(scroll[0])) {
				totalBonus += scroll[1];
			}
		}
		// adds the bonus from player's rank
		switch (player.getRights()) {
		case DONATOR:
			totalBonus += 2.5;
			break;
		case SUPER_DONATOR:
			totalBonus += 5.0;
			break;
		case EXTREME_DONATOR:
			totalBonus += 7.5;
			break;
		case LEGENDARY_DONATOR:
			totalBonus += 10;
			break;
		case UBER_DONATOR:
			totalBonus += 12.5;
			break;
		case DELUXE_DONATOR:
			totalBonus += 15;
			break;
		case VIP_DONATOR:
			totalBonus += 17.5;
			break;
		case SUPPORT:
		case MODERATOR:
		totalBonus += 10;
		break;
		case ADMINISTRATOR:
		totalBonus += 20;
			break;
		case DEVELOPER:
		totalBonus += 100;
			break;
		case COMMUNITY_MANAGER:
		case YOUTUBER:
		totalBonus += 20;
			break;
		case OWNER:
			totalBonus += 100;
			break;
		default:
			break;
		}
		
		switch (player.getGameMode()) {
		case HARDCORE_IRONMAN:
			totalBonus += 8.0;
			break;
		case IRONMAN:
			totalBonus += 5.0;
			break;
		case GROUP_IRONMAN:
			totalBonus += 7.5;
			break;
		default:
			break;
		}

		// gets the player's familiar if they have one, and sees if it gives a dr bonus
		if (playerFamiliar != null) {
			for(int[] pet : DRPETS)
				if(playerFamiliar.getSummonNpc().getId() == pet[0])
					totalBonus += pet[1];
		}

		if(player.isDoubleRateActive())
			totalBonus += 100;
		// does stuff for the results
		return totalBonus;
	}

	
	public static boolean hasCollItemEquipped(Player player) {
		for(int itemId : COLLITEMS)
			if(player.getEquipment().contains(itemId))
				return true;
		
		return false;
	}
}
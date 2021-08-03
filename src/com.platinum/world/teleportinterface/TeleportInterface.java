package com.platinum.world.teleportinterface;

import com.platinum.model.Position;
import com.platinum.model.definitions.NPCDrops;
import com.platinum.world.content.transportation.TeleportHandler;
import com.platinum.world.entity.impl.player.Player;

/**
 * @author Emerald
 * @since 8th July 2019
 */

public class TeleportInterface {

	private final static int CATEGORY_NAME_ID = 50508;

	public enum Bosses {
		// this is 1st field etc
		STARTER(50601, "Starter Zone", "Bugs Bunny@gre@(T1)", "Starter Tasks", "@red@HP:@gre@ 2.5k", "", "", 4455,
				new int[] { 3795, 3543, 0 }),
		
		HERCULES(50602, "Hercules", "Hercules@gre@(T2)", "Drops Hercules set ", "@red@HP:@gre@ 150k", "", "", 17,
				new int[] { 2783, 4636, 0 }),
		
		ONSLAUGHT(50603, "Onslaught", "Onslaught(T3)", "Drops a bunch of items", "@red@MASS STARTER BOSS", "@red@HP:@gre@ 225k", "", 422,
		new int[] { 2414, 2856, 0 }, 3000),
		
		LUCARIO(50604, "Lucario", "Lucario@yel@(T3)", "Drops the blessed set", "@red@KC REQ: 50 Hercules", "@red@HP:@gre@ 300k", "", 3263,
				new int[] { 2913, 4759, 0 },2000),
		
		HADES(50605, "Hades", "Hades@yel@(T3)", "Drops Misc items", "@red@KC REQ: 50 Lucario", "@red@HP:@gre@ 350k", "", 15, 
				new int[] { 2095, 3677, 0 }, 3000),
		
		CHARIZARD(50606, "Charizard", "Charizard@yel@(T3)", "Gather Claw Tokens", "For the Shop", "@red@HP:@gre@ 375k", "@red@KC REQ: 75 Hades", 1982, 
				new int[] { 2270, 3240, 0 });

		Bosses(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Bosses(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;
	}

	public enum Monsters {


		
		
		DEFENDERS(50601, "Defenders", "Defenders@yel@(T4)", "Protects Itself", "from Range!", "@red@KC REQ: 125 Charizards", "@red@HP:@gre@ 400k", 9994, 
				new int[] { 2724, 9821, 0 }, 2000),
		
		
		GODZILLA(50602, "Godzilla", "Godzilla@yel@(T4)", "This NPC drops", "the Rex set", "@red@KC REQ: 175 Defenders", "@red@HP:@gre@ 425k", 9932, 
				new int[] { 3374, 9807, 0 }),
		
		DEMONOLM(50603, "Demonic Olm", "Demonic Olm@yel@(T4)", "This NPC drops", "Misc Items", "@red@KC REQ: 200 Godzilla", "@red@HP:@gre@ 450k", 224, 
				new int[] { 2399, 3548, 0 }),
		
		CERBERUS(50604, "Cerberus", "Cerberus@yel@(T4)", "Drops Misc Gear", "@red@KC REQ: 250 Demonic Olm", "@red@HP:@gre@ 500k", "", 1999, 
				new int[] { 1240, 1247, 0 }, 3000),
		
		ZEUS(50605, "Zeus", "Zeus@yel@(T5)", "Drops Zeus set", "@red@KC REQ: 25 Cerberus", "@red@HP:@gre@ 500k", "", 16, 
				new int[] { 2065, 3663, 0 }, 3000),
		
		INFERNAL_BEAST(50606, "Infartico", "Infartico@red@(T5)", "@red@KC REQ: 300 Zeus", "@red@HP:@gre@ 525k", "", "", 9993,
				new int[] { 3479, 3087, 0 }, 3000),
		

		VALOR(50607, "Lord Valor", "Lord Valor@red@(T5)", "Hybrid NPC", "@red@KC REQ: 50 Infartico", "@red@HP:@gre@ 550k", "", 9277, 
		new int[] { 2780, 10000, 0 }),

		SKYROCKET(50608, "Hurricane Warriors", "Hurricane Warriors@red@(T5)", "@red@KC REQ: 500 Lord valors", "@red@HP:@gre@ 600k", "", "", 9944,
				new int[] { 2509, 4693, 0 }),
		
		TRIDENT(50609, "Dzanth", "Dzanth@red@(T6)", "@red@KC REQ: 750 Hurricane Warriors", "@red@HP:@gre@ 625k", "", "", 9273,
				new int[] { 2369, 4944, 0 }),
		
		HARAMBE(50610, "King Kong", "King Kong@red@(T6)", "Multi MASS BOSS", "@red@KC REQ: 1000 Dzanth", "@red@HP:@gre@ 650k)", "", 9903,
				new int[] { 2720, 9880, 0 });

		Monsters(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Monsters(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;
	}

	public enum Wilderness {
		
	

		
		CORPOREAL_BEAST(50601, "Corp Beast", "Corp Beast@red@(T6)", "@red@KC REQ: 1000 King Kong", "@red@HP:@gre@ 650k", "", "", 8133, 
				new int[] { 2886, 4376, 0 }, 4000),
		
		LUCID(50602, "Lucid Warriors", "Lucid Warriors@red@(T7)", "@red@KC REQ: 300 Corp Beasts", "@red@HP:@gre@ 650k", "", "", 9247,
				new int[] { 2557, 4953, 0 }),
		
		HULK(50603, "Hulk", "Hulk@red@(T7)", "@red@KC REQ: 1000 Lucid Warriors", "@red@HP:@gre@ 675k", "", "", 8493,
				new int[] { 3852, 5846, 0 }),
		WIZARDS(50604, "Darkblue Wizards", "Darkblue Wizards@bla@(T7)", "@red@KC REQ: 1000 Hulk", "@red@HP:@gre@ 700k", "", "", 9203,
				new int[] { 2920, 9687, 0 }),
		
		PYROS(50605, "Heated Pyros", "Heated Pyros@bla@(T7)", "@red@KC REQ: 1500 Darkblue wizards", "@red@HP:@gre@ 700k", "", "", 172,
				new int[] { 3040, 4838, 0 }),
		
		PURPLEFIRE_WYRM(50606, "Dark Purple Wyrm", "Dark Purple Wyrm@bla@(T8)", "Its a massboss", "@red@KC REQ: 2500 Pyros", "@red@HP:@gre@ 700k", "", 9935,
				new int[] { 2325, 4586, 0 }, 2000),
		
		TRINITY(50607, "Trinity", "Trinity@bla@(T8)", "ITS a Massboss", "@red@KC REQ: 200 Purple Wyrms", "@red@HP:@gre@ 725k", "", 170,
				new int[] { 2517, 4645, 0 }, 3000),
		CLOUD(50608, "Cloud", "Cloud@bla@(T8)", "@red@KC REQ: 2500 Trinity", "@red@HP:@gre@ 750k", "", "", 169, 
				new int[] { 2539, 5774, 0 }),
		
		HERBAL(50609, "Herbal Rogue", "Herbal Rogue@bla@(T9)", "@red@KC REQ: 2500 Cloud", "@red@HP:@gre@ 750000(750k)", "", "", 219, new int[] { 2737, 5087, 0 },
				4000),
		
		EXODEN(50610, "Exoden", "Exoden@bla@(T9)", "@red@KC REQ: 3500 Herbal Rogue", "@red@HP:@gre@ 800k", "", "", 12239, new int[] { 2540, 10162, 0 },
				4000);

		Wilderness(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Wilderness(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;

	}

	public enum Zones {
	
		

		
		NEX(50601, "Supreme", "Supreme@bla@(T9)", "This npc drops Supreme set", "@red@KC REQ: 3500 Exoden", "@red@HP:@gre@ 825k", "", 3154,
				new int[] { 2599, 4699, 0 },2000),
		
		STORMBREAKER(50602, "Storm Breaker", "Storm Breaker@bla@(T10)", "This drops Stormbreaker", "@red@KC REQ: 3500 Nex", "@red@HP:@gre@ 825k", "", 33,
				new int[] { 3226, 2844, 0 },2000),
		APOLLO(50603, "Apollo Ranger", "Apollo Ranger@bla@(T10)", "This drops Apollo Set", "@red@KC REQ: 3500 Stormbreakers", "@red@HP:@gre@ 850k", "", 1684,
				new int[] { 3178, 4237, 2 },2000),
		TROLL(50604, "Noxious Troll", "Noxious Troll@bla@(T10)", "This drops Noxious Set", "@red@KC REQ: 3500 Apollo Rangers", "@red@HP:@gre@ 900k", "", 5957,
				new int[] { 3232, 3043, 0 },3000),
		AZAZEL(50605, "Azazel Beast", "Azazel Beast@bla@(T10)", "This drops Azazel Set", "@red@KC REQ: 3500 Noxious Trolls", "@red@HP:@gre@ 925k", "", 5958,
				new int[] { 2468, 3372, 0 },3000),
		RAVANA(50606, "Ravana", "Ravana@bla@(T10)", "This drops Detrimental Set", "@red@KC REQ: 3500 Azazel Beasts", "@red@HP:@gre@ 950k", "", 5959,
				new int[] { 3595, 3492, 0 },3000),
		LUMINITIOS(50607, "Luminitous Warriors", "Warriors@bla@(T10)", "This drops Luminitous Set", "@red@KC REQ: 4000 Ravanas", "@red@HP:@gre@ 1m", "", 185,
				new int[] { 2525, 4776, 0 },3000),
		HELLHOUND(50608, "Custom Hellhounds", "Hell Hounds@bla@(T10)", "This drops BFG set", "@red@KC REQ: 5000 Luminitous warriors", "@red@HP:@gre@ 1.25m", "", 6311,
				new int[] { 3176, 3029, 0 },3000);

		Zones(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Zones(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;
	}

	public enum Minigames {
		
		BOX_ZONE(50601, "Box Zone", "Box Zone", "Box Zone For Starters!", "", "", "", 197,
				new int[] { 3344, 3409, 0 }, 4000),
		
		TOKEN_ZONE(50602, "Token Zone", "Earn Tokens", "to be used at", "the Token shop","", "", 729, 
				new int[] { 2526, 2841, 0 }, 4000),
		//DBZ_ZONE(50603, "DBZ Zone", "This place drops DBZ Tokens!", "Multi Area", "", "", "", 100,
				//new int[] { 2141, 5535, 3 }),
		
		EVENTBOSS(50603, "World boss Event", "THIS NPC Spawns", "Every Hour", "Multiple People ", "May be Required!", "", 2745,
				new int[] { 2409, 4679, 0 });
		
		//AMONG(50605, "Among Us", "Among Us Points", "", "", "", "", 600,
				//new int[] { 2511, 3820, 0 });
	

		Minigames(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Minigames(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;
	}

	public enum Cities {
		FRANKENSTIEN(50601, "Frankenstiens Castle", "Frankenstiens Castle", "This Minigame drops", "Tier 1-7 Defenders", "", "", 4291, new int[] { 2845, 3540, 2 }),
		PESTCONTROL(50602, "Pest control", "Pest Control", "Earn points to use", "at the pest control shop", "", "", 3789, new int[] { 2657, 2648, 0 }),
		BARROWSMINIGAME(50603, "Barrows", "Barrows", "Dig your way for some", " fancy diamonds!", "", "", 2025, new int[] { 3564, 3289, 0 }),
		DUNGEON(50604, "Dungeons Minigame", "Dungeons Minigame", "Do you have what it", " takes to survive?", "", "", 499, new int[] { 3309, 9808, 8 }),
		CHALLENGER(50605, "challenging minigame", "challenging minigame", "only for the toughest", "are you tough?", "", "", 50, new int[] { 2333, 4646, 0});
		//HALLOWEEN(50602, "Trios Minigame", "Earn all 3 Keys", "to be used at", "the Trio Chest!","", "", 75, 
			//	new int[] { 3107, 3427, 0 }, 0);
		Cities(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;

		}

		Cities(int textId, String name, String description1, String description2, String description3,
				String description4, String description5, int npcId, int[] teleportCords, int adjustedZoom) {
			this.textId = textId;
			this.name = name;
			this.description1 = description1;
			this.description2 = description2;
			this.description3 = description3;
			this.description4 = description4;
			this.description5 = description5;
			this.npcId = npcId;
			this.teleportCords = teleportCords;
			this.adjustedZoom = adjustedZoom;

		}

		private int textId;
		private String name;
		private String description1, description2, description3, description4, description5;
		private int npcId;
		private int[] teleportCords;
		private int adjustedZoom;
	}

	public static void resetOldData() {
		currentTab = 0;
		currentClickIndex = 0;
	}

	public static void handleTeleports(Player player) {
		switch (currentTab) {
		case 0:
			Bosses bossData = Bosses.values()[currentClickIndex];
			handleBossTeleport(player, bossData);
			break;
		case 1:
			Monsters monsterData = Monsters.values()[currentClickIndex];
			handleMonsterTeleport(player, monsterData);
			break;
		case 2:
			Wilderness wildyData = Wilderness.values()[currentClickIndex];
			handleWildyTeleport(player, wildyData);
			break;
		case 3:
			Zones ZonesData = Zones.values()[currentClickIndex];
			handleZonesTeleport(player, ZonesData);
			break;
		case 4:
			Minigames minigameData = Minigames.values()[currentClickIndex];
			handleMinigameTeleport(player, minigameData);
			break;
		case 5:
			Cities cityData = Cities.values()[currentClickIndex];
			handleCityTeleport(player, cityData);
			break;
		}
	}

	public static void handleBossTeleport(Player player, Bosses bossData) {

		TeleportHandler.teleportPlayer(player,
				new Position(bossData.teleportCords[0], bossData.teleportCords[1], bossData.teleportCords[2]),
				player.getSpellbook().getTeleportType());
	}

	public static void handleMonsterTeleport(Player player, Monsters monsterData) {

		TeleportHandler.teleportPlayer(player,
				new Position(monsterData.teleportCords[0], monsterData.teleportCords[1], monsterData.teleportCords[2]),
				player.getSpellbook().getTeleportType());
	}

	public static void handleWildyTeleport(Player player, Wilderness wildyData) {

		TeleportHandler.teleportPlayer(player,
				new Position(wildyData.teleportCords[0], wildyData.teleportCords[1], wildyData.teleportCords[2]),
				player.getSpellbook().getTeleportType());
	}

	public static void handleZonesTeleport(Player player, Zones skillData) {

		TeleportHandler.teleportPlayer(player,
				new Position(skillData.teleportCords[0], skillData.teleportCords[1], skillData.teleportCords[2]),
				player.getSpellbook().getTeleportType());
	}

	public static void handleMinigameTeleport(Player player, Minigames minigameData) {

		TeleportHandler.teleportPlayer(player, new Position(minigameData.teleportCords[0],
				minigameData.teleportCords[1], minigameData.teleportCords[2]), player.getSpellbook().getTeleportType());
	}

	public static void handleCityTeleport(Player player, Cities cityData) {

		TeleportHandler.teleportPlayer(player,
				new Position(cityData.teleportCords[0], cityData.teleportCords[1], cityData.teleportCords[2]),
				player.getSpellbook().getTeleportType());
	}

	private static void clearData(Player player) {
		for (int i = 50601; i < 50700; i++) {
			player.getPacketSender().sendString(i, "");
		}
	}

	private static int currentTab = 0; // 0 = Boss, 1 = Monsters, 2 = Wildy, 3 = Zones, 4 = Minigame, 5 = Cities

	public static boolean handleButton(Player player, int buttonID) {

		if (!(buttonID >= -14935 && buttonID <= -14836)) {
			return false;
		}
		int index = -1;

		if (buttonID >= -14935) {
			index = 14935 + buttonID;
		}
		if (currentTab == 0) {
			if (index >= 0 && index < Bosses.values().length) {
				System.out.println("Handled boss data [As index was 0]");
				Bosses bossData = Bosses.values()[index];
				currentClickIndex = index;
				sendBossData(player, bossData);
				sendDrops(player, bossData.npcId);
			}
		}
		if (currentTab == 1) {
			if (index >= 0 && index < Monsters.values().length) {
				System.out.println("Handled monster data [As index was 1]");
				Monsters monsterData = Monsters.values()[index];
				currentClickIndex = index;
				sendMonsterData(player, monsterData);
				sendDrops(player, monsterData.npcId);
			}
		}
		if (currentTab == 2) {
			if (index >= 0 && index < Wilderness.values().length) {
				System.out.println("Handled monster data [As index was 1]");
				Wilderness wildyData = Wilderness.values()[index];
				currentClickIndex = index;
				sendWildyData(player, wildyData);
				sendDrops(player, wildyData.npcId);
			}
		}
		if (currentTab == 3) {
			if (index >= 0 && index < Zones.values().length) {
				System.out.println("Handled monster data [As index was 1]");
				Zones ZonesData = Zones.values()[index];
				currentClickIndex = index;
				sendZonesData(player, ZonesData);
				sendDrops(player, ZonesData.npcId);
			}
		}
		if (currentTab == 4) {
			if (index >= 0 && index < Minigames.values().length) {
				System.out.println("Handled monster data [As index was 1]");
				Minigames minigamesData = Minigames.values()[index];
				currentClickIndex = index;
				sendMinigameData(player, minigamesData);
				sendDrops(player, minigamesData.npcId);
			}
		}
		if (currentTab == 5) {
			if (index >= 0 && index < Cities.values().length) {
				System.out.println("Handled monster data [As index was 1]");
				Cities cityData = Cities.values()[index];
				currentClickIndex = index;
				sendCityData(player, cityData);
				sendDrops(player, cityData.npcId);
			}
		}
		return true;

	}

	public static int currentClickIndex = 0;

	public static void sendBossData(Player player, Bosses data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendMonsterData(Player player, Monsters data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendWildyData(Player player, Wilderness data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendZonesData(Player player, Zones data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendMinigameData(Player player, Minigames data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendCityData(Player player, Cities data) {
		player.getPacketSender().sendString(51200, data.description1);
		player.getPacketSender().sendString(51201, data.description2);
		player.getPacketSender().sendString(51202, data.description3);
		player.getPacketSender().sendString(51203, data.description4);
		player.getPacketSender().sendString(51204, data.description5);
		player.getPacketSender().sendNpcOnInterface(50514, data.npcId, data.adjustedZoom != 0 ? data.adjustedZoom : 0);
	}

	public static void sendBossTab(Player player) {
		player.getPacketSender().sendInterface(50500);
		currentTab = 0;
		clearData(player);
		resetOldData();
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Starters");
		for (Bosses data : Bosses.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}

	}

	public static void sendMonsterTab(Player player) {
		currentTab = 1;
		clearData(player);
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Monsters");
		for (Monsters data : Monsters.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}
	}

	public static void sendWildyTab(Player player) {
		currentTab = 2;
		clearData(player);
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Hardened ");
		for (Wilderness data : Wilderness.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}
	}

	public static void sendZonesTab(Player player) {
		currentTab = 3;
		clearData(player);
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Expert");
		for (Zones data : Zones.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}
	}

	public static void sendMinigamesTab(Player player) {
		currentTab = 4;
		clearData(player);
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Zones");
		for (Minigames data : Minigames.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}
	}

	public static void sendCitiesTab(Player player) {
		currentTab = 5;
		clearData(player);
		player.getPacketSender().sendString(CATEGORY_NAME_ID, "@red@Minigames");
		for (Cities data : Cities.values()) {
			player.getPacketSender().sendString(data.textId, data.name);
		}
	}

	public static void sendDrops(Player player, int npcId) {
		player.getPacketSender().resetItemsOnInterface(51251, 100);
		try {
			NPCDrops drops = NPCDrops.forId(npcId);
			if(drops == null) {
				System.out.println("Was null");
				return;
			}
			for (int i = 0; i < drops.getDropList().length; i++) {
				
				player.getPacketSender().sendItemOnInterface(51251, drops.getDropList()[i].getId(), i,
						drops.getDropList()[i].getItem().getAmount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
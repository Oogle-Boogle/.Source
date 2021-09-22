package com.platinum.world.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.GameObject;
import com.platinum.model.GroundItem;
import com.platinum.model.Item;
import com.platinum.model.Position;
import com.platinum.world.World;
import com.platinum.world.clip.region.RegionClipping;
import com.platinum.world.content.raids.addons.RaidChest;
import com.platinum.world.entity.impl.GroundItemManager;
import com.platinum.world.entity.impl.player.Player;

/**
 * Handles customly spawned objects (mostly global but also privately for players)
 * @author Gabriel Hannason
 */
public class CustomObjects {
	
	private static final int DISTANCE_SPAWN = 70; //Spawn if within 70 squares of distance
	public static final CopyOnWriteArrayList<GameObject> CUSTOM_OBJECTS = new CopyOnWriteArrayList<GameObject>();
	public static final CopyOnWriteArrayList<RaidChest> RAID_CHESTS = new CopyOnWriteArrayList<RaidChest>();
	
	public static void init() {
		for(int i = 0; i < CLIENT_OBJECTS.length; i++) {
			int id = CLIENT_OBJECTS[i][0];
			int x = CLIENT_OBJECTS[i][1];
			int y = CLIENT_OBJECTS[i][2];
			int z = CLIENT_OBJECTS[i][3];
			int face = CLIENT_OBJECTS[i][4];
			GameObject object = new GameObject(id, new Position(x, y, z));
			object.setFace(face);
			RegionClipping.addObject(object);
		}
		for(int i = 0; i < CUSTOM_OBJECTS_SPAWNS.length; i++) {
			int id = CUSTOM_OBJECTS_SPAWNS[i][0];
			int x = CUSTOM_OBJECTS_SPAWNS[i][1];
			int y = CUSTOM_OBJECTS_SPAWNS[i][2];
			int z = CUSTOM_OBJECTS_SPAWNS[i][3];
			int face = CUSTOM_OBJECTS_SPAWNS[i][4];
			GameObject object = new GameObject(id, new Position(x, y, z));
			object.setFace(face);
			CUSTOM_OBJECTS.add(object);
			World.register(object);
		}
	}
	
	private static void handleRaidList(GameObject object, String handleType) {
		switch(handleType.toUpperCase()) {
		case "DELETE":
			for(GameObject objects : CUSTOM_OBJECTS) {
				if(objects.getId() == object.getId() && object.getPosition().equals(objects.getPosition())) {
					CUSTOM_OBJECTS.remove(objects);
				}
			}
			break;
		case "ADD":
			if(!CUSTOM_OBJECTS.contains(object)) {
				CUSTOM_OBJECTS.add(object);
			}
			break;
		case "EMPTY":
			CUSTOM_OBJECTS.clear();
			break;
		}
	}

	private static void handleRaidList(RaidChest object, String handleType) {
		switch (handleType.toUpperCase()) {
		case "DELETE":
			for (RaidChest objects : RAID_CHESTS) {
				if (objects.getId() == object.getId() && object.getPosition().equals(objects.getPosition())) {
					RAID_CHESTS.remove(objects);
				}
			}
			break;
		case "ADD":
			if (!RAID_CHESTS.contains(object)) {
				RAID_CHESTS.add(object);
			}
			break;
		case "EMPTY":
			RAID_CHESTS.clear();
			break;
		}
	}
	
	public static RaidChest getRaidChest(Position position) {
		for(RaidChest chest : RAID_CHESTS) {
			if(chest.getPosition().sameAs(position)) {
				if(chest instanceof RaidChest) {
					return (RaidChest) chest;
				}
			}
		}
		return null;
	}
	
	public static void deleteGlobalObjectWithinDistance(RaidChest object) {
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				deleteObject(player, object);
			}
		}
	}
	
	public static void spawnGlobalObjectWithinDistance(RaidChest object) {
		handleRaidList(object, "add");
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				spawnObject(player, object);
			}
		}
	}
	
	public static void deleteObject(Player p, RaidChest object) {
		handleRaidList(object, "delete");
		p.getPacketSender().sendObjectRemoval(object);
		if (RegionClipping.objectExists(object)) {
			RegionClipping.removeObject(object);
		}
	}
	
	public static void spawnObject(Player p, RaidChest object) {
		if (object.getId() != -1) {
			p.getPacketSender().sendObject(object);
			if (!RegionClipping.objectExists(object)) {
				RegionClipping.addObject(object);
			}
		} else {
			deleteObject(p, object);
		}
	}
	public static void spawnObject(Player p, GameObject object) {
		if(object.getId() != -1) {
			p.getPacketSender().sendObject(object);
			if(!RegionClipping.objectExists(object)) {
				RegionClipping.addObject(object);
			}
		} else {
			deleteObject(p, object);
		}
	}
	
	public static void deleteObject(Player p, GameObject object) {
		p.getPacketSender().sendObjectRemoval(object);
		if(RegionClipping.objectExists(object)) {
			RegionClipping.removeObject(object);
		}
	}
	
	public static void deleteGlobalObject(GameObject object) {
		handleRaidList(object, "delete");
		World.deregister(object);
	}

	public static void spawnGlobalObject(GameObject object) {
		handleRaidList(object, "add");
		World.register(object);
	}
	
	public static void spawnGlobalObjectWithinDistance(GameObject object) {
		for(Player player : World.getPlayers()) {
			if(player == null)
				continue;
			if(object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				spawnObject(player, object);
			}
		}
	}
	
	public static void deleteGlobalObjectWithinDistance(GameObject object) {
		for(Player player : World.getPlayers()) {
			if(player == null)
				continue;
			if(object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
				deleteObject(player, object);
			}
		}
	}
	
		public static boolean objectExists(Position pos) {
			return getGameObject(pos) != null;
		}

		public static GameObject getGameObject(Position pos) {
			for(GameObject objects : CUSTOM_OBJECTS) {
				if(objects != null && objects.getPosition().equals(pos)) {
					return objects;
				}
			}
			return null;
		}

		public static void handleRegionChange(Player p) {
			for(GameObject object: CUSTOM_OBJECTS) {
				if(object == null)
					continue;
				if(object.getPosition().isWithinDistance(p.getPosition(), DISTANCE_SPAWN)) {
					spawnObject(p, object);
				}
			}
		}
	
		public static void objectRespawnTask(Player p, final GameObject tempObject, final GameObject mainObject, final int cycles) {
			deleteObject(p, mainObject);
			spawnObject(p, tempObject);
			TaskManager.submit(new Task(cycles) {
				@Override
				public void execute() {
					deleteObject(p, tempObject);
					spawnObject(p, mainObject);
					this.stop();
				}
			});
		}
		
		public static void globalObjectRespawnTask(final GameObject tempObject, final GameObject mainObject, final int cycles) {
			deleteGlobalObject(mainObject);
			spawnGlobalObject(tempObject);
			TaskManager.submit(new Task(cycles) {
				@Override
				public void execute() {
					deleteGlobalObject(tempObject);
					spawnGlobalObject(mainObject);
					this.stop();
				}
			});
		}

		public static void globalObjectRemovalTask(final GameObject object, final int cycles) {
			spawnGlobalObject(object);
			TaskManager.submit(new Task(cycles) {
				@Override
				public void execute() {
					deleteGlobalObject(object);
					this.stop();
				}
			});
		}

	public static void globalFiremakingTask(final GameObject fire, final Player player, final int cycles) {
		spawnGlobalObject(fire);
		TaskManager.submit(new Task(cycles) {
			@Override
			public void execute() {
				deleteGlobalObject(fire);
				if(player.getInteractingObject() != null && player.getInteractingObject().getId() == 2732) {
					player.setInteractingObject(null);
				}
				this.stop();
			}
			@Override
			public void stop() {
				setEventRunning(false);
				GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(592), fire.getPosition(), player.getUsername(), false, 150, true, 100));
			}
		});
	}

	/**
	 * Contains
	 *
	 * @param ObjectId - The object ID to spawn
	 * @param absX - The X position of the object to spawn
	 * @param absY - The Y position of the object to spawn
	 * @param Z - The Z position of the object to spawn
	 * @param face - The position the object will face
	 */

	//Only adds clips to these objects, they are spawned clientsided
	//NOTE: You must add to the client's customobjects array to make them spawn, this is just clipping!
	private static final int[][] CLIENT_OBJECTS = {

			//Flub BossMinigame Stairs
			{12131, 2334, 4645, 0, 1}, //Boss Minigame Starting Stairs
			{4126, 2336, 4644, 0 ,1},//Boss Minigame Reward Chest


			// NEW HOME
			/*{4483, 3363, 9652, 0, 0},//Bank North
			{4483, 3376, 9640, 0, 1},//Bank East
			{4483, 3363, 9627, 0, 4},//Bank South
			{4483, 3351, 9640, 0, 3},//Bank West*/

			/*{-1, 1906, 5222, 0, 0},
			{-1, 1906, 5221, 0, 0},
			{-1, 1907, 5222, 0, 0},
			{-1, 1908, 5222, 0, 0},
			{-1, 1909, 5221, 0, 0},
			//{-1, 1909, 5222, 0, 0},
			{-1, 1908, 5223, 0, 0},
			{-1, 1907, 5223, 0, 0},*/

			{2469, 1910, 5201, 0, 0},

			/*{3045, 1906, 5221, 0, 1},
			{3045, 1906, 5222, 0, 1},

			{3045, 1909, 5221, 0, 1},
			//{3045, 1909, 5222, 0, 1},

			{3045, 1908, 5220, 0, 0},
			{3045, 1907, 5220, 0, 0},

			{3045, 1908, 5223, 0, 0},
			{3045, 1907, 5223, 0, 0},*/

			{6420, 1914, 5226, 0, 4},
			{172, 1914, 5224, 0, 4},
			{6421, 1909, 5228, 0, 4},


			{2469, 3063, 2863, 0, 4}, //delete
			{2478, 2844, 4832, 0, 0},
			{2844, 2153, 4839, 0, 0},

			{172, 3021, 2847, 0, 1}, //delete
			{6420, 3021, 2840, 0, 1}, //delete

			{172, 3030, 2847, 0, 3}, //delete
			{6420, 3030, 2840, 0, 3}, //delete

			{172, 3049, 2847, 0, 1}, //delete
			{6420, 3049, 2840, 0, 1}, //delete
			{3479, 3038, 2844, 0, 2},//St
			{3479, 3038, 2840, 0, 0},//St
			{-1, 3038, 2843, 0, 0},

			{172, 3058, 2847, 0, 3}, //delete
			{6420, 3058, 2840, 0, 3}, //delete

			{54587, 3016, 2863, 0, 4}, //delete
			{42611, 3013, 2863, 0, 4}, //delete
			
			{2644, 3208, 3166, 0, 0},

			{409, 3011, 2841, 0, 1}, //delete
			{411, 3011, 2845, 0, 1}, //delete

			{2213, 3051, 2852, 0, 4}, //delete
			{2213, 3052, 2852, 0, 4}, //delete
			{2213, 3054, 2852, 0, 4}, //delete
			{2213, 3055, 2852, 0, 4}, //delete
			{2213, 3057, 2852, 0, 4}, //delete

			{2213, 3023, 2852, 0, 4}, //delete
			{2213, 3024, 2852, 0, 4}, //delete
			{2213, 3026, 2852, 0, 4}, //delete
			{2213, 3027, 2852, 0, 4}, //delete
			{2213, 3029, 2852, 0, 4}, //delete

			{2213, 3056, 2835, 0, 0}, //delete
			{2213, 3055, 2835, 0, 0}, //delete
			{2213, 3053, 2835, 0, 0}, //delete
			{2213, 3052, 2835, 0, 0}, //delete
			{2213, 3050, 2835, 0, 0}, //delete

			{2213, 3028, 2835, 0, 0}, //delete
			{2213, 3027, 2835, 0, 0}, //delete
			{2213, 3025, 2835, 0, 0}, //delete
			{2213, 3024, 2835, 0, 0}, //delete
			{2213, 3022, 2835, 0, 0}, //delete


			{411, 3209, 3197, 0, 0}, //delete
			{409, 3209, 3211, 0, 2},

			{359, 2903, 4067, 0, 1}, //easy box
			{360, 2904, 4067, 0, 1}, //med box
			{361, 2905, 4067, 0, 1}, //hard box

			{2213, 3183, 3211, 0, 1}, //bank
			{2213, 3183, 3210, 0, 1}, //bank
			{2213, 3183, 3209, 0, 1}, //bank
			{2213, 3183, 3208, 0, 1}, //bank
			{2213, 3183, 3200, 0, 1}, //bank
			{2213, 3183, 3199, 0, 1}, //bank
			{2213, 3183, 3198, 0, 1}, //bank
			{2213, 3183, 3197, 0, 1}, //bank


			{2213, 3215, 3211, 0, 3}, //bank
			{2213, 3215, 3210, 0, 3}, //bank
			{2213, 3215, 3209, 0, 3}, //bank
			{2213, 3215, 3208, 0, 3}, //bank
			{2213, 3215, 3200, 0, 3}, //bank
			{2213, 3215, 3199, 0, 3}, //bank
			{2213, 3215, 3198, 0, 3}, //bank
			{2213, 3215, 3197, 0, 3}, //bank


			{411, 3188, 3211, 0, 2}, //delete
			{409, 3188, 3197, 0, 0},

			/*** new home ***/
			{-1, 2897, 4067, 0, 2}, //Starter key chest
			{-1, 2899, 4067, 0, 2}, //Starter key chest
			{172, 2897, 4067, 0, 0}, //Starter key chest
			{6420, 2899, 4067, 0, 0}, //Starter key chest
			{54587, 2916, 4041, 0, 3}, //chest for raids new
			{-1, 3188, 3211, 0, 2}, //delete
			{-1, 3188, 3197, 0, 0},
			{409, 2930, 4078, 0, 2}, //delete
			{411, 2934, 4074, 0, 3},


			/*** Bas Extreme Donator zone ***/

			{47180, 3413, 2919, 0, 0}, //Frost drags teleport

			{4875, 3451, 2881, 0, 0}, //Thief stalls
			{4874, 3450, 2881, 0, 0}, //Thief stalls
			{4876, 3449, 2881, 0, 0}, //Thief stalls
			{4877, 3448, 2881, 0, 0}, //Thief stalls
			{4878, 3447, 2881, 0, 0}, //Thief stalls
			{13493, 3446, 2881, 0, 0}, //Thief stalls
			{4875, 3200, 3431, 0, 0}, //theif stalls
			{4874, 3200, 3432, 0, 0}, //theif stalls
			{4876, 3200, 3433, 0, 0}, //theif stalls
			{4877, 3200, 3434, 0, 0}, //theif stalls
			{4878, 3200, 3435, 0, 0}, //theif stalls
			//theif stalls

			{2403, 2596, 5732, 0, 2},
			{2403, 2596, 5723, 0, 0},

			// all the new minigame objects
			{2468, 2583, 4635, 0, 0},
			{2469, 2320, 3670, 0, 0},
			{53, 2583, 4620, 0, 0},
			{52, 2582, 4620, 0, 0},

			/*** Home zone ***/
			{2213, 2330, 3692, 0, 2}, //bank
			{2213, 2332, 3692, 0, 2}, //bank
			{2213, 2334, 3692, 0, 2}, //bank
			{2213, 2336, 3692, 0, 2}, //bank
			{2213, 2338, 3692, 0, 2}, //bank
			{2213, 2340, 3692, 0, 2}, //bank
			{2213, 2342, 3692, 0, 2}, //bank


			{2213, 3219, 2797, 0, 2}, //delzone
			{2213, 3220, 2797, 0, 2}, //delzone
			{2213, 3221, 2797, 0, 2}, //delzone
			{2213, 3222, 2797, 0, 2}, //delzone
			{2213, 3223, 2797, 0, 2}, //delzone
			{2213, 3223, 2763, 0, 0}, //delzone
			{2213, 3222, 2763, 0, 0}, //delzone
			{2213, 3221, 2763, 0, 0}, //delzone
			{2213, 3220, 2763, 0, 0}, //delzone
			{2213, 3219, 2763, 0, 0}, //delzone
			{49347, 3223, 2771, 0, 0}, // Deluxe Chest
			{4114, 3306, 5289, 0, 0}, // Vip Chest
			{2213, 3293, 5285, 0, 1}, // Vip bank
			{2213, 3293, 5284, 0, 1}, // Vip bank
			{2213, 3293, 5283, 0, 1}, // Vip bank

			{49347, 3219, 2771, 0, 0}, // Deluxe Chest
			{49347, 3223, 2789, 0, 2}, // Deluxe Chest
			{49347, 3219, 2789, 0, 2}, // Deluxe Chest
			{409, 3228, 2771, 0, 0}, // Deluxe altar
			{411, 3215, 2771, 0, 0}, // Deluxe altar
			{409, 3215, 2789, 0, 2}, // Deluxe altar
			{411, 3228, 2789, 0, 2}, // Deluxe altar
			// raids portal
			//{ 4389, 2329, 3674, 0, 1 }, // raids portal
			/*
			* Remove Uber Zone Objects
			*/
			{-1, 2425, 4714, 0, 0},
			{-1, 2420, 4716, 0, 0},
			{-1, 2426, 4726, 0, 0},
			{-1, 2420, 4709, 0, 0},
			{-1, 2419, 4698, 0, 0},
			{-1, 2420, 4700, 0, 0},
			{-1, 2399, 4721, 0, 0},
			{-1, 2398, 4721, 0, 0},
			{-1, 2399, 4720, 0, 0},
			{-1, 2395, 4722, 0, 0},
			{-1, 2398, 4717, 0, 0},
			{-1, 2396, 4717, 0, 0},
			{-1, 2396, 4718, 0, 0},
			{-1, 2396, 4719, 0, 0},
			{-1, 2395, 4718, 0, 0},
			{-1, 2394, 4711, 0, 0},
			{-1, 2396, 4711, 0, 0},
			{-1, 2397, 4711, 0, 0},
			{-1, 2397, 4713, 0, 0},
			{-1, 2399, 4713, 0, 0},
			{-1, 2402, 4726, 0, 0},
			{-1, 2407, 4728, 0, 0},
			{-1, 2405, 4724, 0, 0},
			{-1, 2409, 4705, 0, 0},
			{-1, 2410, 4704, 0, 0},
			{-1, 2407, 4702, 0, 0},
			{-1, 2407, 4701, 0, 0},
			{-1, 2408, 4701, 0, 0},
			{-1, 2412, 4701, 0, 0},
			{-1, 2413, 4701, 0, 0},
			{-1, 2414, 4703, 0, 0},
			{-1, 2416, 4714, 0, 0},
			{-1, 2412, 4732, 0, 0},
			{-1, 2413, 4729, 0, 0},
			{-1, 2414, 4733, 0, 0},
			{-1, 2415, 4730, 0, 0},
			{-1, 2416, 4730, 0, 0},
			{-1, 2416, 4731, 0, 0},
			{-1, 2419, 4731, 0, 0},
			{-1, 2420, 4731, 0, 0},
			{-1, 2420, 4732, 0, 0},
			{-1, 2415, 4725, 0, 0},
			{-1, 2417, 4729, 0, 0},
			{-1, 2418, 4727, 0, 0},
			{-1, 2418, 4723, 0, 0},
			{-1, 2419, 4722, 0, 0},
			{-1, 2420, 4726, 0, 0},
			{-1, 2415, 4725, 0, 0},
			{-1, 2417, 4729, 0, 0},
			{-1, 2418, 4727, 0, 0},
			{-1, 2418, 4723, 0, 0},
			{-1, 2419, 4722, 0, 0},
			{-1, 2420, 4726, 0, 0},

			{11231, 2771, 9340, 0, 3}, // Last Man Standing Object (Chest)
			{11231, 2788, 9320, 0, 3}, // Last Man Standing Object (Chest)
			{11231, 2809, 9338, 0, 3}, // Last Man Standing Object (Chest)
			{11231, 2809, 9307, 0, 3}, // Last Man Standing Object (Chest)
			{11231, 2785, 9307, 0, 3}, // Last Man Standing Object (Chest)
			{11231, 2769, 9297, 0, 3}, // Last Man Standing Object (Chest)

			//{2470, 2317, 3670, 0, 0},

			{13405, 3439, 2906, 0, 1}, //House teleport
			{2183, 2491, 10165, 0, 3},//Dungeon Chest

			{4306, 3431, 2872, 0, 2}, //Anvil
			{6189, 3433, 2871, 0, 3}, //Furnace

			{10091, 3452, 2871, 0, 0}, //Rocktail fishing 
			{10091, 3449, 2867, 0, 0}, //Rocktail fishing 

			{14859, 3439, 2872, 0, 0}, //Rune ore 
			{14859, 3442, 2871, 0, 0}, //Rune ore 
			{14859, 3444, 2870, 0, 0}, //Rune ore 
			{14859, 3445, 2868, 0, 0}, //Rune ore 
			{14859, 3443, 2869, 0, 0}, //Rune ore 
			{14859, 3441, 2869, 0, 0}, //Rune ore 
			{14859, 3439, 2870, 0, 0}, //Rune ore 
			{14859, 3437, 2872, 0, 0}, //Rune ore 

			{1306, 3422, 2870, 0, 0}, //Magic tree's 
			{1306, 3422, 2872, 0, 0}, //Magic tree's 
			{1306, 3422, 2874, 0, 0}, //Magic tree's 
			{1306, 3422, 2876, 0, 0}, //Magic tree's 
			{1306, 3422, 2878, 0, 0}, //Magic tree's 
			{1306, 3422, 2880, 0, 0}, //Magic tree's 
			{1306, 3422, 2882, 0, 0}, //Magic tree's 

			/**
			 * Minigame
			*/

			{23095, 2457, 10164, 0, 1}, //portal room 2
			{13619, 2460, 10136, 0, 1}, //portal room 1
			{13619, 2460, 10135, 0, 1}, //portal room 1


			{10804, 3213, 3223, 0, 0}, //portal for raids
			{2469, 3213, 3218, 0, 0}, //portal for raids
			{42611, 3217, 3225, 0, 0}, //portal for raids
			{42611, 2310, 3675, 0, 3}, //portal for raids
			{54587, 2323, 3677, 0, 3}, //chest for raids
			{54587, 3215, 3225, 0, 0}, //chest for raids new

			{1306, 2241, 3741, 0, 0}, //Magic tree's 
			{1306, 2241, 3738, 0, 0}, //Magic tree's 
			{1306, 2241, 3735, 0, 0}, //Magic tree's 

			{14859, 2242, 3751, 0, 0}, //Rune ore 
			{14859, 2242, 3750, 0, 0}, //Rune ore 
			{14859, 2242, 3749, 0, 0}, //Rune ore 
			{14859, 2242, 3748, 0, 0}, //Rune ore 
			{14859, 2242, 3747, 0, 0}, //Rune ore 
			{14859, 2242, 3746, 0, 0}, //Rune ore 
			{14859, 2242, 3745, 0, 0}, //Rune ore 
			{14859, 2242, 3752, 0, 0}, //Rune ore 
			{14859, 2242, 3753, 0, 0}, //Rune ore 

			{3192, 3087, 3504, 0, 2}, //Scoreboard

			{409, 3443, 2918, 0, 2}, //Altar 
			{6552, 2337, 3657, 0, 2},
			{8749, 3445, 2913, 0, 3}, //Altar 
			{411, 3441, 2910, 0, 0}, //Altar 
			{13179, 3439, 2912, 0, 2}, //Altar 

			{409, 2331, 3652, 0, 0}, //delAltar 
			{411, 2337, 3652, 0, 0}, //delAltar 
			{10660, 3217, 2795, 0, 0}, // Custom Deluxe Tree
			{4090, 2332, 3657, 0, 1}, // special altar
			{10804, 2320, 3680, 0, 1}, //trapdoor 1
			{26301, 3309, 9807, 0, 2},


			{2213, 3425, 2930, 0, 0}, //Banks
			{2213, 3426, 2930, 0, 0}, //Banks
			{2213, 3427, 2930, 0, 0}, //Banks
			{2213, 3428, 2930, 0, 0}, //Banks

			{2213, 3426, 2894, 0, 1}, //Banks
			{2213, 3426, 2893, 0, 1}, //Banks
			{2213, 3426, 2892, 0, 1}, //Banks
			{2213, 3426, 2891, 0, 1}, //Banks
			{2213, 3426, 2890, 0, 1}, //Banks
			{2213, 3426, 2889, 0, 1}, //Banks


			{10503, 3456, 2876, 0, 0}, //rocks to fix random wall
			{10503, 3456, 2877, 0, 0}, //rocks to fix random wall
			{10503, 3456, 2878, 0, 0}, //rocks to fix random wall
			{10503, 3456, 2879, 0, 0}, //rocks to fix random wall		
			{10503, 3446, 2889, 0, 0}, //rocks to fix random wall
			{10503, 3438, 2904, 0, 0}, //rocks to fix random wall
			{10503, 3415, 2917, 0, 0}, //rocks to fix random wall
			{10503, 3411, 2925, 0, 0}, //rocks to fix random wall

			/*** Bas Extreme Donator zone end ***/
			{2646, 3207, 3162, 0, 0}, // flax
			{2646, 3205, 3159, 0, 0}, // flax
			{2646, 3207, 3158, 0, 0}, // flax
			{2646, 3207, 3155, 0, 0}, // flax
			{2646, 3205, 3156, 0, 0}, // flax
			{2646, 3205, 3164, 0, 0}, // flax
			{2646, 3205, 3162, 0, 0}, // flax

			/*** Deluxe Zone ***/

			{10660, 1906, 4655, 0, 0}, // Custom Deluxe Tree
			{10660, 1906, 4649, 0, 0}, // Custom Deluxe Tree
			{4090, 3291, 5277, 0, 1},

			/*** Bas Gambling area ***/
			{2213, 2729, 3467, 0, 3}, //bank
			{2213, 2729, 3468, 0, 3}, //bank
			{2213, 2729, 3469, 0, 3}, //bank
			{2213, 2729, 3470, 0, 3}, //bank
			{2213, 2729, 3471, 0, 3}, //bank

			/*** Bas Vip Zone ***/

			{10660, 1905, 4651, 0, 0}, // Custom Deluxe Tree
			{10660, 1905, 4657, 0, 0}, // Custom Deluxe Tree
			{4090, 1929, 4663, 0, 0}, // Special altar
			{409, 1931, 4663, 0, 0}, // Alter
			{411, 1934, 4663, 0, 0}, // Chaos altar
			{1306, 1898, 4652, 0, 0}, //Magic tree's 
			{1306, 1898, 4655, 0, 0}, //Magic tree's 
			{1306, 1898, 4658, 0, 0}, //Magic tree's 
			{1306, 1898, 4661, 0, 0}, //Magic tree's  
			{8702, 1897, 4639, 0, 0}, //Rocktail fishing 
			{8702, 1897, 4641, 0, 0}, //Rocktail fishing


			{2213, 1906, 4639, 0, 1}, //bank
			{2213, 1906, 4640, 0, 1}, //bank
			{2213, 1906, 4641, 0, 1}, //bank
			{2213, 1906, 4642, 0, 1}, //bank
			{2213, 1905, 4643, 0, 0}, //bank
			{2213, 1904, 4643, 0, 0}, //bank
			{2213, 1903, 4642, 0, 1}, //bank
			{2213, 1903, 4641, 0, 1}, //bank
			{2213, 1903, 4640, 0, 1}, //bank
			{2213, 1903, 4639, 0, 1}, //bank
			{2213, 1904, 4638, 0, 0}, //bank
			{2213, 1905, 4638, 0, 0}, //bank
			{2213, 1945, 4642, 0, 1}, //bank
			{2213, 1945, 4641, 0, 1}, //bank
			{2213, 1945, 4640, 0, 1}, //bank
			{2213, 1945, 4639, 0, 1}, //bank
			{2213, 1944, 4638, 0, 0}, //bank
			{2213, 1943, 4638, 0, 0}, //bank
			{2213, 1942, 4639, 0, 1}, //bank
			{2213, 1942, 4640, 0, 1}, //bank
			{2213, 1942, 4641, 0, 1}, //bank
			{2213, 1942, 4642, 0, 1}, //bank
			{2213, 1944, 4643, 0, 0}, //bank
			{2213, 1943, 4643, 0, 0}, //bank

			/*** DUNGEON FLOOR 1***/

			{3193, 3308, 9813, 8, 0}, //bank

			/*** Bas Varrock home ***/

			{172, 2331, 3685, 0, 2}, //Starter key chest

			{172, 3201, 3212, 0, 1}, //Starter key chest
			{6420, 3197, 3212, 0, 3}, //Starter key chest
			{13179, 3090, 3511, 0, 3}, //veng
			{6552, 2327, 3697, 0, 3},
			{409, 3085, 3508, 0, 1}, //pray altar
			{411, 3085, 3511, 0, 1}, //turmoil
			//{884, 2325, 3682, 0, 1}, //well
			{6420, 2341, 3685, 0, 2}, //medium chest

			{3192, 2343, 3661, 0, 2}, //scoreboard

			{-1, 3217, 3436, 0, 0}, //remove stall
			{-1, 3219, 3436, 0, 0}, //remove stall
			{-1, 3220, 3431, 0, 0}, //remove stall
			{-1, 3220, 3425, 0, 0}, //remove stall
			{-1, 3223, 3434, 0, 0}, //remove oak
			{-1, 3226, 3431, 0, 0}, //remove plant
			{-1, 2728, 3350, 0, 0}, //remove plant
			{-1, 2729, 3350, 0, 0}, //remove plant

			/*** Bas Varrock home end ***/


			/*** Bas UBER Donator zone ***/

			{8749, 2307, 9806, 0, 1}, //special attack altar 

//			{411, 2307, 9807, 0, 1}, //pray altar
//			{409, 2307, 9805, 0, 1}, //pray switch altar

			{2213, 2317, 9798, 0, 0}, //bank
			{2213, 2316, 9798, 0, 0}, //bank
			{2213, 2315, 9798, 0, 0}, //bank
			{2213, 2314, 9798, 0, 0}, //bank
			{2213, 2313, 9798, 0, 0}, //bank
			{2213, 2312, 9798, 0, 0}, //bank
			{2213, 2311, 9798, 0, 0}, //bank
			{2213, 2310, 9798, 0, 0}, //bank
			{2213, 2309, 9798, 0, 0}, //bank

			{-1, 2325, 9798, 0, 0}, //remove box
			{-1, 2324, 9798, 0, 0}, //remove barrel
			{-1, 2324, 9799, 0, 0}, //remove boxes
			{-1, 2320, 9798, 0, 0}, //remove chair
			{-1, 2319, 9799, 0, 0}, //remove workspace
			{-1, 2319, 9798, 0, 0}, //remove workspace
			{-1, 2318, 9798, 0, 0}, //remove workspace
			{-1, 2309, 9799, 0, 0}, //remove workspace
			{-1, 2321, 9800, 0, 0}, //remove workspace
			{-1, 2309, 9806, 0, 0}, //remove workspace
			{-1, 2318, 9801, 0, 0}, //remove workspace
			{-1, 2327, 9800, 0, 0}, //remove workspace
			{-1, 2327, 9799, 0, 0}, //remove workspace
			{-1, 2327, 9798, 0, 0}, //remove workspace
			{-1, 2326, 9798, 0, 0}, //remove workspace

			{14859, 2330, 9795, 0, 0}, //rune ore's
			{14859, 2329, 9794, 0, 0}, //rune ore's
			{14859, 2328, 9793, 0, 0}, //rune ore's
			{14859, 2327, 9793, 0, 0}, //rune ore's

			{6189, 2324, 9794, 0, 3}, //Smith bars
			{4306, 2331, 9798, 0, 1}, //Anvil
			{4306, 2331, 9800, 0, 1}, //Anvil

			{13493, 2317, 9801, 0, 2}, //thief stall
			{13493, 2309, 9802, 0, 2}, //thief stall

			{8702, 2323, 9799, 0, 0}, //fish spot

			{2732, 2316, 9809, 0, 0}, //fire

			{1306, 2318, 9807, 0, 0}, //magic trees
			{1306, 2320, 9807, 0, 0}, //magic trees
			{1306, 2322, 9807, 0, 0}, //magic trees


			/*** Bas UBER Donator zone end ***/

			/*** Bas Donator zone ***/

			{11933, 3353, 9622, 0, 0},//Tin Ore
			{11933, 3351, 9620, 0, 0},//Tin Ore
			{11936, 3349, 9622, 0, 0},//Copper Ore
			{11936, 3347, 9620, 0, 0},//Copper Ore
			{11954, 3344, 9620, 0, 0},//Iron Ore
			{11954, 3345, 9622, 0, 0},//Iron Ore
			{11954, 3343, 9623, 0, 0},//Iron Ore
			{11963, 3345, 9625, 0, 0},//Coal Ore
			{11963, 3344, 9627, 0, 0},//Coal Ore
			{11963, 3345, 9629, 0, 0},//Coal Ore
			{11963, 3346, 9631, 0, 0},//Coal Ore
			{11951, 3347, 9628, 0, 0},//Gold Ore
			{11951, 3347, 9628, 0, 0},//Gold Ore
			{11951, 3347, 9624, 0, 0},//Gold Ore
			{11947, 3351, 9623, 0, 0},//Mithril Ore
			{11947, 3350, 9626, 0, 0},//Mithril Ore
			{11947, 3349, 9628, 0, 0},//Mithril Ore

			{11941, 3373, 9622, 0, 0},//Adamant Ore
			{11941, 3376, 9621, 0, 0},//Adamant Ore
			{11941, 3379, 9622, 0, 0},//Adamant Ore
			{11941, 3383, 9623, 0, 0},//Adamant Ore
			{11941, 3382, 9626, 0, 0},//Adamant Ore
			{11941, 3381, 9629, 0, 0},//Adamant Ore
			{14859, 3378, 9627, 0, 0},//Rune Ore
			{14859, 3375, 9624, 0, 0},//Rune Ore

			{1307, 3382, 9651, 0, 0},//Tree's
			{1307, 3381, 9648, 0, 0},//Tree's
			{1307, 3383, 9655, 0, 0},//Tree's
			{1309, 3382, 9657, 0, 0},//Tree's
			{1309, 3378, 9658, 0, 0},//Tree's
			{1309, 3375, 9658, 0, 0},//Tree's
			{1309, 3371, 9657, 0, 0},//Tree's

			{1308, 3355, 9657, 0, 0},//Tree's
			{1308, 3351, 9659, 0, 0},//Tree's
			{1281, 3346, 9658, 0, 0},//Tree's
			{1281, 3344, 9656, 0, 0},//Tree's
			{1278, 3344, 9652, 0, 0},//Tree's
			{1278, 3345, 9648, 0, 0},//Tree's

			{28716, 3376, 9632, 0, 1},//Obelisk

			{13405, 3376, 9646, 0, 1},//Portal construction

			{4875, 3350, 9648, 0, 1},//Thief
			{4874, 3350, 9647, 0, 1},//Thief
			{4876, 3350, 9646, 0, 1},//Thief
			{4877, 3350, 9645, 0, 1},//Thief
			{4878, 3350, 9644, 0, 1},//Thief
			{13493, 3350, 9643, 0, 3},//Thief Donor


			{4875, 3188, 3159, 0, 3},//Thief
			{4874, 3188, 3160, 0, 3},//Thief
			{4876, 3188, 3161, 0, 3},//Thief
			{4877, 3188, 3162, 0, 3},//Thief
			{4878, 3188, 3163, 0, 3},//Thief

			{8702, 3350, 9636, 0, 0},//Fish barrel
			{12269, 3223, 3204, 0, 0},//Cook


			{411, 2267, 3246, 0, 0}, //delAltar
			{409, 2269, 3246, 0, 0}, //delAltar


			{6189, 3350, 9630, 0, 0},//Furnace
			{4306, 3350, 9632, 0, 1},//Anvil

			{14859, 3372, 9626, 0, 0},//Rune Ore
			{14859, 3371, 9626, 0, 0},//Rune Ore
			{14859, 3370, 9626, 0, 0},//Rune Ore
			{14859, 3369, 9626, 0, 0},//Rune Ore
			{14859, 3368, 9626, 0, 0},//Rune Ore
			{14859, 3367, 9626, 0, 0},//Rune Ore
			{14859, 3366, 9626, 0, 0},//Rune Ore
			{14859, 3365, 9626, 0, 0},//Rune Ore
			{14859, 3361, 9626, 0, 0},//Rune Ore
			{14859, 3360, 9626, 0, 0},//Rune Ore
			{14859, 3359, 9626, 0, 0},//Rune Ore
			{14859, 3358, 9626, 0, 0},//Rune Ore
			{14859, 3357, 9626, 0, 0},//Rune Ore
			{14859, 3356, 9626, 0, 0},//Rune Ore
			{14859, 3355, 9626, 0, 0},//Rune Ore
			{14859, 3354, 9626, 0, 0},//Rune Ore

			{210, 3361, 9642, 0, 0},//Ice Light
			{210, 3365, 9642, 0, 0},//Ice Light
			{210, 3361, 9638, 0, 0},//Ice Light
			{210, 3365, 9638, 0, 0},//Ice Light

			{586, 3363, 9640, 0, 2},//Statue

			{4483, 3363, 9652, 0, 0},//Bank North
			{4483, 3376, 9640, 0, 1},//Bank East
			{4483, 3363, 9627, 0, 4},//Bank South
			{4483, 3351, 9640, 0, 3},//Bank West

			{1306, 3355, 9652, 0, 0},//Magic trees
			{1306, 3357, 9652, 0, 0},//Magic trees
			{1306, 3359, 9652, 0, 0},//Magic trees

			{1306, 3370, 9652, 0, 0},//Magic trees
			{1306, 3368, 9652, 0, 0},//Magic trees
			{1306, 3366, 9652, 0, 0},//Magic trees

			/* REMOVE PORTALS */
			{-1, 3353, 9640, 0, 0}, //Delete Portals
			{-1, 3363, 9629, 0, 0}, //Delete Portals
			{-1, 3374, 9640, 0, 0}, //Delete Portals
			{-1, 3363, 9650, 0, 0}, //Delete Portals

			/* NORTH EAST REMOVE PILE'S */
			{-1, 3371, 9658, 0, 0}, //Delete Pile's corners
			{-1, 3375, 9657, 0, 0}, //Delete Pile's corners
			{-1, 3377, 9653, 0, 0}, //Delete Pile's corners
			{-1, 3379, 9655, 0, 0}, //Delete Pile's corners
			{-1, 3381, 9657, 0, 0}, //Delete Pile's corners
			{-1, 3381, 9653, 0, 0}, //Delete Pile's corners
			{-1, 3381, 9650, 0, 0}, //Delete Pile's corners

			/* NORTH WEST REMOVE PILE'S */
			{-1, 3346, 9651, 0, 0}, //Delete Pile's corners
			{-1, 3348, 9652, 0, 0}, //Delete Pile's corners
			{-1, 3345, 9654, 0, 0}, //Delete Pile's corners
			{-1, 3348, 9655, 0, 0}, //Delete Pile's corners
			{-1, 3352, 9654, 0, 0}, //Delete Pile's corners
			{-1, 3345, 9657, 0, 0}, //Delete Pile's corners
			{-1, 3350, 9657, 0, 0}, //Delete Pile's corners
			{-1, 3354, 9658, 0, 0}, //Delete Pile's corners
			{-1, 3356, 9657, 0, 0}, //Delete Pile's corners

			/* SOUTH EAST REMOVE PILE'S */
			{-1, 3381, 9727, 0, 0}, //Delete Pile's corners
			{-1, 3378, 9625, 0, 0}, //Delete Pile's corners
			{-1, 3376, 9624, 0, 0}, //Delete Pile's corners
			{-1, 3381, 9623, 0, 0}, //Delete Pile's corners
			{-1, 3379, 9621, 0, 0}, //Delete Pile's corners
			{-1, 3374, 9621, 0, 0}, //Delete Pile's corners
			{-1, 3370, 9621, 0, 0}, //Delete Pile's corners
			{-1, 3381, 9627, 0, 0}, //Delete Pile's corners

			/* SOUTH WEST REMOVE PILE'S */
			{-1, 3355, 9621, 0, 0}, //Delete Pile's corners
			{-1, 3352, 9622, 0, 0}, //Delete Pile's corners
			{-1, 3350, 9621, 0, 0}, //Delete Pile's corners
			{-1, 3348, 9625, 0, 0}, //Delete Pile's corners
			{-1, 3347, 9620, 0, 0}, //Delete Pile's corners
			{-1, 3345, 9623, 0, 0}, //Delete Pile's corners
			{-1, 3347, 9628, 0, 0}, //Delete Pile's corners
			{-1, 3345, 9628, 0, 0}, //Delete Pile's corners

			/*** Bas Donator zone end ***/

			//H'ween even chest
			{2079, 3107, 3427, 0, 0},
			/**Grand EXCHANGE**/
			{8799, 3091, 3495, 0, 3},

			//lumby cows gate
			{2344, 3253, 3266, 0, 0},
			{3192, 3084, 3485, 0, 4},
			{-1, 3084, 3487, 0, 2},


			/* ZULRAH */

			{1, 3038, 3415, 0, 0},
			{357, 3034, 3422, 0, 0},
			{25136, 2278, 3070, 0, 0},
			{25136, 2278, 3065, 0, 0},
			{25138, 2273, 3066, 0, 0},
			{25136, 2272, 3065, 0, 0},
			{25139, 2267, 3065, 0, 0},
			{25136, 2260, 3081, 0, 0},
			{401, 3503, 3567, 0, 0},
			{401, 3504, 3567, 0, 0},
			{1309, 2715, 3465, 0, 0},
			{1309, 2709, 3465, 0, 0},
			{1309, 2709, 3458, 0, 0},
			{1306, 2705, 3465, 0, 0},
			{1306, 2705, 3458, 0, 0},
			{-1, 2715, 3466, 0, 0},
			{-1, 2712, 3466, 0, 0},
			{-1, 2713, 3464, 0, 0},
			{-1, 2711, 3467, 0, 0},
			{-1, 2710, 3465, 0, 0},
			{-1, 2709, 3464, 0, 0},
			{-1, 2708, 3466, 0, 0},
			{-1, 2707, 3467, 0, 0},
			{-1, 2704, 3465, 0, 0},
			{-1, 2714, 3466, 0, 0},
			{-1, 2705, 3457, 0, 0},
			{-1, 2709, 3461, 0, 0},
			{-1, 2709, 3459, 0, 0},
			{-1, 2708, 3458, 0, 0},
			{-1, 2710, 3457, 0, 0},
			{-1, 2711, 3461, 0, 0},
			{-1, 2713, 3461, 0, 0},
			{-1, 2712, 3459, 0, 0},
			{-1, 2712, 3457, 0, 0},
			{-1, 2714, 3458, 0, 0},
			{-1, 2705, 3459, 0, 0},
			{-1, 2705, 3464, 0, 0},
			{2274, 2912, 5300, 2, 0},
			{2274, 2914, 5300, 1, 0},
			{2274, 2919, 5276, 1, 0},
			{2274, 2918, 5274, 0, 0},
			{2274, 3001, 3931, 0, 0},
			{-1, 2884, 9797, 0, 2},
			{4483, 2909, 4832, 0, 3},
			{4483, 2901, 5201, 0, 2},
			{4483, 2902, 5201, 0, 2},
			{9326, 3001, 3960, 0, 0},
			{1662, 3112, 9677, 0, 2},
			{1661, 3114, 9677, 0, 2},
			{1661, 3122, 9664, 0, 1},
			{1662, 3123, 9664, 0, 2},
			{1661, 3124, 9664, 0, 3},
			{4483, 2918, 2885, 0, 3},
			{12356, 3081, 3500, 0, 0},
			{2182, 3081, 3497, 0, 0},
			{1746, 3090, 3492, 0, 1},
			{2644, 2737, 3444, 0, 0},
			{-1, 2608, 4777, 0, 0},
			{-1, 2601, 4774, 0, 0},
			{-1, 2611, 4776, 0, 0},


			/**Oude ruse Member Zone*/

//		{2344, 3421, 2908, 0, 0}, //Rock blocking
//        {2345, 3438, 2909, 0, 0},
//        {2344, 3435, 2909, 0, 0},
//        {2344, 3432, 2909, 0, 0},
//        {2345, 3431, 2909, 0, 0},
//        {2344, 3428, 2921, 0, 1},
//        {2344, 3428, 2918, 0, 1},
//        {2344, 3428, 2915, 0, 1},
//        {2344, 3428, 2912, 0, 1},
//        {2345, 3428, 2911, 0, 1},
//        {2344, 3417, 2913, 0, 1},
//        {2344, 3417, 2916, 0, 1},
//        {2344, 3417, 2919, 0, 1},
//        {2344, 3417, 2922, 0, 1},
//        {2345, 3417, 2912, 0, 0},
//        {2346, 3418, 2925, 0, 0},
//        {10378, 3426, 2907, 0, 0},
//        {8749, 3426, 2923, 0, 2}, //Altar
//        {-1, 3420, 2909, 0, 10}, //Remove crate by mining
//        {-1, 3420, 2923, 0, 10}, //Remove Rockslide by Woodcutting
//        {14859, 3421, 2909, 0, 0}, //Mining
//        {14859, 3419, 2909, 0, 0},
//        {14859, 3418, 2910, 0, 0},
//        {14859, 3418, 2911, 0, 0},
//        {14859, 3422, 2909, 0, 0},
//        {1306, 3418, 2921, 0, 0}, //Woodcutting
//        {1306, 3421, 2924, 0, 0},
//        {1306, 3420, 2924, 0, 0},
//        {1306, 3419, 2923, 0, 0},
//        {1306, 3418, 2922, 0, 0},
//		{-1, 3430, 2912, 0, 2}, 
//		{13493, 3424, 2916, 0, 1},//Armour  stall

			/**Oude ruse Member Zone end*/


			{-1, 3098, 3496, 0, 1},
			{-1, 3095, 3498, 0, 1},
			{-1, 3088, 3509, 0, 1},
			{-1, 3095, 3499, 0, 1},
			{-1, 3085, 3506, 0, 1},
			{30205, 3085, 3509, 0, 3},
			{-1, 3206, 3263, 0, 0},
			{-1, 2794, 2773, 0, 0},
			{2, 2692, 3712, 0, 3},
			{2, 2688, 3712, 0, 1},
			{4483, 3081, 3496, 0, 1},
			{4483, 3081, 3495, 0, 1},
			{4875, 3094, 3500, 0, 0},
			{4874, 3095, 3500, 0, 0},
			{4876, 3096, 3500, 0, 0},
			{4877, 3097, 3500, 0, 0},
			{4878, 3098, 3500, 0, 0},
			{11758, 3019, 9740, 0, 0},
			{11758, 3020, 9739, 0, 1},
			{11758, 3019, 9738, 0, 2},
			{11758, 3018, 9739, 0, 3},
			{11933, 3028, 9739, 0, 1},
			{11933, 3032, 9737, 0, 2},
			{11933, 3032, 9735, 0, 0},
			{11933, 3035, 9742, 0, 3},
			{11933, 3034, 9739, 0, 0},
			{11936, 3028, 9737, 0, 1},
			{11936, 3029, 9734, 0, 2},
			{11936, 3031, 9739, 0, 0},
			{11936, 3032, 9741, 0, 3},
			{11936, 3035, 9734, 0, 0},
			{11954, 3037, 9739, 0, 1},
			{11954, 3037, 9735, 0, 2},
			{11954, 3037, 9733, 0, 0},
			{11954, 3039, 9741, 0, 3},
			{11954, 3039, 9738, 0, 0},
			{11963, 3039, 9733, 0, 1},
			{11964, 3040, 9732, 0, 2},
			{11965, 3042, 9734, 0, 0},
			{11965, 3044, 9737, 0, 3},
			{11963, 3042, 9739, 0, 0},
			{11963, 3045, 9740, 0, 1},
			{11965, 3043, 9742, 0, 2},
			{11964, 3045, 9744, 0, 0},
			{11965, 3048, 9747, 0, 3},
			{11951, 3048, 9743, 0, 0},
			{11951, 3049, 9740, 0, 1},
			{11951, 3047, 9737, 0, 2},
			{11951, 3050, 9738, 0, 0},
			{11951, 3052, 9739, 0, 3},
			{11951, 3051, 9735, 0, 0},
			{11947, 3049, 9735, 0, 1},
			{11947, 3049, 9734, 0, 2},
			{11947, 3047, 9733, 0, 0},
			{11947, 3046, 9733, 0, 3},
			{11947, 3046, 9735, 0, 0},
			{11941, 3053, 9737, 0, 1},
			{11939, 3054, 9739, 0, 2},
			{11941, 3053, 9742, 0, 0},
			{14859, 3038, 9748, 0, 3},
			{14859, 3044, 9753, 0, 0},
			{14859, 3048, 9754, 0, 1},
			{14859, 3054, 9746, 0, 2},
			{4306, 3026, 9741, 0, 0},
			{6189, 3022, 9742, 0, 1},
			{75, 2914, 3452, 0, 2},
			{11954, 2350, 3714, 0, 2},
			{11954, 2350, 3716, 0, 2},
			{11951, 2345, 3718, 0, 2},
			{11965, 2349, 3718, 0, 2},
			{11965, 2347, 3718, 0, 2},
			{11947, 2348, 3714, 0, 2},
			{11947, 2347, 3716, 0, 2},
			{11941, 2345, 3714, 0, 2},
			{11941, 2344, 3716, 0, 2},
			{14859, 2341, 3714, 0, 2},
			{14859, 2341, 3715, 0, 2},


			{4875, 2339, 3726, 0, 2},
			{4874, 2338, 3726, 0, 2},
			{4876, 2337, 3726, 0, 2},
			{4877, 2336, 3726, 0, 2},
			{4878, 2335, 3726, 0, 2},

			{12269, 2342, 3706, 0, 3},


			{1309, 2335, 3710, 0, 1},
			{1309, 2329, 3714, 0, 1},
			{1306, 2325, 3715, 0, 1},
			{1306, 2333, 3706, 0, 1},


			{10091, 2343, 3705, 0, 2},
			{6189, 2330, 3701, 0, 1},
			{4306, 2333, 3698, 0, 1},


			{11936, 2346, 3712, 0, 1},
			{11936, 2344, 3712, 0, 1},
			{11933, 2350, 3712, 0, 1},
			{11933, 2348, 3712, 0, 1},
			{11758, 3449, 3722, 0, 0},
			{11758, 3450, 3722, 0, 0},
			{50547, 3445, 3717, 0, 3},

			{2465, 3085, 3512, 0, 0},
			{-1, 3090, 3496, 0, 0},
			{-1, 3090, 3494, 0, 0},
			{-1, 3092, 3496, 0, 0},

			{-1, 3659, 3508, 0, 0},
			{4053, 3660, 3508, 0, 0},
			{4051, 3659, 3508, 0, 0},
			{1, 3649, 3506, 0, 0},
			{1, 3650, 3506, 0, 0},
			{1, 3651, 3506, 0, 0},
			{1, 3652, 3506, 0, 0},
			{-1, 2860, 9734, 0, 1},
			{-1, 2857, 9736, 0, 1},
			{664, 2859, 9742, 0, 1},
			{1167, 2860, 9742, 0, 1},
			{5277, 3691, 3465, 0, 2},
			{5277, 3690, 3465, 0, 2},
			{5277, 3688, 3465, 0, 2},
			{5277, 3687, 3465, 0, 2},
			{114, 3093, 3933, 0, 0},

	};
	
	
	/**
	 * Contains
	 * @param ObjectId - The object ID to spawn
	 * @param absX - The X position of the object to spawn
	 * @param absY - The Y position of the object to spawn
	 * @param Z - The Z position of the object to spawn
	 * @param face - The position the object will face
	 */
	
	//Objects that are handled by the server on regionchange
	private static final int[][] CUSTOM_OBJECTS_SPAWNS = {
			{2079, 2576, 9876, 0, 0},

			
			/**
			 * ZULRAH
			 */
			{ 25136, 2278, 3070, 0, 0 },
			{ 25136, 2278, 3065, 0, 0 },
			{ 25138, 2273, 3066, 0, 0 },
			{ 25136, 2272, 3065, 0, 0 },
			{ 25139, 2267, 3065, 0, 0 },
			{ 25136, 2260, 3081, 0, 0 },
			{1, 3038, 3415, 0, 0},
			{357, 3034, 3422, 0, 0},
			{ -1, 2265, 3071, 0, 0 },
			{ -1, 2271, 3071, 0, 0 },
			
	
			
			{-1, 3084, 3487, 0, 2},
			
			{ 2274, 3652, 3488, 0, 0 },
			{ 2274, 3039, 9555, 0, 0 },
			{ 2274, 3039, 9554, 0, 0 },
			//{ 4389, 2329, 3674, 0, 1 },
			/*
			* Remove Uber Zone Objects
			*/
			{-1, 2425, 4714, 0, 0},
			{-1, 2420, 4716, 0, 0},
			{-1, 2426, 4726, 0, 0},
			{-1, 2420, 4709, 0, 0},
			{-1, 2419, 4698, 0, 0},
			{-1, 2420, 4700, 0, 0},
			{-1, 2399, 4721, 0, 0},
			{-1, 2398, 4721, 0, 0},
			{-1, 2399, 4720, 0, 0},
			{-1, 2395, 4722, 0, 0},
			{-1, 2398, 4717, 0, 0},
			{-1, 2396, 4717, 0, 0},
			{-1, 2396, 4718, 0, 0},
			{-1, 2396, 4719, 0, 0},
			{-1, 2395, 4718, 0, 0},
			{-1, 2394, 4711, 0, 0},
			{-1, 2396, 4711, 0, 0},
			{-1, 2397, 4711, 0, 0},
			{-1, 2397, 4713, 0, 0},
			{-1, 2399, 4713, 0, 0},
			{-1, 2402, 4726, 0, 0},
			{-1, 2407, 4728, 0, 0},
			{-1, 2405, 4724, 0, 0},
			{-1, 2409, 4705, 0, 0},
			{-1, 2410, 4704, 0, 0},
			{-1, 2407, 4702, 0, 0},
			{-1, 2407, 4701, 0, 0},
			{-1, 2408, 4701, 0, 0},
			{-1, 2412, 4701, 0, 0},
			{-1, 2413, 4701, 0, 0},
			{-1, 2414, 4703, 0, 0},
			{-1, 2416, 4714, 0, 0},
			{-1, 2412, 4732, 0, 0},
			{-1, 2413, 4729, 0, 0},
			{-1, 2414, 4733, 0, 0},
			{-1, 2415, 4730, 0, 0},
			{-1, 2416, 4730, 0, 0},
			{-1, 2416, 4731, 0, 0},
			{-1, 2419, 4731, 0, 0},
			{-1, 2420, 4731, 0, 0},
			{-1, 2420, 4732, 0, 0},
			{-1, 2415, 4725, 0, 0},
			{-1, 2417, 4729, 0, 0},
			{-1, 2418, 4727, 0, 0},
			{-1, 2418, 4723, 0, 0},
			{-1, 2419, 4722, 0, 0},
			{-1, 2420, 4726, 0, 0},
			{-1, 2415, 4725, 0, 0},
			{-1, 2417, 4729, 0, 0},
			{-1, 2418, 4727, 0, 0},
			{-1, 2418, 4723, 0, 0},
			{-1, 2419, 4722, 0, 0},
			{-1, 2420, 4726, 0, 0},
			
			
		//lumby cows gate
		{2344, 3253, 3266, 0, 1},
		
		//gamble zone
		{2213, 2842, 5143, 0, 0},
		{2213, 2843, 5143, 0, 0},
		{2213, 2844, 5143, 0, 0},
		{2213, 2845, 5143, 0, 0},
		{2213, 2846, 5143, 0, 0},
		{2213, 2847, 5143, 0, 0},
		{2213, 2848, 5143, 0, 0},
		{2213, 2849, 5143, 0, 0},
		{2213, 2850, 5143, 0, 0},
		{2213, 2851, 5143, 0, 0},
		
		
		{2274, 3652, 3488, 0, 0},
		/**Jail Start*/
		{ 12269, 3093, 3933, 0, 0},
		{ 1864, 3093, 3932, 0, 1},//Cell 1
		{ 1864, 3094, 3932, 0, 1},
		{ 1864, 3095, 3932, 0, 1},
		{ 1864, 3096, 3932, 0, 1},
		{ 1864, 3097, 3932, 0, 1},
		{ 1864, 3097, 3931, 0, 2},
		{ 1864, 3097, 3930, 0, 2},
		{ 1864, 3097, 3929, 0, 2},
		{ 1864, 3097, 3928, 0, 3},
		{ 1864, 3096, 3928, 0, 3},
		{ 1864, 3095, 3928, 0, 3},
		{ 1864, 3094, 3928, 0, 3},
		{ 1864, 3093, 3928, 0, 3},
		{ 1864, 3093, 3929, 0, 4},
		{ 1864, 3093, 3930, 0, 4},
		{ 1864, 3093, 3931, 0, 4},
		{ 1864, 3098, 3932, 0, 1},//Cell 2
		{ 1864, 3099, 3932, 0, 1},
		{ 1864, 3100, 3932, 0, 1},
		{ 1864, 3101, 3932, 0, 1},
		{ 1864, 3102, 3932, 0, 1},
		{ 1864, 3102, 3931, 0, 2},
		{ 1864, 3102, 3930, 0, 2},
		{ 1864, 3102, 3929, 0, 2},
		{ 1864, 3102, 3928, 0, 3},
		{ 1864, 3101, 3928, 0, 3},
		{ 1864, 3100, 3928, 0, 3},
		{ 1864, 3099, 3928, 0, 3},
		{ 1864, 3098, 3928, 0, 3},
		{ 1864, 3098, 3929, 0, 4},
		{ 1864, 3098, 3930, 0, 4},
		{ 1864, 3098, 3931, 0, 4},
		{ 1864, 3093, 3939, 0, 1},//Cell 3
		{ 1864, 3094, 3939, 0, 1},
		{ 1864, 3095, 3939, 0, 1},
		{ 1864, 3096, 3939, 0, 1},
		{ 1864, 3097, 3939, 0, 1},
		{ 1864, 3097, 3938, 0, 2},
		{ 1864, 3097, 3937, 0, 2},
		{ 1864, 3097, 3936, 0, 2},
		{ 1864, 3097, 3935, 0, 3},
		{ 1864, 3096, 3935, 0, 3},
		{ 1864, 3095, 3935, 0, 3},
		{ 1864, 3094, 3935, 0, 3},
		{ 1864, 3093, 3935, 0, 3},
		{ 1864, 3093, 3936, 0, 4},
		{ 1864, 3093, 3937, 0, 4},
		{ 1864, 3093, 3938, 0, 4},
		{ 1864, 3098, 3939, 0, 1},//Cell 4
		{ 1864, 3099, 3939, 0, 1},
		{ 1864, 3100, 3939, 0, 1},
		{ 1864, 3101, 3939, 0, 1},
		{ 1864, 3102, 3939, 0, 1},
		{ 1864, 3102, 3938, 0, 2},
		{ 1864, 3102, 3937, 0, 2},
		{ 1864, 3102, 3936, 0, 2},
		{ 1864, 3102, 3935, 0, 3},
		{ 1864, 3101, 3935, 0, 3},
		{ 1864, 3100, 3935, 0, 3},
		{ 1864, 3099, 3935, 0, 3},
		{ 1864, 3098, 3935, 0, 3},
		{ 1864, 3098, 3936, 0, 4},
		{ 1864, 3098, 3937, 0, 4},
		{ 1864, 3098, 3938, 0, 4},
		{ 1864, 3103, 3932, 0, 1},//Cell 5
		{ 1864, 3104, 3932, 0, 1},
		{ 1864, 3105, 3932, 0, 1},
		{ 1864, 3106, 3932, 0, 1},
		{ 1864, 3107, 3932, 0, 1},
		{ 1864, 3107, 3931, 0, 2},
		{ 1864, 3107, 3930, 0, 2},
		{ 1864, 3107, 3929, 0, 2},
		{ 1864, 3107, 3928, 0, 3},
		{ 1864, 3106, 3928, 0, 3},
		{ 1864, 3105, 3928, 0, 3},
		{ 1864, 3104, 3928, 0, 3},
		{ 1864, 3103, 3928, 0, 3},
		{ 1864, 3103, 3929, 0, 4},
		{ 1864, 3103, 3930, 0, 4},
		{ 1864, 3103, 3931, 0, 4},
		{ 1864, 3108, 3932, 0, 1},//Cell 6
		{ 1864, 3109, 3932, 0, 1},
		{ 1864, 3110, 3932, 0, 1},
		{ 1864, 3111, 3932, 0, 1},
		{ 1864, 3112, 3932, 0, 1},
		{ 1864, 3112, 3931, 0, 2},
		{ 1864, 3112, 3930, 0, 2},
		{ 1864, 3112, 3929, 0, 2},
		{ 1864, 3112, 3928, 0, 3},
		{ 1864, 3111, 3928, 0, 3},
		{ 1864, 3110, 3928, 0, 3},
		{ 1864, 3109, 3928, 0, 3},
		{ 1864, 3108, 3928, 0, 3},
		{ 1864, 3108, 3929, 0, 4},
		{ 1864, 3108, 3930, 0, 4},
		{ 1864, 3108, 3931, 0, 4},
		{ 1864, 3103, 3939, 0, 1},//Cell 7
		{ 1864, 3104, 3939, 0, 1},
		{ 1864, 3105, 3939, 0, 1},
		{ 1864, 3106, 3939, 0, 1},
		{ 1864, 3107, 3939, 0, 1},
		{ 1864, 3107, 3938, 0, 2},
		{ 1864, 3107, 3937, 0, 2},
		{ 1864, 3107, 3936, 0, 2},
		{ 1864, 3107, 3935, 0, 3},
		{ 1864, 3106, 3935, 0, 3},
		{ 1864, 3105, 3935, 0, 3},
		{ 1864, 3104, 3935, 0, 3},
		{ 1864, 3103, 3935, 0, 3},
		{ 1864, 3103, 3936, 0, 4},
		{ 1864, 3103, 3937, 0, 4},
		{ 1864, 3103, 3938, 0, 4},
		{ 1864, 3108, 3939, 0, 1},//Cell 8
		{ 1864, 3109, 3939, 0, 1},
		{ 1864, 3110, 3939, 0, 1},
		{ 1864, 3111, 3939, 0, 1},
		{ 1864, 3112, 3939, 0, 1},
		{ 1864, 3112, 3938, 0, 2},
		{ 1864, 3112, 3937, 0, 2},
		{ 1864, 3112, 3936, 0, 2},
		{ 1864, 3112, 3935, 0, 3},
		{ 1864, 3111, 3935, 0, 3},
		{ 1864, 3110, 3935, 0, 3},
		{ 1864, 3109, 3935, 0, 3},
		{ 1864, 3108, 3935, 0, 3},
		{ 1864, 3108, 3936, 0, 4},
		{ 1864, 3108, 3937, 0, 4},
		{ 1864, 3108, 3938, 0, 4},
		{ 1864, 3113, 3932, 0, 1},//Cell 9
		{ 1864, 3114, 3932, 0, 1},
		{ 1864, 3115, 3932, 0, 1},
		{ 1864, 3116, 3932, 0, 1},
		{ 1864, 3117, 3932, 0, 1},
		{ 1864, 3117, 3931, 0, 2},
		{ 1864, 3117, 3930, 0, 2},
		{ 1864, 3117, 3929, 0, 2},
		{ 1864, 3117, 3928, 0, 3},
		{ 1864, 3116, 3928, 0, 3},
		{ 1864, 3115, 3928, 0, 3},
		{ 1864, 3114, 3928, 0, 3},
		{ 1864, 3113, 3928, 0, 3},
		{ 1864, 3113, 3929, 0, 4},
		{ 1864, 3113, 3930, 0, 4},
		{ 1864, 3113, 3931, 0, 4},
		{ 1864, 3113, 3939, 0, 1},//Cell 10
		{ 1864, 3114, 3939, 0, 1},
		{ 1864, 3115, 3939, 0, 1},
		{ 1864, 3116, 3939, 0, 1},
		{ 1864, 3117, 3939, 0, 1},
		{ 1864, 3117, 3938, 0, 2},
		{ 1864, 3117, 3937, 0, 2},
		{ 1864, 3117, 3936, 0, 2},
		{ 1864, 3117, 3935, 0, 3},
		{ 1864, 3116, 3935, 0, 3},
		{ 1864, 3115, 3935, 0, 3},
		{ 1864, 3114, 3935, 0, 3},
		{ 1864, 3113, 3935, 0, 3},
		{ 1864, 3113, 3936, 0, 4},
		{ 1864, 3113, 3937, 0, 4},
		{ 1864, 3113, 3938, 0, 4},
		
		{ 2995, 2336, 3604, 0, 4},//VIP CHEST
		{ 2995, 2333, 3604, 0, 4},//VIP CHEST
		

		
		{ 27330, 2335, 3668, 0, 2},//Emerald STATUE
		
		
		{ 9075, 2333, 3649, 0, 2},//VIP BANKCHEST
		{ 11356, 2334, 3652, 0, 0},//VIP PORTAL
		{ 9075, 2336, 3649, 0, 2},//VIP BANKCHEST
		

		{ -1, 3092, 3488, 0, 0},

	};
}

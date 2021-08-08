package com.platinum.world.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.GroundItem;
import com.platinum.model.Item;
import com.platinum.model.Position;
import com.platinum.util.Misc;
import com.platinum.world.content.combat.CombatBuilder.CombatDamageCache;
import com.platinum.world.content.skill.impl.pvm.NpcGain;
import com.platinum.world.World;
import com.platinum.world.content.combat.CombatFactory;
import com.platinum.world.entity.impl.GroundItemManager;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class Bork extends NPC {

	public static int[] COMMONLOOT = { 2572,19137,19138,19139,6041,5130,
			18865,19132,19131,19133,6199,18940,18941,18942,2749,2750,2751,2752,2753,2754,2755,19721,19132,19131,19133,6199,18940,18941,18942,2749,2750,2751,2752,2753,2754,2755,19721,19722,19723,18892,15418,19468};
	public static int[] RARELOOT = { 19935,19004,5131,4771,4772,4770,19935,19004,5131,4771,4772,4770, 4799 ,4800,4801,5079,3973,3951,15012
			,5131,4770,4771,4772,3988,6193,6194,6195,6196,6197,6198};
	public static int[] SUPERRARELOOT = { 15374,19936,3815,3814,3813,3812,3811,3810,3069,19886};

	/**
	 * 
	 */
	public static final int NPC_ID = 7134;

	/**
	 * add your maps to that folder open me your client.java in client
	 */
	public static final BorkLocation[] LOCATIONS = { new BorkLocation(3100, 5534, 0, "") };

	/**
	 * 
	 */
	private static Bork current;

	/**
	 * 
	 * @param position
	 */
	public Bork(Position position) {

		super(NPC_ID, position);
	}

	/**
	 * 
	 */
	public static void initialize() {

		TaskManager.submit(new Task( 1500, false) { // 6000

			@Override
			public void execute() {
				spawn();
			}

		});

	}

	/**
	 * 
	 */
	public static void spawn() {

		if (getCurrent() != null) {
			return;
		}

		BorkLocation location = Misc.randomElement(LOCATIONS);
		Bork instance = new Bork(location.copy());

		// //System.out.println(instance.getPosition());

		World.register(instance);
		setCurrent(instance);
		// System.out.print("spawned.");

		World.sendFilteredMessage("<img=9><col=bababa><shad=10>[<col=0999ad>UBER BOSS<col=bababa>]<col=0999ad>Bork <col=00a745>has <shad=10>respawned <img=385> ::uberboss");
	}


	/**
	 * 
	 * @param npc
	 */
	public static void handleDrop(NPC npc) {
		World.getPlayers().forEach(p -> p.getPacketSender().sendString(26707, "@or2@WildyWyrm: @gre@N/A"));

		setCurrent(null);

		if (npc.getCombatBuilder().getDamageMap().size() == 0) {
			return;
		}

		Map<Player, Integer> killers = new HashMap<>();

		for (Entry<Player, CombatDamageCache> entry : npc.getCombatBuilder().getDamageMap().entrySet()) {

			if (entry == null) {
				continue;
			}

			long timeout = entry.getValue().getStopwatch().elapsed();

			if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
				continue;
			}

			Player player = entry.getKey();

			if (player.getConstitution() <= 0 || !player.isRegistered()) {
				continue;
			}

			killers.put(player, entry.getValue().getDamage());

		}

		npc.getCombatBuilder().getDamageMap().clear();

		List<Entry<Player, Integer>> result = sortEntries(killers);
		int count = 0;

		for (Entry<Player, Integer> entry : result) {

			Player killer = entry.getKey();
			int damage = entry.getValue();

			handleDrop(npc, killer, damage);
			NpcGain.WorldBossXP(killer);

			if (++count >= 10) {
				break;
			}

		}

	}

	public static void giveLoot(Player player, NPC npc, Position pos) {
		int chance = Misc.getRandom(1000);
		int common = COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)];
		@SuppressWarnings("unused")
		int common1 = COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)];
		int rare = RARELOOT[Misc.getRandom(RARELOOT.length - 1)];
		int superrare = SUPERRARELOOT[Misc.getRandom(SUPERRARELOOT.length - 1)];

		GroundItemManager.spawnGroundItem(player,
				new GroundItem(new Item(10835, 100), pos, player.getUsername(), false, 150, true, 200));

		if (chance >= 950) {
			GroundItemManager.spawnGroundItem(player,
					new GroundItem(new Item(superrare), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(superrare).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			World.sendFilteredMessage(
					"<img=382><col=FF0000>" + player.getUsername() + " received<col=eaeaea>[ " + itemMessage + "<col=eaeaea>]<col=FF0000> from the Uber Boss!");
			return;
		}

		if (chance >= 500) {
			GroundItemManager.spawnGroundItem(player,
					new GroundItem(new Item(rare), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(rare).getDefinition().getName());
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;
			World.sendFilteredMessage(
					"<img=382><col=FF0000>" + player.getUsername() + " received<col=eaeaea>[ " + itemMessage + "<col=eaeaea>]<col=FF0000> from the Uber Boss!");
			return;
		}
		if (chance >= 1) {
			GroundItemManager.spawnGroundItem(player,
					new GroundItem(new Item(common, 1), pos, player.getUsername(), false, 150, true, 200));
			String itemName = (new Item(common).getDefinition().getName());
			World.sendFilteredMessage(
					"<img=382><col=FF0000>" + player.getUsername() + " received<col=eaeaea>[ " + itemName + "<col=eaeaea>]<col=FF0000> from the Uber Boss!");
			return;
		}

	}

	/**
	 * 
	 * @param npc
	 * @param player
	 * @param damage
	 */
	private static void handleDrop(NPC npc, Player player, int damage) {
		Position pos = npc.getPosition();
		giveLoot(player, npc, pos);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortEntries(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {

			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}

		});

		return sortedEntries;

	}

	/**
	 * 
	 * @return
	 */
	public static Bork getCurrent() {
		return current;
	}

	/**
	 * 
	 * @param current
	 */
	public static void setCurrent(Bork current) {
		Bork.current = current;
	}

	/**
	 * 
	 * @author Levi <levi.patton69 @ skype>
	 *
	 */
	public static class BorkLocation extends Position {

		/**
		 * 
		 */
		private String location;

		/**
		 * 
		 * @param x
		 * @param y
		 * @param z
		 * @param location
		 */
		public BorkLocation(int x, int y, int z, String location) {
			super(x, y, z);
			setLocation(location);
		}

		/**
		 * 
		 * @return
		 */

		String getLocation() {
			return location;
		}

		/**
		 * 
		 * @param location
		 */
		public void setLocation(String location) {
			this.location = location;
		}

	}

}
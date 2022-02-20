package com.zamron.world.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zamron.engine.task.Task;
import com.zamron.engine.task.TaskManager;
import com.zamron.model.GroundItem;
import com.zamron.model.Item;
import com.zamron.model.Position;
import com.zamron.util.Misc;
import com.zamron.world.content.combat.CombatBuilder.CombatDamageCache;
import com.zamron.world.content.skill.impl.pvm.NpcGain;
import com.zamron.world.World;
import com.zamron.world.content.combat.CombatFactory;
import com.zamron.world.entity.impl.GroundItemManager;
import com.zamron.world.entity.impl.npc.NPC;
import com.zamron.world.entity.impl.player.Player;

public class Death extends NPC {

    public static int[] COMMONLOOT = { 2572,19137,19138,19139,6041,5130,
            18865,19132,19131,19133,6199,18940,18941,18942,19721,19132,19131,19133,6199,18940,18941,18942,19721,19722,19723,18892,15418,19468 };
    public static int[] RARELOOT = { 1499, 3973, 4800, 4801, 4802, 5079, 15012, 3951, 3316, 3931, 3958, 3959, 3960, 5186, 5187, 6584, 14559, 18750, 18751, 5131, 4770, 4771, 4772, 5209, 923, 3994, 3995, 3996, 5132, 12605, 19720, 3910, 3909, 3908, 3907, 19886,
            3980, 3999, 4000, 4001, 5167, 15649, 15650, 15651, 15652, 15653, 15654, 15655, 3905, 4761, 4762, 4763, 4764, 4765, 5089, 18894, 926, 5210, 931, 5211, 930, 15045, 12001, 5173, 3821, 3822, 3820, 19945,
            20054, 4781, 4782, 4783, 15032, 4785, 5195, 3914, 15656, 5082, 5083, 5084, 5085, 17151, 19619, 19470, 19471, 19472, 19473, 19474, 5129, 5130,3075, 3076, 3078, 3242, 3244, 5198, 5199, 5200 };
    public static int[] SUPERRARELOOT = { 22196, 22197, 22198, 22199, 22200, 22201, 22203, 5170, 22204};

    /**
     *
     */
    public static final int NPC_ID = 2862;

    /**
     * add your maps to that folder open me your client.java in client
     */
    public static final deathlocations[] LOCATIONS = { new deathlocations(3037, 5344, 0, "") };

    /**
     *
     */
    private static Death current;

    /**
     *
     * @param position
     */
    public Death(Position position) {

        super(NPC_ID, position);
    }

    /**
     *
     */
    public static void initialize() {

        TaskManager.submit(new Task( 10800, false) { // 6000

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

        deathlocations location = Misc.randomElement(LOCATIONS);
        Death instance = new Death(location.copy());

        // //System.out.println(instance.getPosition());

        World.register(instance);
        setCurrent(instance);
        // System.out.print("spawned.");

        /*World.sendMessageNonDiscord("<img=11><<shad=10>[@or2@BOSS] @bla@ Death @or2@has respawned Use ::death");*/
    }
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

            if (++count >= 4) {
                break;
            }

        }

    }
    /**
     *
     * @param npc
     */
    public static void giveLoot(Player player, NPC npc, Position pos) {
        int chance = Misc.getRandom(100);
        int common = COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)];
        @SuppressWarnings("unused")
        int common1 = COMMONLOOT[Misc.getRandom(COMMONLOOT.length - 1)];
        int rare = RARELOOT[Misc.getRandom(RARELOOT.length - 1)];
        int superrare = SUPERRARELOOT[Misc.getRandom(SUPERRARELOOT.length - 1)];

        GroundItemManager.spawnGroundItem(player,
                new GroundItem(new Item(10835, 100), pos, player.getUsername(), false, 150, true, 200));

        if (chance >= 95) {
            GroundItemManager.spawnGroundItem(player,
                    new GroundItem(new Item(superrare), pos, player.getUsername(), false, 150, true, 200));
            String itemName = (new Item(superrare).getDefinition().getName());
            String itemMessage = Misc.anOrA(itemName) + " " + itemName;
            //DiscordMessenger.sendRareDrop(player.getUsername(), " received<col=eaeaea><img=11>[ " + itemMessage + "<col=eaeaea>]<img=11><col=FF0000>from the Death!");
            World.sendMessageNonDiscord(
                    "<img=11><col=FF0000>" + player.getUsername() + " received<col=eaeaea><img=11>[ " + itemMessage + "<col=eaeaea>]<img=11><col=FF0000>from the Death!");
            //DiscordMessenger.sendRareDrop(player.getUsername(), " Just received " + itemMessage + " from the Death!");
            return;
        }

        if (chance >= 85) {
            GroundItemManager.spawnGroundItem(player,
                    new GroundItem(new Item(rare), pos, player.getUsername(), false, 150, true, 200));
            String itemName = (new Item(rare).getDefinition().getName());
            String itemMessage = Misc.anOrA(itemName) + " " + itemName;
            World.sendMessageNonDiscord(
                    "<img=11><col=FF0000>" + player.getUsername() + " received<img=11><col=eaeaea>[ " + itemMessage + "<col=eaeaea>]<img=11><col=FF0000> from the Death!");
            //DiscordMessenger.sendRareDrop(player.getUsername(), " Just received " + itemMessage + " from the Death!");
            return;
        }
        if (chance >= 0) {
            GroundItemManager.spawnGroundItem(player,
                    new GroundItem(new Item(common, 1), pos, player.getUsername(), false, 150, true, 200));
            String itemName = (new Item(common).getDefinition().getName());
            World.sendMessageNonDiscord(
                    "<img=11><col=FF0000>" + player.getUsername() + " received<col=eaeaea><img=11>[<col=07b481> " + itemName + "<col=eaeaea>]<img=11><col=FF0000> from the Death!");
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
    public static Death getCurrent() {
        return current;
    }

    /**
     *
     * @param current
     */
    public static void setCurrent(Death current) {
        Death.current = current;
    }

    /**
     *
     * @author Levi <levi.patton69 @ skype>
     *
     */
    public static class deathlocations extends Position {

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
        public deathlocations(int x, int y, int z, String location) {
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
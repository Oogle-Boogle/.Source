package com.platinum.world.content;

import com.platinum.model.container.impl.Inventory;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.net.packet.PacketSender;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

public class DonationChests {

    // TODO Rename & change ID's
    public static final int lowTierChestID = 0;
    public static final int mediumTierChestID = 1;
    public static final int highTierChestID = 2;

    // TODO Rename & change ID's
    public static final int lowTierKeyID = 3459;
    public static final int mediumTierKeyID = 3455;
    public static final int highTierKeyID = 3458;

    // TODO Rename & change ID's
    public static final int[] lowTierItems = {902, 903, 904, 905, 3082, 2577, 2749, 2750, 2752, 2751, 2753, 2754, 2755, 19721, 19722, 19723, 19724, 19734, 19736, 19468, 19137};
    public static final int[] mediumTierItems = {1499, 3973, 4800, 4801, 4802, 5079, 15012, 3951, 3316, 3931, 3958, 3959, 3960, 5186, 5187, 6584, 14559, 18750, 18751, 5131, 4770, 4771, 4772, 6193, 6194, 6195, 6196, 6197, 6198, 5209, 923, 3994, 3995, 3996, 5132, 12605, 19720, 3910, 3909, 3908, 3907, 19886,
    3980, 3999, 4000, 4001, 18955, 18956, 18957, 5167, 15649, 15650, 15651, 15652, 15653, 15654, 15655, 3905, 4761, 4762, 4763, 4764, 4765, 5089, 18894, 926, 5210, 931, 5211, 930, 15045, 12001, 5173, 3821, 3822, 3820, 19945,
    20054, 4781, 4782, 4783, 15032, 4785, 5195, 3914, 15656, 5082, 5083, 5084, 5985, 17151, 19619, 19470, 19471, 19472, 19473, 19474, 5129};
    public static final int[] highTierItems = {4643, 4641, 4642, 3983, 3064, 19618, 19691, 19692, 19693, 19694, 19696, 19695, 19159, 19160, 19161, 19163, 19164, 19165, 19166,
    9492, 9493, 9494, 9495, 9104, 19935, 14494, 14492, 14490, 2760, 19727, 19730, 19731, 19732, 19728, 6485, 19729, 19106, 13206, 13202, 13203, 13204, 13205, 13207, 11143, 11144, 11145, 11146, 11147,
    4797, 4794, 4795, 19127, 19128, 19129, 8664, 4796, 18931, 15374, 13992, 13994, 13993, 13995, 13991, 14448, 14447, 9496, 9497, 9498, 19155, 10905, 19741, 19742, 19743, 19744, 19154, 20427, 19936, 19937};


    /** Handles clicking each chest **/
    public static void handleChestClick(int ButtonID, Player player) {

        switch (ButtonID) {
            case lowTierChestID:
                lowTierReward(player);
                break;
            case mediumTierChestID:
                mediumTierReward(player);
                break;
            case highTierChestID:
                highTierReward(player);
                break;
        }
    }

    /** Low tier 1-3 rewards **/
    private static void lowTierReward(Player player) {

        Inventory invent = player.getInventory();
        PacketSender pck = player.getPacketSender();

        if (invent.getFreeSlots() < 1) {
            pck.sendMessage("You need at least 1 slot free to open the chest.");
            return;
        }

        if (!invent.contains(lowTierKeyID)) {
            pck.sendMessage("You don't have the right key to open this chest!");
            return;
        }

        player.getPacketSender().sendMessage("You open the chest..");
        invent.delete(lowTierKeyID, 1);
        int reward = (lowTierItems[Misc.getRandom(lowTierItems.length - 1)]);
        invent.add(reward, 1);
        World.sendMessageNonDiscord("[@red@T1-T3 Chest@red@] @blu@"+player.getUsername()
                + "@bla@ has just received @blu@1 @bla@x @blu@"
                + ItemDefinition.forId(reward).getName()
                + "@bla@!");
    }

    /** Medium tier 4-7 rewards **/
    private static void mediumTierReward(Player player) {

        Inventory invent = player.getInventory();
        PacketSender pck = player.getPacketSender();

        if (invent.getFreeSlots() < 1) {
            pck.sendMessage("You need at least 1 slot free to open the chest.");
            return;
        }

        if (!invent.contains(mediumTierKeyID)) {
            pck.sendMessage("You don't have the right key to open this chest!");
            return;
        }

        player.getPacketSender().sendMessage("You open the chest..");
        invent.delete(mediumTierKeyID, 1);
        int reward = (mediumTierItems[Misc.getRandom(mediumTierItems.length - 1)]);
        invent.add(reward, 1);
        World.sendMessageDiscord("[@red@T4-T7 Chest@red@] @blu@"+player.getUsername()
                + "@bla@ has just received @blu@1 @bla@x @blu@"
                + ItemDefinition.forId(reward).getName()
                + "@bla@!");
    }

    /** Top tier 8-10 rewards **/
    private static void highTierReward(Player player) {

        Inventory invent = player.getInventory();
        PacketSender pck = player.getPacketSender();

        if (invent.getFreeSlots() < 1) {
            pck.sendMessage("You need at least 1 slot free to open the chest.");
            return;
        }

        if (!invent.contains(highTierKeyID)) {
            pck.sendMessage("You don't have the right key to open this chest!");
            return;
        }

        player.getPacketSender().sendMessage("You open the chest..");
        invent.delete(highTierKeyID, 1);
        int reward = (highTierItems[Misc.getRandom(highTierItems.length - 1)]);
        invent.add(reward, 1);
        World.sendMessageDiscord("[@red@T8-T10 Chest@red@] @blu@"+player.getUsername()
                + "@bla@ has just received @blu@1 @bla@x @blu@"
                + ItemDefinition.forId(reward).getName()
                + "@bla@!");
    }
}

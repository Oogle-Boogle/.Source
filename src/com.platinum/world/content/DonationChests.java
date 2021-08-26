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
    public static final int[] lowTierItems = {123, 456, 789};
    public static final int[] mediumTierItems = {123, 456, 789};
    public static final int[] highTierItems = {123, 456, 789};


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

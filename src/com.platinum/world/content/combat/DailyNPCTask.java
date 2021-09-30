package com.platinum.world.content.combat;

import com.platinum.model.Item;
import com.platinum.model.definitions.NpcDefinition;
import com.platinum.util.Misc;
import com.platinum.util.Stopwatch;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

public class DailyNPCTask {

    private static final int TIME = 86400000;

    private static Stopwatch timer = new Stopwatch().reset();

    public static int CHOSEN_NPC_ID = 0; //Leave this as 0, it's just a default initialiser

    public static int KILLS_REQUIRED = 100; //Change this if you want to update the task amount

    public static int[] NPC_IDs = {4455, 4456, 4462, 17, 3253, 15, 9932, 1999,   }; //Add your NPC ID's here

    public static Player winner = null;

    public static String lastWinner;
    /** Stores the reward items **/
    public static final Item[] REWARDS = {new Item(123, 1), //TODO CHANGE THE REWARDS
            new Item(537, 100),
            new Item(555, 10),
            new Item(556, 100),
            new Item(380, 30),
            new Item(2437, 50),
            new Item(2443, 25),
            new Item(3041, 5),
            new Item(4151, 1),
            new Item(4131, 1),
            new Item(1725, 1),
            new Item(1540, 1),
            new Item(3751, 1),
            new Item(1712, 1),
            new Item(13734, 1),
            new Item(11126, 1),
            new Item(6524, 1),
            new Item(884, 1000),
            new Item(533, 200)};

    /** Picks a random NPC for the next task **/
    public static void pickDailyNPC() {
        CHOSEN_NPC_ID = NPC_IDs[Misc.random(NPC_IDs.length - 1)];
        //System.out.println("Today's Chosen NPC is "+ NpcDefinition.forId(CHOSEN_NPC_ID).getName());
        World.sendMessageNonDiscord("@red@Daily NPC Task Reset! New task: Kill "
                + KILLS_REQUIRED
                + NpcDefinition.forId(CHOSEN_NPC_ID).getName()
                + "!");
        timer.reset(); // <- This was missing.
    }

    /** Tracks a players progress **/
    public static void countPlayerKill(Player player) {
        int lastKnownNPC = player.getCurrentDailyNPC();
        if (lastKnownNPC != CHOSEN_NPC_ID || lastKnownNPC == 0) { //If the last task wasn't finished and we're onto a new NPC, we need to reset the KC
            player.setCurrentDailyNPCKills(1); //Resetting KC and adding one for the current kill
            player.setCurrentDailyNPC(CHOSEN_NPC_ID); //Setting the last known NPC Task to the current one
        }
        if (lastKnownNPC == CHOSEN_NPC_ID) { //Players last killed NPC task matches the current one
            if (player.getCurrentDailyNPCKills() < KILLS_REQUIRED) { //Player hasn't won yet
                player.setCurrentDailyNPCKills(player.getCurrentDailyNPCKills() + 1);
                player.getPacketSender().sendMessage((KILLS_REQUIRED - player.getCurrentDailyNPCKills()
                        + " "
                        + NpcDefinition.forId(CHOSEN_NPC_ID).getName()
                        + " kills remaining.")); //Tells the player their current KC every time they get a kill
            } else if (player.getCurrentDailyNPCKills() >= KILLS_REQUIRED) {
                winner = player; //Save the winner
                rewardWinner(winner); //Reward the winner
            }
        }
    }

    public static void rewardWinner(Player player) {
        Item reward = REWARDS[Misc.getRandom(REWARDS.length - 1)];//Pick a random reward from the list above
        boolean stackable = reward.getDefinition().isStackable(); //Doing this just to make it clean
        int rewardQuantity = reward.getAmount(); //Int saved to make it clean
        int freeSlots = player.getInventory().getFreeSlots(); //Int saved to make it clean

        if (stackable) { //If the item is stackable
            if (freeSlots >= 1) //If stackable we only need 1 slot
                player.getInventory().add(reward); //Add to inventory
            else if (freeSlots == 0) //If player has no slots
                player.getBank(0).add(reward, true); //Add reward to bank
            player.getPacketSender().sendMessage("@red@Congratulations on winning the daily NPC task! Reward sent to your bank!");
        }

        if (!stackable) { //Non stackable reward
                if (freeSlots >= rewardQuantity) //Check if the player has enough slots
                    player.getInventory().add(reward);//Add to inventory

                if (freeSlots < rewardQuantity) //If player doesn't have enough slots
                    player.getBank(0).add(reward, true); //Add reward to bank
                    player.getPacketSender().sendMessage("@red@Congratulations on winning the daily NPC task! Reward sent to your bank!");
        }
        World.sendMessageNonDiscord("@red@" + winner.getUsername() + " has won today's NPC task and was rewarded "
                + reward.getAmount() + " x "
                + reward.getDefinition().getName()
                + "!");

        resetDailyNPCGame(); //Restarts the game
    }

    /** Simply restarts the game **/
    public static void resetDailyNPCGame() {
        lastWinner = winner.getUsername(); // Saves last winner name as a string to be used when player is offline.
        winner = null; // Wipes the current winner value
        pickDailyNPC(); // Picks new NPC Task
    }

    public static void dailyResetTask() {
        if (CHOSEN_NPC_ID == 0) {
            pickDailyNPC();
        }
        if (timer.elapsed(TIME)) {
            pickDailyNPC();
        }
    }
}

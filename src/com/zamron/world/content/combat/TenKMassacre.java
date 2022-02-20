package com.zamron.world.content.combat;

import com.zamron.model.Item;
import com.zamron.util.Misc;
import com.zamron.world.World;
import com.zamron.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
/@Author Flub
*/
public class TenKMassacre {

    public static List<String> POSSIBLE_WINNERS = new ArrayList<>();

    public static boolean winnerReceivedReward = false;

    public static String lastWinnerName;

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

    public static int CURRENT_SERVER_KILLS = 0; //Used to keep track of current server kills (obviously)
    public static int REQUIRED_SERVER_KILLS = 10000; //How many kills are required to start prize draw?

    public static void incrementServerKills(Player possibleWinner, int numberOfKills) {
        //System.out.println("Player "+possibleWinner.getUsername() + " Added to 10kMassacre list after "+numberOfKills+" kills");
        if (CURRENT_SERVER_KILLS <= REQUIRED_SERVER_KILLS) {
            CURRENT_SERVER_KILLS += numberOfKills;
            //System.out.println("CURRENT SERVER KILLS = "+CURRENT_SERVER_KILLS);
            POSSIBLE_WINNERS.add(possibleWinner.getUsername());
            updateQuestTab();
        } else {
            pickWinner();
            restartGame();
        }
    }

    public static void updateQuestTab() {
        //TODO Update quest tab for all players with current KC
    }

    public static void pickWinner() {
        String winner = POSSIBLE_WINNERS.get(Misc.getRandom(POSSIBLE_WINNERS.size() - 1));//Pick winner from list
        try {
            Player winningPlayer = World.getPlayerByName(winner);//Set the
            rewardPlayer(winningPlayer);//Starts Reward Process below
        } catch (Exception e) {
            //System.out.println("Picking winner - player offline");
        }
        lastWinnerName = winner;//Saving last players username
        //System.out.println(lastWinnerName+"won but was offline");
    }

    public static void rewardPlayer(Player winningPlayer) {
        Item reward = REWARDS[Misc.getRandom(REWARDS.length - 1)];
        boolean stackable = reward.getDefinition().isStackable();
        try {
            if (winningPlayer == null) {// If Player is offline
                winnerReceivedReward = false;
                //System.out.println("User "+lastWinnerName+" is Offline");
            }
            if (winningPlayer != null && !winnerReceivedReward) { //If player is online and winner hasn't been rewarded yet
                //System.out.println("Reward player reached part 2");
                //TODO CHANGE THE GLOBAL MESSAGE BELOW TO SOMETHING YOU LIKE
                String rewardMessage = (winningPlayer.getUsername() + " has won " + reward.getAmount() + " x " + reward.getDefinition().getName());
                int freeInvSlots = winningPlayer.getInventory().getFreeSlots();
                //IF THE PLAYER IS ONLINE...
                if (stackable && freeInvSlots >= 1) { //STACKABLE REWARD ONLY NEEDS 1 INV SLOT
                    winningPlayer.getInventory().add(reward);
                    World.sendMessageNonDiscord(rewardMessage);
                    winnerReceivedReward = true;
                    restartGame();
                } else if (!stackable && freeInvSlots >= reward.getAmount()) { //NON STACKABLE
                    winningPlayer.getInventory().add(reward);
                    World.sendMessageNonDiscord(rewardMessage);
                    winnerReceivedReward = true;
                    restartGame();
                } else {
                    winningPlayer.getPacketSender().sendMessage("You won the 10k Massacre prize! Free up some invent space and relog to claim.");
                    winnerReceivedReward = false;
                }
            }
        } catch (Exception e) {
            //System.out.println("Winner of 10k Massacre was offline.");
        }
    }

    public static void checkOnLogin(Player player) {
        if (!winnerReceivedReward && player.getUsername().equalsIgnoreCase(lastWinnerName)) {
                rewardPlayer(player);
        }
    }

    public static void restartGame() {
        POSSIBLE_WINNERS.clear();
        CURRENT_SERVER_KILLS = 0;
    }


}
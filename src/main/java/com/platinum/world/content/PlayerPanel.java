package com.platinum.world.content;

import com.platinum.model.definitions.NpcDefinition;
import com.platinum.util.Misc;
import com.platinum.world.content.combat.DailyNPCTask;
import com.platinum.world.content.combat.TenKMassacre;
import com.platinum.world.content.event.SpecialEvents;
import com.platinum.world.entity.impl.player.Player;

public class PlayerPanel {
	
	
	//#1
		public static final String LINE_START = "            ";
		public static final String LINE5_START = " ";
		public static final String LINE1_START = "                ";
		public static final String LINE3_START = "              ";
		public static final String LINE2_START = " ";
		//#2
		public static final String LINE6_START = " ";
		public static final String MIDDLE_START = " ";
		public static final String MIDDLE2_START = " ";
		//#3
		public static final String LINE7_START = "          ";
		public static final String LINE9_START = " ";
		public static final String MIDDLE3_START = "           ";
		public static final String MIDDLE8_START = " ";
		public static final String MIDDLE7_START = "              ";
		public static final String MIDDLE6_START = " ";
		//#4
		public static final String MIDDLE9_START = " ";
		public static final String MIDDLE10_START = "            ";
		public static final String MIDDLE11_START = " ";
		public static final String MIDDLE12_START = "          ";
		//#5
		public static final String MIDDLE13_START = " ";
		public static final String MIDDLE14_START = " ";
		
	//#quest tab1
		public static final String Quest_START = "               ";
		public static final String Quest2_START = "                ";
		public static final String Quest3_START = "                 ";
		public static final String Quest4_START = "                     ";
		public static final String Quest5_START = "                   ";
		public static final String Quest6_START = "                  ";
		//#quest tab 1 under writting
		//#quest tab1
			public static final String QuestU_START = "                    ";
			public static final String QuestU2_START = "                      ";
			public static final String QuestU3_START = "-                    ";
			public static final String QuestU4_START = "                    ";
			public static final String QuestU5_START = "                       ";
			public static final String QuestU6_START = "                  ";

	private static int FIRST_STRING = 39159;
	private static int LAST_STRING = 39210;


	public static void handleSwitch(Player player, int index, boolean fromCurrent) {
		if (!fromCurrent) {
			resetStrings(player);
		}
		player.currentPlayerPanelIndex = index;
		switch (index) {
		case 1:
			refreshPanel(player); // first tab, cba rename just yet.
			break;

		case 2:
			sendSecondTab(player);
			break;
		case 3:
			sendThirdTab(player);
			break;
		case 4:
			sendForthTab(player);
			break;
		}
	}

	public static void refreshCurrentTab(Player player) {
		handleSwitch(player, player.currentPlayerPanelIndex, true);
	}

	public static void refreshPanel(Player player) {

		if (player.currentPlayerPanelIndex != 1) { // now it would update the other tab, if this is not the current tab
			refreshCurrentTab(player);
			return;
		}
		String[] Messages = new String[] { "  ", "<img=16>@blu@Player Information", "",
				"@whi@-@gre@Server Time:",
				"@bla@*@whi@"+Misc.getCurrentServerTime(),
		"@whi@-@gre@Time Played:",
		"@bla@*@whi@"+Misc.getHoursPlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())),
	 "@whi@-@gre@Total Level: ",
		 "@bla@*@whi@" +player.getSkillManager().getTotalLevel(),
	 	"@whi@-@gre@Username:",
		"@bla@*@whi@"+player.getUsername(),
	 	 "@whi@-@gre@Rank: ",
	 	 "@bla@*@whi@"+player.getRights().toString(),
	 	 "@whi@-@gre@Donator Rank: ",
	 	 "@bla@*@whi@"+player.getSecondaryPlayerRights().toString(),
	 "@whi@-@gre@Donated:",
	 "@bla@*@whi@"+player.getAmountDonated(),
	"@whi@-@gre@Exp Lock: ",
 "@bla@*@whi@"+(player.experienceLocked() ? "Locked" : "Unlocked"),


	
		};
		for (int i = 0; i < Messages.length; i++) {
			if (i + FIRST_STRING > LAST_STRING) {
				System.out.println("PlayerPanel(" + player.getUsername() + "): " + i + " is larger than max string: "
						+ LAST_STRING + ". Breaking.");
				break;
			}

			player.getPacketSender().sendString(i + FIRST_STRING, Messages[i]);

		}

	}

	private static void sendSecondTab(Player player) {

		String[] Messages = new String[] { "  ", "<img=16>@red@World And Events", "",
				"@whi@-@gre@Evil Tree:",
			 "@bla@*@whi@"	+(EvilTrees.getLocation() != null ? EvilTrees.getLocation().playerPanelFrame : "N/A"),
			 
			 "@whi@-@gre@Well of Goodwill:",
			 "@bla@*@whi@"	+(WellOfGoodwill.isActive() ? WellOfGoodwill.getMinutesRemaining() + " mins" : "N/A"),
			 
			 "@whi@-@gre@Crashed Star:",
		 "@bla@*@whi@"	+(ShootingStar.getLocation() != null ?ShootingStar.getLocation().playerPanelFrame : "N/A"),
		 
		 "@whi@-@gre@Bonus:",
			 "@bla@*@whi@"	+ SpecialEvents.getSpecialDay(),

				"@whi@-@gre@NPC Deaths Until Prize Draw:",
				"@bla@*@whi@"	+ TenKMassacre.CURRENT_SERVER_KILLS+"/"+TenKMassacre.REQUIRED_SERVER_KILLS,

				"@whi@-@gre@Daily NPC Task:",
				"@bla@*@whi@" + DailyNPCTask.KILLS_REQUIRED + " x " + NpcDefinition.forId(DailyNPCTask.CHOSEN_NPC_ID).getName(),
				"@bla@*@whi@Progress: "	+ player.getCurrentDailyNPCKills()+"/"+DailyNPCTask.KILLS_REQUIRED,


		 
		};

		for (int i = 0; i < Messages.length; i++) {
			if (i + FIRST_STRING > LAST_STRING) {
				System.out.println("PlayerPanel(" + player.getUsername() + "): " + i + " is larger than max string: "
						+ LAST_STRING + ". Breaking.");
				break;
			}

			player.getPacketSender().sendString(i + FIRST_STRING, Messages[i]);

		}

	}

	private static void sendThirdTab(Player player) {

		String[] Messages = new String[] { "  ", "<img=16>@whi@ Points & Statistics", "",
				
				"@whi@-@gre@Loyalty Points: ",
			 "@bla@*@whi@"+player.getPointsHandler().getLoyaltyPoints(),

			 "@whi@-@gre@Custom Well Donations: ",
	 "@bla@*@whi@"		+player.getCustomDonations(),

	 "@whi@-@gre@Prestige Points: ",
	 "@bla@*@whi@"	+player.getPointsHandler().getPrestigePoints(),

	 "@whi@-@gre@Trivia Points: ",
 "@bla@*@whi@"	+player.getPointsHandler().getTriviaPoints(),

 "@whi@-@gre@Voting Points: ",
 "@bla@*@whi@"	+player.getPointsHandler().getVotingPoints(),

 "@whi@-@gre@Donation Points: ",
 "@bla@*@whi@"	+player.getPointsHandler().getDonationPoints(),

 "@whi@-@gre@Raid Points: ",
 "@bla@*@whi@"	+player.getPointsHandler().getRaidPoints(),

 "@whi@-@gre@Custom pest control points: ",
 "@bla@*@whi@"	+player.getPointsHandler().getCustompestcontrolpoints(),

 "@whi@-@gre@Dung. Tokens: ",
"@bla@*@whi@"	+player.getPointsHandler().getDungeoneeringTokens(),

"@whi@-@gre@Boss Points: ",
"@bla@*@whi@"	+player.getBossPoints(),

"@whi@-@gre@Custom Boss Points: ",
	 "@bla@*@whi@"	+player.getCustomPoints(),

	 "@whi@-@gre@Slayer Points: ",
	  "@bla@*@whi@" +player.getPointsHandler().getSlayerPoints(),
	 	 
	  "@whi@-@gre@Bravek Tasks Completed: ",
	 "@bla@*@whi@" +player.getBravekTasksCompleted(),

	 "@whi@-@gre@Minigame Points1: ",
	 "@bla@*@whi@" +player.getPointsHandler().getminiGamePoints1(),

	 "@whi@-@gre@Pk Points: ",
	 "@bla@*@whi@" +player.getPointsHandler().getPkPoints(),

	 "@whi@-@gre@Wilderness Killstreak: ",
"@bla@*@whi@"+player.getPlayerKillingAttributes().getPlayerKillStreak(),

"@whi@-@gre@Wilderness Kills: ",
"@bla@*@whi@" +player.getPlayerKillingAttributes().getPlayerKills(),

"@whi@-@gre@Wilderness Deaths: ",
"@bla@*@whi@" +player.getPlayerKillingAttributes().getPlayerDeaths(),

		};

		for (int i = 0; i < Messages.length; i++) {
			if (i + FIRST_STRING > LAST_STRING) {
				System.out.println("PlayerPanel(" + player.getUsername() + "): " + i + " is larger than max string: "
						+ LAST_STRING + ". Breaking.");
				break;
			}

			player.getPacketSender().sendString(i + FIRST_STRING, Messages[i]);

		}

	}

	private static void sendForthTab(Player player) {

		String[] Messages = new String[] { " ", "<img=16>@gre@Slayer Information", "",

				"@whi@-@gre@Master: ",
			 "@bla@*@whi@"	 +player.getSlayer().getSlayerMaster(),
		

			 "@whi@-@gre@Duo Partner: ",
			"@bla@*@whi@"	+ player.getSlayer().getDuoPartner(),
			
			"@whi@-@gre@Task: ",
		 "@bla@*@whi@"		+player.getSlayer().getSlayerTask(),
				
		 "@whi@-@gre@Task Amount: ",
		 "@bla@*@whi@"	+player.getSlayer().getAmountToSlay(),
		 
		 "@whi@-@gre@Task Streak: ",
 "@bla@*@whi@"	+player.getSlayer().getTaskStreak(),

		};

		for (int i = 0; i < Messages.length; i++) {
			if (i + FIRST_STRING > LAST_STRING) {
				System.out.println("PlayerPanel(" + player.getUsername() + "): " + i + " is larger than max string: "
						+ LAST_STRING + ". Breaking.");
				break;
			}

			player.getPacketSender().sendString(i + FIRST_STRING, Messages[i]);

		}

	}

	private static void resetStrings(Player player) {
		for (int i = FIRST_STRING; i < LAST_STRING; i++) {
			player.getPacketSender().sendString(i, "");
		}
	}
	/***
	 * 
	 * 
	 * 
	 * if(player.currentPlayerPanelIndex != 1) { // now it would update the other
	 * tab, if this is not the current tab refreshCurrentTab(player); return; }
	 * 
	 * String[] Messages = new String[] { "@red@ - @whi@ World Overview",
	 * "@or2@Players Online: @or2@[ @yel@" + (int) (World.getPlayers().size()) +
	 * "@or2@ ]", (ShootingStar.CRASHED_STAR == null ? "@or2@Crashed
	 * Star: @red@Cleared" : "@or2@Crashed Star: @gre@" +
	 * ShootingStar.CRASHED_STAR.getStarLocation().playerPanelFrame),
	 * (EvilTree.SPAWNED_TREE == null ? "@or2@Evil Tree: @red@Cleared" : "@or2@Evil
	 * Tree: @gre@" + EvilTree.SPAWNED_TREE.getTreeLocation().playerPanelFrame),
	 * (Wildywyrm.wyrmAlive ? "@or2@WildyWyrm: @gre@" +
	 * Wildywyrm.getPlayerPanelHint() : "@or2@WildyWyrm: @red@Dead"), //
	 * (Abyssector.wyrmAlive ? "@or2@Abyssector:
	 * // @gre@"+Abyssector.getPlayerPanelHint() : "@or2@Abyssector: @red@Dead"),
	 * (WellOfGoodwill.isActive() ? "@or2@Well of Goodwill: @gre@On" : "@or2@Well of
	 * Goodwill: @red@Off"), (doMotivote.getVoteCount() <= 20 ? "@or2@Vote
	 * Boss: @yel@" + doMotivote.getVoteCount() + "/20" : "@or2@Vote
	 * Boss: @gre@::Vboss"), // "Vote Boss" + doMotivote.getVoteCount()+"@bla@
	 * votes.", "@or3@ - @whi@ Account Information", // "@yel@Difficulty:
	 * // @whi@"+Misc.capitalizeString(player.getDifficulty().toString().toLowerCase()),
	 * "@or2@Mode: @yel@" +
	 * Misc.capitalizeString(player.getGameMode().toString().toLowerCase().replace("_",
	 * " ")), "@or2@Claimed: @yel@$" + player.getAmountDonated(), "@or2@Time
	 * played: @yel@" + Misc.getTimePlayed((player.getTotalPlayTime() +
	 * player.getRecordedLogin().elapsed())), "@or2@Current Double XP skill:@yel@ "
	 * + StringUtils.capitalizeFirst(DoubleXPSkillEvent.currentSkill.toString()),
	 * "@or3@ - @whi@ Statistics", "@or2@NPC kill Count: @yel@ " +
	 * player.getPointsHandler().getNPCKILLCount(), "@red@Boss Points: @yel@ " +
	 * player.getPointsHandler().getBossPoints(), "@or2@Event Points: @yel@ " +
	 * player.getPointsHandler().getEventPoints(), "@or2@Prestige Points: @yel@" +
	 * player.getPointsHandler().getPrestigePoints(), "@or2@Total Prestige: @yel@" +
	 * player.getPointsHandler().getTotalPrestiges(), "@or2@Commendations: @yel@ " +
	 * player.getPointsHandler().getCommendations(), "@or2@Loyalty Points: @yel@" +
	 * (int) player.getPointsHandler().getLoyaltyPoints(), "@or2@Dung. Tokens: @yel@
	 * " + player.getPointsHandler().getDungeoneeringTokens(), "@or2@Voting
	 * Points: @yel@ " + player.getPointsHandler().getVotingPoints(), "@or2@Slayer
	 * Points: @yel@" + player.getPointsHandler().getSlayerPoints(), "@or2@Penguin
	 * Multiplier: +@yel@ " + player.getPointsHandler().getSHILLINGRate() +
	 * "%", "@or2@Barrows Points: @yel@" +
	 * player.getPointsHandler().getBarrowsPoints(), "@or2@Member Points: @yel@" +
	 * player.getPointsHandler().getMemberPoints(), "@or2@Pk Points: @yel@" +
	 * player.getPointsHandler().getPkPoints(), "@or2@Wilderness Killstreak: @yel@"
	 * + player.getPlayerKillingAttributes().getPlayerKillStreak(), "@or2@Wilderness
	 * Kills: @yel@" + player.getPlayerKillingAttributes().getPlayerKills(),
	 * "@or2@Wilderness Deaths: @yel@" +
	 * player.getPlayerKillingAttributes().getPlayerDeaths(), "@or2@Arena
	 * Victories: @yel@" + player.getDueling().arenaStats[0], "@or2@Arena
	 * Points: @yel@" + player.getDueling().arenaStats[1], "@or2@Imp Kill
	 * Count: @yel@ " + player.getPointsHandler().getSPAWNKILLCount(), "@or2@Lord
	 * Kill Count: @yel@ " + player.getPointsHandler().getLORDKILLCount(),
	 * "@or2@Demon Kill Count: @yel@ " +
	 * player.getPointsHandler().getDEMONKILLCount(), "@or2@Dragon Kill Count: @yel@
	 * " + player.getPointsHandler().getDRAGONKILLCount(), "@or2@Beast Kill
	 * Count: @yel@ " + player.getPointsHandler().getBEASTKILLCount(), "@or2@King
	 * Kill Count: @yel@ " + player.getPointsHandler().getKINGKILLCount(),
	 * "@or2@Avatar Kill Count: @yel@ " +
	 * player.getPointsHandler().getAVATARKILLCount(), "@or2@Angel Kill Count: @yel@
	 * " + player.getPointsHandler().getANGELKILLCount(), "@or2@Lucien Kill
	 * Count: @yel@ " + player.getPointsHandler().getLUCIENKILLCount(),
	 * "@or2@Hercules Kill Count: @yel@ " +
	 * player.getPointsHandler().getHERCULESKILLCount(), "@or2@Satan Kill
	 * Count: @yel@ " + player.getPointsHandler().getSATANKILLCount(), "@or2@Zeus
	 * Kill Count: @yel@ " + player.getPointsHandler().getZEUSKILLCount(), "",
	 * "@or3@ - @whi@ Slayer", // "@or2@Open Kills Tracker", // "@or2@Open Drop
	 * Log", "@or2@Master: @yel@" + Misc
	 * .formatText(player.getSlayer().getSlayerMaster().toString().toLowerCase().replaceAll("_",
	 * " ")), (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK ?
	 * "@or2@Task: @yel@" + Misc.formatText(
	 * player.getSlayer().getSlayerTask().toString().toLowerCase().replaceAll("_", "
	 * ")) : "@or2@Task: @yel@" + Misc.formatText(
	 * player.getSlayer().getSlayerTask().toString().toLowerCase().replaceAll("_", "
	 * ")) + "s"), "@or2@Task Streak: @yel@" + player.getSlayer().getTaskStreak(),
	 * "@or2@Task Amount: @yel@" + player.getSlayer().getAmountToSlay(),
	 * (player.getSlayer().getDuoPartner() != null ? "@or2@Duo Partner: @yel@" +
	 * player.getSlayer().getDuoPartner() : "@or2@Duo Partner: @yel@N/A"),
	 * 
	 * /* "@yel@lre", "@red@red", "@dre@dre", "@yel@yel", "@whi@whi", "blu",
	 * "cya", "@mag@mag", "@bla@bla", "@gre@gre", "@gr1@gr1", "@gr2@gr2",
	 * "@gr3@gr3", "@str@str", "@or1@or1", "@or2@or2", "@or3@or3",
	 */

}
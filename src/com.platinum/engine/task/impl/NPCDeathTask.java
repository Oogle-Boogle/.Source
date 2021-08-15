package com.platinum.engine.task.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.*;
import com.platinum.model.Locations.Location;
import com.platinum.model.definitions.NPCDrops;
import com.platinum.util.Misc;
import com.platinum.util.RandomUtility;
import com.platinum.world.World;
import com.platinum.world.content.*;
import com.platinum.world.content.Achievements.AchievementData;
import com.platinum.world.content.NpcTasks.NpcTaskData;
import com.platinum.world.content.StarterTasks.StarterTaskData;
import com.platinum.world.content.combat.DailyNPCTask;
import com.platinum.world.content.combat.TenKMassacre;
import com.platinum.world.content.combat.bossminigame.BossMinigameFunctions;
import com.platinum.world.content.combat.strategy.impl.HarLakkRiftsplitter;
import com.platinum.world.content.combat.strategy.impl.KalphiteQueen;
import com.platinum.world.content.combat.strategy.impl.Nex;
import com.platinum.world.content.combat.strategy.impl.SuicsBoss;
import com.platinum.world.content.raids.OldRaidParty;
import com.platinum.world.content.raids.RaidNpc;
import com.platinum.world.content.serverperks.GlobalPerks;
import com.platinum.world.content.skill.impl.pvm.NpcGain;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;
import com.platinum.world.entity.impl.player.PlayerSaving;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an npc's death task, which handles everything an npc does before
 * and after their death animation (including it), such as dropping their drop
 * table items.
 * 
 * @author relex lawl
 */

public class NPCDeathTask extends Task {

	private Set<Integer> Tier1to3 = new HashSet<>(
			Arrays.asList(420, 842, 174, 3767, 51, 2783, 17, 422, 3263, 15, 1982));
	private Set<Integer> Tier4to6 = new HashSet<>(
			Arrays.asList(9994, 9932, 224, 1999, 16, 9993, 9277, 9944, 9273, 9903, 8133));
	private Set<Integer> Tier7to9 = new HashSet<>(
			Arrays.asList(9247, 8493, 9203, 172, 9935, 170, 169, 219, 12239, 3154));
	private Set<Integer> Tier10 = new HashSet<>(Arrays.asList(33, 1684, 5957, 5958, 5959, 185, 6311));

	int[] justiciarIds = new int[] { 9903, 8133 };

	/**
	 * The NPCDeathTask constructor.
	 * 
	 * @param npc The npc being killed.
	 */
	public NPCDeathTask(NPC npc) {
		super(2);
		this.npc = npc;
		this.ticks = 2;
	}

	/**
	 * The npc setting off the death task.
	 */
	private final NPC npc;

	/**
	 * The amount of ticks on the task.
	 */
	private int ticks = 2;

	/**
	 * The player who killed the NPC
	 */
	private Player killer = null;

	@SuppressWarnings("incomplete-switch")
	@Override
	public void execute() {
		try {
			if (killer != null && !killer.isMiniMe) {
				killer.resetRichPresence();
			}
			npc.setEntityInteraction(null);
			switch (ticks) {
			case 2:
				npc.getMovementQueue().setLockMovement(true).reset();
				killer = npc.getCombatBuilder().getKiller(npc.getId() != 2745 && npc.getId() != 25
						&& npc.getId() != 6309 && npc.getId() != 8548 && npc.getId() != 8949 && npc.getId() != 6593
						&& npc.getId() != 9993 && npc.getId() != 9903 && npc.getId() != 2005 && npc.getId() != 421
						&& npc.getId() != 6313 && npc.getId() != 9913 && npc.getId() != 422 && npc.getId() != 7286);

				if (!(npc.getId() >= 6142 && npc.getId() <= 6145) && !(npc.getId() > 5070 && npc.getId() < 5081))
					npc.performAnimation(new Animation(npc.getDefinition().getDeathAnimation()));

				/** CUSTOM NPC DEATHS **/
				if (npc.getId() == 13447) {
					Nex.handleDeath();
				}

				break;
			case 0:
				if (killer != null) {
					if (killer.isMiniMe) {
						killer = killer.getMinimeOwner();
						if (!World.getPlayers().contains(killer)) {
							stop();
							return;
						}
					}
				}
				if (killer != null) {
					killer.setNpcKills(killer.getNpcKills() + 1);
					PlayerSaving.save(killer);
					if (npc instanceof RaidNpc) {
						OldRaidParty party = killer.getOldRaidParty();
						RaidNpc raidNpc = (RaidNpc) npc;
						if (party != null) {

							if (party.getCurrentRaid().getStage() == raidNpc.getStageRequiredToAttack()) {
								party.getCurrentRaid().nextLevel();
							}
							stop();
							return;
						}
					}

					killer.handleKeyRates(killer, npc);

					//Add here

					if (npc.getId() == 4541 && killer.getcustomOlmKC() < 99) {
						killer.incrementCustomOlmKC(1);
						killer.sendMessage(
								"@red@You now have: @blu@" + killer.getcustomOlmKC() + " Custom Olm Kill-count");
					}
					if (killer.getcustomOlmKC() == 99) {
						killer.setCustomOlmKC(0);
						killer.getInventory().add(19100, 1);
						killer.sendMessage("@red@Enjoy the key");
					}
					
					killer.addNpcKillCount(npc.getId());
					killer.getStarterProgression().handleKill(npc.getId());
					killer.sendMessage("@blu@You now have @blu@" + killer.getNpcKillCount(npc.getId()) + "@red@ "
							+ npc.getDefinition().getName() + "@red@ KC!");
					
					
					if (Tier1to3.contains(npc.getId())) {
						killer.getInventory().addItem(19864, 1);
						killer.getInventory().addItem(7629, 1);
						killer.sendMessage("@blu@You received 1x PVM ticket for killing an T1 - T3 NPC");
						killer.sendMessage("@blu@You received 1x Starter ticket for killing an T1 - T3 NPC");
						// taxbag
						int chance = RandomUtility.inclusiveRandom(0, 100);
						if (chance >= 97 && chance <= 100) {
							World.sendStaffMessage("Testing for the chance: " + chance);
							killer.sendMessage("@red@You received 10x Tax Bag for killing this NPC");
							killer.getInventory().add(10835, 10);
						} else if (chance >= 0 && chance <= 97) {
							killer.getInventory().add(10835, 1);
							killer.sendMessage("@red@You received 1x Tax Bag for killing this NPC");
						}

						// expcustomskill
						if (killer.getOldRaidParty() == null && !(npc instanceof RaidNpc)) {
							NpcGain.GainBossesXP(killer);
						} else if (killer.getOldRaidParty() != null && npc instanceof RaidNpc) {
							NpcGain.RaidNPCBossXP(killer, npc);
						}
						if (GlobalPerks.getInstance().getActivePerk() == GlobalPerks.Perk.BOSS_POINTS) {
							killer.setBossPoints(killer.getBossPoints() + 2);
						}
						
						killer.sendMessage("<img=11>You now have @red@" + killer.getBossPoints() + " Boss Points!");
						killer.incrementTotalBossKills(1);
					}

					if (npc.getId() == 9280) {
						if (killer.getSummoning().getFamiliar().getSummonNpc().getId() == 3032) {
							killer.incrementMinionsKC(2);
							killer.sendMessage("@red@You now have: @blu@" + killer.getMinionsKC()
									+ " Custom Rex Minions Kill-count");
						} else {
							killer.incrementMinionsKC(1);
							killer.sendMessage("@red@You now have: @blu@" + killer.getMinionsKC()
									+ " Custom Rex Minions Kill-count");
						}

					}
					if (npc.getId() == 2436) {
						killer.setRuneUnityPoints(killer.getRuneUnityPoints() + 5);
						killer.sendMessage(
								"<img=11>You now have @red@" + killer.getRuneUnityPoints() + " Platinum Points!");
					}
					if (npc.getId() == 604 || npc.getId() == 605 || npc.getId() == 609 || npc.getId() == 600
							|| npc.getId() == 603 || npc.getId() == 610 || npc.getId() == 607 || npc.getId() == 608
							|| npc.getId() == 611) {
						killer.setAmongPoints(killer.getAmongPoints() + 1);
						killer.sendMessage("<img=11>You now have @red@" + killer.getAmongPoints() + " Among Points!");
					}
					if (npc.getId() == 9855 && killer.getEquipment().contains(5131)) {
						killer.getPointsHandler().incrementMiniGamePoints1(1);
						killer.sendMessage("@red@Since you're wearing DMG you recieve an extra Minigame1 point");
						killer.sendMessage("@blu@You Now Have " + killer.getPointsHandler().getminiGamePoints1()
								+ " Minigame1 Points");
					}
					if (npc.getId() == 9176) {
						int chance = RandomUtility.random(100);
						if (chance >= 95) {
							killer.getPointsHandler().incrementMiniGamePoints1(1);
							killer.sendMessage("You have received a MiniGamePoint1 you now have "
									+ killer.getPointsHandler().getminiGamePoints1() + " Minigame1 Points");
						}
					}
					if (npc.getId() == 8549) {
						int chance = RandomUtility.random(1000);
						if (chance >= 1000) {
							killer.getPointsHandler().incrementMiniGamePoints3(1);
							killer.sendMessage("Gj you got a Minigamepoint3!! You now have: "
									+ killer.getPointsHandler().getminiGamePoints3() + " Minigame3 Points");
						}
					}

					if (killer.isInRaid()) {
						killer.getRaidParty().getOwner().getCustomRaid().handleKill();
					}

					if (npc.getId() == 8572) {
						killer.getDefendersMg().handleKill();
					}

					if (npc.getId() == 8573) {

						int random = RandomUtility.inclusiveRandom(0, 100);

						if (random > 95) {
							killer.getInventory().add(5206, 10);
							killer.sendMessage("[LUCKY DROP] You got 10 tokens from this kill");
						} else if (random > 60) {
							killer.getInventory().add(5206, 2);
							killer.sendMessage("You got 2 tokens from this kill");

							killer.getInventory().add(5206, 1);
							// killer.sendMessage("You got 1 token from this kill.");
						}
					}
					if (npc.getId() == 420) { // Joker
						NpcTasks.doProgress(killer, NpcTaskData.KILL_50_JOKER);
					}
					if (npc.getId() == 51) { // Frost dragons
						NpcTasks.doProgress(killer, NpcTaskData.KILL_100_FROST_DRAGONS);
					}
					if (npc.getId() == 2783) { // Sirenic beasts
						NpcTasks.doProgress(killer, NpcTaskData.KILL_150_SIRENIC_BEASTS);
					}
					if (npc.getId() == 15) { // Hades
						NpcTasks.doProgress(killer, NpcTaskData.KILL_250_HADES);
					}
					if (npc.getId() == 9994) { // Shaman Defenders
						NpcTasks.doProgress(killer, NpcTaskData.KILL_300_DEFENDERS);
					}
					if (npc.getId() == 224) { // Demonic Olm
						NpcTasks.doProgress(killer, NpcTaskData.KILL_250_DEMONIC_OLMS);
					}
					if (npc.getId() == 1999) { // Cerb
						NpcTasks.doProgress(killer, NpcTaskData.KILL_50_CEREBRUS);
					}
					if (npc.getId() == 16) { // Abbadon
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_ZEUS);
					}
					if (npc.getId() == 9993) { // Custom infartico
						NpcTasks.doProgress(killer, NpcTaskData.KILL_50_INFARTICO);
					}
					if (npc.getId() == 9277) { // Lord Valor
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_LORD_VALOR);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_LORD_VALOR);
					}
					if (npc.getId() == 9944) { // Hurricane warriors
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_HURRICANE_WARRIORS);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_3000_HURRICANE_WARRIORS);
					}
					if (npc.getId() == 9273) { // Dzanth
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_DZANTH);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_3000_DZANTH);
					}
					if (npc.getId() == 9903) { // King Kong
						NpcTasks.doProgress(killer, NpcTaskData.KILL_50_KINGKONG);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_KINGKONG);
					}
					if (npc.getId() == 8133) { // CORP
						NpcTasks.doProgress(killer, NpcTaskData.KILL_50_CORP_BEAST);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_CORP_BEAST);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_CORP_BEAST);
					}
					if (npc.getId() == 9247) { // Lucid Warriors
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_LUCID_WARRIORS);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_LUCID_WARRIORS);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_LUCID_WARRIORS1);
					}
					if (npc.getId() == 8493) { // HULK
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_HULK);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_HULK);
					}
					if (npc.getId() == 9203) { // DARKBLUE WIZARD Dragon
						NpcTasks.doProgress(killer, NpcTaskData.KILL_500_DARKBLUE_WIZARDS);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_DARKBLUE_WIZARDS);
					}
					if (npc.getId() == 172) { // Ice Warrior
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_HEATED_PYRO);
					}
					if (npc.getId() == 9935) { // WYRM
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_WYRM);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_WYRM);
					}
					if (npc.getId() == 170) { // TRINITY
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_TRINITY);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_TRINITY);
					}
					if (npc.getId() == 169) { // CLOUD
						NpcTasks.doProgress(killer, NpcTaskData.KILL_2500_CLOUD);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_CLOUD);
					}

					if (npc.getId() == 219) { // ROUGE
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_HERBAL_ROGUE);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_HERBAL_ROGUE);
					}
					if (npc.getId() == 12239) { // Exoden
						NpcTasks.doProgress(killer, NpcTaskData.KILL_1000_EXODEN);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_EXODEN);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_EXODEN);
					}
					if (npc.getId() == 3154) { // Nex
						NpcTasks.doProgress(killer, NpcTaskData.KILL_1000_SUPREME_NEX);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_5000_SUPREME_NEX);
						NpcTasks.doProgress(killer, NpcTaskData.KILL_10000_SUPREME_NEX);
					}

					// Start Raids1 NPCS

					if (npc.getId() == 3224 || npc.getId() == 3225 || npc.getId() == 3226 || npc.getId() == 177) {
						Position player = null;
						KeysEvent.handleRaidsNPC(killer, player, npc.getPosition());
					}

					if (npc.getId() == 527) {
						Position player = null;
						KeysEvent.handleRaidsNPCBoss(killer, player, npc.getPosition());
					}
					// End Raids1 NPCS

					if (killer.getSummoning() != null && killer.getSummoning().getFamiliar() != null
							&& killer.getSummoning().getFamiliar().getSummonNpc().getId() == 3032) {
						killer.incrementNPCKills(1);
					}

					if (npc.getId() == 6306) {
						int chance = RandomUtility.random(1000);
						if (chance >= 1000) {
							killer.getPointsHandler().incrementMiniGamePoints4(1);
							killer.sendMessage("You have received a Minigamepoint4!! You now have: "
									+ killer.getPointsHandler().getminiGamePoints4() + " Minigame4 Points");
						}
					}
					if (npc.getId() == 8281) {
						killer.forceChat("Aaahh, that hurts!!");
						killer.dealDamage(new Hit(Misc.random(100, 800), Hitmask.DARK_PURPLE, CombatIcon.DEFLECT));
					}
					if (npc.getId() == 2060) {
						int chance = RandomUtility.random(10000);
						if (chance >= 10000) {
							killer.getPointsHandler().incrementMiniGamePoints5(1);
							killer.sendMessage("LUCKY! You have received a Minigamepoint5, You now have "
									+ killer.getPointsHandler().getminiGamePoints5() + " Minigame5 points");
						}
					}

					if (npc.getId() == 6593) {
						SuicsBoss.resetAll();
					}

					Achievements.doProgress(killer, AchievementData.DEFEAT_10000_MONSTERS);
					if (npc.getId() == 50) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_KING_BLACK_DRAGON);
					} else if (npc.getId() == 3200) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CHAOS_ELEMENTAL);
					} else if (npc.getId() == 8349) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_A_TORMENTED_DEMON);
					} else if (npc.getId() == 3491) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CULINAROMANCER);
					} else if (npc.getId() == 12840 || npc.getId() == 12841) {
						StarterTasks.finishTask(killer, StarterTaskData.KILL_WARMONGER);
					} else if (npc.getId() == 8528) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_NOMAD);
					} else if (npc.getId() == 2745) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_JAD);
						StarterTasks.doProgress(killer, StarterTaskData.KILL_10_JADS);
					} else if (npc.getId() == 5996) {
						StarterTasks.doProgress(killer, StarterTaskData.KILL_10_GLODS);
					} else if (npc.getId() == 4540) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_BANDOS_AVATAR);
					} else if (npc.getId() == 6260) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_GENERAL_GRAARDOR);
						killer.getAchievementAttributes().setGodKilled(0, true);
					} else if (npc.getId() == 6222) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_KREE_ARRA);
						killer.getAchievementAttributes().setGodKilled(1, true);
					} else if (npc.getId() == 6247) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_COMMANDER_ZILYANA);
						killer.getAchievementAttributes().setGodKilled(2, true);
					} else if (npc.getId() == 6203) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_KRIL_TSUTSAROTH);
						killer.getAchievementAttributes().setGodKilled(3, true);
					} else if (npc.getId() == 8133) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CORPOREAL_BEAST);
					} else if (npc.getId() == 3154) {
						Achievements.finishAchievement(killer, AchievementData.DEFEAT_NEX);
						killer.getAchievementAttributes().setGodKilled(4, true);
					}
					/** ACHIEVEMENTS **/
					switch (killer.getLastCombatType()) {
					case MAGIC:
						Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_MAGIC);
						break;
					case MELEE:
						Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_MELEE);
						break;
					case RANGED:
						Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_RANGED);
						break;
					}

					
					killer.getDpsOverlay().resetDamageDone(); // will work now
					/** LOCATION KILLS **/
					if (npc.getLocation().handleKilledNPC(killer, npc)) { //todo check
						stop();
						return;
					}
					/*
					 * Halloween event dropping
					 */

					if (npc.getId() == 1973) {
						TrioBosses.handleSkeleton(killer, npc.getPosition());
					}
					if (npc.getId() == 75) {
						TrioBosses.handleZombie(killer, npc.getPosition());
					}
					if (npc.getId() == 103) {
						TrioBosses.handleGhost(killer, npc.getPosition());
					}
					if (npc.getId() == 3334) {
						Wildywyrm.giveLoot(killer, npc, npc.getPosition());
					}

					/*
					 * End Halloween event dropping
					 */

					/**
					 * Keys Event Monsters
					 **/
					if (npc.getId() == 7134) {
						KeysEvent.handleSkotizo(killer, npc.getPosition());
					}
					if (npc.getId() == 8549) {
						KeysEvent.handlePhoenix(killer, npc.getPosition());
					}
					if (npc.getId() == 499) {
						KeysEvent.handleThermo(killer, npc.getPosition());
					}
					if (npc.getId() == 2060) {
						KeysEvent.handleSlashBash(killer, npc.getPosition());
					}
					if (npc.getId() == 2642) {
						KeysEvent.handleKBD(killer, npc.getPosition());
					}
					if (npc.getId() == 1999) {
						KeysEvent.handleCerb(killer, npc.getPosition());
					}
					if (npc.getId() == 7134) {
						KeysEvent.handleBork(killer, npc.getPosition());
					}
					if (npc.getId() == 1382) {
						KeysEvent.handleGlacor(killer, npc.getPosition());
					}
					if (npc.getId() == 6766) {
						KeysEvent.handleShaman(killer, npc.getPosition());
					}
					if (npc.getId() == 941) {
						KeysEvent.handleGreenDragon(killer, npc.getPosition());
					}
					if (npc.getId() == 55) {
						KeysEvent.handleBlueDragon(killer, npc.getPosition());
					}
					if (npc.getId() == 1615) {
						KeysEvent.handleAbbyDemon(killer, npc.getPosition());
					}

					/** PARSE DROPS **/
					if (npc.getLocation() != Location.BOSS_TIER_LOCATION) {
						if (npc.getId() == 9911) {
							HarLakkRiftsplitter.handleDrop(npc);
						}

						if (npc.getId() == 25) {
							TheSeph.handleDrop(npc);
						}
						if (npc.getId() == 8548) {
							HWeenBoss.handleDrop(npc);
						}
						if (npc.getId() == 8949) {
							Juggernaut.handleDrop(npc);
						}
						if (npc.getId() == 25) {
							TheSeph.handleDrop(npc);
						}
						if (npc.getId() == 8548) {
							DailyNpc.handleDrop(npc);
						}
						if (npc.getId() == 2005) {
							TheMay.handleDrop(npc);
						}
						if (npc.getId() == 421) {
							TheRick.handleDrop(npc);
						}
						if (npc.getId() == 422) {
							Onslaught.handleDrop(npc);
						}
						if (npc.getId() == 2745) {
							Tztok.handleDrop(npc);
						}
						if (npc.getId() == 7134) {
							Bork.handleDrop(npc);
						} else {
							NPCDrops.dropItems(killer, npc); //todo check
						}
					}

					/** SLAYER **/
					killer.getSlayer().killedNpc(npc);
				}
				stop();
				break;
			}
			ticks--;
		} catch (Exception e) {
			System.out.println("ERROR IN NPCDEATHTASK .. "+e.getMessage());
			e.printStackTrace();
			stop();
		}
	}

	@Override
	public void stop() {
		setEventRunning(false);
		npc.setDying(false);


		if (npc.getLocation() == Location.BOSS_TIER_LOCATION) {
			if (killer.currentBossWave <= 4 && !killer.isShouldGiveBossReward()) {
				killer.currentBossWave++;
				killer.setShouldGiveBossReward(true);
				killer.forceChat("I should leave now!");
				BossMinigameFunctions.despawnNpcs(killer);
			}

			if (killer.currentBossWave <= 4) {
				World.sendMessageNonDiscord("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just completed wave " + (killer.getCurrentBossWave()) + " at ::boss!");
			}
			if (killer.currentBossWave == 5) {
				World.sendMessageNonDiscord("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just killed completed the final wave at ::boss!");
			}

			TaskManager.submit(new Task(2, killer, false) {
				@Override
				public void execute() {
					killer.moveTo(BossMinigameFunctions.ARENA_ENTRANCE);
					stop();
				}
			});
		}

		TenKMassacre.incrementServerKills(killer,1); // Add killer to list and increment server kills by 1

		if (npc.getId() == DailyNPCTask.CHOSEN_NPC_ID) {
			DailyNPCTask.countPlayerKill(killer);
		}

		// respawn
		if (npc.getDefinition().getRespawnTime() > 0 && npc.getLocation() != Location.GRAVEYARD
				&& npc.getLocation() != Location.DUNGEONEERING && npc.getLocation() != Location.BOSS_TIER_LOCATION
				&& npc.getLocation() != Location.INSTANCE_ARENA && !killer.isInRaid() && !(npc instanceof RaidNpc)) {

			TaskManager.submit(new NPCRespawnTask(npc, npc.getDefinition().getRespawnTime(), killer));
			System.out.println("setting respawn task for npc: " + npc.getId() + " OBJECT: " + npc.getDefinition().getName());
		} else {
			System.out.println("Not setting respawn task for npc: " + npc.getId() + " OBJECT: " + npc.getDefinition().getName());
		}

		World.deregister(npc);

		if (npc.getId() == 1158 || npc.getId() == 1160) {
			KalphiteQueen.death(npc.getId(), npc.getPosition());
		}
		if (Nex.nexMob(npc.getId())) {
			Nex.death(npc.getId());
		}
	}
}

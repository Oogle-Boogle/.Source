package com.platinum.world.content.skillingboss;

import com.platinum.model.Skill;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.content.combat.CombatBuilder;
import com.platinum.world.content.combat.CombatFactory;
import com.platinum.world.content.discord.DiscordMessenger;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

import java.text.NumberFormat;
import java.util.*;

public class SkillBossHandler {

    private static Skill selectedSkill;

    public static NPC npc;

    public static void handleServerXP(long XP) {

        if (SkillBossConfig.serverXPCounter < SkillBossConfig.requiredServerXP) {
            SkillBossConfig.serverXPCounter += XP;
        } else {
            SkillBossConfig.serverXPCounter = 0;
            spawnSkillBoss();
        }
    }

    public static String formatNumber(long number) {
        return NumberFormat.getInstance().format(number);
    }

   /** Check the total XP **/
    public static void sequence() { //Runs every tick
        if (SkillBossConfig.xpUpdateTimer > 0 && SkillBossConfig.serverXPCounter < SkillBossConfig.requiredServerXP) {
            SkillBossConfig.xpUpdateTimer--;
        } else {
            SkillBossConfig.xpUpdateTimer = SkillBossConfig.timeDelay;
            long remainder = SkillBossConfig.requiredServerXP - SkillBossConfig.serverXPCounter;
            World.sendMessageDiscord("The global XP counter is currently: "
                    + formatNumber(SkillBossConfig.serverXPCounter)
                    + "! We need " + formatNumber(remainder)
                    + " until the skilling boss spawns!");
        }
    }

    /** Spawns the Skilling Boss **/
    private static void spawnSkillBoss() {
        selectedSkill = selectSkill();
        npc = new NPC(SkillBossConfig.npcID, SkillBossConfig.spawnPos);
        World.register(npc);

        World.sendMessageNonDiscord("@blu@The Skilling Boss has just spawned! Skill Selected: @red@"
                + selectedSkill.getFormatName()
                + "@blu@!");

        DiscordMessenger.sendInGameMessage("The Skilling Boss has just spawned! Skill Selected: "
                + selectedSkill.getFormatName()
                + "!");


    }

    /** Chooses a random Skill **/
    private static Skill selectSkill() {
        return Skill.values()[Misc.exclusiveRandom(Skill.values().length)];
    }

    public static void calculateDamage(NPC npc) {

        if (npc.getCombatBuilder().getDamageMap().size() == 0) {
            return;
        }

        Map<Player, Integer> killers = new HashMap<>();

        for (Map.Entry<Player, CombatBuilder.CombatDamageCache> entry : npc.getCombatBuilder().getDamageMap().entrySet()) {

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

        List<Map.Entry<Player, Integer>> result = sortEntries(killers);
        int count = 0;

        Player[] topFive = {null, null, null, null, null};

        int[] topFiveDmg = {0, 0, 0, 0, 0};

        int i = 0;

        for (Map.Entry<Player, Integer> entry : result) {

            Player killer = entry.getKey();
            int damage = entry.getValue();

            if (!Arrays.asList(topFive).equals(killer) && i <= 4) {
                topFive[i] = killer;
                topFiveDmg[i] = damage;

                System.out.println("Added " + killer.getUsername() + " who had dmg " + damage);
            } else {
                topFive[i] = null;
                topFiveDmg[i] = 0;
            }


            if (++count >= 20) {
                break;
            }
            i++;

        }

        String winners = "";
        String fir = "";
        String sec = "";
        String thi = "";
        String fou = "";
        String fif = "";
        int xpReward = 0;
        for (int t = 0; t <= 4; t++) {
            if (topFive[t] != null) {

                String username = topFive[t].getUsername();
                String dmg = " dmg: " + Misc.formatAmount(topFiveDmg[t]);

                topFive[t].getPacketSender().sendInterface(297);
                topFive[t].getPacketSender().sendString(299, "@bla@Congratulations "
                                + username
                                + "!")
                        .sendString(6158, selectedSkill.getFormatName() + " XP!")
                        .sendString(303, "")
                        .sendString(304, "");

                switch (t) {
                    case 0:
                        fir = "1st: " + username + dmg;
                        xpReward = topFiveDmg[t];
                        topFive[t].getPacketSender().sendString(301, "@bla@First Place!")
                                .sendString(4444, "@gre@" + Misc.formatAmount(xpReward));
                        break;
                    case 1:
                        sec = "2nd: " + username + dmg;
                        xpReward = topFiveDmg[t] / 25;
                        topFive[t].getPacketSender().sendString(301, "@bla@Second Place!")
                                .sendString(4444, "@gre@" + Misc.formatAmount(xpReward));
                        break;
                    case 2:
                        thi = "3rd: " + username + dmg;
                        xpReward = topFiveDmg[t] / 50;
                        topFive[t].getPacketSender().sendString(301, "@bla@Third Place!")
                                .sendString(4444, "@gre@" + Misc.formatAmount(xpReward));
                        break;
                    case 3:
                        fou = "4th: " + username + dmg;
                        xpReward = topFiveDmg[t] / 75;
                        topFive[t].getPacketSender().sendString(301, "@bla@Fourth Place!")
                                .sendString(4444, "@gre@" + Misc.formatAmount(xpReward));
                        break;
                    case 4:
                        fif = "5th: " + username + dmg;
                        xpReward = topFiveDmg[t] / 100;
                        topFive[t].getPacketSender().sendString(301, "@bla@Fifth Place!")
                                .sendString(4444, "@gre@" + Misc.formatAmount(xpReward));
                        break;
                }

                reward(topFive[t], xpReward, selectedSkill);
            }
            winners = fir + sec + thi + fou + fif;
        }

        World.sendMessageDiscord("[Skill Boss] " + winners);
        World.deregister(npc);

    }

    private static void reward(Player player, int xpReward, Skill skill) {
        if (player != null && skill != null) {
            player.getSkillManager().addExperience(skill, xpReward);
            player.getPacketSender().sendMessage("You have gained @blu@" + Misc.formatAmount(xpReward) + " @bla@" + selectedSkill.getFormatName() + " XP!");
        }
    }


    static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(sortedEntries, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }

        });

        return sortedEntries;

    }




}

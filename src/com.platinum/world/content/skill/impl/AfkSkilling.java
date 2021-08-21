package com.platinum.world.content.skill.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Animation;
import com.platinum.model.Skill;
import com.platinum.world.content.combat.weapon.FightStyle;
import com.platinum.world.entity.impl.player.Player;

public class AfkSkilling {

    public static int afkToken = 12852;

    /** Should the player be eligable to receive XP when AFKing a Skill? **/

    public static boolean canEarnAfkXP(Player player, Skill skill) {
       return player.getRights().isMember()
               || player.getSecondaryPlayerRights().isSecondaryMember()
               || player.getRights().isStaff()
               || player.getSkillManager().getMaxLevel(skill) >= 80;
    }

    public static void afkSkilling(Player player, int xp, Skill skill, int anim) {

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        player.setCurrentTask(new Task(2, player, true) {
            @Override
            public void execute() {
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                }
                player.performAnimation(new Animation(anim));

                if (canEarnAfkXP(player, skill)) {
                    player.getSkillManager().addExperience(skill, xp);
                }
                player.getInventory().add(afkToken, 1);
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }

    public static void afkCombat(Player player, int xp) {

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }

        player.setCurrentTask(new Task(2, player, true) {
            @Override
            public void execute() {
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                }
                player.performAnimation(new Animation(423));

                if (player.getFightType().getStyle() == FightStyle.AGGRESSIVE) {
                    player.getPacketSender().sendRichPresenceState("AFK Strength Training");
                    if (player.getSkillManager().getMaxLevel(Skill.STRENGTH) >= 80) {
                        player.getSkillManager().addExperience(Skill.STRENGTH, xp);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                    player.getPacketSender().sendRichPresenceState("AFK Combat Training");
                    if (player.getSkillManager().getMaxLevel(Skill.ATTACK) >= 80
                    && player.getSkillManager().getMaxLevel(Skill.STRENGTH) >= 80
                    && player.getSkillManager().getMaxLevel(Skill.DEFENCE) >= 80) {
                        player.getSkillManager().addExperience(Skill.ATTACK, xp / 3);
                        player.getSkillManager().addExperience(Skill.STRENGTH, xp / 3);
                        player.getSkillManager().addExperience(Skill.DEFENCE, xp / 3);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.DEFENSIVE) {
                    player.getPacketSender().sendRichPresenceState("AFK Defence Training");
                   if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) >= 80) {
                        player.getSkillManager().addExperience(Skill.DEFENCE, xp);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.ACCURATE) {
                    player.getPacketSender().sendRichPresenceState("AFK Attack Training");
                    if (player.getSkillManager().getMaxLevel(Skill.ATTACK) >= 80) {
                        player.getSkillManager().addExperience(Skill.ATTACK, xp);
                    }
                }
                player.getInventory().add(afkToken, 1);
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }


}

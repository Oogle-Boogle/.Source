package com.platinum.world.entity.impl.player;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.CharacterAnimations;
import com.platinum.model.Flag;
import com.platinum.util.NameUtils;
import com.platinum.world.World;

public class MiniMe {

    /*private static void makeBot(Player botOwner, Player bot) {
        bot.isBot = true;
        bot.botOwner = botOwner;
        bot.getSkillManager().setSkills(botOwner.getSkillManager().getSkills());
    }*/

    public static void create(Player player) {
        if (player.getMinime() != null) {
            player.sendMessage("@red@You already have a mini-me active.");
            return;
        }
        Player shadow = new Player(null).setUsername(player.getUsername() + "!");
        shadow.setLongUsername(NameUtils.stringToLong(shadow.getUsername()));
        //makeBot(player, shadow);
        shadow.isMiniMe = true;
        shadow.minimeOwner = player;
        shadow.getSkillManager().setSkills(player.getSkillManager().getSkills());
        player.setMinime(shadow);
        shadow.setMiniMeData(new MiniMeData(shadow));
        World.register(shadow);
        player.sendMessage("@blu@You summon your personal mini-me!");
        shadow.getMovementQueue().setFollowCharacter(player);

        CharacterAnimations originalPlayer = player.getCharacterAnimations().clone(); // Makes it possible to store animations from the owner of the bot.

        Player puppet = player.getMinime();
        TaskManager.submit(new Task(2, player.getMinime(), false) {
            @Override
            public void execute() {
                puppet.getEquipment().setItems(player.getEquipment().getItems());
                puppet.setWeapon(player.getWeapon());
                puppet.getUpdateFlag().flag(Flag.APPEARANCE);

                puppet.setCharacterAnimations(originalPlayer); //Fix shit animation

                if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked()) {
                    if (player.getCombatBuilder().isAttacking()) {
                        puppet.getMovementQueue().setFollowCharacter(null);
                        puppet.setPositionToFace(player.getCombatBuilder().getVictim().getPosition());
                        puppet.getCombatBuilder().attack(player.getCombatBuilder().getVictim());
                    }
                } else {
                    if (puppet.getCombatBuilder().isAttacking())
                        puppet.getCombatBuilder().reset(true);

                    if (puppet.getMovementQueue().getFollowCharacter() != player)
                        puppet.getMovementQueue().setFollowCharacter(player);
                }

                if (!puppet.getLocalPlayers().contains(player)) {
                    puppet.moveTo(player.getPosition());
                    puppet.getMovementQueue().setFollowCharacter(player);
                }

                if (puppet.checkPendingBotRemoval()) {
                    World.deregister(puppet);
                    player.setMinime(null);
                    puppet.getMinimeOwner().sendMessage("@blu@Your mini-me vanishes.");
                    stop();
                    return;
                }

                if (!World.getPlayers().contains(player) && World.getPlayers().contains(puppet)) {
                    World.deregister(puppet);
                    System.out.println("stopped");
                    stop();
                }
            }
        });
    }
}

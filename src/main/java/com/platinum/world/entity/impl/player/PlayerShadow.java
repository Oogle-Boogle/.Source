package com.platinum.world.entity.impl.player;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Flag;
import com.platinum.util.NameUtils;
import com.platinum.world.World;

public class PlayerShadow {

    public static void create(Player player) {
        if (player.getPuppet() != null) {
            player.sendMessage("@red@You already have a mini-me active.");
            return;
        }
        Player shadow = new Player(null).setUsername(player.getUsername() + "!");
        shadow.setLongUsername(NameUtils.stringToLong(shadow.getUsername()));
        shadow.makeBot(player, true);
        player.setPuppet(shadow);
        shadow.setMiniMeData(new MiniMeData(shadow));
        World.register(shadow);
        player.sendMessage("@blu@You summon your personal mini-me!");
        shadow.getMovementQueue().setFollowCharacter(player);

        Player puppet = player.getPuppet();
        TaskManager.submit(new Task(2, player.getPuppet(), false) {
            @Override
            public void execute() {
                player.getPuppet().getEquipment().setItems(player.getEquipment().getItems());
                player.getPuppet().getUpdateFlag().flag(Flag.APPEARANCE);
                puppet.setWeapon(player.getWeapon());

                if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked()) {
                    if (player.getCombatBuilder().isAttacking()) {
                        player.getPuppet().getMovementQueue().setFollowCharacter(null);
                        player.getPuppet().setPositionToFace(player.getCombatBuilder().getVictim().getPosition());
                        player.getPuppet().getCombatBuilder().attack(player.getCombatBuilder().getVictim());
                    }
                } else {
                    if (puppet.getCombatBuilder().isAttacking())
                        player.getPuppet().getCombatBuilder().reset(true);

                    if (puppet.getMovementQueue().getFollowCharacter() != player)
                        player.getPuppet().getMovementQueue().setFollowCharacter(player);
                }

                if (!player.getPuppet().getLocalPlayers().contains(player)) {
                    player.getPuppet().moveTo(player.getPosition());
                    player.getPuppet().getMovementQueue().setFollowCharacter(player);
                }

                if (puppet.checkPendingBotRemoval()) {
                    World.deregister(puppet);
                    player.setPuppet(null);
                    puppet.getBotOwner().sendMessage("@blu@Your mini-me vanishes.");
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

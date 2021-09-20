package com.platinum.world.content.combat.strategy.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Animation;
import com.platinum.model.Graphic;
import com.platinum.model.Locations;
import com.platinum.util.Misc;
import com.platinum.world.content.combat.CombatContainer;
import com.platinum.world.content.combat.CombatHitTask;
import com.platinum.world.content.combat.CombatType;
import com.platinum.world.content.combat.strategy.CombatStrategy;
import com.platinum.world.entity.impl.Character;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class AssassinStrategy implements CombatStrategy {



    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }


    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC assassin = (NPC)entity;
        Animation attack_anim = assassin.getAnimation();
        @SuppressWarnings("unused")
        Graphic graphic1 = assassin.getGraphic();
        if(assassin.isChargingAttack() || assassin.getConstitution() <= 0) {
            return true;
        }
        CombatType style = Misc.getRandom(4) <= 1 && Locations.goodDistance(assassin.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MELEE;
        if(style == CombatType.MELEE) {

            assassin.performAnimation(new Animation(assassin.getDefinition().getAttackAnimation()));
            assassin.getCombatBuilder().setContainer(new CombatContainer(assassin, victim, 1, 1, CombatType.MELEE, true));
        } else {
            assassin.performAnimation(attack_anim);
            assassin.setChargingAttack(true);
            Player target = (Player)victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if(t == null)
                    continue;
                if(t.getPosition().distanceToPoint(assassin.getPosition().getX(), assassin.getPosition().getY()) > 20)
                    continue;
                new CombatHitTask(assassin.getCombatBuilder(), new CombatContainer(assassin, t, 1, CombatType.MELEE, true)).handleAttack();

            }
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if(t == null)
                            continue;
                        assassin.getCombatBuilder().setVictim(t);
                        new CombatHitTask(assassin.getCombatBuilder(), new CombatContainer(assassin, t, 1, CombatType.MELEE, true)).handleAttack();
                    }
                    assassin.setChargingAttack(false);
                    stop();
                }
            });
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 7;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MELEE;
    }
}

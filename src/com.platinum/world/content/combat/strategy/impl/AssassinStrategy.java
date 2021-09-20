package com.platinum.world.content.combat.strategy.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Animation;
import com.platinum.model.Graphic;
import com.platinum.model.Locations;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.content.combat.CombatContainer;
import com.platinum.world.content.combat.CombatFactory;
import com.platinum.world.content.combat.CombatHitTask;
import com.platinum.world.content.combat.CombatType;
import com.platinum.world.content.combat.effect.CombatPoisonEffect;
import com.platinum.world.content.combat.strategy.CombatStrategies;
import com.platinum.world.content.combat.strategy.CombatStrategy;
import com.platinum.world.entity.impl.Character;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class AssassinStrategy implements CombatStrategy {

    private static void attackAll(Character entity, Character victim) {



    }


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
        Animation attackAnim = new Animation(422);
        Animation spinAnim = new Animation(2107);
        @SuppressWarnings("unused")
        Graphic graphic1 = assassin.getGraphic();

        if(assassin.getConstitution() <= 0) {
            return true;
        }

        if (Misc.random(3) == 1) {
            Player target = (Player)victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if(t == null || t.getLocation() != Locations.Location.ASSASSIN)
                    continue;
                if (Locations.goodDistance(t.getPosition(), assassin.getPosition(), 2)) {
                    assassin.getCombatBuilder().setVictim(t);
                    new CombatHitTask(assassin.getCombatBuilder(), new CombatContainer(assassin, t, 2, CombatType.MELEE, true)).handleAttack();
                    assassin.performAnimation(spinAnim);
                    CombatFactory.poisonEntity(t, CombatPoisonEffect.PoisonType.SUPER);
                }
            }
        } else {
            assassin.performAnimation(attackAnim);
            assassin.getCombatBuilder().setContainer(new CombatContainer(assassin, victim, 1, 1, CombatType.MELEE, true));
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

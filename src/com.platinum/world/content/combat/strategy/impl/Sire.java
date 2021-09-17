package com.platinum.world.content.combat.strategy.impl;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Animation;
import com.platinum.model.CombatIcon;
import com.platinum.model.Graphic;
import com.platinum.model.Hit;
import com.platinum.model.Hitmask;
import com.platinum.model.Locations;
import com.platinum.model.Position;
import com.platinum.model.Projectile;
import com.platinum.model.Skill;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.content.combat.CombatContainer;
import com.platinum.world.content.combat.CombatType;
import com.platinum.world.content.combat.strategy.CombatStrategy;
import com.platinum.world.entity.impl.Character;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class Sire implements CombatStrategy {

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
		NPC Sire = (NPC)entity;
		if(Sire.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if(Locations.goodDistance(Sire.getPosition().copy(), victim.getPosition().copy(), 5)&& Misc.getRandom(10) > 8 ) {
			Sire.getCombatBuilder().setContainer(new CombatContainer(Sire, victim, 1, 1, CombatType.MAGIC, true));
		} else {
			final int x = Sire.getPosition().getX(), y = Sire.getPosition().getY();
			TaskManager.submit(new Task(4) {
				@Override
				protected void execute() {
					for(Player p : World.getPlayers())
					{
						if(p != null)
						{

						if(p.getPosition().distanceToPoint(x, y) <= 15)
						{
							p.performGraphic(new Graphic(1176));
							p.dealDamage(new Hit(Misc.random(200,750), Hitmask.CRITICAL, CombatIcon.MAGIC));
						}
						}
					}
					this.stop();
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
		return 3;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}

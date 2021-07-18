package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Position;
import com.arlania.model.Projectile;
import com.arlania.util.Misc;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class ZamorakLefosh implements CombatStrategy {

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
		NPC Lefosh = (NPC)entity;
		if(Lefosh.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if (Misc.getRandom(6) == 1) {
			Lefosh.forceChat("Wisdom & Nike #1");
		}
		if (Misc.getRandom(6) == 1) {
			Lefosh.forceChat("K time to k0 u");
		}
		if (Misc.getRandom(6) == 1) {
			Lefosh.forceChat("ur going to die!");
		}
		if (Misc.getRandom(6) == 1) {
			Lefosh.forceChat("omg im about to die soon pls help");
		}
		if(Locations.goodDistance(Lefosh.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 1) {
			Lefosh.performAnimation(new Animation(Lefosh.getDefinition().getAttackAnimation()));
			Lefosh.getCombatBuilder().setContainer(new CombatContainer(Lefosh, victim, 1, 1, CombatType.MELEE, true));
		} else if(!Locations.goodDistance(Lefosh.getPosition().copy(), victim.getPosition().copy(), 3) && Misc.getRandom(2) == 1) {
			Lefosh.setChargingAttack(true);
			final Position pos = new Position(victim.getPosition().getX() - 2, victim.getPosition().getY());
			((Player)victim).getPacketSender().sendGlobalGraphic(new Graphic(5327), pos);
			Lefosh.performAnimation(new Animation(5328));
			Lefosh.forceChat("You dare run from me? bout 2 cuck ya");
			TaskManager.submit(new Task(2) {
				@Override
				protected void execute() {
					Lefosh.moveTo(pos);
					Lefosh.performAnimation(new Animation(Lefosh.getDefinition().getAttackAnimation()));
					Lefosh.getCombatBuilder().setContainer(new CombatContainer(Lefosh, victim, 1, 1, CombatType.MELEE, false));
					Lefosh.setChargingAttack(false);
					Lefosh.getCombatBuilder().setAttackTimer(0);
					stop();
				}
			});
		} else {
			Lefosh.setChargingAttack(true);
			boolean barrage = Misc.getRandom(4) <= 2;
			Lefosh.performAnimation(new Animation(barrage ? 5327 : 5327));
			Lefosh.getCombatBuilder().setContainer(new CombatContainer(Lefosh, victim, 1, 1, CombatType.MAGIC, true));
			TaskManager.submit(new Task(1, Lefosh, false) {
				int tick = 0;
				@Override
				public void execute() {
					if(tick == 0 && !barrage) {
						new Projectile(Lefosh, victim, 2718, 44, 3, 43, 43, 0).sendProjectile();
					} else if(tick == 1) {
						if(barrage && victim.isPlayer() && Misc.getRandom(10) <= 5) {
							victim.getMovementQueue().freeze(15);
							victim.performGraphic(new Graphic(369));
						}
						if(barrage && Misc.getRandom(6) <= 3) {
							Lefosh.performAnimation(new Animation(5327));
							for(Player toAttack : Misc.getCombinedPlayerList((Player)victim)) {
								if(toAttack != null && Locations.goodDistance(Lefosh.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
									Lefosh.forceChat("U srs gonna kill a cute little spideR? ye defo reported to Wisdom lmao.....");
									new CombatHitTask(Lefosh.getCombatBuilder(), new CombatContainer(Lefosh, toAttack, 2, CombatType.MAGIC, false)).handleAttack();
									toAttack.performGraphic(new Graphic(1557));
									
								}
							}
						}
						Lefosh.setChargingAttack(false).getCombatBuilder().setAttackTimer(attackDelay(Lefosh) - 2);
						stop();
					}
					tick++;
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
		return 5;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}

	public static Object initialize() {
		// TODO Auto-generated method stub
		return null;
	}
}

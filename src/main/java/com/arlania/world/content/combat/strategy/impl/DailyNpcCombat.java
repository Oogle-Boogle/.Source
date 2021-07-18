package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Position;
import com.arlania.model.Projectile;
import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class DailyNpcCombat implements CombatStrategy {

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
		NPC DailyNpc = (NPC)entity;
		if(DailyNpc.isChargingAttack() || victim.getConstitution() <= 0) {
			return true;
		}
		if (Misc.getRandom(6) == 1) {
			DailyNpc.forceChat("I am the strongest NPC ingame!");
		}
		if (Misc.getRandom(6) == 1) {
			DailyNpc.forceChat("Fear me");
		}
		if(Locations.goodDistance(DailyNpc.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 1) {
			DailyNpc.performAnimation(new Animation(DailyNpc.getDefinition().getAttackAnimation()));
			DailyNpc.getCombatBuilder().setContainer(new CombatContainer(DailyNpc, victim, 1, 1, CombatType.MELEE, true));
		} else if(!Locations.goodDistance(DailyNpc.getPosition().copy(), victim.getPosition().copy(), 3) && Misc.getRandom(2) == 1) {
			DailyNpc.setChargingAttack(true);
			final Position pos = new Position(victim.getPosition().getX() - 2, victim.getPosition().getY());
			//((Player)victim).getPacketSender().sendGlobalGraphic(new Graphic(5327), pos);
			//DailyNpc.performAnimation(new Animation(5328));
			DailyNpc.forceChat("Don't run from me!");
			TaskManager.submit(new Task(2) {
				@Override
				protected void execute() {
					DailyNpc.moveTo(pos);
					DailyNpc.performAnimation(new Animation(DailyNpc.getDefinition().getAttackAnimation()));
					DailyNpc.getCombatBuilder().setContainer(new CombatContainer(DailyNpc, victim, 1, 1, CombatType.MELEE, false));
					DailyNpc.setChargingAttack(false);
					DailyNpc.getCombatBuilder().setAttackTimer(0);
					stop();
				}
			});
		} else {
			DailyNpc.setChargingAttack(true);
			boolean barrage = Misc.getRandom(4) <= 2;
			//DailyNpc.performAnimation(new Animation(barrage ? 5327 : 5327));
			DailyNpc.getCombatBuilder().setContainer(new CombatContainer(DailyNpc, victim, 1, 1, CombatType.MAGIC, true));
			TaskManager.submit(new Task(1, DailyNpc, false) {
				int tick = 0;
				@Override
				public void execute() {
					if(tick == 0 && !barrage) {
						new Projectile(DailyNpc, victim, 2718, 44, 3, 43, 43, 0).sendProjectile();
					} else if(tick == 1) {
						if(barrage && victim.isPlayer() && Misc.getRandom(10) <= 5) {
							victim.getMovementQueue().freeze(15);
							victim.performGraphic(new Graphic(369));
						}
						if(barrage && Misc.getRandom(6) <= 3) {
							DailyNpc.forceChat("Sec, ima barrage u real quick fella");
							//DailyNpc.performAnimation(new Animation(5327));
							for(Player toAttack : Misc.getCombinedPlayerList((Player)victim)) {
								if(toAttack != null && Locations.goodDistance(DailyNpc.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
									DailyNpc.forceChat("Are you guys actually attempting to kill me?...");
									new CombatHitTask(DailyNpc.getCombatBuilder(), new CombatContainer(DailyNpc, toAttack, 4, CombatType.MAGIC, false)).handleAttack();
									DailyNpc.forceChat("One of you will die in the next 2 secs!");
									toAttack.performGraphic(new Graphic(1557));
									int skill = (5);
									Skill skillT = Skill.forId(skill);
									Player player = (Player) victim;
									int lvl = player.getSkillManager().getCurrentLevel(skillT);
									lvl -= 96 + Misc.getRandom(3);
									victim.performGraphic(new Graphic(287));
									player.getSkillManager().setCurrentLevel(skillT, player.getSkillManager().getCurrentLevel(skillT) - lvl <= 0 ?  1 : lvl);
									((Player)victim).getPacketSender().sendMessage("@red@Your Prayer has been drained by alot!");
									
								}
							}
						}
						DailyNpc.setChargingAttack(false).getCombatBuilder().setAttackTimer(attackDelay(DailyNpc) - 2);
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

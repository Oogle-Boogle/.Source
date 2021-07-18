package com.arlania.world.content.combat.strategy.impl.dks;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.CombatIcon;
import com.arlania.model.Graphic;
import com.arlania.model.Hit;
import com.arlania.model.Hitmask;
import com.arlania.model.Locations;
import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Thecustomwrencher implements CombatStrategy {
	
	/** @author Lord Lewis **/

	private static final Animation attackanim = new Animation(64);
	private static final Graphic HeavyAttackGFX = new Graphic(287);

	@Override
	public boolean canAttack(Character entity, Character victim) {
		return victim.isPlayer();
	}

	@Override
	public CombatContainer attack(Character entity, Character victim) {
		return null;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC Customwrencher = (NPC)entity;
		if(Customwrencher.isChargingAttack() || Customwrencher.getConstitution() <= 0) {
			return true;
		}
		
		if(Customwrencher.getConstitution() <= 60000 && !Customwrencher.hasHealed()) {
			victim.performGraphic(HeavyAttackGFX);
			Customwrencher.forceChat("Armadyl Banish Thee!");
			victim.dealDamage(new Hit(980, Hitmask.RED, CombatIcon.NONE));
			Customwrencher.setHealed(true);
		}
		
		Player target = (Player)victim;
		boolean crucio = false;
		for (Player t : Misc.getCombinedPlayerList(target)) {
			
			if (Locations.goodDistance(t.getPosition(), Customwrencher.getPosition(), 1)) {
				crucio = true;
				Customwrencher.getCombatBuilder().setVictim(t);
				new CombatHitTask(Customwrencher.getCombatBuilder(), new CombatContainer(Customwrencher, t, 1, CombatType.MAGIC, true)).handleAttack();
			}
		}
		if (crucio) {
			Customwrencher.performAnimation(attackanim);
			//Customwrencher.performGraphic(attack_graphic);
		}

		int attackStyle = Misc.getRandom(3);
	if (attackStyle == 0) { // Hand slash
			Customwrencher.performAnimation(attackanim);
			Customwrencher.getCombatBuilder().setContainer(new CombatContainer(Customwrencher, target, 1, 2, CombatType.MELEE, true));
	} else if (attackStyle == 1) { // Hand slash
				Customwrencher.performAnimation(attackanim);
				Customwrencher.getCombatBuilder().setContainer(new CombatContainer(Customwrencher, target, 1, 2, CombatType.MELEE, true));

		} else if (attackStyle == 2) { // Single Poison Blast
			
			Customwrencher.performAnimation(attackanim);
			Customwrencher.getCombatBuilder().setContainer(new CombatContainer(Customwrencher, target, 1, 2, CombatType.MELEE, true));
		} else if (attackStyle == 3) { // Skill Drain Attack
			Customwrencher.performAnimation(attackanim);
				Customwrencher.getCombatBuilder().setContainer(new CombatContainer(Customwrencher, target, 2, 2, CombatType.MELEE, true));
			TaskManager.submit(new Task(1, target, false) {
				@Override
				public void execute() {
					int skill = (6);
					Skill skillT = Skill.forId(skill);
					Player player = (Player) target;
					int lvl = player.getSkillManager().getCurrentLevel(skillT);
					lvl -= 4 + Misc.getRandom(3);
					 target.performGraphic(new Graphic(287));
					player.getSkillManager().setCurrentLevel(skillT, player.getSkillManager().getCurrentLevel(skillT) - lvl <= 0 ?  1 : lvl);
					target.getPacketSender().sendMessage("@red@Customwrencher has drained your Magic!");
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
		return 20;
	}
	
	

	@Override
	public CombatType getCombatType() {
		return CombatType.MAGIC;
	}
}
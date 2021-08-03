package com.platinum.world.content.aoesystem;

import java.util.Iterator;

import com.platinum.model.CombatIcon;
import com.platinum.model.Hit;
import com.platinum.model.Hitmask;
import com.platinum.model.Locations;
import com.platinum.util.RandomUtility;
import com.platinum.world.entity.impl.Character;
import com.platinum.world.entity.impl.npc.NPC;
import com.platinum.world.entity.impl.player.Player;

public class AOEHandler {

	public static void handleAttack(Character attacker, Character victim, int minimumDamage, int maximumDamage,
			int radius, CombatIcon combatIcon) {

		// if no radius, loc isn't multi, stops.
		if (radius == 0 || !Locations.Location.inMulti(attacker) || Locations.Location.inDrStrange(victim)
				|| Locations.Location.inTotodile(victim)) {
			if(!Locations.Location.inMulti(attacker)) {
				int calc2 = RandomUtility.inclusiveRandom(minimumDamage, maximumDamage);
				victim.dealDamage(new Hit(calc2, Hitmask.RED, combatIcon));
				victim.getCombatBuilder().attack(attacker);
				victim.getCombatBuilder().addDamage(attacker, calc2);
			}
			return;
		}

		// We passed the checks, so now we do multiple target stuff.
		Iterator<? extends Character> it = null;
		if (attacker.isPlayer() && victim.isPlayer()) {
			it = ((Player) attacker).getLocalPlayers().iterator();
		} else if (attacker.isPlayer() && victim.isNpc()) {
			it = ((Player) attacker).getLocalNpcs().iterator();

			int calc2 = RandomUtility.inclusiveRandom(minimumDamage, maximumDamage);
			victim.dealDamage(new Hit(calc2, Hitmask.RED, combatIcon));
			victim.getCombatBuilder().attack(attacker);
			victim.getCombatBuilder().addDamage(attacker, calc2);
			for (Iterator<? extends Character> $it = it; $it.hasNext();) {
				Character next = $it.next();

				if (next == null) {
					continue;
				}

				if (next.isNpc()) {
					NPC n = (NPC) next;
					if (!n.getDefinition().isAttackable() || n.isSummoningNpc()) {
						continue;
					}
				} else {
					Player p = (Player) next;
					if (p.getLocation() != Locations.Location.WILDERNESS || !Locations.Location.inMulti(p)) {
						continue;
					}
				}

				if (next.getPosition().isWithinDistance(victim.getPosition(), radius) && !next.equals(attacker)
						&& !next.equals(victim) && next.getConstitution() > 0 && next.getConstitution() > 0) {
					int calc = RandomUtility.inclusiveRandom(minimumDamage, maximumDamage);
					next.dealDamage(new Hit(calc, Hitmask.RED, combatIcon));
					next.getCombatBuilder().attack(attacker);
					next.getCombatBuilder().addDamage(attacker, calc);

					}
				}
			}
		}

	}


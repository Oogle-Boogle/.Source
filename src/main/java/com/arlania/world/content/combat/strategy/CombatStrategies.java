package com.arlania.world.content.combat.strategy;

import java.util.HashMap;
import java.util.Map;

import com.arlania.world.content.combat.strategy.impl.Abbadon;
import com.arlania.world.content.combat.strategy.impl.ArmadylAbyzou;
import com.arlania.world.content.combat.strategy.impl.AvatarOfCreation;
import com.arlania.world.content.combat.strategy.impl.BalfrugKreeyath;
import com.arlania.world.content.combat.strategy.impl.BallakPummeler;
import com.arlania.world.content.combat.strategy.impl.BandosAvatar;
import com.arlania.world.content.combat.strategy.impl.BorkStrategy;
import com.arlania.world.content.combat.strategy.impl.Cerberus;
import com.arlania.world.content.combat.strategy.impl.CorporealBeast;
import com.arlania.world.content.combat.strategy.impl.CustomVoragoCombat;
import com.arlania.world.content.combat.strategy.impl.Cyrisus;
import com.arlania.world.content.combat.strategy.impl.DailyNpcCombat;
import com.arlania.world.content.combat.strategy.impl.DefaultMagicCombatStrategy;
import com.arlania.world.content.combat.strategy.impl.DefaultMeleeCombatStrategy;
import com.arlania.world.content.combat.strategy.impl.DefaultRangedCombatStrategy;
import com.arlania.world.content.combat.strategy.impl.DireWolf;
import com.arlania.world.content.combat.strategy.impl.Dragon;
import com.arlania.world.content.combat.strategy.impl.Dragonix;
import com.arlania.world.content.combat.strategy.impl.Geerin;
import com.arlania.world.content.combat.strategy.impl.Glacor;
import com.arlania.world.content.combat.strategy.impl.HarLakkRiftsplitter;
import com.arlania.world.content.combat.strategy.impl.Harambe;
import com.arlania.world.content.combat.strategy.impl.Icedemon;
import com.arlania.world.content.combat.strategy.impl.InfernalWizardCombat;
import com.arlania.world.content.combat.strategy.impl.Jad;
import com.arlania.world.content.combat.strategy.impl.KCMinions;
import com.arlania.world.content.combat.strategy.impl.KalphiteQueen;
import com.arlania.world.content.combat.strategy.impl.Kreeyath;
import com.arlania.world.content.combat.strategy.impl.Lexicus;
import com.arlania.world.content.combat.strategy.impl.LizardMan;
import com.arlania.world.content.combat.strategy.impl.MiniDire;
import com.arlania.world.content.combat.strategy.impl.Nazastarool;
import com.arlania.world.content.combat.strategy.impl.Necrolord;
import com.arlania.world.content.combat.strategy.impl.Nex;
import com.arlania.world.content.combat.strategy.impl.Nomad;
import com.arlania.world.content.combat.strategy.impl.OlmMinionsCombat;
import com.arlania.world.content.combat.strategy.impl.PlaneFreezer;
import com.arlania.world.content.combat.strategy.impl.RainbowTextureNPC;
import com.arlania.world.content.combat.strategy.impl.RemadeZulrah;
import com.arlania.world.content.combat.strategy.impl.Revenant;
import com.arlania.world.content.combat.strategy.impl.Sagittare;
import com.arlania.world.content.combat.strategy.impl.Sire;
import com.arlania.world.content.combat.strategy.impl.SkeletalHorror;
import com.arlania.world.content.combat.strategy.impl.Skotizo;
import com.arlania.world.content.combat.strategy.impl.Spinolyp;
import com.arlania.world.content.combat.strategy.impl.SuicsBoss;
import com.arlania.world.content.combat.strategy.impl.TheGeneral;
import com.arlania.world.content.combat.strategy.impl.Thermonuclear;
import com.arlania.world.content.combat.strategy.impl.ToKashBloodchiller;
import com.arlania.world.content.combat.strategy.impl.TormentedDemon;
import com.arlania.world.content.combat.strategy.impl.UnholyCursebearer;
import com.arlania.world.content.combat.strategy.impl.Warmonger;
import com.arlania.world.content.combat.strategy.impl.WizardOfTridentCombat;
import com.arlania.world.content.combat.strategy.impl.ZamorakIktomi;
import com.arlania.world.content.combat.strategy.impl.ZamorakLefosh;
import com.arlania.world.content.combat.strategy.impl.dks.DagannothSupreme;
import com.arlania.world.content.combat.strategy.impl.godwars.Aviansie;
import com.arlania.world.content.combat.strategy.impl.godwars.Graardor;
import com.arlania.world.content.combat.strategy.impl.godwars.Grimspike;
import com.arlania.world.content.combat.strategy.impl.godwars.Gritch;
import com.arlania.world.content.combat.strategy.impl.godwars.Growler;
import com.arlania.world.content.combat.strategy.impl.godwars.KreeArra;
import com.arlania.world.content.combat.strategy.impl.godwars.Steelwill;
import com.arlania.world.content.combat.strategy.impl.godwars.Tsutsuroth;
import com.arlania.world.content.combat.strategy.impl.godwars.WingmanSkree;
import com.arlania.world.content.combat.strategy.impl.godwars.Zilyana;
import com.arlania.world.content.combat.strategy.impl.raid.BalanceElemental;
import com.arlania.world.content.combat.strategy.wilderness.Callisto;
import com.arlania.world.content.combat.strategy.wilderness.ChaosElemental;
import com.arlania.world.content.combat.strategy.wilderness.Venenatis;
import com.arlania.world.content.combat.strategy.wilderness.Vetion;
import com.arlania.world.content.raids.RaidNpc;
import com.arlania.world.entity.impl.npc.NPC;



public class CombatStrategies {

	private static final DefaultMeleeCombatStrategy defaultMeleeCombatStrategy = new DefaultMeleeCombatStrategy();
	private static final DefaultMagicCombatStrategy defaultMagicCombatStrategy = new DefaultMagicCombatStrategy();
	private static final DefaultRangedCombatStrategy defaultRangedCombatStrategy = new DefaultRangedCombatStrategy();
	private static final Map<Integer, CombatStrategy> STRATEGIES = new HashMap<Integer, CombatStrategy>();
	
	public static void init() {
		DefaultMagicCombatStrategy defaultMagicStrategy = new DefaultMagicCombatStrategy();
		STRATEGIES.put(13, defaultMagicStrategy);
		STRATEGIES.put(172, defaultMagicStrategy);
		STRATEGIES.put(174, defaultMagicStrategy);
		STRATEGIES.put(2025, defaultMagicStrategy);
		STRATEGIES.put(3495, defaultMagicStrategy);
		STRATEGIES.put(3496, defaultMagicStrategy);
		STRATEGIES.put(3491, defaultMagicStrategy);
		STRATEGIES.put(2882, defaultMagicStrategy);
		STRATEGIES.put(13451, defaultMagicStrategy);
		STRATEGIES.put(13452, defaultMagicStrategy);
		STRATEGIES.put(13453, defaultMagicStrategy);
		STRATEGIES.put(13454, defaultMagicStrategy);
		STRATEGIES.put(1643, defaultMagicStrategy);
		STRATEGIES.put(6254, defaultMagicStrategy);
		STRATEGIES.put(6257, defaultMagicStrategy);
		STRATEGIES.put(6278, defaultMagicStrategy);
		STRATEGIES.put(6221, defaultMagicStrategy);
		STRATEGIES.putIfAbsent(1, defaultMagicStrategy);
	;
		
		DefaultRangedCombatStrategy defaultRangedStrategy = new DefaultRangedCombatStrategy();
		STRATEGIES.put(688, defaultRangedStrategy);
		STRATEGIES.put(2028, defaultRangedStrategy);
		STRATEGIES.put(6220, defaultRangedStrategy);
		STRATEGIES.put(6256, defaultRangedStrategy);
		STRATEGIES.put(6276, defaultRangedStrategy);
		STRATEGIES.put(6252, defaultRangedStrategy);
		STRATEGIES.put(27, defaultRangedStrategy);
		STRATEGIES.put(1684, defaultRangedStrategy);
		
		STRATEGIES.put(2745, new Jad());
		STRATEGIES.put(8528, new Nomad());
		STRATEGIES.put(8349, new TormentedDemon());
		STRATEGIES.put(9994, new TormentedDemon());
		STRATEGIES.put(3200, new ChaosElemental());
		STRATEGIES.put(4540, new BandosAvatar());
		STRATEGIES.put(8133, new CorporealBeast());
		STRATEGIES.put(2896, new Spinolyp());
		STRATEGIES.put(2881, new DagannothSupreme());
		STRATEGIES.put(6260, new Graardor());
		STRATEGIES.put(13447, new Nex());
		STRATEGIES.put(6263, new Steelwill());
		STRATEGIES.put(6265, new Grimspike());
		STRATEGIES.put(6222, new KreeArra());
		STRATEGIES.put(6223, new WingmanSkree());
		STRATEGIES.put(6225, new Geerin());
		STRATEGIES.put(6203, new Tsutsuroth());
		STRATEGIES.put(6208, new Kreeyath());
		STRATEGIES.put(6206, new Gritch());
		STRATEGIES.put(6247, new Zilyana());
		STRATEGIES.put(6250, new Growler());
		STRATEGIES.put(1382, new Glacor());
		STRATEGIES.put(9939, new PlaneFreezer());
		 STRATEGIES.put(2042, new RemadeZulrah());
			STRATEGIES.put(2043, new RemadeZulrah());
			STRATEGIES.put(2044, new RemadeZulrah());
		
		Dragon dragonStrategy = new Dragon();
		STRATEGIES.put(50, dragonStrategy);
		STRATEGIES.put(941, dragonStrategy);
		STRATEGIES.put(55, dragonStrategy);
		STRATEGIES.put(53, dragonStrategy);
		STRATEGIES.put(54, dragonStrategy);
		STRATEGIES.put(51, dragonStrategy);
		STRATEGIES.put(1590, dragonStrategy);
		STRATEGIES.put(1591, dragonStrategy);
		STRATEGIES.put(1592, dragonStrategy);
		STRATEGIES.put(5362, dragonStrategy);
		STRATEGIES.put(5363, dragonStrategy);
		STRATEGIES.put(1982, dragonStrategy);
		
		Aviansie aviansieStrategy = new Aviansie();
		STRATEGIES.put(6246, aviansieStrategy);
		STRATEGIES.put(6230, aviansieStrategy);
		STRATEGIES.put(6231, aviansieStrategy);
		
		KalphiteQueen kalphiteQueenStrategy = new KalphiteQueen();
		STRATEGIES.put(1158, kalphiteQueenStrategy);
		STRATEGIES.put(1160, kalphiteQueenStrategy);
		
		Revenant revenantStrategy = new Revenant();
		STRATEGIES.put(6715, revenantStrategy);
		STRATEGIES.put(6716, revenantStrategy);
		STRATEGIES.put(6701, revenantStrategy);
		STRATEGIES.put(6725, revenantStrategy);
		STRATEGIES.put(6691, revenantStrategy);
		
		STRATEGIES.put(2000, new Venenatis());
		STRATEGIES.put(2006, new Vetion());
		STRATEGIES.put(2010, new Callisto());
		STRATEGIES.put(1999, new Cerberus());
		STRATEGIES.put(6766, new LizardMan());
		STRATEGIES.put(8281, new BalanceElemental());
		STRATEGIES.put(499, new Thermonuclear());
		STRATEGIES.put(7286, new Skotizo());
		STRATEGIES.put(5886, new Sire());
		/*
		 * new npc's. added by ME
		 */
		STRATEGIES.put(8597, new AvatarOfCreation());
		STRATEGIES.put(10126, new UnholyCursebearer());
		STRATEGIES.put(10051, new Icedemon()); //now it does , b
		STRATEGIES.put(9176, new SkeletalHorror()); // this one nee
		STRATEGIES.put(6208, new BalfrugKreeyath());
		STRATEGIES.put(9911, new HarLakkRiftsplitter());
		STRATEGIES.put(11751, new Necrolord());
		STRATEGIES.put(9766, new Sagittare());
		STRATEGIES.put(9855, new Lexicus());

		/**
		 * Converted Bosses.
		 */
		
		STRATEGIES.put(4413, new DireWolf());
        STRATEGIES.put(6305, new Dragonix());
        STRATEGIES.put(10141, new BallakPummeler());
        STRATEGIES.put(10039, new ToKashBloodchiller());
        STRATEGIES.put(6307, new ZamorakIktomi());
        STRATEGIES.put(839, new MiniDire());
        STRATEGIES.put(509, new Nazastarool());
        STRATEGIES.put(433, new Cyrisus());
        STRATEGIES.put(6307, new ZamorakIktomi());
        
		/**
		 * End of converted bosses.
		 */
        
        
		STRATEGIES.put(7553, new TheGeneral());
		STRATEGIES.put(12841, new Warmonger());
		STRATEGIES.put(6313, new ArmadylAbyzou());
		STRATEGIES.put(6314, new ArmadylAbyzou());
		STRATEGIES.put(6309, new ZamorakLefosh());
		STRATEGIES.put(7134, new BorkStrategy());
		STRATEGIES.put(6303, new Abbadon());
		STRATEGIES.put(9903, new Harambe());
		STRATEGIES.put(9913, new DailyNpcCombat());
		STRATEGIES.put(8507, new CustomVoragoCombat());
		STRATEGIES.put(9273, new WizardOfTridentCombat());
		STRATEGIES.put(9277, new RainbowTextureNPC());
		STRATEGIES.put(421, new RainbowTextureNPC());
		STRATEGIES.put(8548, new RainbowTextureNPC());
		STRATEGIES.put(2005, new Glacor());
		STRATEGIES.put(9280, new KCMinions());
		STRATEGIES.put(9647, new InfernalWizardCombat());
		STRATEGIES.put(8675, new OlmMinionsCombat());
		STRATEGIES.put(6593, new SuicsBoss());
		
		
		
	}
	
	public static CombatStrategy getStrategy(NPC npc) {
		if(npc instanceof RaidNpc) {
			return defaultMagicCombatStrategy;
		}
		if(STRATEGIES.get(npc.getId()) != null) {
			return STRATEGIES.get(npc.getId());
		}
		return defaultMeleeCombatStrategy;
	}
	
	public static CombatStrategy getDefaultMeleeStrategy() {
		return defaultMeleeCombatStrategy;
	}

	public static CombatStrategy getDefaultMagicStrategy() {
		return defaultMagicCombatStrategy;
	}


	public static CombatStrategy getDefaultRangedStrategy() {
		return defaultRangedCombatStrategy;
	}
}

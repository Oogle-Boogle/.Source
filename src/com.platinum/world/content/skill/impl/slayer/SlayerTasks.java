package com.platinum.world.content.skill.impl.slayer;

import com.platinum.model.Position;
import com.platinum.util.Misc;
import com.platinum.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason 
 */

public enum SlayerTasks {

	NO_TASK(null, -1, null, -1, null),

	/**
	 * Easy tasks
	 */
	BUGS_BUNNY(SlayerMaster.VANNAKA, 4455, "Bugs Bunny can be found in the Starter Zone", 400, new Position(3795, 3543, 0)),
	DAFFY_DUCK(SlayerMaster.VANNAKA, 4457, "Daffy Duck can be found in the Starter Zone", 400, new Position(3780, 3551, 0)),
	SYLVESTER(SlayerMaster.VANNAKA, 4459, "Sylvester can be found in the Starter Zone", 400, new Position(3754, 3551)),
	COYOTE(SlayerMaster.VANNAKA, 4456, "Coyote can be found in the Starter Zone ", 400, new Position(3745, 3564, 0)),
	YOSEMITE_SAM(SlayerMaster.VANNAKA, 4462, "Yosemite Sam can be found in the Starter Zone!", 400, new Position(3760, 3572, 0)),
	MARTIAN(SlayerMaster.DURADEL, 4409, "Martian can be found in the Starter Zone!", 400, new Position(3785, 3572, 0)),
	
	/**
	 * Medium tasks
	 */
	HERCULES(SlayerMaster.DURADEL, 17, "Hercules can be found in the Starter Teleports!", 600, new Position(2783, 4636, 0)),
	LUCARIO(SlayerMaster.DURADEL, 3263, "Lucario can be found in the Starer Teleports", 600, new Position(2891, 4767)),
	DEFENDERS(SlayerMaster.DURADEL, 9994, "Defenders can be found in the Medium Teleports", 600, new Position(2724, 9821)),
	GODZILLA(SlayerMaster.DURADEL, 9932, "Godzilla can be found in the Medium Teleports", 600, new Position(2452, 10147)),
	CERBERUS(SlayerMaster.DURADEL, 1999, "Cerberus can be found in the Medium Teleports", 600, new Position(1240, 1247)),

	/**
	 * Hard tasks
	 */
	CRAWLING_HANDS1(SlayerMaster.SUMONA, 1652, "Find crawling hands in slayer tower", 750, new Position(2602, 5713)),
	BLOODVIEL1(SlayerMaster.SUMONA, 1618, "Find bloodveld in slayer tower", 750, new Position(2273, 4680, 1)),
	INFERNAL_MAGE1(SlayerMaster.SUMONA, 1643, "Find infernal mages in slayer tower", 750, new Position(1908, 4367)),

	/**
	 * Elite
	 */
	CRAWLING_HANDS(SlayerMaster.SUMONA, 1652, "Find crawling hands in slayer tower", 1500, new Position(2602, 5713)),
	BLOODVIEL(SlayerMaster.SUMONA, 1618, "Find bloodviel in slayer tower", 1500, new Position(2273, 4680, 1)),
	INFERNAL_MAGE(SlayerMaster.SUMONA, 1643, "Find infernal mages in slayer tower", 1500, new Position(1908, 4367)),
	
	/**
	 * Extreme - Bravek
	 */

	ABBERANT_SPECTRE(SlayerMaster.BRAVEK, 1604, "Find aberrant spectres in slayer tower", 2500, new Position(3247, 3033)),
	GARGOYLE(SlayerMaster.BRAVEK, 1610, "Find gargoyles in slayer tower", 2500, new Position(2463, 3372)),
	NECHRYAEL(SlayerMaster.BRAVEK, 1613, "Find nechryaels in slayer tower", 2500, new Position(3178, 3032)),
	ABYSSAL_DEMON(SlayerMaster.BRAVEK, 1615, "Find abyssal demons in slayer tower", 2500, new Position(3593, 3493));



	private SlayerTasks(SlayerMaster taskMaster, int npcId, String npcLocation, int XP, Position taskPosition) {
		this.taskMaster = taskMaster;
		this.npcId = npcId;
		this.npcLocation = npcLocation;
		this.XP = XP;
		this.taskPosition = taskPosition;
	}

	private SlayerMaster taskMaster;
	private int npcId;
	private String npcLocation;
	private int XP;
	private Position taskPosition;

	public SlayerMaster getTaskMaster() {
		return this.taskMaster;
	}

	public int getNpcId() {
		return this.npcId;
	}

	public String getNpcLocation() {
		return this.npcLocation;
	}

	public int getXP() {
		return this.XP;
	}

	public Position getTaskPosition() {
		return this.taskPosition;
	}

	public static SlayerTasks forId(int id) {
		for (SlayerTasks tasks : SlayerTasks.values()) {
			if (tasks.ordinal() == id) {
				return tasks;
			}
		}
		return null;
	}

	public static int[] getNewTaskData(SlayerMaster master, Player player) {
		int slayerTaskId = 1, slayerTaskAmount = 20;
		int easyTasks = 0, mediumTasks = 0, hardTasks = 0, eliteTasks = 0, extremeTasks = 0;

		/*
		 * Calculating amount of tasks
		 */
		for (SlayerTasks task : SlayerTasks.values()) {
			if (task.getTaskMaster() == SlayerMaster.VANNAKA)
				easyTasks++;
			else if (task.getTaskMaster() == SlayerMaster.DURADEL)
				mediumTasks++;
			else if (task.getTaskMaster() == SlayerMaster.KURADEL)
				hardTasks++;
			else if (task.getTaskMaster() == SlayerMaster.SUMONA)
				eliteTasks++;
			else if (task.getTaskMaster() == SlayerMaster.BRAVEK)
				extremeTasks++;
		}

		if (master == SlayerMaster.VANNAKA) {
			slayerTaskId = 1 + Misc.getRandom(easyTasks);
			if (slayerTaskId > easyTasks)
				slayerTaskId = easyTasks;
			slayerTaskAmount = 10 + Misc.getRandom(5);
		} else if (master == SlayerMaster.DURADEL) {
			slayerTaskId = easyTasks - 1 + Misc.getRandom(mediumTasks);
			slayerTaskAmount = 15 + Misc.getRandom(5);
		} else if (master == SlayerMaster.KURADEL) {
			slayerTaskId = 1 + easyTasks + mediumTasks + Misc.getRandom(hardTasks - 1);
			slayerTaskAmount = 20 + Misc.getRandom(5);
		} else if (master == SlayerMaster.SUMONA) {
			slayerTaskId = 1 + easyTasks + mediumTasks + hardTasks + Misc.getRandom(eliteTasks - 1);
			slayerTaskAmount = 25 + Misc.getRandom(5);
		} else if (master == SlayerMaster.BRAVEK) {
			slayerTaskId = 1 + easyTasks + mediumTasks + hardTasks + eliteTasks + Misc.getRandom(extremeTasks - 1);
			if (player.getBravekDifficulty() == null) {
				slayerTaskAmount = 30 + Misc.getRandom(5);
			} else {
				switch (player.getBravekDifficulty()) {
				case "easy":
					slayerTaskAmount = 15 + Misc.getRandom(5);
					break;
				case "medium":
					slayerTaskAmount = 20 + Misc.getRandom(10);
					break;
				case "hard":
					slayerTaskAmount = 25 + Misc.getRandom(15);
					break;
				}
			}
		}
		return new int[] { slayerTaskId, slayerTaskAmount };
	}
	
	@Override
	public String toString() {
		return Misc.ucFirst(name().toLowerCase().replaceAll("_", " "));
	}
}

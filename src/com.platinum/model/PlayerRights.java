package com.platinum.model;

import com.platinum.util.Misc;
import com.platinum.world.entity.impl.player.Player;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


/**
 * Represents a player's privilege rights.
 * @author Gabriel Hannason
 */

public enum PlayerRights {

	/*
	 * A regular member of the server.
	 */
	PLAYER(-1, null, 1, 1), // 0
	/*
	 * A moderator who has more privilege than other regular members and donators.
	 */
	MODERATOR(-1, "><col=6600CC>", 1, 1.5), //1

	/*
	 * The second-highest-privileged member of the server.
	 */
	ADMINISTRATOR(-1, "<col=FFFF64>", 1, 1.5), //2

	/*
	 * The highest-privileged member of the server
	 */
	OWNER(-1, "<col=B40404>", 1, 1.5), //3

	/*
	 * The Developer of the server, has same rights as the owner.
	 */
	DEVELOPER(-1, "<col=fa0505>", 1, 1.5), //4


	/*
	 * A member who has donated to the server. 
	 */
	DONATOR(60, "<shad=FF7F00>", 1.5, 1.1), //5
	SUPER_DONATOR(40, "<col=787878>", 1.5, 1.2), //6
 	EXTREME_DONATOR(20, "<col=D9D919>", 2, 1.3), //7
	LEGENDARY_DONATOR(10, "<shad=697998>", 2.5, 1.4), //8
	UBER_DONATOR(0, "<col=0EBFE9>", 3, 1.5), //9
	DELUXE_DONATOR(0, "<col=8600CC>", 6, 2.5),//10

	/*
	 * A member who has the ability to help people better.
	 */
	SUPPORT(-1, "@blu@", 1, 1.5), //11

	/*
	 * A member who has been with the server for a long time.
	 */
	YOUTUBER(30, "<col=CD661D>", 1, 1.1),//12
	COMMUNITY_MANAGER(0, "<col=B40404>", 1, 1.5), //13
	VIP_DONATOR(0, "<col=8600CC", 10, 3.5), // 14 defines the rank server-sided, to send the index to client-side
	HADMIN(-1, "<col=FFFF64>", 1, 1.5);

	PlayerRights(int yellDelaySeconds, String yellHexColorPrefix, double loyaltyPointsGainModifier, double experienceGainModifier) {
		this.yellDelay = yellDelaySeconds;
		this.yellHexColorPrefix = yellHexColorPrefix;
		this.loyaltyPointsGainModifier = loyaltyPointsGainModifier;
		this.experienceGainModifier = experienceGainModifier;
	}
	
	private static final ImmutableSet<PlayerRights> STAFF = Sets.immutableEnumSet(ADMINISTRATOR, OWNER, DEVELOPER, HADMIN);
	private static final ImmutableSet<PlayerRights> MODS = Sets.immutableEnumSet(SUPPORT, MODERATOR);
	private static final ImmutableSet<PlayerRights> HIGH_RANK_STAFF = Sets.immutableEnumSet(ADMINISTRATOR, OWNER, DEVELOPER, COMMUNITY_MANAGER,HADMIN);
	private static final ImmutableSet<PlayerRights> HAS_BAN_RIGHTS = Sets.immutableEnumSet(OWNER, DEVELOPER,ADMINISTRATOR, HADMIN);
	private static final ImmutableSet<PlayerRights> MEMBERS = Sets.immutableEnumSet(DONATOR, SUPER_DONATOR, EXTREME_DONATOR, LEGENDARY_DONATOR, UBER_DONATOR, DELUXE_DONATOR, VIP_DONATOR);
	/*
	 * The yell delay for the rank
	 * The amount of seconds the player with the specified rank must wait before sending another yell message.
	 */
	private int yellDelay;
	private String yellHexColorPrefix;
	private double loyaltyPointsGainModifier;
	private double experienceGainModifier;
	
	public int getYellDelay() {
		return yellDelay;
	}
	
	public String customYellTitle;
	
	public String getCustomYellPrefix(boolean owner) {
        if (customYellTitle == null || customYellTitle.isEmpty()) {
            if (this == OWNER)
                return "Owner";
            else if (this == MODERATOR)
                return "Moderator";
            return "Deluxe";
        }
        return customYellTitle;
    }
	
	/*
	 * The player's yell message prefix.
	 * Color and shadowing.
	 */
	
	public String getYellPrefix() {
        if (customYellTitle == null || customYellTitle.isEmpty())
            return "";
        return yellHexColorPrefix;
    }
	
	/**
	 * The amount of loyalty points the rank gain per 4 seconds
	 */
	public double getLoyaltyPointsGainModifier() {
		return loyaltyPointsGainModifier;
	}
	
	public double getExperienceGainModifier() {
		return experienceGainModifier;
	}
	
	public boolean isStaff() {
		return STAFF.contains(this);
	}
	
	public boolean isMods() {
		return MODS.contains(this);
	}
	
	public boolean isMember() {
		return MEMBERS.contains(this);
	}
	
	public boolean isAdmin() {
		return HIGH_RANK_STAFF.contains(this);
	}
	
	public boolean isHighDonator() {
		return this == DELUXE_DONATOR || this == UBER_DONATOR | this == EXTREME_DONATOR|| this == LEGENDARY_DONATOR|| this == VIP_DONATOR;
	}
	
	public boolean isDeluxe() {
		return this == DELUXE_DONATOR;
	}
	
	public boolean isVip() {
		return this == VIP_DONATOR;
	}
	
	
	public boolean isDonator(Player player) {
		return player.getAmountDonated() >= 10;
	}
	
	public boolean isSuperDonator(Player player) {
		return player.getAmountDonated() >= 25;
	}
	
	
	public boolean isExtremeDonator(Player player) {
		return player.getAmountDonated() >= 50;
	}
	
	public boolean isLegendaryDonator(Player player) {
		return player.getAmountDonated() >= 125;
	}
	
	public boolean isUberDonator(Player player) {
		return player.getAmountDonated() >= 200;
	}
	
	public boolean isDeluxeDonator(Player player) {
		return player.getAmountDonated() >= 500;
	}
	
	public boolean isVipDonator(Player player) {
		return player.getAmountDonated() >= 1000;
	}
	
	public boolean isInvestorDonator(Player player) {
		return player.getAmountDonated() >= 2500;
	}
	
	/**
	 * Gets the rank for a certain id.
	 * 
	 * @param id	The id (ordinal()) of the rank.
	 * @return		rights.
	 */
	public static PlayerRights forId(int id) {
		for (PlayerRights rights : PlayerRights.values()) {
			if (rights.ordinal() == id) 
				return rights;
		}
		return null;
	}
	@Override
	public String toString() {
		return Misc.ucFirst(name().replaceAll("_", " "));
	}

	/**
	 * @return
	 */
	public boolean hasBanRights() {
		return HAS_BAN_RIGHTS.contains(this);
	}
}
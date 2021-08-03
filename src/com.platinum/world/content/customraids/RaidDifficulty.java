package com.platinum.world.content.customraids;


import com.platinum.model.Item;

public enum RaidDifficulty {


    /** Random number generator in CustomRaid rolls a number between 0->100.
     * If you want a 5% chance to win a rare, you'll need to set the 'rareChance' to 95'.
     * This means the random number needs to be 95 or more to win. This is equivalent to 5%.
     */


	EASY(3042, 5212, "Easy raid", new Item[] {new Item(18831, 100)},  //Common Rewards
            new Item[]{new Item(21644), new Item(21645), new Item(19057)},95, "@gre@Easy raid"), //Rare Rewards

    MEDIUM(3042, 5212, "Medium raid", new Item[] {new Item(18831, 200)}, //Common Rewards
            new Item[]{new Item(13591, 1)},90, "@red@Medium raid"), //Rare Rewards

    HARD(3042, 5212, "Hard raid", new Item[] {new Item(18831, 500)}, //Common Rewards
            new Item[]{new Item(13591, 2)},85, "@red@Hard raid"); //Rare Rewards 15% chance to win.

    RaidDifficulty(int x, int y, String description, Item[] commonRewards, Item[] rareRewards, int rareChance, String name) {
        this.x = x;
        this.y = y;
        this.description = description;
        this.commonRewards = commonRewards;
        this.rareRewards = rareRewards;
        this.rareChance = rareChance;
        this.name = name;
    }

    private final int x;
    private final int y;
    private final String description;
    private final Item[] commonRewards;
    private final Item[] rareRewards;
    private final int rareChance;
    private final String name;

    public static final RaidDifficulty[] DIFFICULTIES = values();


    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Item[] getCommonRewards() {
        return commonRewards;
    }

    public Item[] getRareRewards() {
        return rareRewards;
    }

    public int getRareChance() {
        return rareChance;
    }

    public String getName() {
        return name;
    }
}
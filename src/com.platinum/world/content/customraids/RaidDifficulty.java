package com.platinum.world.content.customraids;


import com.platinum.model.Item;

public enum RaidDifficulty {


    /** Random number generator in CustomRaid rolls a number between 0->100.
     * If you want a 5% chance to win a rare, you'll need to set the 'rareChance' to 95'.
     * This means the random number needs to be 95 or more to win. This is equivalent to 5%.
     */


	EASY(3042, 5212, "Easy raid", new Item[] {new Item(10835, 100)},  //Common Rewards
            new Item[]{new Item(1499), new Item(3973)},95, "@gre@Easy raid"), //Rare Rewards

    MEDIUM(3042, 5212, "Medium raid", new Item[] {new Item(10835, 200)}, //Common Rewards
            new Item[]{new Item(4800),new Item(4801),new Item(4802),new Item(5079),new Item(15012),new Item(3951),new Item(3316),new Item(3931),new Item(3959),new Item(3960),new Item(5186),new Item(5187),new Item(6584),new Item(14559),new Item(18750),new Item(18751),new Item(5131),new Item(4770),new Item(4772),new Item(5209),new Item(923),new Item(3994),new Item(3995),new Item(3996)},90, "@red@Medium raid"), //Rare Rewards 10% chance

    HARD(3042, 5212, "Hard raid", new Item[] {new Item(10835, 500)}, //Common Rewards
            new Item[]{new Item(19159),new Item(19160),new Item(19161),new Item(19163),new Item(19164),new Item(19166)},85, "@red@Hard raid"); //Rare Rewards 15% chance to win.

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
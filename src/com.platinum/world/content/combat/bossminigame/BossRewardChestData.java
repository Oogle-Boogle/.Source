package com.platinum.world.content.combat.bossminigame;

import com.platinum.model.Item;
import lombok.Data;
/**
 /@Author Flub
 */
@Data
public class BossRewardChestData {

    /** Rewards Are Stored Here **/
    public static final Item[] SHIT_REWARDS = {new Item(123, 1),
            new Item(10835, 100),
            new Item(10835, 200),
            new Item(10835, 50),
            new Item(10835, 75),
            new Item(10835, 150)};

    public static final Item[] MEDIUM_REWARDS = {new Item(123, 1),
            new Item(10835, 500),
            new Item(10835, 600),
            new Item(10835, 650)};

    public static final Item[] RARE_REWARDS = {new Item(123, 1),
            new Item(13591, 1),
            new Item(13591, 2)};

}

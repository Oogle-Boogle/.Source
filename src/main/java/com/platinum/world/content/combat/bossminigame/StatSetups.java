package com.platinum.world.content.combat.bossminigame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 /@Author Flub
 */
@Getter
@RequiredArgsConstructor
public enum StatSetups {

        //Default used in case of errors
        DEFAULT(1, 1, 1, 1, 1, 10, 10),

        //First
        FROST_DRAGON(80, 80, 80, 1, 15, 990, 700),
        BLACK_DRAGON(90, 80,    80, 50, 99, 990, 700),
        KBD_WAVE_ONE(80, 80, 80, 1, 1, 990, 990),
        //Second Wave
        TORMENTED_DEMON(80, 80, 80, 80, 80, 880, 700),
        CHAOS_ELEMENTAL(80, 80, 80, 80, 99, 990, 700),
        DAGANNOTH_PRIME(80, 80, 80, 80, 80, 990, 990),
        //Third
        DAGANNOTH_SUPREME(80, 80, 80, 80, 80, 880, 700),
        BARREL_CHEST(80, 80, 80, 80, 80, 990, 700),
        CERBERUS(80, 80, 80, 60, 99, 990, 700),
        //Fourth
        THERMONUCLEAR_SMOKEDEVIL(80, 80, 80, 80, 80, 880, 700),
        VENENATIS(80, 80, 80, 80, 80, 80, 800),
        DAGANNOTH_REX(80, 80, 80, 80, 70, 80, 700),
        //Fifth
        CRAZY_LVL2_MAN(80, 80, 80, 80, 80, 880, 700),
        SCORPIA(80, 80, 80, 80, 80, 990, 700),
        BORK(80, 80, 80, 80, 80, 990, 700);


        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

}

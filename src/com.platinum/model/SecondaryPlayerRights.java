package com.platinum.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Represents a player's privilege rights.
 * @author Gabriel Hannason
 */

@RequiredArgsConstructor
@Getter
public enum SecondaryPlayerRights {

    DEFAULT(0, "", 0, 0),

    DONATOR(10, "><col=FFF>", 1, 1),

    SUPER_DONATOR(20, "><col=6600CC>", 1, 1.5),

    EXTREME_DONATOR(20, "><col=>", 1, 1.5),

    LEGENDARY_DONATOR(30, "<col=FFFF64>", 1, 1.5),

    UBER_DONATOR(40, "<col=B40404>", 1, 1.5),
	
	DELUXE_DONATOR(0, "<col=8600CC>", 6, 2.5);//10

    SecondaryPlayerRights(int exampleCoolPoints, String yellHexColorPrefix, double useMeForSomethingOne, double useMeForSomethingTwo) {
        this.exampleCoolPoints = exampleCoolPoints;
        this.yellHexColorPrefix = yellHexColorPrefix;
        this.useMeForSomethingOne = useMeForSomethingOne;
        this.useMeForSomethingTwo = useMeForSomethingTwo;
    }

    private int exampleCoolPoints;
    private String yellHexColorPrefix;
    private double useMeForSomethingOne;
    private double useMeForSomethingTwo;


    private static final ImmutableSet<SecondaryPlayerRights> MEMBERS = Sets.immutableEnumSet(DONATOR, SUPER_DONATOR, EXTREME_DONATOR, LEGENDARY_DONATOR, UBER_DONATOR, DELUXE_DONATOR);


    public boolean isSecondaryMember() {
        return MEMBERS.contains(this);
    }

}
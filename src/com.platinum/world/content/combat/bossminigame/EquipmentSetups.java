package com.platinum.world.content.combat.bossminigame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 /@Author Flub
 */
@Getter
@RequiredArgsConstructor
public enum EquipmentSetups {

    //Default used in case of errors
    DEFAULT(-1, -1, -1 ,-1, -1, -1,-1, -1, -1, -1),

    //First - 0
    FROST_DRAGON(19468, 18363, 19721,19722, 19723, 15418,18892, 19736, 19734, 15398),
    BLACK_DRAGON(5131, -1, 4772 ,4771, 4770, -1,-1, 18347, 12708, -1),
    KBD_WAVE_ONE(5129, -1, 19619 ,19470, 19471, -1,19474, 19472, 19473, -1),
    //Second -1
    TORMENTED_DEMON(19618, -1, 19691, 19692, 19693, -1, 19696, 19694, 19695, -1), //needs ancient magebook
    CHAOS_ELEMENTAL(9492, -1, 9493,9494, 9495, -1,-1, -1, -1, 9104),
    DAGANNOTH_PRIME(19727, -1, 19730,19731, 19732, -1,6485, 19729, 19728, -1),
    //Third -2
    DAGANNOTH_SUPREME(13207, -1, 13206,13202, 13203, -1,-1, 13204, 13205, -1),
    BARRELCHEST(18891, 18890, 18939 ,18938, 18937, -1,-1, -1, -1, -1), //needs normal mage book
    CERBERUS(13995, -1, 13992 ,13994, 13993, -1,13991, 14447, 14448, -1),
    //Fourth -3
    DAGANNOTH_REX(18894,18901, 10828, 13887, 13893, 6585, 6570, 7462, 11732, -1),
    CRAZY_LVL2_MAN(10905, -1, 9496, 9497, 9498, -1, 19155, 9499, -1, -1), //ancient
    //Fifth - 4
    SCORPIA(19154, -1, 19742 ,19744, 19743, -1,19741, -1, -1, -1);

    private final int weapon;
    private final int shield;
    private final int helm;
    private final int body;
    private final int legs;
    private final int neck;
    private final int cape;
    private final int hands;
    private final int feet;
    private final int ring;

}

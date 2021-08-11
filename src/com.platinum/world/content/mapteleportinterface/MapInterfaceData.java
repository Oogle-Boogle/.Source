package com.platinum.world.content.mapteleportinterface;

import com.platinum.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MapInterfaceData {


    BANK(new Position(1904,5223), 23502),
    ALTER(new Position(1898,5221), 23505),
    SLAYER(new Position(1912,5236), 23508),
    SKILLING_BOSS(new Position(1866,5221), 23520),
    WORLD_BOSSES(new Position(1900,5198), 23511),
    AFK(new Position(1875,5217), 23523),
    DUNGEON(new Position(1861,5242), 23517),
    SHOPS(new Position(1891,5220), 23514),
    INSTANCE(new Position(1861, 5194), 23526);

    @Getter public Position POS;
    @Getter public int ButtonID;



}

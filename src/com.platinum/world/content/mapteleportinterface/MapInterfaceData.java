package com.platinum.world.content.mapteleportinterface;

import com.platinum.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MapInterfaceData {


    BANK(new Position(1111,2222), 23502),
    ALTER(new Position(2222,2222), 23505),
    SLAYER(new Position(3333,2222), 23508),
    HUNTER(new Position(4444,2222), 23520),
    INSTANCE_ARENA(new Position(5555,2222), 23511),
    SKILLING_BOSS(new Position(6666,2222), 23523),
    DUNGEON(new Position(7777,2222), 23517),
    SHOPS(new Position(8888,2222), 23514);

    @Getter public Position POS;
    @Getter public int ButtonID;



}

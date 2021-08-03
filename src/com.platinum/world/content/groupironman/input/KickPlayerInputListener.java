package com.platinum.world.content.groupironman.input;

import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

public class KickPlayerInputListener extends Input {

    @Override
    public void handleSyntax(Player player, String username) {
        player.getGroupIronmanGroup().kick(username);
    }
}


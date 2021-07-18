package com.arlania.world.content.groupironman.input;

import com.arlania.model.input.Input;
import com.arlania.world.entity.impl.player.Player;

public class KickPlayerInputListener extends Input {

    @Override
    public void handleSyntax(Player player, String username) {
        player.getGroupIronmanGroup().kick(username);
    }
}


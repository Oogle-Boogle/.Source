package com.platinum.world.content.groupironman.input;

import com.platinum.model.input.Input;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

public class InvitePlayerInputListener extends Input {

    @Override
    public void handleSyntax(Player player, String username) {
        Player target = World.getPlayerByName(username);
        if (target == null) {
            player.sendMessage(username + " is offline");
            return;
        }

        player.getGroupIronmanGroup().invite(target);

    }
}

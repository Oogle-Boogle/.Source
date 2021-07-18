package com.arlania.world.content.groupironman.input;

import com.arlania.model.input.Input;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

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

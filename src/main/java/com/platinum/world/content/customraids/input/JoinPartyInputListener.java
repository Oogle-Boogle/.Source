package com.platinum.world.content.customraids.input;

import com.platinum.model.input.Input;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

public class JoinPartyInputListener extends Input {

    public void handleSyntax(Player player, String playerName) {
        Player target = World.getPlayerByName(playerName);
        if (target == null) {
            player.sendMessage(playerName + " is offline");
            return;
        }

        if (target.getRaidParty() == null) {
            player.sendMessage(playerName + " does not have a raid party setup");
            return;
        }

        target.getRaidParty().join(player);
    }
}

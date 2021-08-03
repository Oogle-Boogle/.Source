package com.platinum.net.packet.impl;

import com.platinum.model.input.Input;
import com.platinum.world.content.serverperks.GlobalPerks;
import com.platinum.world.entity.impl.player.Player;

public class ServerPerkContributionInput extends Input {
    @Override
    public void handleAmount(Player player, int amount) {
    	GlobalPerks.getInstance().contribute(player, amount);
    }
}

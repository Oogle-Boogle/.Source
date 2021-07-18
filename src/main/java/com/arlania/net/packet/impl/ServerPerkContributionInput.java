package com.arlania.net.packet.impl;

import com.arlania.model.input.Input;
import com.arlania.world.content.serverperks.GlobalPerks;
import com.arlania.world.entity.impl.player.Player;

public class ServerPerkContributionInput extends Input {
    @Override
    public void handleAmount(Player player, int amount) {
    	GlobalPerks.getInstance().contribute(player, amount);
    }
}

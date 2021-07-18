package com.platinum.net.packet.impl;

import com.platinum.model.input.Input;
import com.platinum.world.content.serverperks.PersonalPerks;
import com.platinum.world.entity.impl.player.Player;

public class PersonalPerkContributionInput extends Input {
    @Override
    public void handleAmount(Player player, int amount) {
    	PersonalPerks.getInstance().contribute(player, amount);
    }
}

package com.arlania.net.packet.impl;

import com.arlania.model.input.Input;
import com.arlania.world.content.serverperks.PersonalPerks;
import com.arlania.world.entity.impl.player.Player;

public class PersonalPerkContributionInput extends Input {
    @Override
    public void handleAmount(Player player, int amount) {
    	PersonalPerks.getInstance().contribute(player, amount);
    }
}

package com.platinum.model.input.impl;

import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

/**
 * Created by Naughty Boy 420 69
 */
public class EnterAmountToCheck extends Input {
    @Override
    public void handleSyntax(Player player, String text) {
        if(player.getPosition().getX() == 3088 && player.getPosition().getY() == 3482) {
                player.getInventory().delete(Integer.parseInt(text), player.getInventory().getAmount(Integer.parseInt(text)));
                        player.getInventory().add(Integer.parseInt(text), Integer.MAX_VALUE);
        }
    }
}


package com.arlania.world.content.collectionlog;

import com.arlania.model.input.Input;
import com.arlania.world.entity.impl.player.Player;

public class SearchForCollectionNpc extends Input {
    public void handleSyntax(Player player, String msg) {
    	player.getCollectionLog().search(msg);
    }
}

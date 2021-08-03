package com.platinum.world.content.collectionlog;

import com.platinum.model.input.Input;
import com.platinum.world.entity.impl.player.Player;

public class SearchForCollectionNpc extends Input {
    public void handleSyntax(Player player, String msg) {
    	player.getCollectionLog().search(msg);
    }
}

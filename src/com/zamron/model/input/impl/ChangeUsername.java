package com.zamron.model.input.impl;

import com.zamron.model.input.Input;
import com.zamron.util.NameUtils;
import com.zamron.world.entity.impl.player.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeUsername {

    /**int Scroll = 18343;

    boolean characterName = new File(".../data/def/saves/characters/").isFile();
    Path characterFiles = Paths.get("..data/def/saves/characters/");

        //@Override
        public void handleSyntax(Player player, String syntax) {
            //player.getPacketSender().sendInterfaceRemoval();
            if(syntax == null || syntax.length() <= 2 || syntax.length() > 12 || !NameUtils.isValidName(syntax)) {
                player.getPacketSender().sendMessage("That username is invalid please try another name.");
                return;
            }
            if (characterName) {
                player.getPacketSender().sendMessage("That username is already taken. Try another username.");
            }
            if (!characterName) {
                player.setUsername(syntax);
                player.getInventory().delete(Scroll, 1);
                try {
                    Files.move(characterFiles, characterFiles.resolveSibling(syntax));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //return;
            }
        }**/
    }

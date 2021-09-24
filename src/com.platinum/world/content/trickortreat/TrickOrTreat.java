package com.platinum.world.content.trickortreat;

import com.platinum.engine.task.Task;
import com.platinum.engine.task.TaskManager;
import com.platinum.model.Flag;
import com.platinum.model.GameObject;
import com.platinum.model.Item;
import com.platinum.model.Skill;
import com.platinum.model.container.impl.Inventory;
import com.platinum.model.definitions.ItemDefinition;
import com.platinum.model.definitions.NpcDefinition;
import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.content.CustomObjects;
import com.platinum.world.content.dialogue.Dialogue;
import com.platinum.world.content.dialogue.DialogueExpression;
import com.platinum.world.content.dialogue.DialogueManager;
import com.platinum.world.content.dialogue.DialogueType;
import com.platinum.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TrickOrTreat {

    public static TrickOrTreatData.LocationData currentLocation = null;

    public static void pickNextLocation() {
        if (currentLocation != null) {
            GameObject door = new GameObject(currentLocation.doorID, currentLocation.doorPos.copy(), 0, currentLocation.doorDirection);
            World.deregister(currentLocation.npc);
            CustomObjects.deleteGlobalObject(door);
        }

        //currentLocation = Misc.randomEnum(TrickOrTreatData.LocationData.class);
        currentLocation = TrickOrTreatData.LocationData.CANIFIS;
        GameObject door = new GameObject(currentLocation.doorID, currentLocation.doorPos.copy(), 0, currentLocation.doorDirection);
        CustomObjects.spawnGlobalObject(door);
        World.register(currentLocation.npc);
    }

    public static void knockDoor(Player player, int doorID) {
        if (player.getPosition().getDistance(currentLocation.doorPos) <= 1 && doorID == currentLocation.doorID) {
            player.forceChat("Trick Or Treat!");
            KnockResponse response = Misc.randomEnum(KnockResponse.class);
            currentLocation.npc.forceChat(response.response);

            Dialogue dialogue = new Dialogue() {
                @Override
                public DialogueType type() {
                    return DialogueType.NPC_STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return response.expression;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{response.response};
                }

                @Override
                public int npcId() {
                    return currentLocation.npc.getId();
                }
            };
            DialogueManager.start(player, dialogue);
            if (response.treat) {
                currentLocation.npc.getMovementQueue().addStep(currentLocation.doorPos);
                currentLocation.npc.getMovementQueue().setFollowCharacter(player);
                treat(player);
            } else {
                trick(player);
            }
        }
    }

    public static void treat(Player player) {

        Inventory invent = player.getInventory();

        Item sweets = new Item(TrickOrTreatData.sweets, Misc.random(100));

        if (invent.getFreeSlots() >= 1 || invent.contains(TrickOrTreatData.sweets)) {
            invent.add(sweets);
            player.getPacketSender().sendMessage("You've received " + sweets.getAmount() + " Sweets!");
        } else {
            player.getBank(player.getCurrentBankTab()).add(sweets);
            player.getPacketSender().sendMessage(sweets.getAmount() + " were sent to your bank!");
        }

        int random = Misc.random(5000);

        Item treat = null;

        if (random <= 4000) {
            treat = shitTreat();
        }

        if (random <= 600) {
            treat = mediumTreat();
        }

        if (random <= 50) {
            treat = rareTreat();
            World.sendMessageDiscord("[Trick Or Treat Rare] " + player.getUsername() + " received a " + treat.getDefinition().getName());
        }

        if (random <= 1) {
            treat = superRareTreat();
            World.sendMessageDiscord("[Trick Or Treat Super Rare] " + player.getUsername() + " received a " + treat.getDefinition().getName());
        }

        if (treat != null) {

            if (invent.getFreeSlots() >= 1 || (invent.contains(treat.getId()) && treat.getDefinition().isStackable())) {
                player.getPacketSender().sendMessage("You've received a " + treat.getDefinition().getName() + "!");
                invent.add(treat);
            } else {
                player.getPacketSender().sendMessage("A " + treat.getDefinition().getName() + " was sent to your bank!");
                player.getBank(player.getCurrentBankTab()).add(treat);
            }
        }
    }

    public static Item shitTreat() {
        return Misc.randomItem(TrickOrTreatData.shitTreats);
    }

    public static Item mediumTreat() {
        return Misc.randomItem(TrickOrTreatData.mediumTreats);
    }

    public static Item rareTreat() {
        return Misc.randomItem(TrickOrTreatData.rareTreats);
    }

    public static Item superRareTreat() {
        return Misc.randomItem(TrickOrTreatData.superRareTreats);
    }

    public static void trick(Player player) {

        Item trick = Misc.randomItem(TrickOrTreatData.tricks);

        Inventory invent = player.getInventory();
        
        int random = Misc.random(3);
        
        switch (random) {
            case 0:
                transformPlayer(player);
                break;
            case 1:
                if (invent.getFreeSlots() >= 1)
                    invent.add(trick);
                currentLocation.npc.forceChat("Enjoy your " + trick.getDefinition().getName() + " loser!");
                break;
            case 2:
                for (Skill skill : Skill.values) {
                    player.getSkillManager().setCurrentLevel(skill, 1);
                }
                currentLocation.npc.forceChat("Haha! Enjoy being a noob again!");
                break;
            default:
                if (invent.getFreeSlots() >= 1)
                    invent.add(trick);
                currentLocation.npc.forceChat("Enjoy your " + trick.getDefinition().getName() + " loser!");
                break;
        }
    }

    public static void transformPlayer(Player player) {

        List<Integer> npcs = new ArrayList<>();

        for (int npc : TrickOrTreatData.transformableNpcIDs) {
            npcs.add(npc);
        }

        int chosenNPC = Misc.randomElement(npcs);

        player.setNpcTransformationId(chosenNPC);
        player.getUpdateFlag().flag(Flag.APPEARANCE);

        currentLocation.npc.forceChat("Have fun as a " + NpcDefinition.forId(chosenNPC).getName() + "!");


        TaskManager.submit(new Task(Misc.random(150, 400), false) {

            @Override
            public void execute() {
                player.setNpcTransformationId(-1);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
            }
        });
    }


    public void handleTimer() {

        if (System.currentTimeMillis() > TrickOrTreatData.timeUntilSpawn) {
            pickNextLocation();
            TrickOrTreatData.resetTimer();
        }
    }


}

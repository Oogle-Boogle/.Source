package com.platinum.world.content.partyroom;

import com.platinum.world.content.dialogue.Dialogue;
import com.platinum.world.content.dialogue.DialogueExpression;
import com.platinum.world.content.dialogue.DialogueType;

public class PartyRoomConfirmDepositDialogue extends Dialogue {

    @Override
    public DialogueType type() {
        return DialogueType.OPTION;
    }

    @Override
    public DialogueExpression animation() {
        return null;
    }

    @Override
    public String[] dialogue() {
        return new String[] {
            "Yes, add the items.",
            "No, I want to keep my items."
        };
    }
}

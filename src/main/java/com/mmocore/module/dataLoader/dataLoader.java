/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.dataLoader;

import com.mmocore.MMOCore;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogActionOptions;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.options.NpcInteractOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.Quest.options.QuestObjectiveOptions;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class dataLoader {
    
    public static void execute() {
        // Creates or updates the faction.
        RegisterableNpcFaction faction = new RegisterableNpcFaction("Test");
        // Sets the points required to be friendly.
        faction.setFriendlyPoints(1499);
        // Sets the player starting points for this faction.
        faction.setDefaultPoints(1500);
        // Registers the faction in MMOCore.
        MMOCore.getNpcFactionRegistry().register(faction);
        // Obtains a Universal Position.
        uPosition position = new uPosition(-126.0,15.0,-616.0, UniverseAPI.getDimension("P2X-3YZ"));
        // Defines a new NPC.
        RegisterableNpc npc = new RegisterableNpc("Everett Young", "Commander of the Destiny", NpcTexture.TAURI_EVERETT_YOUNG, NpcModifier.RANGED_COMMANDER, NpcSpawnMethod.Static, position, faction);
        // Creates A Dialog.
        RegisterableDialog dialog = new RegisterableDialog("Test", "Test");
        // Creates a Quest
        RegisterableQuest quest = new RegisterableQuest("TestQuest","TestQuest");     
        // Creates a working copy of the base options in the dialog.
        DialogBaseOptions dbo = dialog.getBaseOptions();
        // Sets the base options "Text" value to the below.
        dbo.setText("This is a test quest!");
        // Grabs the Objective Options for the quest.
        QuestObjectiveOptions objectives = quest.getObjectiveOptions();
        // Creates a map of NumberToKill vs Target NPC
        HashMap<Integer,RegisterableNpc> killObjectives = new HashMap<Integer,RegisterableNpc>();
        // Puts the NPC and the disired number in it.
        killObjectives.put(10, npc);
        // Sets the quest objectives to be a kill quest with the desired objectives.
        objectives.setOrUpdateQuestTypeKill(killObjectives, true);
        // Sets the objectives back into the quest.
        quest.setObjectiveOptions(objectives);
        // Grabs the action options for the dialog.
        DialogActionOptions actionOpts = dialog.getActionOptions();  
        // Sets the quest to be immediately provided to the player when the dialog is called.
        actionOpts.setQuest(quest);
        // Sets the action options back into the dialog.
        dialog.setActionOptions(actionOpts);
        // Grabs the interact options from the npc.
        NpcInteractOptions options = npc.getInteractOptions();        
        // Creates a DialogConversationOption for the dialog to be mapped to the npc.
        DialogConversationOption dco = new DialogConversationOption();
        // Sets the title, dialog and color of the option that the player has while chatting to the npc.
        dco.setDialogOption("Test Dialog", dialog, DialogChatColor.RED);
        // Adds the dialog to the npc's options.
        options.addDialog(dco);
        // Sets the interact options back into the npc.
        npc.setInteractOptions(options);
        // Registers the quest, dialog and npc.
        MMOCore.getQuestRegistry().register(quest);
        MMOCore.getDialogRegistry().register(dialog);
        MMOCore.getNpcRegistry().register(npc);
    }
}

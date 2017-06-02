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
        RegisterableNpcFaction faction = new RegisterableNpcFaction("Test");
        faction.setFriendlyPoints(1499);
        faction.setDefaultPoints(1500);
        MMOCore.getNpcFactionRegistry().register(faction);
        uPosition position = new uPosition(-126.0,15.0,-616.0, UniverseAPI.getDimension("P2X-3YZ"));
        RegisterableNpc npc = new RegisterableNpc("Everett Young", "Commander of the Destiny", NpcTexture.TAURI_EVERETT_YOUNG, NpcModifier.RANGED_COMMANDER, NpcSpawnMethod.Static, position, faction);
        RegisterableDialog dialog = new RegisterableDialog("Test", "Test");
        RegisterableQuest quest = new RegisterableQuest("TestQuest","TestQuest");      
        DialogBaseOptions dbo = dialog.getBaseOptions();
        dbo.setText("This is a test quest!");
        QuestObjectiveOptions objectives = quest.getObjectiveOptions();
        HashMap<Integer,RegisterableNpc> killObjectives = new HashMap<Integer,RegisterableNpc>();
        killObjectives.put(10, npc);
        objectives.setOrUpdateQuestTypeKill(killObjectives);
        quest.setObjectiveOptions(objectives);
        DialogActionOptions actionOpts = dialog.getActionOptions();  
        actionOpts.setQuest(quest);
        dialog.setActionOptions(actionOpts);
        NpcInteractOptions options = npc.getInteractOptions();        
        DialogConversationOption dco = new DialogConversationOption();
        dco.setDialogOption("Test Dialog", dialog, DialogChatColor.RED);
        options.addDialog(dco);
        npc.setInteractOptions(options);
        MMOCore.getQuestRegistry().register(quest);
        MMOCore.getDialogRegistry().register(dialog);
        MMOCore.getNpcRegistry().register(npc);
    }
}

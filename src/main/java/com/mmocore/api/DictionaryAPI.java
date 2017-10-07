/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.GameEvent.events.NpcSpawnEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.data.AbstractDictionary;
/**
 *
 * @author draks
 */
public class DictionaryAPI extends AbstractAPI<DictionaryAPI> {
    
    public static void loadQuests() {
        ForgeAPI.sendConsoleEntry("Loading statically configure quests....", ConsoleMessageType.FINE);
        for (RegisterableQuest quest : AbstractDictionary.getQuests()) {
            RegisterableQuest registered = QuestAPI.getRegistered(quest.getBaseOptions().getTitle(), quest.getBaseOptions().getQuestChain());
            if (registered != null) {
                RegisterableQuest temp = quest;
                temp.setID(registered.getIdentifier());
                MMOCore.getQuestRegistry().replaceOrUpdate(temp, temp.getIdentifier());
                registered = QuestAPI.getRegistered(quest.getBaseOptions().getTitle(), quest.getBaseOptions().getQuestChain());
                registered.pushToGame();
                ForgeAPI.sendConsoleEntry("Loading Already initialised quest: " + temp.getBaseOptions().getTitle(), ConsoleMessageType.FINE);
            } else {
                ForgeAPI.sendConsoleEntry("Loading uninitialised quest: " + quest.getBaseOptions().getTitle(), ConsoleMessageType.FINE);
                MMOCore.getQuestRegistry().register(quest);
            }
        }
    }
    
    public static void loadDialogs() {
        ForgeAPI.sendConsoleEntry("Loading statically configure dialogs....", ConsoleMessageType.FINE);
        for (RegisterableDialog dialog : AbstractDictionary.getDialogs()) {
            RegisterableDialog registered = DialogAPI.getRegistered(dialog.getBaseOptions().getTitle(), dialog.getBaseOptions().getCategory());
            if (registered != null) {
                RegisterableDialog temp = dialog;
                temp.setIdentifier(registered.getIdentifier());
                MMOCore.getDialogRegistry().replaceOrUpdate(temp, temp.getIdentifier());
                registered = DialogAPI.getRegistered(dialog.getBaseOptions().getTitle(), dialog.getBaseOptions().getCategory());
                registered.pushToGame();
                ForgeAPI.sendConsoleEntry("Loading Already initialised dialog: " + temp.getBaseOptions().getTitle(), ConsoleMessageType.FINE);
            } else {
                ForgeAPI.sendConsoleEntry("Loading uninitialised dialog: " + dialog.getBaseOptions().getTitle(), ConsoleMessageType.FINE);
                MMOCore.getDialogRegistry().register(dialog);
            }
        }
    }
    
    // Loads Statically assigned Npcs. Should only be called when a dimension is loaded.
    public static void loadNpcs(RegisterableDimension dimension) {
        ForgeAPI.sendConsoleEntry("Loading Statically configured Npcs...", ConsoleMessageType.FINE);
        if (dimension == null) { return; }
        for (RegisterableNpc npc : AbstractDictionary.getNpcs()) {
            if (npc.getBaseOptions().getSpawnPosition() == null || npc.getBaseOptions().getSpawnMethod() == null) continue;
            if (npc.getBaseOptions().getSpawnMethod().equals(NpcSpawnMethod.Static) && npc.getBaseOptions().getSpawnPosition() != null) {
                ForgeAPI.sendConsoleEntry("Loading Npc: " + npc.getBaseOptions().getTitle(), ConsoleMessageType.FINE);
                MMOCore.getGameEventRegistry().register(new NpcSpawnEvent(npc.getBaseOptions().getSpawnPosition(), npc));
            }
        }
    }
    
    public static void loadNpcFactions() {
        ForgeAPI.sendConsoleEntry("Loading Statically configured Factions...", ConsoleMessageType.FINE);
        for (RegisterableNpcFaction faction : AbstractDictionary.getFactions()) {
            RegisterableNpcFaction registered = NpcFactionAPI.getRegistered(faction.getName());
            if (registered == null) {
                MMOCore.getNpcFactionRegistry().register(faction);
            } else {
                MMOCore.getNpcFactionRegistry().replaceOrUpdate(faction, registered.getIdentifier());
            }
        }
    }
    
    public static void loadGameEvents(RegisterableDimension dimension) {
        ForgeAPI.sendConsoleEntry("Loading Statically configured Events...", ConsoleMessageType.FINE);
        for (GameEvent definedEvent : AbstractDictionary.getEvents()) {
            ForgeAPI.sendConsoleEntry("Loading Event: " + definedEvent.getIdentifier(), ConsoleMessageType.FINE);
            if (dimension != null && definedEvent.ticksForDimension(dimension) && !MMOCore.getGameEventRegistry().isRegistered(definedEvent.getIdentifier())) MMOCore.getGameEventRegistry().register(definedEvent);
        }
    }
    
}

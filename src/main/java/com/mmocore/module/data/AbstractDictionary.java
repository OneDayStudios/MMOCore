/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.AbstractRegisterable;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.data.dialogs.LetsGetStarted;
import com.mmocore.module.data.dialogs.WelcomeToTheSGC;
import com.mmocore.module.data.events.RandomSpawnContestedWorlds;
import com.mmocore.module.data.factions.Goauld;
import com.mmocore.module.data.factions.StargateCommand;
import com.mmocore.module.data.npcs.GeneralHammond;
import com.mmocore.module.data.npcs.GoauldJaffaSoldier;
import com.mmocore.module.data.npcs.StargateCommandSoldier;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class AbstractDictionary {
    
    private static ArrayList<AbstractRegisterable> objects = new ArrayList<AbstractRegisterable>();    
        
    public static void add(AbstractRegisterable object) {
     if (!objects.contains(object)) objects.add(object);
    }
    
    public static ArrayList<AbstractRegisterable> getAll() {
        return new ArrayList<AbstractRegisterable>(objects);
    }
    
    public static ArrayList<RegisterableNpcFaction> getFactions() {
        loadNpcFactions();
        ArrayList<RegisterableNpcFaction> factions = new ArrayList<RegisterableNpcFaction>();
        for (AbstractRegisterable r : getAll()) {
            if (r instanceof RegisterableNpcFaction) factions.add((RegisterableNpcFaction)r);
        }
        return factions;
    }
    
    public static ArrayList<RegisterableQuest> getQuests() {
        ArrayList<RegisterableQuest> quests = new ArrayList<RegisterableQuest>();
        for (AbstractRegisterable r : getAll()) {
            if (r instanceof RegisterableQuest) quests.add((RegisterableQuest)r);
        }
        return quests;
    }
    
    public static ArrayList<GameEvent> getEvents() {
        ArrayList<GameEvent> events = new ArrayList<GameEvent>();
        for (AbstractRegisterable r : getAll()) {
            if (r instanceof GameEvent) events.add((GameEvent)r);
        }
        return events;
    }
            
    public static ArrayList<RegisterableNpc> getNpcs() {
        ArrayList<RegisterableNpc> npcs = new ArrayList<RegisterableNpc>();
        for (AbstractRegisterable r : getAll()) {
            if (r instanceof RegisterableNpc) npcs.add((RegisterableNpc)r);
        }
        return npcs;
    }
    
    public static ArrayList<RegisterableDialog> getDialogs() {
        ArrayList<RegisterableDialog> dialogs = new ArrayList<RegisterableDialog>();
        for (AbstractRegisterable r : getAll()) {
            if (r instanceof RegisterableDialog) dialogs.add((RegisterableDialog)r);
        }
        return dialogs;
    }
    
    public static RegisterableDialog getDialogByName(String title, String category) {
        for (AbstractRegisterable registered : getAll()) {
            if (registered instanceof RegisterableDialog) {
                RegisterableDialog dialog = (RegisterableDialog)registered;
                if (dialog.getBaseOptions().getTitle().equals(title) && dialog.getBaseOptions().getCategory().equals(category)) return dialog;
            }
        }
        return null;
    }
    
    public static RegisterableNpc getNpcByName(String name, String title) {
        for (AbstractRegisterable registered : getAll()) {
            if (registered instanceof RegisterableNpc) {
                RegisterableNpc npc = (RegisterableNpc)registered;
                if (npc.getBaseOptions().getName().equals(name) && npc.getBaseOptions().getTitle().equals(title)) return npc;
            }
        }
        return null;
    }
    
    public static GameEvent getEventByName(String name) {
        for (AbstractRegisterable registered : getAll()) {
            if (registered instanceof GameEvent) {
                GameEvent event = (GameEvent)registered;
                if (event.getIdentifier().equals(name)) return event;
            }
        }
        return null;
    }
    
    public static RegisterableQuest getQuestByName(String title, String category) {
        for (AbstractRegisterable registered : getAll()) {
            if (registered instanceof RegisterableQuest) {
                RegisterableQuest quest = (RegisterableQuest)registered;
                if (quest.getBaseOptions().getTitle().equals(title) && quest.getBaseOptions().getQuestChain().equals(category)) return quest;
            }
        }
        return null;
    }
    
    public static void loadDialogs() {
        add(new LetsGetStarted());
        add(new WelcomeToTheSGC());
    }

    public static void loadNpcFactions() {
        add(new Goauld());
        add(new StargateCommand());
    }

    public static void loadNpcs() {
        add(new GeneralHammond());
        add(new StargateCommandSoldier());
        add(new GoauldJaffaSoldier());
    }

    public static void loadGameEvents() {
        add(new RandomSpawnContestedWorlds());
    }

    public static void loadQuests() {

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest.options;

import com.mmocore.constants.QuestType;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.location.RegisterableLocation;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class QuestObjectiveOptions {
    
    private QuestType type = QuestType.Assassination;
    private ArrayList<RegisterableDialog> dialogObjectives = new ArrayList<RegisterableDialog>();
    private ArrayList<RegisterableLocation> locationObjectives = new ArrayList<RegisterableLocation>();
    private HashMap<Integer, RegisterableNpc> killObjectives = new HashMap<Integer, RegisterableNpc>();
    private ArrayList<NpcItem> itemObjectives = new ArrayList<NpcItem>();
    private boolean exactMatchForItems = false;
    private boolean takeItemsForItemQuest = false;
    
    public QuestType getType() {
        return this.type;
    }
    
    private void resetObjectives() {
        this.itemObjectives = new ArrayList<NpcItem>();
        this.killObjectives = new HashMap<Integer, RegisterableNpc>();
        this.locationObjectives = new ArrayList<RegisterableLocation>();
        this.dialogObjectives = new ArrayList<RegisterableDialog>();
        this.exactMatchForItems = false;
        this.takeItemsForItemQuest = false;
    }
    
    public boolean requireExactItem() {
        return this.exactMatchForItems;
    }
    
    public boolean takeItems() {
        return this.takeItemsForItemQuest;
    }
    
    public void setOrUpdateQuestTypeItem(ArrayList<NpcItem> objectives, boolean takeItems, boolean exactMatch) {
        this.resetObjectives();
        this.itemObjectives = objectives;
        this.type = QuestType.ItemRetrieval;
        this.takeItemsForItemQuest = takeItems;
        this.exactMatchForItems = exactMatch;
    }
    
    public void setOrUpdateQuestTypeConversation(ArrayList<RegisterableDialog> objectives) {        
        this.resetObjectives();
        this.dialogObjectives = objectives;
        this.type = QuestType.Conversation;
    }
    
    public void setOrUpdateQuestTypeLocation(ArrayList<RegisterableLocation> objectives) {
        this.resetObjectives();
        this.locationObjectives = objectives;
        this.type = QuestType.Location;
    }
    
    public void setOrUpdateQuestTypeKill(HashMap<Integer, RegisterableNpc> objectives) {
        this.resetObjectives();
        this.killObjectives = objectives;
        this.type = QuestType.Assassination;
    }
    
    public boolean itemObjectiveTakesItems() {
        return this.takeItemsForItemQuest;
    }
    
    public boolean itemObjectiveRequiresExactItem() {
        return this.exactMatchForItems;
    }
    
    public HashMap<Integer, RegisterableNpc> getKillObjectives() {
        return this.killObjectives;
    }
    
    public ArrayList<NpcItem> getItemObjectives() {
        return this.itemObjectives;
    }
    
    public ArrayList<RegisterableLocation> getLocationObjectives() {
        return this.locationObjectives;
    }
    
    public ArrayList<RegisterableDialog> getConversationObjectives() {
        return this.dialogObjectives;
    }
}

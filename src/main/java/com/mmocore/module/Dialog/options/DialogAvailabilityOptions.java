/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog.options;

import com.mmocore.constants.DialogAvailability;
import com.mmocore.constants.QuestAvailability;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Quest.RegisterableQuest;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class DialogAvailabilityOptions {
    
    private HashMap<RegisterableDialog, DialogAvailability> dialogs = new HashMap<RegisterableDialog, DialogAvailability>();
    private HashMap<RegisterableQuest, QuestAvailability> quests = new HashMap<RegisterableQuest, QuestAvailability>();
    private int availableLevel = 0;
    private boolean availableDay = true;
    private boolean availableNight = true;
    
    public void setAvailableLevel(int level) {
        this.availableLevel = level;
    }
    
    public int getAvailableLevel() {
        return this.availableLevel;
    }
    
    public void setAvailableNight(boolean value) {
        this.availableNight = value;
    }
    
    public boolean getAvailableNight() {
        return this.availableNight;
    }
    
    public void setAvailableDay(boolean value) {
        this.availableDay = value;
    }
    
    public boolean getAvailableDay() {
        return this.availableDay;
    }
    
    public void clearQuestAvailability() {
        this.quests = new HashMap<RegisterableQuest, QuestAvailability>();
    }
    
    public void setQuestAvailability(HashMap<RegisterableQuest, QuestAvailability> questAvailability) {
        if (questAvailability.size() > 4) return;
        this.quests = questAvailability;
    }
    
    public HashMap<RegisterableQuest, QuestAvailability> getQuestAvailability() {
        return this.quests;
    }
    
    public void clearDialogAvailability() {
        this.dialogs = new HashMap<RegisterableDialog, DialogAvailability>();
    }
    
    public void setDialogAvailability(HashMap<RegisterableDialog, DialogAvailability> DialogAvailability) {
        if (DialogAvailability.size() > 4) return;
        this.dialogs = DialogAvailability;
    }
    
    public HashMap<RegisterableDialog, DialogAvailability> getDialogAvailability() {
        return this.dialogs;
    }
}

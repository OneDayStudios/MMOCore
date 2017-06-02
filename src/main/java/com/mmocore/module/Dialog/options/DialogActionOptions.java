/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog.options;

import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcFactionValue;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;

/**
 *
 * @author draks
 */
public class DialogActionOptions {
    
    private NpcFactionValue factionOneAction;
    private NpcFactionValue factionTwoAction;    
    private String command = "No Command";
    private RegisterableQuest quest = null;
    
    public void setQuest(RegisterableQuest quest) {
        this.quest = quest;
    }
    
    public RegisterableQuest getQuest() {
        return this.quest;
    }
    
    public void setCommandAction(String command) {
        this.command = command;
    }
    
    public String getCommandAction() {
        return this.command;
    }
    
    public NpcFactionValue getPrimaryFactionAction() {
        return this.factionOneAction;
    }
    
    public NpcFactionValue getSecondaryFactionAction() {
        return this.factionTwoAction;
    }
    
    public void setPrimaryFactionAction(RegisterableNpcFaction faction, AbstractScale scaledPoints, boolean isIncrease) {
        this.factionOneAction = new NpcFactionValue(faction, scaledPoints, isIncrease);
    }
    
    public void setSecondaryFactionAction(RegisterableNpcFaction faction, AbstractScale scaledPoints, boolean isIncrease) {
        this.factionTwoAction = new NpcFactionValue(faction, scaledPoints, isIncrease);
    }
    
    public void clearPrimaryFactionAction() {
        this.factionOneAction = null;
    }    
        
    public void clearSecondaryFactionAction() {
        this.factionTwoAction = null;
    }    
}

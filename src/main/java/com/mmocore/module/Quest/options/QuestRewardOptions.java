/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest.options;

import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcFactionValue;
import com.mmocore.constants.QuestCompletionType;
import com.mmocore.constants.QuestRewardType;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class QuestRewardOptions {
    
    private QuestCompletionType completionType = QuestCompletionType.Instant;
    private RegisterableNpc completeAtNpc = null;
    private RegisterableQuest followOnQuest = null;
    private String completionText = "";
    private QuestRewardType rewardType = QuestRewardType.All;
    private String completionCommand = "NONE";
    private AbstractScale experienceReward = AbstractScale.None;
    private ArrayList<NpcItem> rewardItemStacks = new ArrayList<NpcItem>();
    private NpcFactionValue factionOneReward = null;
    private NpcFactionValue factionTwoReward = null;    
    
    public QuestRewardOptions(QuestRewardOptions options) {
        this.completeAtNpc = options.completeAtNpc;
        this.completionCommand = options.completionCommand;
        this.completionText = options.completionText;
        this.completionType = options.completionType;
        this.followOnQuest = options.followOnQuest;
        this.rewardType = options.rewardType;
        this.experienceReward = options.experienceReward;
        this.rewardItemStacks = new ArrayList<NpcItem>(options.rewardItemStacks);
        this.factionOneReward = options.factionOneReward;
        this.factionTwoReward = options.factionTwoReward;
    }
    
    public QuestRewardOptions() {
        // Nothing assigned as default.
    }
    
    public void setPrimaryFactionReward(RegisterableNpcFaction faction, AbstractScale scaledPoints, boolean isIncrease) {
        this.factionOneReward = new NpcFactionValue(faction, scaledPoints, isIncrease);
    }
    
    public void setSecondaryFactionReward(RegisterableNpcFaction faction, AbstractScale scaledPoints, boolean isIncrease) {
        this.factionTwoReward = new NpcFactionValue(faction, scaledPoints, isIncrease);
    }
    
    public void clearPrimaryFactionReward() {
        this.factionOneReward = null;
    }    
        
    public void clearSecondaryFactionReward() {
        this.factionTwoReward = null;
    }    
    
    public void setCompletionCommand(String command) {
        this.completionCommand = command;
    }
    
    public String getCompletionCommand() {
        return this.completionCommand;
    }
    
    public NpcFactionValue getPrimaryFactionReward() {
        return this.factionOneReward;
    }
    
    public NpcFactionValue getSecondaryFactionReward() {
        return this.factionTwoReward;
    }
    
    public AbstractScale getExperienceReward() {
        return this.experienceReward;
    }
    
    public void setExperienceReward(AbstractScale scaledReward) {
        this.experienceReward = scaledReward;
    }
    
    public void setCompletionTypeInstant() {
        this.completionType = QuestCompletionType.Instant;
        this.completeAtNpc = null;
    }
    
    public void setCompletionTypeNpc(RegisterableNpc npc) {
        this.completionType = QuestCompletionType.Npc;
        this.completeAtNpc = npc;
    }
    
    public QuestCompletionType getCompletionType() {
        return this.completionType;
    }
    
    public RegisterableNpc getCompletionNpc() {
        return this.completeAtNpc;
    }
    
    public RegisterableQuest getFollowOnQuest() {
        return this.followOnQuest;
    }
    
    public void clearFollowOnQuest() {
        this.followOnQuest = null;
    }
    
    public void setFollowOnQuest(RegisterableQuest quest) {
        this.followOnQuest = quest;
    }
    
    public void setCompletionText(String text) {
        this.completionText = text;
    }
    
    public String getCompletionText() {
        return this.completionText;
    }
    
    public QuestRewardType getRewardType() {
        return this.rewardType;
    }
    
    public void setRewardType(QuestRewardType type) {
        this.rewardType = type;
    }
    
    public void addRewardItem(NpcItem item) {
        if (this.rewardItemStacks.size() < 9 && !this.rewardItemStacks.contains(item)) this.rewardItemStacks.add(item);
    }
    
    public void clearRewardItems() {
        this.rewardItemStacks.clear();
    }
    
    public ArrayList<NpcItem> getRewardItems() {
        return this.rewardItemStacks;
    }
    
    public void removeRewardItem(NpcItem item) {
        if (this.rewardItemStacks.contains(item)) this.rewardItemStacks.remove(item);
    }
    
    
}

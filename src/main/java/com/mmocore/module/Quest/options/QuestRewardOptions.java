/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest.options;

import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.QuestCompletionType;
import com.mmocore.constants.QuestRewardType;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcItem;
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
    
    public QuestRewardOptions(QuestRewardOptions options) {
        this.completeAtNpc = options.completeAtNpc;
        this.completionCommand = options.completionCommand;
        this.completionText = options.completionText;
        this.completionType = options.completionType;
        this.followOnQuest = options.followOnQuest;
        this.rewardType = options.rewardType;
        this.experienceReward = options.experienceReward;
        this.rewardItemStacks = new ArrayList<NpcItem>(options.rewardItemStacks);
    }
    
    public QuestRewardOptions() {
        // Nothing assigned as default.
    }
    
    public AbstractScale getExperienceReward() {
        return this.experienceReward;
    }
    
    public void setExperienceReward(AbstractScale scaledReward) {
        this.experienceReward = scaledReward;
    }
    
    public void setQuestCompletionType(QuestCompletionType type) {
        this.completionType = type;
    }
    
    public void setCompletionNpc(RegisterableNpc npc) {
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

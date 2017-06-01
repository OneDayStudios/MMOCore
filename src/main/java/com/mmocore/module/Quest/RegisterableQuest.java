/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.QuestAPI;
import com.mmocore.constants.AbstractScale;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.QuestCompletionType;
import com.mmocore.constants.QuestRepeatType;
import com.mmocore.constants.QuestRewardType;
import com.mmocore.constants.QuestType;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Quest.options.QuestObjectiveOptions;
import com.mmocore.module.Quest.options.QuestRewardOptions;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Random;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import net.minecraft.item.*;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.quests.QuestDialog;
import noppes.npcs.quests.QuestKill;
import noppes.npcs.quests.QuestLocation;
import noppes.npcs.quests.QuestItem;
import com.mmocore.module.Quest.options.QuestBaseOptions;
import noppes.npcs.constants.EnumQuestType;

/**
 *
 * @author Drakster
 */
public final class RegisterableQuest extends AbstractRegisterable<RegisterableQuest, Integer, Quest> {
    
    private Quest actualQuest;
    
    private QuestBaseOptions baseOptions = new QuestBaseOptions();
    private QuestRewardOptions rewardOptions = new QuestRewardOptions();
    private QuestObjectiveOptions objectiveOptions = new QuestObjectiveOptions();
        
    private void pushToGame() {
        if (!MMOCore.getQuestRegistry().isRegistered(this.getID())) return;
        if (this.getBaseOptions().getQuestChain() == null ? this.actualQuest.category.title != null : !this.getBaseOptions().getQuestChain().equals(this.actualQuest.category.title)) {
            this.setQuestCategory(this.getBaseOptions().getQuestChain());
        }
        if (!this.getBaseOptions().getTitle().equals(actualQuest.title)) actualQuest.title = this.getBaseOptions().getTitle();
        if (!this.getBaseOptions().getQuestLogText().equals(this.actualQuest.logText)) actualQuest.logText = this.getBaseOptions().getQuestLogText();
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.Always)) actualQuest.repeat = EnumQuestRepeat.REPEATABLE;
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.MCDaily)) actualQuest.repeat = EnumQuestRepeat.MCDAILY;
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.MCWeekly)) actualQuest.repeat = EnumQuestRepeat.MCWEEKLY;
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.RLDaily)) actualQuest.repeat = EnumQuestRepeat.RLDAILY;
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.RLWeekly)) actualQuest.repeat = EnumQuestRepeat.RLDAILY;
        if (this.getBaseOptions().getRepeatType().equals(QuestRepeatType.Never)) actualQuest.repeat = EnumQuestRepeat.NONE;

        
        if (this.getRewardOptions().getCompletionType().equals(QuestCompletionType.Instant)) {
            actualQuest.completion = EnumQuestCompletion.Instant;
            actualQuest.completerNpc = "NONE";
        } else {
            actualQuest.completion = EnumQuestCompletion.Npc;
            if (this.getRewardOptions().getCompletionNpc() != null && !NpcAPI.exists(this.getRewardOptions().getCompletionNpc().getBaseOptions().getName(),this.getRewardOptions().getCompletionNpc().getBaseOptions().getTitle(), this.getRewardOptions().getCompletionNpc().getBaseOptions().getFaction())) {
                MMOCore.getNpcRegistry().register(this.getRewardOptions().getCompletionNpc());
                ForgeAPI.sendConsoleEntry("Registering of completion Npc for quest: " + this.getTitle() + " aka. " + this.getRewardOptions().getCompletionNpc().getBaseOptions().getName() + ": " + (NpcAPI.exists(this.getRewardOptions().getCompletionNpc().getBaseOptions().getName(),this.getRewardOptions().getCompletionNpc().getBaseOptions().getTitle(), this.getRewardOptions().getCompletionNpc().getBaseOptions().getFaction()) ? "succeeded." : "failed."), ConsoleMessageType.FINE);
            }
            actualQuest.completerNpc = this.getRewardOptions().getCompletionNpc().getBaseOptions().getName();
        }
        
        if (this.getRewardOptions().getFollowOnQuest() != null && !this.getRewardOptions().getFollowOnQuest().isRegistered()) {
            MMOCore.getQuestRegistry().register(this.getRewardOptions().getFollowOnQuest());
            ForgeAPI.sendConsoleEntry("Registering of follow-on quest for : " + this.getTitle() + " aka. " + this.getRewardOptions().getFollowOnQuest().getTitle() + ": " + (this.getRewardOptions().getFollowOnQuest().isRegistered() ? "succeeded." : "failed."), ConsoleMessageType.FINE);
        }

        if (this.getRewardOptions().getFollowOnQuest() != null) {
            this.actualQuest.nextQuestTitle = this.getRewardOptions().getFollowOnQuest().getTitle();
            this.actualQuest.nextQuestid = this.getRewardOptions().getFollowOnQuest().getID();
        } else {
            this.actualQuest.nextQuestTitle = "NONE";
            this.actualQuest.nextQuestid = -1;
        }
        
        actualQuest.completeText = this.getRewardOptions().getCompletionText();
        
        actualQuest.randomReward = (this.getRewardOptions().getRewardType().equals(QuestRewardType.Random));
        actualQuest.randomReward = (this.getRewardOptions().getRewardType().equals(QuestRewardType.All));
        
        
        actualQuest.command = this.getRewardOptions().getCompletionCommand();
        
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Absolute)) actualQuest.rewardExp = 1000;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Highest)) actualQuest.rewardExp = 750;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Higher)) actualQuest.rewardExp = 500;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.High)) actualQuest.rewardExp = 250;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Medium)) actualQuest.rewardExp = 100;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Low)) actualQuest.rewardExp = 50;
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Lower)) actualQuest.rewardExp = 25;        
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.Lowest)) actualQuest.rewardExp = 10;        
        if (this.getRewardOptions().getExperienceReward().equals(AbstractScale.None)) actualQuest.rewardExp = 0;        
        
        actualQuest.rewardItems.items.clear();
        
        for (NpcItem item : this.getRewardOptions().getRewardItems()) {
            if (!item.hasItem()) continue;
            if (actualQuest.rewardItems.items.size() > 8) continue;
            int index = actualQuest.rewardItems.items.size();
            actualQuest.rewardItems.items.put(index, item.getItem());
        }
        
        if (this.getRewardOptions().getPrimaryFactionReward() != null) {
            actualQuest.factionOptions.decreaseFactionPoints = this.getRewardOptions().getPrimaryFactionReward().isDecrease();
            actualQuest.factionOptions.factionId = this.getRewardOptions().getPrimaryFactionReward().getFaction().getID();
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Absolute)) this.actualQuest.factionOptions.factionPoints = 250;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Highest)) this.actualQuest.factionOptions.factionPoints = 100;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Higher)) this.actualQuest.factionOptions.factionPoints = 50;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.High)) this.actualQuest.factionOptions.factionPoints = 25;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Medium)) this.actualQuest.factionOptions.factionPoints = 10;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Low)) this.actualQuest.factionOptions.factionPoints = 5;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Lower)) this.actualQuest.factionOptions.factionPoints = 3;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.Lowest)) this.actualQuest.factionOptions.factionPoints = 1;
            if (this.getRewardOptions().getPrimaryFactionReward().getValue().equals(AbstractScale.None)) this.actualQuest.factionOptions.factionPoints = 0;
        } else {
            actualQuest.factionOptions.decreaseFactionPoints = false;
            actualQuest.factionOptions.factionId = -1;
            actualQuest.factionOptions.factionPoints = 0;            
        }               
        
        if (this.getRewardOptions().getSecondaryFactionReward() != null) {
            actualQuest.factionOptions.decreaseFaction2Points = this.getRewardOptions().getSecondaryFactionReward().isDecrease();
            actualQuest.factionOptions.faction2Id = this.getRewardOptions().getSecondaryFactionReward().getFaction().getID();
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Absolute)) this.actualQuest.factionOptions.faction2Points = 250;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Highest)) this.actualQuest.factionOptions.faction2Points = 100;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Higher)) this.actualQuest.factionOptions.faction2Points = 50;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.High)) this.actualQuest.factionOptions.faction2Points = 25;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Medium)) this.actualQuest.factionOptions.faction2Points = 10;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Low)) this.actualQuest.factionOptions.faction2Points = 5;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Lower)) this.actualQuest.factionOptions.faction2Points = 3;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.Lowest)) this.actualQuest.factionOptions.faction2Points = 1;
            if (this.getRewardOptions().getSecondaryFactionReward().getValue().equals(AbstractScale.None)) this.actualQuest.factionOptions.faction2Points = 0;
        } else {
            actualQuest.factionOptions.decreaseFaction2Points = false;
            actualQuest.factionOptions.faction2Id = -1;
            actualQuest.factionOptions.faction2Points = 0;            
        }       
        
        if (this.getObjectiveOptions().getType().equals(QuestType.Assassination)) {
            actualQuest.questInterface = new QuestKill();        
            QuestKill iface = (QuestKill)actualQuest.questInterface;
            iface.questId = getID();
            actualQuest.questInterface = iface;
            if (iface.targets.size() >= 3) return;
            iface.targets.clear();
            for (Integer count : this.getObjectiveOptions().getKillObjectives().keySet()) {
                iface.targets.put(this.getObjectiveOptions().getKillObjectives().get(count).getBaseOptions().getName(), count);
                actualQuest.questInterface = iface;
            }
        }
        
        if (this.getObjectiveOptions().getType().equals(QuestType.Location)) {
            actualQuest.questInterface = new QuestLocation();
            QuestLocation iface = (QuestLocation)actualQuest.questInterface;
            iface.questId = getID();
            if (this.getObjectiveOptions().getLocationObjectives().get(0) != null) {
                iface.location = this.getObjectiveOptions().getLocationObjectives().get(0).getIdentifier();
            } else {
                iface.location = "";
            }
            
            if (this.getObjectiveOptions().getLocationObjectives().get(1) != null) {
                iface.location2 = this.getObjectiveOptions().getLocationObjectives().get(1).getIdentifier();
            } else {
                iface.location2 = "";
            }
            
            if (this.getObjectiveOptions().getLocationObjectives().get(2) != null) {
                iface.location3 = this.getObjectiveOptions().getLocationObjectives().get(2).getIdentifier();
            } else {
                iface.location3 = "";
            }            
            actualQuest.questInterface = iface;
        }
        
        if (this.getObjectiveOptions().getType().equals(QuestType.ItemRetrieval)) {
            actualQuest.questInterface = new QuestItem();
            QuestItem iface = (QuestItem)actualQuest.questInterface;
            iface.items.items.clear();
            iface.ignoreNBT = !this.getObjectiveOptions().requireExactItem();
            iface.leaveItems = !this.getObjectiveOptions().takeItems();
            for (NpcItem item : this.getObjectiveOptions().getItemObjectives()) {
                iface.items.items.put(iface.items.items.size(), item.getItem());
            }
        }

        
        this.save();
    }
    
    public RegisterableQuest(String title, String chain) {
        QuestBaseOptions bOpts = this.getBaseOptions();
        bOpts.setTitle(title);
        bOpts.setQuestChain(chain);
        this.setBaseOptions(bOpts);        
    }
    
    public QuestObjectiveOptions getObjectiveOptions() {
        return this.objectiveOptions;
    }
    
    public void setObjectiveOptions(QuestObjectiveOptions options) {
        this.objectiveOptions = options;
        this.pushToGame();
    }
    
    public QuestBaseOptions getBaseOptions() {
        return this.baseOptions;
    }
    
    public void setBaseOptions(QuestBaseOptions options) {
        this.baseOptions = options;
        this.pushToGame();
    }
    
    public QuestRewardOptions getRewardOptions() {
        return this.rewardOptions;
    }
    
    public void setRewardOptions(QuestRewardOptions options) {
        this.rewardOptions = options;
        this.pushToGame();
    }
    
    public RegisterableQuest getRegisteredCopy() {
        return MMOCore.getQuestRegistry().getRegistered(this.getID());
    }
    
    public boolean isRegistered() {
        return MMOCore.getQuestRegistry().getRegistered(this.getID()) != null;
    }
    
    public void setQuestTypeDialog(String dialog1, String dialog2, String dialog3) {
        actualQuest.questInterface = new QuestDialog();
        QuestDialog iface = (QuestDialog)actualQuest.questInterface;
        iface.questId = getID();
        for (Dialog d : DialogController.instance.dialogs.values()) {
            if (d.title.equals(dialog1)) iface.dialogs.put(0, d.id);
            if (d.title.equals(dialog2)) iface.dialogs.put(1, d.id);
            if (d.title.equals(dialog3)) iface.dialogs.put(2, d.id);
        }
        if (iface.dialogs.isEmpty()) return;
        actualQuest.questInterface = iface;
    }
    
    public void setQuestTypeItem(boolean doIgnoreDamage, boolean doIgnoreNBT, boolean takeItems) {
        actualQuest.questInterface = new QuestItem();
        QuestItem iface = (QuestItem)actualQuest.questInterface;
        iface.questId = getID();
        iface.ignoreDamage = doIgnoreDamage;
        iface.ignoreNBT = doIgnoreNBT;
        iface.leaveItems = !takeItems;
        actualQuest.questInterface = iface;
    }
    
    public boolean save() {
            if (!actualQuest.category.quests.containsValue(actualQuest)) actualQuest.category.quests.put(actualQuest.id, actualQuest);
            if (actualQuest.category.quests.containsValue(actualQuest)) actualQuest.category.quests.replace(actualQuest.id, actualQuest);
            if (QuestController.instance.categories.containsKey(actualQuest.category.id)) QuestController.instance.categories.replace(actualQuest.category.id, actualQuest.category);
            if (!QuestController.instance.categories.containsKey(actualQuest.category.id)) QuestController.instance.categories.put(actualQuest.category.id, actualQuest.category);
            QuestController.instance.saveCategory(actualQuest.category);
            QuestController.instance.saveQuest(actualQuest.category.id, actualQuest);
            QuestController.instance.load();
            return true;
    }
    
    public int getID() {
        return actualQuest.id;
    }
    
    public void setID(int id) {
        actualQuest.id = id;
    }
    
    public int getRewardedExp() {
        return actualQuest.rewardExp;
    }
    
    public void setRewardedExp(int xp) {
        actualQuest.rewardExp = xp;
    }
    
    public int getVersion() {
        return actualQuest.version;
    }
    
    public void setVersion() {
        actualQuest.version = VersionCompatibility.ModRev;
    }
    
    public QuestCategory getCategory() {
        return actualQuest.category;
    }
    
    public void setQuestCategory(String categoryName) {
        
        if (QuestAPI.questCategoryExists(categoryName)) {
            actualQuest.category = QuestAPI.getQuestCategoryForTitle(categoryName);
            if (actualQuest.category.quests.containsKey(actualQuest.id)) actualQuest.category.quests.replace(actualQuest.id, actualQuest);
            if (!actualQuest.category.quests.containsKey(actualQuest.id)) actualQuest.category.quests.put(actualQuest.id, actualQuest);
        } 
        if (actualQuest.category == null) {
            QuestCategory newCategory = new QuestCategory();
            newCategory.id = QuestController.instance.categories.size();
            newCategory.title = categoryName;
            newCategory.quests.put(getID(), actualQuest);
            QuestController.instance.categories.put(newCategory.id, newCategory);
            actualQuest.category = newCategory;
        }
        
    }
    
    public QuestCategory getQuestCategory() {
        return actualQuest.category;
    }
    
    public String getTitle() {
        return actualQuest.title;
    }
    
    public void setTitle(String title) {
        actualQuest.title = title;
    }
    
    public void setType(String type) {
        if (EnumQuestType.valueOf(type) == null) return;
        actualQuest.type = EnumQuestType.valueOf(type);
    }
    
    public String getType() {
        return actualQuest.type.name();
    }
    
    public void setCompleteText(String text) {
        actualQuest.completeText = text; 
    }
    
    public String getCompleteText() {
        return actualQuest.completeText; 
    }
    
    public void setCompletionCommand(String command) {
        actualQuest.command = command;
    }
    
    public String getCompletionCommand() {
        return actualQuest.command;
    }
    
    public void setCompleterNpc(String name) {
        actualQuest.completerNpc = name;
    }
    
    public String getCompleterNpc() {
        return actualQuest.completerNpc;
    }
    
    public void setQuestLogText(String text) {
        actualQuest.logText = text;
    }
    
    public String getQuestLogText() {
        return actualQuest.logText;
    }
    
    public String getNextQuestTitle() {
        return actualQuest.nextQuestTitle;
    }
    
    public void setCompletionType(String completionType) {
        if (EnumQuestCompletion.valueOf(completionType) == null) return;
        actualQuest.completion = EnumQuestCompletion.valueOf(completionType);
    }
    
    public String getCompletionType() {
        return actualQuest.completion.name();
    }
    
    public void setQuestRepeatType(String type) {
        System.out.println("Setting repeat type: " + type);
        if (EnumQuestRepeat.valueOf(type) == null) return;
        actualQuest.repeat = EnumQuestRepeat.valueOf(type);
    }
    
    public String getQuestRepeatType() {
        return actualQuest.repeat.name();
    }
    
    public void setRandomReward(boolean value) {
        actualQuest.randomReward = value;
    }
    
    public boolean getRandomReward() {
        return actualQuest.randomReward;
    }
    
    public boolean hasItemReward(String mod, String item, int numberOf, int dmg) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
        if (stack.getMaxStackSize() < numberOf) return false;
        stack.setItemDamage(dmg);
        return actualQuest.rewardItems.items.values().contains(stack);
    }
    
    public boolean addItemReward(String mod, String item, int numberOf, int dmg) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        if (actualQuest.rewardItems.items.size() > 8) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
        if (stack.getMaxStackSize() < numberOf) return false;
        stack.setItemDamage(dmg);
        int index = actualQuest.rewardItems.items.size();
        actualQuest.rewardItems.items.put(index, stack);
        return actualQuest.rewardItems.items.get(index).equals(stack);
    }
    
    public boolean setFactionPointRewardsPrimary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        actualQuest.factionOptions.decreaseFactionPoints = (points < 0);
        actualQuest.factionOptions.factionId = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) actualQuest.factionOptions.factionPoints = points * -1;
        if (points >= 0) actualQuest.factionOptions.factionPoints = points;
        return true;
    }
    
    public boolean setFactionPointRewardsSecondary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        actualQuest.factionOptions.decreaseFaction2Points = (points < 0);
        actualQuest.factionOptions.faction2Id = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) actualQuest.factionOptions.faction2Points = points * -1;
        if (points >= 0) actualQuest.factionOptions.faction2Points = points;
        return true;
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getIdentifier() {
        return this.getID();
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialised quest: " + this.getIdentifier(), ConsoleMessageType.FINE);
        if (QuestAPI.exists(this.getBaseOptions().getTitle()) && QuestAPI.get(this.getBaseOptions().getTitle()).category.title.equals(this.getBaseOptions().getQuestChain())) {
            this.actualQuest = QuestAPI.get(this.getBaseOptions().getTitle());
        } else {
            actualQuest = new Quest();
            actualQuest.title = this.getBaseOptions().getTitle();
            setVersion();
            Random r = new Random();
            int id = 1;
            while (QuestAPI.get(id) != null) {
                id = r.nextInt(100000);
            }
            setID(id);
        }
        this.pushToGame();
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalised quest: " + this.getIdentifier(), ConsoleMessageType.FINE);
    }

    @Override
    public Quest getRegisteredObject() {
        return this.actualQuest;
    }

    public String getCategoryName() {
        return this.actualQuest.category.title;
    }
}

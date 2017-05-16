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
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.QuestBaseOptions;
import com.mmocore.constants.QuestCompletionType;
import com.mmocore.constants.QuestRewardType;
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
        
        this.save();
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
    
    public void setQuestTypeKill() {
        actualQuest.questInterface = new QuestKill();
        QuestKill iface = (QuestKill)actualQuest.questInterface;
        iface.questId = getID();
        actualQuest.questInterface = iface;
    }
    
    public void addKillTarget(String name, int number) {
        QuestKill iface = (QuestKill)actualQuest.questInterface;
        if (iface.targets.size() > 2) return;
        System.out.println("Adding kill target: " + name + " and number: " + number);
        iface.targets.put(name,number);
        actualQuest.questInterface = iface;
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
    
    public void setQuestTypeLocation(String location1, String location2, String location3) {
        actualQuest.questInterface = new QuestLocation();
        QuestLocation iface = (QuestLocation)actualQuest.questInterface;
        iface.questId = getID();
        if (!"NONE".equals(location1)) iface.location = location1;
        if (!"NONE".equals(location2)) iface.location2 = location2;
        if (!"NONE".equals(location3)) iface.location3 = location3;
        if ("NONE".equals(location1)) iface.location = "";
        if ("NONE".equals(location2)) iface.location2 = "";
        if ("NONE".equals(location3)) iface.location3 = "";
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
    
    public boolean hasItemRequired(String mod, String item, int numberOf, int dmg) {
        QuestItem iface = (QuestItem)actualQuest.questInterface;
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
        if (stack.getMaxStackSize() < numberOf) return false;
        stack.setItemDamage(dmg);
        return iface.items.items.values().contains(stack);
    }
    
    public boolean addItemRequired(String mod, String item, int numberOf, int dmg) {
        QuestItem iface = (QuestItem)actualQuest.questInterface;
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        if (iface.items.items.size() > 2) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
        if (stack.getMaxStackSize() < numberOf) return false;
        stack.setItemDamage(dmg);
        int index = iface.items.items.size();
        iface.items.items.put(index, stack);
        return iface.items.items.get(index).equals(stack);
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
        if (QuestAPI.exists(this.getBaseOptions().getTitle()) && QuestAPI.get(this.getBaseOptions().getTitle()).category.title.equals(this.getBaseOptions().getQuestChainName())) {
            this.actualQuest = QuestAPI.get(title);
            this.setID(this.actualQuest.id);
        } else {
            actualQuest = new Quest();
            actualQuest.title = title;
            setVersion();
            Random r = new Random();
            int id = 1;
            while (QuestAPI.get(id) != null) {
                id = r.nextInt(100000);
            }
            setID(id);
        }
        this.save();
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

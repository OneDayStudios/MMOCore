/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog;

import com.mmocore.api.DialogAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import java.util.Random;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumAvailabilityFaction;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumDayTime;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;

/**
 *
 * @author Drakster
 */
public final class RegisterableDialog extends AbstractRegisterable<RegisterableDialog, Integer, Dialog> {
    
    private Dialog actualDialog;
    
    public RegisterableDialog(String title, String category) {
        
        actualDialog = null;
        
        if (DialogAPI.exists(title) && DialogAPI.get(title).category.equals(DialogAPI.getCategory(category))) {
            this.actualDialog = DialogAPI.get(title);
        } else {
            actualDialog = new Dialog();
            actualDialog.title = title;
            setVersion();
            Random r = new Random();
            int id = 1;
            while (DialogAPI.get(id) != null) {
                id = r.nextInt(100000);
            }
            setID(id);
        }
    }
    
    public boolean save() {
            if (!actualDialog.category.dialogs.containsValue(actualDialog)) actualDialog.category.dialogs.put(actualDialog.id, actualDialog);
            if (actualDialog.category.dialogs.containsValue(actualDialog)) actualDialog.category.dialogs.replace(actualDialog.id, actualDialog);
            if (DialogController.instance.categories.containsKey(actualDialog.category.id)) DialogController.instance.categories.replace(actualDialog.category.id, actualDialog.category);
            if (!DialogController.instance.categories.containsKey(actualDialog.category.id)) DialogController.instance.categories.put(actualDialog.category.id, actualDialog.category);
            DialogController.instance.saveCategory(actualDialog.category);
            DialogController.instance.saveDialog(actualDialog.category.id, actualDialog);
            DialogController.instance.load();
            return true;
    }
    
    public int getID() {
        return actualDialog.id;
    }
    
    public void setID(int id) {
        actualDialog.id = id;
    }
    
    public int getVersion() {
        return actualDialog.version;
    }
    
    public void setVersion() {
        actualDialog.version = VersionCompatibility.ModRev;
    }
    
    public String getCategoryName() {
        return actualDialog.category.title;
    }
    
    public DialogCategory getCategory() {
        return this.actualDialog.category;
    }
    
    public void setDialogCategory(String categoryName) {
        if (DialogAPI.categoryExists(categoryName)) {
            actualDialog.category = DialogAPI.getCategory(categoryName);
        } 
        
        if (actualDialog.category == null) {
            DialogCategory newCategory = new DialogCategory();
            newCategory.id = DialogController.instance.categories.size();
            newCategory.title = categoryName;
            newCategory.dialogs.put(getID(), actualDialog);
            DialogController.instance.categories.put(newCategory.id, newCategory);
            actualDialog.category = newCategory;
        }
        
    }
    
    public DialogCategory getDialogCategory() {
        return actualDialog.category;
    }
    
    public boolean showsWheel() {
        return actualDialog.showWheel;
    }
    
    public void setShowsWheel(boolean val) {
        actualDialog.showWheel = val;
    }
    
    public void setCmd(String cmd) {
        actualDialog.command = cmd;
    }
    
    public String getCmd() {
        return actualDialog.command;
    }
    
    public String getSound() {
        return actualDialog.sound;
    }
    
    public void setSound(String sound) {
        actualDialog.sound = sound;
    }
    
    public void setText(String text) {
        actualDialog.text = text;
    }
    
    public String getText() {
        return actualDialog.text;
    }
    
    public void setTitle(String title) {
        actualDialog.title = title;
    }
    
    public String getTitle() {
        return actualDialog.title;
    }
    
    public boolean getEscDisabled() {
        return actualDialog.disableEsc;
    }
    
    public void setEscDisabled(boolean val) {
        actualDialog.disableEsc = val;
    }
    
    public void setQuest(String title) {
        Quest quest = null;
        for (Quest q : QuestController.instance.quests.values()) {
            if (q.title.equals(title)) quest = q;
        }
        if (quest != null) actualDialog.quest = quest.id;
        if (quest == null) actualDialog.quest = -1;
    }
    
    public int getQuest() {
        return actualDialog.quest;
    }
    
    public void setHideNPC(boolean val) {
        actualDialog.hideNPC = val;
    }
    
    public boolean getHideNPC() {
        return actualDialog.hideNPC;
    }
    
    public boolean setFactionPointRewardsPrimary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        actualDialog.factionOptions.decreaseFactionPoints = (points < 0);
        actualDialog.factionOptions.factionId = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) actualDialog.factionOptions.factionPoints = points * -1;
        if (points >= 0) actualDialog.factionOptions.factionPoints = points;
        return true;
    }
    
    public boolean setFactionPointRewardsSecondary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        actualDialog.factionOptions.decreaseFaction2Points = (points < 0);
        actualDialog.factionOptions.faction2Id = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) actualDialog.factionOptions.faction2Points = points * -1;
        if (points >= 0) actualDialog.factionOptions.faction2Points = points;
        return true;
    }
    
    public void setTimeAvailable(boolean day, boolean night) {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        if (night && day) actualDialog.availability.daytime = EnumDayTime.Always;
        if (night && !day) actualDialog.availability.daytime = EnumDayTime.Night;
        if (!night && day) actualDialog.availability.daytime = EnumDayTime.Day;
    }
    
    public void setPlayerLevelAvailable(int playerLevel) {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        actualDialog.availability.minPlayerLevel = playerLevel;
    }
    
    public void setFactionPrimaryAlways() {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        actualDialog.availability.factionAvailable = EnumAvailabilityFactionType.Always;
    }
    
    public void setFactionSecondaryAlways() {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        actualDialog.availability.faction2Available = EnumAvailabilityFactionType.Always;
    }
    
    public void setAvailableFactionPrimary(String faction1Name, boolean isOrIsnt, String relation) {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        Faction f = FactionController.getInstance().getFactionFromName(faction1Name);
        if (f == null) return;
        actualDialog.availability.factionId = f.id;
        if (!isOrIsnt) actualDialog.availability.factionAvailable = EnumAvailabilityFactionType.Is;
        if (isOrIsnt) actualDialog.availability.factionAvailable = EnumAvailabilityFactionType.IsNot;
        if (EnumAvailabilityFaction.valueOf(relation) != null) actualDialog.availability.factionStance = EnumAvailabilityFaction.valueOf(relation);
    }
    
    public void setAvailableFactionSecondary(String faction2Name, boolean isOrIsnt, String relation) {
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        Faction f = FactionController.getInstance().getFactionFromName(faction2Name);
        if (f == null) return;
        actualDialog.availability.faction2Id = f.id;
        if (!isOrIsnt) actualDialog.availability.faction2Available = EnumAvailabilityFactionType.Is;
        if (isOrIsnt) actualDialog.availability.faction2Available = EnumAvailabilityFactionType.IsNot;
        if (EnumAvailabilityFaction.valueOf(relation) != null) actualDialog.availability.faction2Stance = EnumAvailabilityFaction.valueOf(relation);
    }
    
    public void setDialogOne(String dialogTitle, String availabilitySetting) {
        Dialog d = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        actualDialog.availability.dialogId = d.id;
        if (EnumAvailabilityDialog.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.dialogAvailable = EnumAvailabilityDialog.valueOf(availabilitySetting);
    }
    
    public void setDialogTwo(String dialogTitle, String availabilitySetting) {
        Dialog d = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        actualDialog.availability.dialog2Id = d.id;
        if (EnumAvailabilityDialog.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.dialog2Available = EnumAvailabilityDialog.valueOf(availabilitySetting);
    }
    
    public void setDialogThree(String dialogTitle, String availabilitySetting) {
        Dialog d = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        actualDialog.availability.dialog3Id = d.id;
        if (EnumAvailabilityDialog.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.dialog3Available = EnumAvailabilityDialog.valueOf(availabilitySetting);
    }
    
    public void setDialogFour(String dialogTitle, String availabilitySetting) {
        Dialog d = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        actualDialog.availability.dialog4Id = d.id;
        if (EnumAvailabilityDialog.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.dialog4Available = EnumAvailabilityDialog.valueOf(availabilitySetting);
    }
    
    public void setQuestOne(String questTitle, String availabilitySetting) {
        Quest q = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Quest quest : QuestController.instance.quests.values()) {
            if (quest.title.equals(questTitle)) q = quest;
        }
        if (q == null) return;        
        actualDialog.availability.questId = q.id;
        if (EnumAvailabilityQuest.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.questAvailable = EnumAvailabilityQuest.valueOf(availabilitySetting);
    }
    
    public void setQuestTwo(String questTitle, String availabilitySetting) {
        Quest q = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Quest quest : QuestController.instance.quests.values()) {
            if (quest.title.equals(questTitle)) q = quest;
        }
        if (q == null) return;        
        actualDialog.availability.quest2Id = q.id;
        if (EnumAvailabilityQuest.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.quest2Available = EnumAvailabilityQuest.valueOf(availabilitySetting);
    }
    
    public void setQuestThree(String questTitle, String availabilitySetting) {
        Quest q = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Quest quest : QuestController.instance.quests.values()) {
            if (quest.title.equals(questTitle)) q = quest;
        }
        if (q == null) return;        
        actualDialog.availability.quest3Id = q.id;
        if (EnumAvailabilityQuest.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.quest3Available = EnumAvailabilityQuest.valueOf(availabilitySetting);
    }
    
    public void setQuestFour(String questTitle, String availabilitySetting) {
        Quest q = null;
        if (actualDialog.availability == null) actualDialog.availability = new Availability();
        for (Quest quest : QuestController.instance.quests.values()) {
            if (quest.title.equals(questTitle)) q = quest;
        }
        if (q == null) return;        
        actualDialog.availability.quest4Id = q.id;
        if (EnumAvailabilityQuest.valueOf(availabilitySetting) == null) return;
        actualDialog.availability.quest4Available = EnumAvailabilityQuest.valueOf(availabilitySetting);
    }
    
    public void addCommandDialogOption(String title, String cmd, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.title = title;
        dialogOption.optionType = EnumOptionType.CommandBlock;
        dialogOption.command = cmd;
        dialogOption.dialogId = -1;
        dialogOption.optionColor = color;
        actualDialog.options.put(position-1, dialogOption);
    }
    
    public void addDialogDialogOption(String text, String dialogTitle, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.title = text;
        dialogOption.optionType = EnumOptionType.DialogOption;
        Dialog d = null;
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        dialogOption.optionColor = color;
        dialogOption.dialogId = d.id;
        dialogOption.command = "NOCOMMAND";
        actualDialog.options.put(position-1, dialogOption);
    }
            
    
    public void addQuitDialogOption(String text, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.title = text;
        dialogOption.optionType = EnumOptionType.QuitOption;   
        dialogOption.optionColor = color;
        dialogOption.command = "NOCOMMAND";
        dialogOption.dialogId = -1;
        actualDialog.options.put(position-1, dialogOption);
    }
    
    public void addDisabledDialogOption(String text, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.title = text;
        dialogOption.optionType = EnumOptionType.Disabled;   
        dialogOption.optionColor = color;
        dialogOption.command = "NOCOMMAND";
        dialogOption.dialogId = -1;
        actualDialog.options.put(position-1, dialogOption);
    }
    
    public void addRoleDialogOption(String text, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.title = text;
        dialogOption.optionType = EnumOptionType.RoleOption;   
        dialogOption.optionColor = color;
        dialogOption.command = "NOCOMMAND";
        dialogOption.dialogId = -1;
        actualDialog.options.put(position-1, dialogOption);
    }

    @Override
    public void tick() {
        // Not required.
    }

    @Override
    public Integer getIdentifier() {
        return this.getID();
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialised dialog: " + this.getIdentifier(), ConsoleMessageType.FINE);
        this.save();
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalised dialog: " + this.getIdentifier(), ConsoleMessageType.FINE);
    }

    @Override
    public Dialog getRegisteredObject() {
        return this.actualDialog;
    }
}

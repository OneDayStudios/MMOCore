/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog;

import com.mmocore.MMOCore;
import com.mmocore.api.DialogAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.QuestAPI;
import com.mmocore.constants.AbstractScale;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DialogAvailability;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.constants.DialogType;
import com.mmocore.constants.QuestAvailability;
import com.mmocore.module.Dialog.options.DialogActionOptions;
import com.mmocore.module.Dialog.options.DialogAvailabilityOptions;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Dialog.options.DialogConversationOptions;
import com.mmocore.module.Quest.RegisterableQuest;
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
public class RegisterableDialog extends AbstractRegisterable<RegisterableDialog, Integer, Dialog> {
    
    private Dialog actualDialog;
    private int id = -1;
    private DialogBaseOptions baseOptions = new DialogBaseOptions();    
    private DialogConversationOptions conversationOptions = new DialogConversationOptions();
    private DialogActionOptions actionOptions = new DialogActionOptions();
    private DialogAvailabilityOptions availabilityOptions = new DialogAvailabilityOptions();  
    
    public RegisterableDialog(String title, String category) {
        DialogBaseOptions bOpts = this.getBaseOptions();
        bOpts.setTitle(title);
        bOpts.setCategory(category);
        this.setBaseOptions(bOpts);     
    }
    
    private DialogOption getOptionForOption(DialogConversationOption opt) {
            DialogOption dialogOption = new DialogOption();
            if (opt.getType().equals(DialogType.Disabled)) dialogOption.optionType = EnumOptionType.Disabled;
            if (opt.getType().equals(DialogType.Role)) dialogOption.optionType = EnumOptionType.RoleOption;
            if (opt.getType().equals(DialogType.Command)) dialogOption.optionType = EnumOptionType.CommandBlock;
            if (opt.getType().equals(DialogType.Dialog)) dialogOption.optionType = EnumOptionType.DialogOption;
            if (opt.getType().equals(DialogType.Quit)) dialogOption.optionType = EnumOptionType.QuitOption;
            dialogOption.title = opt.getTitle();
            dialogOption.optionColor = opt.getColor().getNumber();
            if (opt.getDialog() != null && opt.getType().equals(DialogType.Dialog)) {
                RegisterableDialog registered = DialogAPI.getRegistered(opt.getDialog().getBaseOptions().getTitle(), opt.getDialog().getBaseOptions().getCategory());
                if (registered == null) {
                    dialogOption.dialogId = -1;
                    dialogOption.optionType = EnumOptionType.QuitOption;
                } else {
                    dialogOption.dialogId = registered.getID();
                }
            } else {
                dialogOption.dialogId = -1;
            }
            dialogOption.command = opt.getCommand();
            return dialogOption;
    }
    
    public void pushToGame() {
        if (this.getID() == -1) return;
        if (actualDialog == null) actualDialog = new Dialog();
        if (this.actualDialog.id != this.getID()) this.actualDialog.id = this.getID();
        if (this.actualDialog.category == null || !this.getBaseOptions().getCategory().equals(this.actualDialog.category.title)) {
            this.setDialogCategory(this.getBaseOptions().getCategory());
        }
        actualDialog.disableEsc = this.getBaseOptions().getEscDisabled();
        actualDialog.title = this.getBaseOptions().getTitle();
        actualDialog.text = this.getBaseOptions().getText();
        actualDialog.sound = this.getBaseOptions().getSound();
        actualDialog.command = this.getActionOptions().getCommandAction();
        actualDialog.hideNPC = this.getBaseOptions().getHidesNpc();
        actualDialog.showWheel = this.getBaseOptions().getHasDialogWheel();
        if (this.getActionOptions().getQuest() != null) {
            if (!this.getActionOptions().getQuest().isRegistered()) MMOCore.getQuestRegistry().register(this.getActionOptions().getQuest());
            if (this.getActionOptions().getQuest().isRegistered()) actualDialog.quest = this.getActionOptions().getQuest().getIdentifier();
            if (!this.getActionOptions().getQuest().isRegistered()) actualDialog.quest = -1;        
        } else {
            actualDialog.quest = -1;    
        }
        actualDialog.options.clear();
        actualDialog.options.put(0, this.getOptionForOption(this.getConversationOptions().getOptionOne()));
        actualDialog.options.put(1, this.getOptionForOption(this.getConversationOptions().getOptionTwo()));
        actualDialog.options.put(2, this.getOptionForOption(this.getConversationOptions().getOptionThree()));
        actualDialog.options.put(3, this.getOptionForOption(this.getConversationOptions().getOptionFour()));
        actualDialog.options.put(4, this.getOptionForOption(this.getConversationOptions().getOptionFive()));
        actualDialog.options.put(5, this.getOptionForOption(this.getConversationOptions().getOptionSix()));
        actualDialog.availability.minPlayerLevel = this.getAvailabilityOptions().getAvailableLevel();
        if (this.getAvailabilityOptions().getAvailableDay() && !this.getAvailabilityOptions().getAvailableNight()) actualDialog.availability.daytime = EnumDayTime.Night;
        if (this.getAvailabilityOptions().getAvailableNight() && !this.getAvailabilityOptions().getAvailableDay()) actualDialog.availability.daytime = EnumDayTime.Day;
        if (this.getAvailabilityOptions().getAvailableNight() && this.getAvailabilityOptions().getAvailableDay()) actualDialog.availability.daytime = EnumDayTime.Always;        
        actualDialog.availability.questId = -1;
        actualDialog.availability.questAvailable = EnumAvailabilityQuest.Always;
        actualDialog.availability.quest2Id = -1;
        actualDialog.availability.quest2Available = EnumAvailabilityQuest.Always;
        actualDialog.availability.quest3Id = -1;
        actualDialog.availability.quest3Available = EnumAvailabilityQuest.Always;
        actualDialog.availability.quest4Id = -1;
        actualDialog.availability.quest4Available = EnumAvailabilityQuest.Always;
        actualDialog.availability.dialogId = -1;
        actualDialog.availability.dialogAvailable = EnumAvailabilityDialog.Always;
        actualDialog.availability.dialog2Id = -1;
        actualDialog.availability.dialog2Available = EnumAvailabilityDialog.Always;
        actualDialog.availability.dialog3Id = -1;
        actualDialog.availability.dialog3Available = EnumAvailabilityDialog.Always;
        actualDialog.availability.dialog4Id = -1;
        actualDialog.availability.dialog4Available = EnumAvailabilityDialog.Always;
        int count = 0;
        for (RegisterableQuest quest : this.getAvailabilityOptions().getQuestAvailability().keySet()) {
            if (count > 3) continue;
            RegisterableQuest registered = QuestAPI.getRegistered(quest.getBaseOptions().getTitle(), quest.getBaseOptions().getQuestChain());
            QuestAvailability avail = this.getAvailabilityOptions().getQuestAvailability().get(registered);
            switch (count) {
                case 0:
                    actualDialog.availability.questId = registered.getIdentifier();
                    if (avail.equals(QuestAvailability.During)) actualDialog.availability.questAvailable = EnumAvailabilityQuest.Active;
                    if (avail.equals(QuestAvailability.NotDuring)) actualDialog.availability.questAvailable = EnumAvailabilityQuest.NotActive;
                    if (avail.equals(QuestAvailability.Before)) actualDialog.availability.questAvailable = EnumAvailabilityQuest.Before;
                    if (avail.equals(QuestAvailability.After)) actualDialog.availability.questAvailable = EnumAvailabilityQuest.After;
                case 1:
                    actualDialog.availability.quest2Id = registered.getIdentifier();
                    if (avail.equals(QuestAvailability.During)) actualDialog.availability.quest2Available = EnumAvailabilityQuest.Active;
                    if (avail.equals(QuestAvailability.NotDuring)) actualDialog.availability.quest2Available = EnumAvailabilityQuest.NotActive;
                    if (avail.equals(QuestAvailability.Before)) actualDialog.availability.quest2Available = EnumAvailabilityQuest.Before;
                    if (avail.equals(QuestAvailability.After)) actualDialog.availability.quest2Available = EnumAvailabilityQuest.After;
                case 2:
                    actualDialog.availability.quest3Id = registered.getIdentifier();
                    if (avail.equals(QuestAvailability.During)) actualDialog.availability.quest3Available = EnumAvailabilityQuest.Active;
                    if (avail.equals(QuestAvailability.NotDuring)) actualDialog.availability.quest3Available = EnumAvailabilityQuest.NotActive;
                    if (avail.equals(QuestAvailability.Before)) actualDialog.availability.quest3Available = EnumAvailabilityQuest.Before;
                    if (avail.equals(QuestAvailability.After)) actualDialog.availability.quest3Available = EnumAvailabilityQuest.After;
                case 3:
                    actualDialog.availability.quest4Id = registered.getIdentifier();
                    if (avail.equals(QuestAvailability.During)) actualDialog.availability.quest4Available = EnumAvailabilityQuest.Active;
                    if (avail.equals(QuestAvailability.NotDuring)) actualDialog.availability.quest4Available = EnumAvailabilityQuest.NotActive;
                    if (avail.equals(QuestAvailability.Before)) actualDialog.availability.quest4Available = EnumAvailabilityQuest.Before;
                    if (avail.equals(QuestAvailability.After)) actualDialog.availability.quest4Available = EnumAvailabilityQuest.After;
            }
            
            count++;
        }
        count = 0;
        for (RegisterableDialog Dialog : this.getAvailabilityOptions().getDialogAvailability().keySet()) {
            if (count > 3) continue;
            if (!Dialog.isRegistered()) MMOCore.getDialogRegistry().register(Dialog);
            DialogAvailability avail = this.getAvailabilityOptions().getDialogAvailability().get(Dialog);
            switch (count) {
                case 0:
                    actualDialog.availability.dialogId = Dialog.getIdentifier();
                    if (avail.equals(DialogAvailability.Before)) actualDialog.availability.dialogAvailable = EnumAvailabilityDialog.Before;
                    if (avail.equals(DialogAvailability.After)) actualDialog.availability.dialogAvailable = EnumAvailabilityDialog.After;
                case 1:
                    actualDialog.availability.dialog2Id = Dialog.getIdentifier();
                    if (avail.equals(DialogAvailability.Before)) actualDialog.availability.dialog2Available = EnumAvailabilityDialog.Before;
                    if (avail.equals(DialogAvailability.After)) actualDialog.availability.dialog2Available = EnumAvailabilityDialog.After;
                case 2:
                    actualDialog.availability.dialog3Id = Dialog.getIdentifier();
                    if (avail.equals(DialogAvailability.Before)) actualDialog.availability.dialog3Available = EnumAvailabilityDialog.Before;
                    if (avail.equals(DialogAvailability.After)) actualDialog.availability.dialog3Available = EnumAvailabilityDialog.After;
                case 3:
                    actualDialog.availability.dialog4Id = Dialog.getIdentifier();
                    if (avail.equals(DialogAvailability.Before)) actualDialog.availability.dialog4Available = EnumAvailabilityDialog.Before;
                    if (avail.equals(DialogAvailability.After)) actualDialog.availability.dialog4Available = EnumAvailabilityDialog.After;
            }
            count++;
        }
        
        if (this.getActionOptions().getPrimaryFactionAction() != null) {
            actualDialog.factionOptions.decreaseFactionPoints = this.getActionOptions().getPrimaryFactionAction().isDecrease();
            actualDialog.factionOptions.factionId = this.getActionOptions().getPrimaryFactionAction().getFaction().getID();
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Absolute)) this.actualDialog.factionOptions.factionPoints = 250;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Highest)) this.actualDialog.factionOptions.factionPoints = 100;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Higher)) this.actualDialog.factionOptions.factionPoints = 50;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.High)) this.actualDialog.factionOptions.factionPoints = 25;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Medium)) this.actualDialog.factionOptions.factionPoints = 10;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Low)) this.actualDialog.factionOptions.factionPoints = 5;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Lower)) this.actualDialog.factionOptions.factionPoints = 3;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.Lowest)) this.actualDialog.factionOptions.factionPoints = 1;
            if (this.getActionOptions().getPrimaryFactionAction().getValue().equals(AbstractScale.None)) this.actualDialog.factionOptions.factionPoints = 0;
        } else {
            actualDialog.factionOptions.decreaseFactionPoints = false;
            actualDialog.factionOptions.factionId = -1;
            actualDialog.factionOptions.factionPoints = 0;            
        }               
        
        if (this.getActionOptions().getSecondaryFactionAction() != null) {
            actualDialog.factionOptions.decreaseFaction2Points = this.getActionOptions().getSecondaryFactionAction().isDecrease();
            actualDialog.factionOptions.faction2Id = this.getActionOptions().getSecondaryFactionAction().getFaction().getID();
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Absolute)) this.actualDialog.factionOptions.faction2Points = 250;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Highest)) this.actualDialog.factionOptions.faction2Points = 100;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Higher)) this.actualDialog.factionOptions.faction2Points = 50;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.High)) this.actualDialog.factionOptions.faction2Points = 25;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Medium)) this.actualDialog.factionOptions.faction2Points = 10;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Low)) this.actualDialog.factionOptions.faction2Points = 5;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Lower)) this.actualDialog.factionOptions.faction2Points = 3;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.Lowest)) this.actualDialog.factionOptions.faction2Points = 1;
            if (this.getActionOptions().getSecondaryFactionAction().getValue().equals(AbstractScale.None)) this.actualDialog.factionOptions.faction2Points = 0;
        } else {
            actualDialog.factionOptions.decreaseFaction2Points = false;
            actualDialog.factionOptions.faction2Id = -1;
            actualDialog.factionOptions.faction2Points = 0;            
        }       
        if (this.save()) {
            ForgeAPI.sendConsoleEntry("Successfully saved dialog: " + this.getID(), ConsoleMessageType.FINE);
        } else {
            ForgeAPI.sendConsoleEntry("Failed saving dialog: " + this.getID(), ConsoleMessageType.FINE);
        }
    }
    
    public boolean isRegistered() {
        return MMOCore.getDialogRegistry().getRegistered(this.getID()) != null;
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
    
    public void setAvailabilityOptions(DialogAvailabilityOptions options) {
        this.availabilityOptions = options;
        this.pushToGame();
    }
    
    public DialogAvailabilityOptions getAvailabilityOptions() {
        return this.availabilityOptions;
    }
    
    public DialogActionOptions getActionOptions() {
        return this.actionOptions;
    }
    
    public void setActionOptions(DialogActionOptions options) {
        this.actionOptions = options;
        this.pushToGame();
    }
    
    public void setBaseOptions(DialogBaseOptions options) {
        this.baseOptions = options;
        this.pushToGame();
    }
    
    public DialogBaseOptions getBaseOptions() {
        return this.baseOptions;
    }
    
    public DialogConversationOptions getConversationOptions() {
        return this.conversationOptions;
    }
    
    public void setConversationOptions(DialogConversationOptions conversationOptions) {
        this.conversationOptions = conversationOptions;
        this.pushToGame();
    }
    
    public int getID() {
        return id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public int getVersion() {
        return actualDialog.version;
    }
    
    public void setVersion() {
        actualDialog.version = VersionCompatibility.ModRev;
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
    
    @Override
    public void tick() {
        // Not required.
    }

    @Override
    public Integer getIdentifier() {
        return this.id;
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialised dialog: " + this.getIdentifier(), ConsoleMessageType.FINE);
        
        if (DialogAPI.exists(this.getBaseOptions().getTitle()) && DialogAPI.get(this.getBaseOptions().getTitle()).category.equals(DialogAPI.getCategory(this.getBaseOptions().getCategory()))) {
            this.actualDialog = DialogAPI.get(this.getBaseOptions().getTitle());
            setID(this.actualDialog.id);
        } else {
            actualDialog = new Dialog();
            actualDialog.title = this.getBaseOptions().getTitle();
            setVersion();
            Random r = new Random();
            int id = 1;
            while (DialogAPI.get(id) != null) {
                id = r.nextInt(100000);
            }
            setID(id);
            this.setDialogCategory(this.getBaseOptions().getCategory());
            this.pushToGame();
        }
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalised dialog: " + this.getIdentifier(), ConsoleMessageType.FINE);
        this.pushToGame();
    }

    @Override
    public Dialog getRegisteredObject() {
        return this.actualDialog;
    }
}

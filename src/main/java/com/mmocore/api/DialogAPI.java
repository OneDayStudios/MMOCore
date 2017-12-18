/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.module.data.AbstractDictionary;
import java.util.ArrayList;
import java.util.Collection;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;

/**
 *
 * @author draks
 */
public class DialogAPI extends AbstractAPI<DialogAPI> {
        
    public static boolean hasPlayerRead(RegisterablePlayer player, String dialogTitle) {
        PlayerData data = PlayerDataController.instance.getDataFromUsername(player.getName());
        for (Integer dialogId : data.dialogData.dialogsRead) {
                Dialog d = DialogController.instance.dialogs.get(dialogId);
                if (d != null && d.title.equals(dialogTitle)) return true;
        }
        return false;
    }
    
    public static boolean categoryExists(String title) {
        for (int id : DialogController.instance.categories.keySet()) {
            DialogCategory beingProcessed = DialogController.instance.categories.get(id);
            if (beingProcessed == null || beingProcessed.title == null) continue;
            if (beingProcessed.title.equals(title)) return true;
        }
        return false;
    }
    
    public static DialogCategory getCategory(String title) {
        for (int id : DialogController.instance.categories.keySet()) {
            DialogCategory beingProcessed = DialogController.instance.categories.get(id);
            if (beingProcessed.title.equals(title)) return beingProcessed;
        }
        return null;
    }
    
    public static boolean exists(String title) {
        for (Dialog d : DialogController.instance.dialogs.values()) {
            if (d.title.equals(title)) return true;
        }
        return false;
    }
    
    public static boolean exists(int id) {
        for (Dialog d : DialogController.instance.dialogs.values()) {
            if (d.id == id) return true;
        }
        return false;
    }
    
    public static Dialog get(int id) {
        for (Dialog d : DialogController.instance.dialogs.values()) {
            if (d.id == id) return d;
        }
        return null;
    }
    
    public static RegisterableDialog getRegistered(String name, String category) {
        for (RegisterableDialog d : MMOCore.getDialogRegistry().getRegistered().values()) {
            if (d.getBaseOptions().getTitle().equals(name) && d.getBaseOptions().getCategory().equals(category)) return d;
        }
        RegisterableDialog d = AbstractDictionary.getDialogByName(name, category);
        if (d != null) {
            MMOCore.getDialogRegistry().register(d);
            return getRegistered(name,category);
        }
        return null;
    }
    
    public static Collection<Dialog> getAllReadOnly() {
        return new ArrayList<Dialog>(DialogController.instance.dialogs.values());
    }
    
    public static Dialog get(String title) {
        for (Dialog d : DialogController.instance.dialogs.values()) {
            if (d.title.equals(title)) return d;
        }
        return null;
    }
}

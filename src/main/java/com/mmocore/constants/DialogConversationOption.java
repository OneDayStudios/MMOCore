/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.MMOCore;
import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogType;
import com.mmocore.module.Dialog.RegisterableDialog;

/**
 *
 * @author draks
 */
public class DialogConversationOption {
    
    private DialogType type = DialogType.Disabled;
    private DialogChatColor color = DialogChatColor.RED;    
    private RegisterableDialog dialogOption;
    private String command = "NONE";
    private String text = "No Text";
    
    private void clearOptions() {
        this.command = "NONE";
        this.color = DialogChatColor.RED;
        this.dialogOption = null;
        this.type = DialogType.Disabled;
        this.text = "No Text";
    }
    
    public RegisterableDialog getDialog() {
        return this.dialogOption;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public String getTitle() {
        return this.text;
    }
    
    public DialogChatColor getColor() {
        return this.color;
    }
    
    public DialogType getType() {
        return this.type;
    }
    
    public void setDisabledOption() {
        this.clearOptions();
    }
    
    public void setQuitOption(String text, DialogChatColor color) {
        this.clearOptions();
        this.text = text;
        this.color = color;
        this.type = DialogType.Quit;
    }
    
    public void setDialogOption(String dialogText, RegisterableDialog dialog, DialogChatColor color) {
        this.clearOptions();
        this.color = color;
        this.text = dialogText;
        this.dialogOption = dialog;
    }
    
    public void setRoleOption(String text, DialogChatColor color) {
        this.clearOptions();
        this.text = text;
        this.color = color;
        this.type = DialogType.Role;
    }
    
    public void setCommandOption(String text, DialogChatColor color, String command) {
        this.clearOptions();
        this.text = text;
        this.command = command;
        this.color = color;
        this.type = DialogType.Command;
    }
}

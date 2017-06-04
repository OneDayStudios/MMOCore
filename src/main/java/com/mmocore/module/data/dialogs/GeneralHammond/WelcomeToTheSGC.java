/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs.GeneralHammond;

import com.mmocore.api.DialogAPI;
import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Dialog.options.DialogConversationOptions;

/**
 *
 * @author draks
 */
public class WelcomeToTheSGC extends RegisterableDialog {
    
    public WelcomeToTheSGC() {
        super("Welcome to the SGC", "Tutorial");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "Welcome to Stargate Command, {player}... \n"
                + "I'm very glad to have you with us."
        );
        options.setHasDialogWheel(true);
        DialogConversationOptions opts = this.getConversationOptions();
        DialogConversationOption conversationOption = new DialogConversationOption();
        RegisterableDialog dialog = DialogAPI.getRegistered("Lets Get Started", "Tutorial");
        conversationOption.setDialogOption("How do we get started?", dialog, DialogChatColor.DARK_GREEN);
        opts.setDialogOne(conversationOption);
        conversationOption = new DialogConversationOption();
        dialog = DialogAPI.getRegistered("Where Am I", "Tutorial");
        conversationOption.setDialogOption("Where exactly am I?", dialog, DialogChatColor.WHITE);
        opts.setDialogThree(conversationOption);
        this.setConversationOptions(opts);
        this.setBaseOptions(options);
    }
    
}

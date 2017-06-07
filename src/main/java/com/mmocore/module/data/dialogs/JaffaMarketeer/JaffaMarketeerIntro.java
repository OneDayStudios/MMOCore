/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs.JaffaMarketeer;

import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Dialog.options.DialogConversationOptions;

/**
 *
 * @author draks
 */
public class JaffaMarketeerIntro extends RegisterableDialog {
    
    public JaffaMarketeerIntro() {
        super("Jaffa Marketeer Intro", "Economy");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "What do you need from the Jaffa Marketeers?\n"
        );
        DialogConversationOptions cOpts = this.getConversationOptions();
        DialogConversationOption actualOpt = new DialogConversationOption();
        actualOpt.setCommandOption("I'd like to view my listings", DialogChatColor.DARK_GREEN, "market browse @dp");
        cOpts.setDialogOne(actualOpt);
        DialogConversationOption secondOpt = new DialogConversationOption();
        secondOpt.setCommandOption("I'd like to view my market mailbox", DialogChatColor.DARK_GREEN, "market browse stock @dp");
        cOpts.setDialogOne(actualOpt);
        cOpts.setDialogTwo(secondOpt);
        this.setConversationOptions(cOpts);
        options.setHasDialogWheel(true);
        this.setBaseOptions(options);
    }
    
}

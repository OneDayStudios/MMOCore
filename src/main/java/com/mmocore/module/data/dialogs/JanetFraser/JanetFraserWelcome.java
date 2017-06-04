/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs.JanetFraser;

import com.mmocore.module.data.dialogs.GeneralHammond.*;
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
public class JanetFraserWelcome extends RegisterableDialog {
    
    public JanetFraserWelcome() {
        super("Welcome to the Infirmary", "Tutorial");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "I'm pretty busy here.. what can I do for you? Quickly? \n"
        );
        options.setHasDialogWheel(true);
        this.setBaseOptions(options);
    }
    
}

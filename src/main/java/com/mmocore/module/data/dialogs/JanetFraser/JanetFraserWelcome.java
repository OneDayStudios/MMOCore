/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs.JanetFraser;

import com.mmocore.module.data.dialogs.GeneralHammond.*;
import com.mmocore.api.DialogAPI;
import com.mmocore.api.QuestAPI;
import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.constants.QuestAvailability;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogAvailabilityOptions;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Dialog.options.DialogConversationOptions;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.data.AbstractDictionary;
import java.util.HashMap;

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
        DialogAvailabilityOptions opts = this.getAvailabilityOptions();
        HashMap<RegisterableQuest, QuestAvailability> availability = new HashMap<RegisterableQuest, QuestAvailability>();
        availability.put(QuestAPI.getRegistered("Visiting The Infirmary", "Tutorial"), QuestAvailability.After);
        availability.put(QuestAPI.getRegistered("Visiting The Infirmary", "Tutorial"), QuestAvailability.During);
        opts.setQuestAvailability(availability);
        this.setAvailabilityOptions(opts);
        this.setBaseOptions(options);
    }
    
}

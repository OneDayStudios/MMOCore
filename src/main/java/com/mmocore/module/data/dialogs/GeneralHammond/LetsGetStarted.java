/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs.GeneralHammond;

import com.mmocore.api.QuestAPI;
import com.mmocore.constants.QuestAvailability;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogActionOptions;
import com.mmocore.module.Dialog.options.DialogAvailabilityOptions;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.data.AbstractDictionary;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class LetsGetStarted extends RegisterableDialog {
    
    public LetsGetStarted() {
        super("Lets Get Started", "Tutorial");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "Very well, right to the point, I like it.\n "  
                + "Well I'm not going to beat around the bush here, we have an SG team offworld who need some assistance.\n"  
                + "First though, we will need to clear you for offworld travel.\n\n"  
                + "Please speak with Doctor Fraser on Level X to have her examine you to confirm you are fit for service." 
        );
        this.setBaseOptions(options);
        DialogAvailabilityOptions opts = this.getAvailabilityOptions();
        HashMap<RegisterableQuest, QuestAvailability> availability = new HashMap<RegisterableQuest, QuestAvailability>();
        availability.put(QuestAPI.getRegistered("Visiting The Infirmary", "Tutorial"), QuestAvailability.NotDuring);
        availability.put(QuestAPI.getRegistered("Visiting The Infirmary", "Tutorial"), QuestAvailability.Before);
        opts.setQuestAvailability(availability);
        DialogActionOptions actions = this.getActionOptions();
        actions.setQuest(QuestAPI.getRegistered("Visiting The Infirmary", "Tutorial"));
        this.setActionOptions(actions);
        this.setAvailabilityOptions(opts);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog;

import com.mmocore.MMOCore;
import com.mmocore.api.DialogAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.Dialog.options.DialogBaseOptions;
import java.util.ArrayList;
import java.util.HashMap;
import noppes.npcs.controllers.Dialog;

/**
 *
 * @author draks
 */
public class DialogRegistry extends AbstractRegistry<DialogRegistry, Integer, RegisterableDialog> {

    @Override
    public void initialise() {
        HashMap<String, String> dialogs = new HashMap<String, String>();
        for (Dialog d : DialogAPI.getAllReadOnly()) {
            dialogs.put(d.title, d.category.title);
            ForgeAPI.sendConsoleEntry("Detected existing Dialog: " + d.title + " (" + d.id + ") with category: " + d.category.title + "), queuing for initialisation.", ConsoleMessageType.FINE);
        }
        for (String dialogName : dialogs.keySet()) {
            RegisterableDialog dialog = new RegisterableDialog(dialogName, dialogs.get(dialogName));
            MMOCore.getDialogRegistry().register(dialog);
            ForgeAPI.sendConsoleEntry("Initialised existing Dialog: " + dialog.getBaseOptions().getTitle() + " (" + dialog.getID() + ") with category: " + dialog.getBaseOptions().getCategory() + ".", ConsoleMessageType.FINE);
        }    
        RegisterableDialog testing = new RegisterableDialog("Test","Testing");
        DialogBaseOptions bOpts = testing.getBaseOptions();
        bOpts.setEscDisabled(true);
        bOpts.setSound("Testing");
        bOpts.setText("This is a test!");
        bOpts.setHasDialogWheel(true);
        testing.setBaseOptions(bOpts);
        this.register(testing);
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.dialogs;

import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Dialog.options.DialogBaseOptions;

/**
 *
 * @author draks
 */
public class WhereAmI extends RegisterableDialog {
    
    public WhereAmI() {
        super("Where Am I", "Tutorial");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "Are you mad?\n"
                + "You're on level 28 of Stargate Command, under Cheyenne Mountain, Colarado.\n"
                + "Are you sure you're okay?\nThat doesnt seem like something someone should be forgetting..."
        );
        this.setBaseOptions(options);
    }
    
}

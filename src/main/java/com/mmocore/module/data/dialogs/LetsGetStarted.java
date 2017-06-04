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
public class LetsGetStarted extends RegisterableDialog {
    
    public LetsGetStarted() {
        super("Lets Get Started", "Tutorial");
        DialogBaseOptions options = this.getBaseOptions();
        options.setText(
                "Very well, right to the point, I like it.\n "
                + "Well I'm not going to beat around the bush here, we have an SG team offworld who need some assistance.\n"
                + "First though, we will need to clear you for offworld travel.\n\n"
                + "Please speak with Doctor Fraser on Level 26 to have her examine you to confirm you are fit for service."
        );
        this.setBaseOptions(options);
    }
    
}

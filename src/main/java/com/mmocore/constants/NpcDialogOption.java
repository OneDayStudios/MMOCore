/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.module.Dialog.RegisterableDialog;

/**
 *
 * @author draks
 */
public class NpcDialogOption {

    private int color;
    private String label;
    private RegisterableDialog dialog;
    
    
    public NpcDialogOption(RegisterableDialog dialog, int color, String label) {
        this.dialog = dialog;
        this.color = color;
        this.label = label;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public RegisterableDialog getDialog() {
        return this.dialog;
    }
}

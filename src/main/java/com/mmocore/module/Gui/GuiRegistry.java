/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Gui;

import com.mmocore.module.AbstractRegistry;
import com.mmocore.constants.GuiSlot;

/**
 *
 * @author draks
 */
public class GuiRegistry extends AbstractRegistry<GuiRegistry, GuiSlot, RegisterableGui> {

    @Override
    public void initialise() {
        // No code required, yet.
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

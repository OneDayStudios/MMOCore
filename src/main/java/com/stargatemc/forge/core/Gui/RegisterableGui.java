/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Gui;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.GuiSlot;

/**
 *
 * @author draks
 */
public class RegisterableGui extends AbstractRegisterable<RegisterableGui, GuiSlot> {
    
    // This is the number of MS the GUI is registered for.
    private long milliseconds = 0;
    private long startTime = 0;
    private GuiSlot slot;
    private GuiElement gui;
    
    public RegisterableGui(GuiElement gui, long lifeTimeMs) {
        this.milliseconds = lifeTimeMs;
        this.gui = gui;        
        this.slot = gui.getSlot();
    }

    public GuiSlot getSlot() {
        return this.gui.getSlot();
    }
    
    @Override
    public void tick() {
        if (this.startTime == 0) this.startTime = System.currentTimeMillis();
        this.gui.render();
        if (System.currentTimeMillis() - this.startTime > this.milliseconds) SForge.getInstance().getGuiRegistry().deregister(this.getIdentifier());
    }

    @Override
    public GuiSlot getIdentifier() {
        return this.gui.getSlot();
    }

    @Override
    public void initialise() {
        // Not required.
    }

    @Override
    public void finalise() {
        // Not required.
    }
}

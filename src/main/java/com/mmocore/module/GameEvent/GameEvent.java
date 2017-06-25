/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent;

import com.mmocore.MMOCore;
import com.mmocore.module.Galaxy.*;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Dimension.RegisterableDimension;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public abstract class GameEvent extends AbstractRegisterable<GameEvent, String, GameEvent> {
    
    private String name;
    private boolean flaggedForRemoval = false;
    
    public GameEvent(String name) {
        this.name = name;
    }
    
    @Override
    public void tick() {
        
    }
    
    public void setFlaggedForRemoval() {
        this.flaggedForRemoval = true;
    }
    
    public boolean getFlaggedForRemoval() {
        return this.flaggedForRemoval;
    }
    
    public abstract void tickForDimension(RegisterableDimension dimension);
    public abstract boolean ticksForDimension(RegisterableDimension dimension);
    public abstract void cleanup();
    
    @Override
    public String getIdentifier() {
        return this.getClass().getName();
    }
    
    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Loading GameEvent: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading GameEvent: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public GameEvent getRegisteredObject() {
        return this;
    }
    
    @Override
    public boolean canRegister() {
        for (RegisterableDimension dim : MMOCore.getDimensionRegistry().getRegistered().values()) {
            if (ticksForDimension(dim)) return true;
        }
        return false;
    }
}

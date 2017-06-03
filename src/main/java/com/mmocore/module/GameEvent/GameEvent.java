/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent;

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
    
    public GameEvent(String name) {
        this.name = name;
    }
    
    @Override
    public void tick() {
        
    }
    
    public abstract void tickForDimension(RegisterableDimension dimension);
    
    @Override
    public String getIdentifier() {
        return this.name;
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
}

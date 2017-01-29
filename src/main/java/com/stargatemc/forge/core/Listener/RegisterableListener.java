/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Listener;
import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
/**
 *
 * @author draks
 */
public class RegisterableListener extends AbstractRegisterable<RegisterableListener, String, RegisterableListener> {

    private boolean loaded;
    
    public boolean getLoaded() {
        return loaded;
    }
    
    public void setLoaded(boolean value) {
        this.loaded = value;
    }
    
    @Override
    public void tick() {
        // This Registerable does not tick.
    }

    @Override
    public String getIdentifier() {
        return this.getClass().getName();
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Loading Listener: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);           
        setLoaded(true);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Listener: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
        FMLCommonHandler.instance().bus().unregister(this);
        MinecraftForge.EVENT_BUS.unregister(this);       
        setLoaded(false);
    }

    @Override
    public RegisterableListener getRegisteredObject() {
        return this;
    }
}

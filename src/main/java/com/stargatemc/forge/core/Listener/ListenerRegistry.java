/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Listener;

import com.stargatemc.forge.core.Listener.Listeners.WorldListener;
import com.stargatemc.forge.SForge;
import com.stargatemc.forge.core.AbstractRegistry;
import com.stargatemc.forge.core.Listener.Listeners.GuiListener;
import com.stargatemc.forge.core.Listener.Listeners.PlayerListener;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

/**
 *
 * @author draks
 */

public class ListenerRegistry extends AbstractRegistry<ListenerRegistry, String, RegisterableListener> {

    @Override
    public void initialise() {
        if (FMLCommonHandler.instance().getSide().equals(Side.SERVER)) initServerListeners();
        if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) initClientListeners();
    }
    
    private void initServerListeners() {
        SForge.getInstance().getListenerRegistry().register(new WorldListener());
        SForge.getInstance().getListenerRegistry().register(new PlayerListener());
    }
    private void initClientListeners() {
        SForge.getInstance().getListenerRegistry().register(new GuiListener());
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return true;
    }
}

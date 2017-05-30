/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener;

import com.mmocore.module.Listener.Listeners.WorldListener;
import com.mmocore.MMOCore;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.Listener.Listeners.BorderListener;
import com.mmocore.module.Listener.Listeners.BreathingListener;
import com.mmocore.module.Listener.Listeners.GuiListener;
import com.mmocore.module.Listener.Listeners.PlayerListener;
import com.mmocore.module.Listener.Listeners.TransitionListener;
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
        MMOCore.getListenerRegistry().register(new WorldListener());
        MMOCore.getListenerRegistry().register(new PlayerListener());
        MMOCore.getListenerRegistry().register(new TransitionListener());
        MMOCore.getListenerRegistry().register(new BorderListener());
        MMOCore.getListenerRegistry().register(new BreathingListener());
    }
    private void initClientListeners() {
        MMOCore.getListenerRegistry().register(new GuiListener());
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

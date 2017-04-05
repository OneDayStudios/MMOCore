/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmcore.core.Listener;

import com.mmocore.core.Listener.Listeners.WorldListener;
import com.mmocore.MMOCore;
import com.mmocore.core.AbstractRegistry;
import com.mmocore.core.Listener.Listeners.GuiListener;
import com.mmocore.core.Listener.Listeners.PlayerListener;
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
        MMOCore.getInstance().getListenerRegistry().register(new WorldListener());
        MMOCore.getInstance().getListenerRegistry().register(new PlayerListener());
    }
    private void initClientListeners() {
        MMOCore.getInstance().getListenerRegistry().register(new GuiListener());
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

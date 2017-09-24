/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 *
 * @author draks
 */
public class ServerListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        for (RegisterableNpcFaction faction : MMOCore.getNpcFactionRegistry().getRegistered().values()) {
            faction.updateHostiles();
        }
    }
    
}

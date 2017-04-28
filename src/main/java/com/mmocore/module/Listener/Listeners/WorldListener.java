/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.NpcAPI;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.Npc.RegisterableNpc;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import java.util.ArrayList;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.world.World;
/**
 *
 * @author draks
 */
public class WorldListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        World w = (World)e.world;
        if (!MMOCore.getInstance().getDimensionRegistry().isRegistered(w.getWorldInfo().getWorldName())) MMOCore.getInstance().getDimensionRegistry().register(new RegisterableDimension(w.getWorldInfo().getWorldName(), DimensionType.Unknown, 2500, 0, 0, DimensionConditions.Unknown));
        RegisterableDimension dim = MMOCore.getInstance().getDimensionRegistry().getRegistered(w.getWorldInfo().getWorldName());
    }
    
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload e) {
        World w = (World)e.world;
        MMOCore.getInstance().getDimensionRegistry().deregister(w.getWorldInfo().getWorldName());
    }
    
    @SubscribeEvent
    public void onWorldTick(WorldTickEvent e) {
        World w = (World)e.world;
        RegisterableDimension dimension = MMOCore.getInstance().getDimensionRegistry().getRegistered(w.getWorldInfo().getWorldName());
        dimension.setLastTick(System.currentTimeMillis());
        MMOCore.getInstance().getNpcRegistry().tickForDimension(dimension);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.WarpDriveAPI;
import com.mmocore.constants.ConsoleMessageType;
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
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 *
 * @author draks
 */
public class WorldListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        World w = (World)e.world;
        int dimensionId = w.provider.dimensionId;
        ForgeAPI.sendConsoleEntry("Loading Dimension: " + w.provider.dimensionId, ConsoleMessageType.FINE);
        if (!MMOCore.getDimensionRegistry().isRegistered(dimensionId)) {
            RegisterableDimension dimension = new RegisterableDimension(WarpDriveAPI.getName(dimensionId), w.getWorldInfo().getWorldName(), WarpDriveAPI.getType(dimensionId), WarpDriveAPI.hasBreathableAtmosphere(dimensionId), WarpDriveAPI.getBorderX(dimensionId), WarpDriveAPI.getBorderZ(dimensionId), WarpDriveAPI.getPosInParentX(dimensionId), WarpDriveAPI.getPosInParentZ(dimensionId), WarpDriveAPI.getSpawnX(dimensionId), WarpDriveAPI.getSpawnZ(dimensionId), WarpDriveAPI.getConditions(dimensionId), dimensionId, WarpDriveAPI.getParentId(dimensionId));
            dimension.setIsLoaded(true);
            MMOCore.getDimensionRegistry().register(dimension);    
            ForgeAPI.sendConsoleEntry("Registering Dimension: " + w.provider.dimensionId, ConsoleMessageType.FINE);
        } else {
            MMOCore.getDimensionRegistry().getRegistered(dimensionId).setIsLoaded(true);
            ForgeAPI.sendConsoleEntry("Activating Dimension: " + w.provider.dimensionId, ConsoleMessageType.FINE);
        }
    }
    
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload e) {
        World w = (World)e.world;
        if (MMOCore.getDimensionRegistry().isRegistered(w.provider.dimensionId)) {
            RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId);
            MMOCore.getNpcRegistry().cleanup(dimension, true);
            MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId).setIsLoaded(false);
            ForgeAPI.sendConsoleEntry("Deactivating Dimension: " + w.provider.dimensionId, ConsoleMessageType.FINE);
        }
    }
    
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.START)) return;
        World w = (World)e.world;
        if (MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId).getIsLoaded()) {
            RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId);
            dimension.setLastTick(System.currentTimeMillis());
            MMOCore.getNpcRegistry().tickForDimension(dimension);
            MMOCore.getGameEventRegistry().tickForDimension(dimension);
        } else {
            ForgeAPI.sendConsoleEntry("Ticking deactivated dimension Dimension: " + w.provider.dimensionId, ConsoleMessageType.FINE);
            MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId).setIsLoaded(true);
        }
    }
    
}

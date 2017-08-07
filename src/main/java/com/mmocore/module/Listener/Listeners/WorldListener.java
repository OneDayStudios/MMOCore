/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.AdvancedRocketryAPI;
import com.mmocore.api.DictionaryAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
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
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class WorldListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        World w = (World)e.world;
        int dimensionId = w.provider.dimensionId;
        
        if (!MMOCore.getDimensionRegistry().isRegistered(dimensionId)) {
            RegisterableDimension dimension = new RegisterableDimension(
                    AdvancedRocketryAPI.getName(dimensionId), 
                    w.getWorldInfo().getWorldName(), 
                    (AdvancedRocketryAPI.getCelestialForDimId(dimensionId) != null ? DimensionType.Planet : DimensionType.StarSystem), 
                    AdvancedRocketryAPI.hasBreathableAtmosphere(dimensionId), 
                    AdvancedRocketryAPI.getPlanetPosition(dimensionId).get("x"), 
                    AdvancedRocketryAPI.getPlanetPosition(dimensionId).get("z"), 
                    AdvancedRocketryAPI.getBorder(dimensionId), dimensionId);
                    MMOCore.getDimensionRegistry().register(dimension);
                    RegisterableDimension registered = MMOCore.getDimensionRegistry().getRegistered(dimensionId);
                    if (registered != null) {
                        // Now loading Factions etc on overworld load.
                        if (registered.getIdentifier() == 0) {                            
                            DictionaryAPI.loadNpcFactions();
                            DictionaryAPI.loadDialogs();
                            DictionaryAPI.loadQuests();
                        }
                        ForgeAPI.sendConsoleEntry("Registering for dimension: " + dimension.getName(), ConsoleMessageType.FINE);
                        DictionaryAPI.loadNpcs(registered);
                        DictionaryAPI.loadGameEvents(registered);
                    } else {
                        ForgeAPI.sendConsoleEntry("Error registering for dimension: " + dimension.getName(), ConsoleMessageType.FINE);
                    }
        }
    }
    
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload e) {
        World w = (World)e.world;
        RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId);
        MMOCore.getNpcRegistry().cleanup(dimension, true);
        MMOCore.getDimensionRegistry().deregister(dimension.getIdentifier());
    }
    
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.START)) return;
        World w = (World)e.world;
        RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId);
        dimension.setLastTick(System.currentTimeMillis());
        if (dimension.getType().equals(DimensionType.Planet)) {
            HashMap<String,Double> position = AdvancedRocketryAPI.getPlanetPosition(w.provider.dimensionId);
            dimension.setPosX(position.get("x"));
            dimension.setPosZ(position.get("z"));
        }
        MMOCore.getNpcRegistry().tickForDimension(dimension);
        MMOCore.getGameEventRegistry().tickForDimension(dimension);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.RandomSpawnMode;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.GameEvent.events.options.RandomSpawnEventOptions;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author draks
 */
public class RandomSpawnEvent extends GameEvent {
    
    private ArrayList<RegisterableNpc> storedNpcs = new ArrayList<RegisterableNpc>();
    private RandomSpawnEventOptions randomOptions = new RandomSpawnEventOptions();
    
    public RandomSpawnEvent(String name, ArrayList<RegisterableNpc> npcs) {
        super(name);
        this.storedNpcs = npcs;
    }
    
    public boolean shouldSpawn(uPosition position) {
        ForgeAPI.sendConsoleEntry("Checking shouldSpawn", ConsoleMessageType.FINE);
        if (!getOptions().spawnEnabled()) return false;        
        ForgeAPI.sendConsoleEntry("Spawn Enabled!", ConsoleMessageType.FINE);
        if (!getOptions().chancePassed()) return false;
        ForgeAPI.sendConsoleEntry("Chance Passed", ConsoleMessageType.FINE);
        if (!getOptions().getSpawnGalaxies().isEmpty() && !getOptions().getSpawnGalaxies().contains(position.getGalaxy())) return false;
        ForgeAPI.sendConsoleEntry("Galaxy check passed", ConsoleMessageType.FINE);
        if (!getOptions().getSpawnDimensions().isEmpty() && !getOptions().getSpawnDimensions().contains(position.getDimension())) return false;
        ForgeAPI.sendConsoleEntry("Dimension Check Passed", ConsoleMessageType.FINE);
        if (!getOptions().getSpawnFactions().isEmpty() && !getOptions().getSpawnFactions().contains(position.getDimension().getFaction())) return false;
        ForgeAPI.sendConsoleEntry("Faction Check passed", ConsoleMessageType.FINE);
        if (getOptions().getDimensionDensity() < NpcAPI.getAllReadOnlyCreatedBy(position.getDimension(), this.getClass()).size()) return false;
        ForgeAPI.sendConsoleEntry("DimensionDensity Passed", ConsoleMessageType.FINE);
        if (getOptions().getGalaxyDensity() < NpcAPI.getAllReadOnlyCreatedBy(position.getGalaxy(), this.getClass()).size()) return false;
        ForgeAPI.sendConsoleEntry("GalaxyDensity Passed", ConsoleMessageType.FINE);
        if (!getOptions().getSpawnsOnContestedWorlds() && position.getDimension().getFaction() == null) return false;
        ForgeAPI.sendConsoleEntry("ContestedPassed", ConsoleMessageType.FINE);
        if (!getOptions().getSpawnFactions().isEmpty() && position.getDimension().getFaction() != null && !getOptions().getSpawnFactions().contains(position.getDimension().getFaction())) return false;
        ForgeAPI.sendConsoleEntry("All passed", ConsoleMessageType.FINE);
        return true;
    }
    
    public boolean spawn(uPosition position) {
        if (!this.shouldSpawn(position)) return false;            
        uPosition origSpawnPos = UniverseAPI.getRandomNearbyPosition(position, 64, 128);
        if (getOptions().getMode().equals(RandomSpawnMode.SingleFromGroup)) {
            if (origSpawnPos == null) {
                ForgeAPI.sendConsoleEntry("Failed to locate spawn location!", ConsoleMessageType.FINE);
                return false;
            }
            RegisterableNpc toSpawn = getRandomNpc();
            toSpawn.setCreator(this.getClass().getName());
            RegisterableNpc actualSpawner = NpcAPI.simpleClone(toSpawn, NpcSpawnMethod.Random, origSpawnPos);
            MMOCore.getNpcRegistry().register(actualSpawner);
            return true;
        } else {
            uPosition actualSpawnPos = UniverseAPI.getRandomNearbyPosition(origSpawnPos, getOptions().getMinSpawnSpread(), getOptions().getMaxSpawnSpread());
            uPosition prevSpawnPos = actualSpawnPos;
            for (RegisterableNpc npc : storedNpcs) {                
                actualSpawnPos = UniverseAPI.getRandomNearbyPosition(prevSpawnPos, getOptions().getMinSpawnSpread(), getOptions().getMaxSpawnSpread());
                if (actualSpawnPos == null) {
                    ForgeAPI.sendConsoleEntry("Failed to locate spawn location!", ConsoleMessageType.FINE);
                    continue;
                }
                RegisterableNpc toSpawn = NpcAPI.simpleClone(npc, NpcSpawnMethod.Random, actualSpawnPos);
                toSpawn.setCreator(this.getClass().getName());
                MMOCore.getNpcRegistry().register(toSpawn);
                prevSpawnPos = actualSpawnPos;
            }
            return true;
        }
    }
    
    public void addNpc(RegisterableNpc npc) {
        if (!this.storedNpcs.contains(npc)) this.storedNpcs.add(npc);
    }
    
    public void removeNpc(RegisterableNpc npc) {
        if (this.storedNpcs.contains(npc)) this.storedNpcs.remove(npc);
    }
    
    public RandomSpawnEventOptions getOptions() {
        return this.randomOptions;
    }
    
    private RegisterableNpc getRandomNpc() {
        Random r = new Random();
        RegisterableNpc npc = NpcAPI.clone(storedNpcs.get(r.nextInt(storedNpcs.size())));
        return npc;
    }
    
    public void setOptions(RandomSpawnEventOptions options) {
        this.randomOptions = options;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        for (RegisterablePlayer p : PlayerAPI.getForDimension(dimension)) {
            spawn(p.getPosition());
        }
    }
    
}

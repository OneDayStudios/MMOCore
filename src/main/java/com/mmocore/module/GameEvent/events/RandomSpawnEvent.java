/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
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
        if (!getOptions().spawnEnabled()) return false;        
        if (!getOptions().chancePassed()) return false;
        if (!getOptions().getSpawnGalaxies().isEmpty() && !getOptions().getSpawnGalaxies().contains(position.getGalaxy())) return false;
        if (!getOptions().getSpawnDimensions().isEmpty() && !getOptions().getSpawnDimensions().contains(position.getDimension()) && !getOptions().getSpawnGalaxies().contains(position.getGalaxy())) return false;
        if (!getOptions().getSpawnFactions().isEmpty() && !getOptions().getSpawnFactions().contains(position.getDimension().getFaction())) return false;
        if (getOptions().getDimensionDensity() >= NpcAPI.getAllReadOnlyCreatedBy(position.getDimension(), this.getClass()).size()) return false;
        if (getOptions().getGalaxyDensity() >= NpcAPI.getAllReadOnlyCreatedBy(position.getGalaxy(), this.getClass()).size()) return false;
        if (!getOptions().getSpawnsOnContestedWorlds() && position.getDimension().getFaction() == null) return false;
        if (!getOptions().getSpawnFactions().isEmpty() && position.getDimension().getFaction() != null && !getOptions().getSpawnFactions().contains(position.getDimension().getFaction())) return false;
        return true;
    }
    
    public boolean spawn(uPosition position) {
        if (!this.shouldSpawn(position)) return false;            
        uPosition origSpawnPos = UniverseAPI.getRandomNearbyPosition(position, 64, 128);
        if (getOptions().getMode().equals(RandomSpawnMode.SingleFromGroup)) {
            RegisterableNpc toSpawn = getRandomNpc();
            toSpawn.setCreator(this.getClass().getName());
            NpcAPI.simpleClone(toSpawn, NpcSpawnMethod.Random, origSpawnPos);
            MMOCore.getNpcRegistry().register(toSpawn);
            return true;
        } else {
            uPosition actualSpawnPos = UniverseAPI.getRandomNearbyPosition(origSpawnPos, getOptions().getMinSpawnSpread(), getOptions().getMaxSpawnSpread());
            uPosition prevSpawnPos = actualSpawnPos;
            for (RegisterableNpc npc : storedNpcs) {                
                actualSpawnPos = UniverseAPI.getRandomNearbyPosition(prevSpawnPos, getOptions().getMinSpawnSpread(), getOptions().getMaxSpawnSpread());
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
        RegisterableNpc npc = storedNpcs.get(r.nextInt(storedNpcs.size()));
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

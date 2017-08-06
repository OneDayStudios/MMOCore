/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events.options;

import com.mmocore.api.ForgeAPI;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.RandomSpawnMode;
import com.mmocore.constants.uPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author draks
 */
public class SpawnEventOptions {
    
    // This field is responsible for globally enabling or disabling spawning via this NpcSpawnOptions class.
    private boolean enabled = true;
    // This field is responsible for the dimensions that this NPC can spawn in. This does not need to include any dimension inside a galaxy listed above.
    private List<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();    
    // This field is responsible for governing how much gravity is required for this NPC to spawn. Default is 30% of Earth.
    private int minimumGravityToSpawn = 30;
    // This field is responsible for governing how much gravity is the limit at which this NPC will spawn. Default is 105% of Earth (Can still jump!).
    private int maximumGravityToSpawn = 105;
    // This field is responsible for governing how much atmosphere is required for this NPC to spawn. Default is player breathable.
    private int minimumAtmosphereToSpawn = 75;
    // This field is responsible for governing how much atmosphere is the limit at which this NPC will spawn. Default is player breathable.
    private int maximumAtmosphereToSpawn = 110;
    // This field is responsible for specific spawn positions that this NPC will spawn at. This will ignore all dimension, galaxy and territory restrictions above.
    private List<uPosition> positions = new ArrayList<uPosition>();
    // This field is responsible for creating a hard-cap of the number of this NPC that spawns within on a single dimension. It will cancel spawns if this limit is reached.
    private int dimensionDensity = 5;    
    // This field is responsible for creating a hard-cap of the number of this NPC that spawns within a single galaxy. It will cancel spawns if this limit is reached.
    private int galaxyDensity = 5;    
    // This field is responsible for determining if the npcs should all spawn toghether or not.
    private RandomSpawnMode spawnMode = RandomSpawnMode.SingleFromGroup;
    // This field is responsibly for controlling how closely together the NPCs spawn if they spawn together.
    private int maxSpread = 32;
    // This field is responsible for controlling the minimum distance between spawning npcs in groups.
    private int minSpread = 4;
    // This field controls the spawn chance for this NPC for all random/incursion spawns. It does not affect specific positions listed above.
    private int spawnChance = 50;
    // This field controls the quests a player must have nearby before this NPC will spawn. If the player leaves the NPC will despawn.
    private List<RegisterableQuest> quests = new ArrayList<RegisterableQuest>();
    // This field controls the blocks this NPC cannot spawn on top of.
    private List<String> blocks = new ArrayList<String>();
    // This field controls the dimensions that this NPC will spawn on based on their faction.
    private List<RegisterableNpcFaction> factions = new ArrayList<RegisterableNpcFaction>();
    // Sets whether or not this NPC will spawn on Contested worlds. This defaults to true but can be disabled if factions are defined above.
    private boolean spawnsOnContestedWorlds = true;
    // Sets if this NPC set requires an atmosphere or not.
    private boolean requiresAtmosphere = true;
    
    public void setRequiresAtmosphere(boolean value) {
        this.requiresAtmosphere = value;
    }
    
    public boolean getRequiresAtmosphere() {
        return this.requiresAtmosphere;
    }
    
    public void setAtmosphereMaximum(int pressure) {
        this.maximumAtmosphereToSpawn = pressure;
    }
    
    public void setAtmosphereMinimum(int pressure) {
        this.minimumAtmosphereToSpawn = pressure;
    }
    
    public int getAtmosphereMaximum() {
        return this.maximumAtmosphereToSpawn;
    }
    
    public int getAtmosphereMinimum() {
        return this.minimumAtmosphereToSpawn;
    }
    
    public int getMaxSpawnSpread() {
        return this.maxSpread;
    }
    
    public void setMaxSpawnSpread(int spread) {
        this.maxSpread = spread;
    }
    public int getMinSpawnSpread() {
        return this.minSpread;
    }
    
    public void setMinSpawnSpread(int spread) {
        this.minSpread = spread;
    }
    
    public RandomSpawnMode getMode() {
        return this.spawnMode;
    }
    
    public void setMode(RandomSpawnMode mode) {
        this.spawnMode = mode;
    }
    
    public boolean spawnEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<RegisterableNpcFaction> getSpawnFactions() {
        return this.factions;
    }
    
    public boolean chancePassed() {
        Random r = new Random();
        int chance = r.nextInt(100);
        if (chance <= this.getSpawnChance()) return true;
        return false;
    }
    
    public void addSpawnFaction(RegisterableNpcFaction faction) {
        if (factions.contains(faction)) ForgeAPI.sendConsoleEntry("Something attempted to add an npc faction to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!factions.contains(faction)) factions.add(faction);
    }
    
    public void setSpawnsOnContestedWorlds(boolean value) {
        spawnsOnContestedWorlds = value;
    }
    
    public boolean getSpawnsOnContestedWorlds() {
        return spawnsOnContestedWorlds;
    }
    
    public void addExcludeBlock(String nameAndMod) {
        if (ForgeAPI.blockExists(nameAndMod.split(":")[0], nameAndMod.split(":")[1])) {
            if (blocks.contains(nameAndMod)) ForgeAPI.sendConsoleEntry("Something attempted to add an exclude block to an NPC twice.", ConsoleMessageType.DEBUG);
            if (!blocks.contains(nameAndMod)) blocks.add(nameAndMod);
        } else {
            ForgeAPI.sendConsoleEntry("Something attempted to add an exclude block that does not exist: " + nameAndMod, ConsoleMessageType.DEBUG);
        }
    }
    
    public List<String> getExcludeBlocks() {
        return blocks;
    }
    
    public List<RegisterableQuest> getSpawnQuests() {
        return this.quests;
    }
    
    public void addSpawnQuest(RegisterableQuest quest) {
        if (quests.contains(quest)) ForgeAPI.sendConsoleEntry("Something attempted to add a spawn quest to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!quests.contains(quest)) quests.add(quest);
    }

    public List<RegisterableDimension> getSpawnDimensions() {
        return this.dimensions;
    }
    
    public void addSpawnDimension(RegisterableDimension dimension) {
        if (dimensions.contains(dimension)) ForgeAPI.sendConsoleEntry("Something attempted to add a spawn dimension to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!dimensions.contains(dimension)) dimensions.add(dimension);
    }
    
    public List<uPosition> getSpawnPositions() {
        return this.positions;
    }
    
    public void addSpawnPosition(uPosition pos) {
        if (positions.contains(pos)) ForgeAPI.sendConsoleEntry("Something attempted to add a spawn position to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!positions.contains(pos)) positions.add(pos);
    }
    
    public int getSpawnChance() {
        return this.spawnChance;
    }
    
    public void setSpawnChance(int chance) {
        if (chance < 1 || chance > 100) ForgeAPI.sendConsoleEntry("NPC attempted to spawn with invalid chance value: " + chance, ConsoleMessageType.FINE);
        if (chance < 1) chance = 0;
        if (chance > 100) chance = 100;
        this.spawnChance = chance;
    }
        
    public void setDimensionDensity(int density) {
        this.dimensionDensity = density;
    }
    
    public int getDimensionDensity() {
        return this.dimensionDensity;
    }
    
    public void setGalaxyDensity(int density) {
        this.galaxyDensity = density;
    }
    
    public int getGalaxyDensity() {
        return this.galaxyDensity;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc.modules;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.Dimension.RegisterableDimension;
import com.stargatemc.forge.core.Galaxy.RegisterableGalaxy;
import com.stargatemc.forge.core.NpcFaction.RegisterableNpcFaction;
import com.stargatemc.forge.core.Quest.RegisterableQuest;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import com.stargatemc.forge.core.constants.uPosition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author draks
 */
public class NpcSpawnOptions {
    
    // This field is responsible for globally enabling or disabling spawning via this NpcSpawnOptions class.
    private boolean enabled = false;
    // This field is responsible for the galaxies that this NPC can spawn in. This allows the NPC to spawn in all dimensions in that galaxy.
    private List<RegisterableGalaxy> galaxies = new ArrayList<RegisterableGalaxy>();    
    // This field is responsible for the dimensions that this NPC can spawn in. This does not need to include any dimension inside a galaxy listed above.
    private List<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();    
    // This field is responsible for filtering out so that this NPC will only spawn in specific territories. If none are listed the NPC will only spawn in unclaimed land.
    private List<String> territories = new ArrayList<String>();
    // This field is responsible for specific spawn positions that this NPC will spawn at. This will ignore all dimension, galaxy and territory restrictions above.
    private List<uPosition> positions = new ArrayList<uPosition>();
    // This field is responsible for creating a hard-cap of the number of this NPC that spawns within a 256x256 area on a single dimension. It will cancel spawns if this limit is reached.
    private int localDensity = 1;    
    // This field is responsible for creating a hard-cap of the number of this NPC that spawns within on a single dimension. It will cancel spawns if this limit is reached.
    private int dimensionDensity = 1;    
    // This field is responsible for creating a hard-cap of the number of this NPC that spawns within a single galaxy. It will cancel spawns if this limit is reached.
    private int galaxyDensity = 1;    
    // This field is responsible for controlling how many NPCs will spawn at once regardless of the location.
    private int numberOf = 1;
    // This field controls the group names this NPC will have spawn triggered for. Any NPC with the same group name listed here will spawn together (based on chance/filtering).
    private List<String> groups = new ArrayList<String>();
    // This field controls the spawn chance for this NPC for all random/incursion spawns. It does not affect specific positions listed above.
    private int spawnChance = 100;
    // This field controls the quests a player must have nearby before this NPC will spawn. If the player leaves the NPC will despawn.
    private List<RegisterableQuest> quests = new ArrayList<RegisterableQuest>();
    // This field controls the blocks this NPC cannot spawn on top of.
    private List<String> blocks = new ArrayList<String>();
    // This field controls the dimensions that this NPC will spawn on based on their faction.
    private List<RegisterableNpcFaction> factions = new ArrayList<RegisterableNpcFaction>();
    // Sets whether or not this NPC will spawn on Contested worlds. This defaults to true but can be disabled if factions are defined above.
    private boolean spawnsOnContestedWorlds = true;
    
    public boolean spawnEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<RegisterableNpcFaction> getSpawnFactions() {
        return this.factions;
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
    
    public List<String> getSpawnTerritories() {
        return this.territories;
    }
    
    public void addSpawnTerritory(String territory) {
        if (territories.contains(territory)) ForgeAPI.sendConsoleEntry("Something attempted to add a spawn territory to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!territories.contains(territory)) territories.add(territory);
    }
    
    public List<RegisterableGalaxy> getSpawnGalaxies() {
        return this.galaxies;
    }
    
    public void addSpawnGalaxy(RegisterableGalaxy Galaxy) {
        if (galaxies.contains(Galaxy)) ForgeAPI.sendConsoleEntry("Something attempted to add a spawn Galaxy to an NPC twice.", ConsoleMessageType.DEBUG);
        if (!galaxies.contains(Galaxy)) galaxies.add(Galaxy);
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
    
    public List<String> getGroups() {
        return this.groups;
    }
    
    public void addGroup(String groupName) {
        groups.add(groupName);
    }
    
    public void setNumber(int numberOf) {
        this.numberOf = numberOf;
    }
    
    public int getNumberOf() {
        return this.numberOf;
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
    
    public void setLocalDensity(int density) {
        this.localDensity = density;
    }
    
    public int getLocalDensity() {
        return this.localDensity;
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

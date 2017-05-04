/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dimension;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableDimension extends AbstractRegisterable<RegisterableDimension, String, World> {
    
    private String name;
    private DimensionType type;
    private DimensionConditions conditions;
    private long border;
    private int posX;
    private int posZ;
    private long lastTick;
    private RegisterableNpcFaction faction;
    
    public RegisterableDimension(String name, DimensionType type, long border, int posX, int posZ, DimensionConditions conditions) {
        this.type = type;        
        this.name = name;
        this.border = border;
        this.posX = posX;
        this.posZ = posZ;
        this.conditions = conditions;
    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setType(DimensionType type) {
        this.type = type;
    }
    
    public void setConditions(DimensionConditions conditions) {
        this.conditions = conditions;
    }
    
    public long getBorder() {
        return this.border;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;        
    }
    
    public void setPosZ(int posZ) {
        this.posZ = posZ;        
    }
    
    public void setRadius(int radius) {
        this.border = radius;        
    }
    
    public void setLastTick(long time) {
        this.lastTick = time;
    }
    
    public long getLastTick() {
        return this.lastTick;
    }
    @Override
    public String getIdentifier() {
        return this.name;
    }
    
    public DimensionType getType() {
        return this.type;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getZ() {
        return this.posZ;
    }
    
    public int getSpawnX() {
       try {
           return getRegisteredObject().getWorldInfo().getSpawnX();
       } catch (Exception e) {
           ForgeAPI.sendConsoleEntry("SpawnX was queried for dimension: " + this.getName() + " and threw an exception.", ConsoleMessageType.FINE);
           ForgeAPI.sendConsoleEntry("Exception details: " + e.getMessage(), ConsoleMessageType.DEBUG);
           return 0;
       }
    }
    
    public int getSpawnY() {
       try {
           return getRegisteredObject().getWorldInfo().getSpawnY();
       } catch (Exception e) {
           ForgeAPI.sendConsoleEntry("SpawnY was queried for dimension: " + this.getName() + " and threw an exception.", ConsoleMessageType.FINE);
           ForgeAPI.sendConsoleEntry("Exception details: " + e.getMessage(), ConsoleMessageType.DEBUG);
           return 0;
       }    
    }
    
    public int getSpawnZ() {
       try {
           return getRegisteredObject().getWorldInfo().getSpawnZ();
       } catch (Exception e) {
           ForgeAPI.sendConsoleEntry("SpawnZ was queried for dimension: " + this.getName() + " and threw an exception.", ConsoleMessageType.FINE);
           ForgeAPI.sendConsoleEntry("Exception details: " + e.getMessage(), ConsoleMessageType.DEBUG);
           return 0;
       }    
    }
    
    public String getDisplayName() {
        String providerName = getRegisteredObject().provider.getDimensionName();
        String actualName = getRegisteredObject().getWorldInfo().getWorldName();
        if (providerName.equals("DIM-65")) return "Space";
        if (providerName.equals("DIM-64")) return "Hyperspace";
        if (providerName.contains("End") || providerName.contains("Overworld") || providerName.contains("Nether")) return actualName;
        if (actualName.equals(providerName)) return providerName;
        if (providerName.contains("DIM") && !actualName.contains("DIM")) return actualName;
        return actualName;
    }
    
    public uPosition getPosition() {
        return new uPosition(this.posX, 0, this.posZ, this);
    }
    
    public DimensionConditions getConditions() {
        return this.conditions;
    }
    
    public void setFaction(String name) {
        this.faction = NpcFactionAPI.getRegistered(name);
    }
    
    public String getFactionName() {
        return (hasFaction() ? this.getFaction().getName() : "Contested");
    }
    
    public boolean hasFaction() {
        return this.faction != null;
    }
    
    public RegisterableNpcFaction getFaction() {
        return this.faction;
    }
    
    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Loading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public World getRegisteredObject() {
        return ForgeAPI.getForgeWorld(name);
    }
}

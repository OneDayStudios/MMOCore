/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dimension;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.api.WarpDriveAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.warpdrive.config.CelestialObjectManager;
import cr0s.warpdrive.data.CelestialObject;
import net.minecraft.world.World;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableDimension extends AbstractRegisterable<RegisterableDimension, Integer, World> {
    
    private String name;
    private DimensionType type;
    private DimensionConditions conditions;
    private int posX;
    private int posZ;
    private long lastTick;
    private RegisterableNpcFaction faction;
    private final int dimensionId;
    private int borderX;
    private int borderZ;
    private int parentId;
    
    public RegisterableDimension(String name, DimensionType type, int borderX, int borderZ, int posX, int posZ, int spawnX, int spawnZ, DimensionConditions conditions, int dimensionId, int parentId) {
        this.type = type;        
        this.name = name;
        this.borderX = borderX;
        this.borderZ = borderZ;
        this.posX = posX;
        this.posZ = posZ;
        this.conditions = conditions;
        this.dimensionId = dimensionId;
        this.parentId = parentId;
        if (this.getSpawnX() != spawnX || this.getSpawnZ() != spawnZ) ForgeAPI.sendConsoleEntry("Dimension: " + name + " has a spawn that does not match configuration, this may cause issues with players not being able to visit the whole world!", ConsoleMessageType.WARNING);
    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    public int getParentId() {
        return this.parentId;
    }
    
    private boolean isParentLoaded() {
        return MMOCore.getDimensionRegistry().getRegistered(parentId) != null;
    }

    public RegisterableDimension getParent() {
        if (!this.isParentLoaded()) return null;
        return MMOCore.getDimensionRegistry().getRegistered(getParentId());
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
    
    public long getBorderZ() {
        return this.borderZ;
    }
    
    public long getBorderX() {
        return this.borderX;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;        
    }
    
    public void setPosZ(int posZ) {
        this.posZ = posZ;        
    }
    
    public void setLastTick(long time) {
        this.lastTick = time;
    }
    
    public long getLastTick() {
        return this.lastTick;
    }
    @Override
    public Integer getIdentifier() {
        return this.dimensionId;
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
    
    public int getId() {
        return this.dimensionId;
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
    
    public int getForgeId() {
       try {
           return getRegisteredObject().provider.dimensionId;
       } catch (Exception e) {
           ForgeAPI.sendConsoleEntry("ID was queried for dimension: " + this.getName() + " and threw an exception.", ConsoleMessageType.FINE);
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
        return this.getName();
    }
    
    public uPosition getPosition() {
        return new uPosition(this.posX, 0, this.posZ, this);
    }
    
    public DimensionConditions getConditions() {
        return this.conditions;
    }
    
    public double getGravity() {
        return WarpDriveAPI.getGravity(this.dimensionId);
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

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
    private boolean hasAtmosphere = false;
    private int posX;
    private int posZ;
    private long lastTick;
    private RegisterableNpcFaction faction;
    private final int dimensionId;
    private int borderX;
    private int borderZ;
    private int parentId;
    private String displayName;
    
    public RegisterableDimension(String displayName, String name, DimensionType type, boolean hasAtmosphere, int borderX, int borderZ, int posX, int posZ, int spawnX, int spawnZ, DimensionConditions conditions, int dimensionId, int parentId) {
        this.type = type;      
        this.displayName = displayName;
        this.name = name;
        this.borderX = borderX;
        this.borderZ = borderZ;
        this.hasAtmosphere = hasAtmosphere;
        this.posX = posX;
        this.posZ = posZ;
        this.conditions = conditions;
        this.dimensionId = dimensionId;
        this.parentId = parentId;
        if (!WarpDriveAPI.isMapped(dimensionId)) {
            ForgeAPI.sendConsoleEntry("Dimension: " + name + " has a spawn configuration mismatch with WarpDrive... attempting to match it...", ConsoleMessageType.WARNING);
            try {
                getRegisteredObject().setSpawnLocation(spawnX, this.getSpawnY(), spawnZ);
                ForgeAPI.sendConsoleEntry("Spawn reconfigured for dimension: " + name + " to: " + spawnX + "," + this.getSpawnY() + "," + spawnZ + "!", ConsoleMessageType.FINE);
            } catch (Exception e) {
                ForgeAPI.sendConsoleEntry("Failed to re-set spawn for dimension: " + name + ", please adjust Warpdrive configuration!", ConsoleMessageType.SEVERE);
            }
        }
        if (this.getSpawnX() != spawnX || this.getSpawnZ() != spawnZ) ForgeAPI.sendConsoleEntry("Dimension: " + name + " has a spawn that does not match configuration (Configured X: " + spawnX + ", actual: " + this.getSpawnX() + ", Configured Z: " + spawnZ + ", actual: " + this.getSpawnZ() + ")!", ConsoleMessageType.WARNING);

    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    public int getParentId() {
        return this.parentId;
    }
    
    public boolean hasAtmopshere() {
        return this.hasAtmosphere;
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
        return getRegisteredObject().getWorldInfo().getSpawnX();
    }
    
    public int getSpawnY() {
        return getRegisteredObject().getWorldInfo().getSpawnY();
    }
    
    public int getForgeId() {
        return getRegisteredObject().provider.dimensionId;
    }
    
    public int getSpawnZ() {
        return getRegisteredObject().getWorldInfo().getSpawnZ();
    }
    
    public String getDisplayName() {
        return this.displayName;        
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
        ForgeAPI.sendConsoleEntry("Loading Dimension: " + this.getName() + "...", ConsoleMessageType.FINE);   
        ForgeAPI.sendConsoleEntry("DisplayName: " + this.getDisplayName()+ "...", ConsoleMessageType.FINE);  
        ForgeAPI.sendConsoleEntry("Conditions: " + this.getConditions(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("Type: " + this.getType(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("PosX: " + this.getX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("PosZ: " + this.getZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("BorderX: " + this.getBorderX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("BorderZ: " + this.getBorderZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnX: " + this.getSpawnX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnZ: " + this.getSpawnZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("Parent: " + this.getParentId(), ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public World getRegisteredObject() {
        return ForgeAPI.getForgeWorld(dimensionId);
    }
}

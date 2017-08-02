/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dimension;

import com.mmocore.MMOCore;
import com.mmocore.api.AdvancedRocketryAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.constants.FakeDimensionType;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.world.World;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableDimension extends AbstractRegisterable<RegisterableDimension, Integer, World> {
    
    private String name;
    private DimensionType type = DimensionType.Unknown;
    private DimensionConditions conditions = DimensionConditions.Unknown;
    private boolean hasAtmosphere = false;
    private double posX;
    private double posZ;
    private long lastTick;
    private RegisterableNpcFaction faction;
    private int dimensionId;
    private double borderX;
    private double borderZ;
    private String displayName;
    
    public RegisterableDimension(String displayName, String name, DimensionType type, boolean hasAtmosphere, double border, double posX, double posZ, int dimensionId) {
        this.type = type;      
        this.displayName = displayName;
        this.name = name;
        this.borderX = border;
        this.borderZ = border;
        this.hasAtmosphere = hasAtmosphere;
        this.posX = posX;
        this.posZ = posZ;
        this.dimensionId = dimensionId;
    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    public boolean hasAtmopshere() {
        return this.hasAtmosphere;
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
    
    public double getRadiusBorderZ() {
        return this.borderZ;
    }
    
    public double getRadiusBorderX() {
        return this.borderX;
    }
    
    public void setPosX(double posX) {
        this.posX = posX;        
    }
    
    public void setPosZ(double posZ) {
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
    
    public double getX() {
        return this.posX;
    }
    
    public double getZ() {
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
        return new uPosition(this.getX(), 0, this.getZ(), this);
    }
    
    public DimensionConditions getConditions() {
        return this.conditions;
    }
    
    public float getGravity() {
        return AdvancedRocketryAPI.getCelestialForDimId(this.dimensionId).getGravitationalMultiplier();
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
        ForgeAPI.sendConsoleEntry("BorderX: " + this.getRadiusBorderX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("BorderZ: " + this.getRadiusBorderZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnX: " + this.getSpawnX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnZ: " + this.getSpawnZ(), ConsoleMessageType.FINE);
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

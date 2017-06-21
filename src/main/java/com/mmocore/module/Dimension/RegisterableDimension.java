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
    private int posX;
    private int posZ;
    private long lastTick;
    private RegisterableNpcFaction faction;
    private int dimensionId;
    private int borderX;
    private int borderZ;
    private int parentId;
    private String displayName;
    private boolean isFake = false;
    private FakeDimensionType fakeType;
    private boolean loaded = false;
    
    public RegisterableDimension(String name, FakeDimensionType type, int borderX, int borderZ, int posX, int posZ, DimensionConditions conditions, int parentId) {
        this.isFake = true;
        this.name = name;
        this.displayName = name;
        this.fakeType = type;
        this.borderX = borderX;
        this.borderZ = borderZ;
        this.posX = posX;
        this.posZ = posZ;        
        Random random = new Random();
        this.dimensionId = random.nextInt(100000) + 5000;
        while (MMOCore.getDimensionRegistry().getRegistered(dimensionId) != null) {
            this.dimensionId = random.nextInt(100000) + 5000;
        }
        this.conditions = conditions;
        this.parentId = parentId;
    }
    
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
    
    public double getPosXInParent(double x) {        
        boolean isSubtracting = (this.getSpawnX() > x);
        double difference = 0;
        if (isSubtracting) difference = (this.getSpawnX() - x);
        if (!isSubtracting) difference = (x - this.getSpawnX());
        if (isSubtracting) return (this.getX() - difference);
        return (this.getX() + difference);
    }
    
    // .getZ() returns the coord in the parent dimension for this dimension.
    // .getSpawnZ() returns the centre of this dimension.
    // Goal is to find the equivalent position on the Z axis for the parent dimension, by saying .getZ() == .getSpawnZ() and figuring out the distance between that and Z.
    public double getPosZInParent(double z) {        
        boolean isSubtracting = (this.getSpawnZ() > z);
        double difference = 0;    
        if (isSubtracting) difference = (this.getSpawnZ() - z);
        if (!isSubtracting) difference = (z - this.getSpawnZ());
        if (isSubtracting) return (this.getZ() - difference);
        return (this.getZ() + difference);
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
    
    public long getRadiusBorderZ() {
        return this.borderZ;
    }
    
    public long getRadiusBorderX() {
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
    
    public FakeDimensionType getFakeType() {
        return this.fakeType;
    }
    
    public boolean isFake() {
        return this.isFake;
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
        if (isFake || !loaded) return 0;
        return getRegisteredObject().getWorldInfo().getSpawnX();
    }
    
    public int getSpawnY() {
        if (isFake || !loaded) return 0;
        return getRegisteredObject().getWorldInfo().getSpawnY();
    }
    
    public int getForgeId() {
        if (isFake) return dimensionId;
        return getRegisteredObject().provider.dimensionId;
    }
    
    public int getSpawnZ() {
        if (isFake || !loaded) return 0;
        return getRegisteredObject().getWorldInfo().getSpawnZ();
    }
    
    public String getDisplayName() {
        return this.displayName;        
    }
    
    public uPosition getPosition() {
        return new uPosition(this.posX, 0, this.posZ, this.getParent());
    }
    
    public DimensionConditions getConditions() {
        return this.conditions;
    }
    
    public double getGravity() {
        if (isFake) return 0;
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
        if (!isFake()) ForgeAPI.sendConsoleEntry("Type: " + this.getType(), ConsoleMessageType.FINE);
        if (isFake()) ForgeAPI.sendConsoleEntry("FakeType: " + this.getFakeType(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("PosX: " + this.getX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("PosZ: " + this.getZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("BorderX: " + this.getRadiusBorderX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("BorderZ: " + this.getRadiusBorderZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnX: " + this.getSpawnX(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("SpawnZ: " + this.getSpawnZ(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("Parent: " + this.getParentId(), ConsoleMessageType.FINE);
        ForgeAPI.sendConsoleEntry("IsFake: " + this.isFake(), ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public World getRegisteredObject() {
        return ForgeAPI.getForgeWorld(dimensionId);
    }

    public boolean getIsLoaded() {
        return this.loaded;
    }
    
    public void setIsLoaded(boolean b) {
        this.loaded = b;
    }
}

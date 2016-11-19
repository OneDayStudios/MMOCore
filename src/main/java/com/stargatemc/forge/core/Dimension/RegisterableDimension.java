/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Dimension;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.constants.uPosition;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import com.stargatemc.forge.core.constants.DimensionConditions;
import com.stargatemc.forge.core.constants.DimensionType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableDimension extends AbstractRegisterable<RegisterableDimension, String> {
    
    private String name;
    private DimensionType type;
    private DimensionConditions conditions;
    private long border;
    private int posX;
    private int posZ;
    private long lastTick;
    
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
    
    public long getBorder() {
        return this.border;
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
       return this.getForgeWorld().getWorldInfo().getSpawnX();
    }
    public int getSpawnY() {
       return this.getForgeWorld().getWorldInfo().getSpawnY();
    }
    public int getSpawnZ() {
       return this.getForgeWorld().getWorldInfo().getSpawnZ();
    }
    
    public World getForgeWorld() {
        return ForgeAPI.getForgeWorld(this.name);
    }
    
    public uPosition getPosition() {
        return new uPosition(this.posX, 0, this.posZ, this);
    }
    
    public DimensionConditions getConditions() {
        return this.conditions;
    }
    
    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Loading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Dimension: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Galaxy;

import com.stargatemc.forge.core.Dimension.*;
import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.api.UniverseAPI;
import com.stargatemc.forge.core.constants.uPosition;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import com.stargatemc.forge.core.constants.DimensionConditions;
import com.stargatemc.forge.core.constants.DimensionType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.Collection;
import net.minecraft.world.World;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableGalaxy extends AbstractRegisterable<RegisterableGalaxy, String> {
    
    private String name;
    private long border;
    private int posX;
    private int posZ;
    
    public RegisterableGalaxy(String name, long border, int posX, int posZ) {
        this.name = name;
        this.border = border;
        this.posX = posX;
        this.posZ = posZ;
    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    public long getBorder() {
        return this.border;
    }
    
    @Override
    public String getIdentifier() {
        return this.name;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getZ() {
        return this.posZ;
    }
    
    public uPosition getPosition() {
        return new uPosition(this.posX, 0, this.posZ, UniverseAPI.getSpace());
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Loading Galaxy: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Unloading Galaxy: " + this.getIdentifier() + "...", ConsoleMessageType.FINE);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmcore.core.Galaxy;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.core.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableGalaxy extends AbstractRegisterable<RegisterableGalaxy, String, RegisterableGalaxy> {
    
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

    @Override
    public RegisterableGalaxy getRegisteredObject() {
        return this;
    }
}

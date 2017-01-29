/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Stargate;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.api.UniverseAPI;
import com.stargatemc.forge.core.constants.uPosition;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import gcewing.sg.SGAddressing;
import gcewing.sg.SGBaseTE;

/**
 *
 * @author Drakster
 */
public class RegisterableStargate extends AbstractRegisterable<RegisterableStargate, String, SGBaseTE> {
    
    public String address;
    public int x;
    public int y;
    public int z;
    public String worldName;
    public boolean isHidden;
    
    public RegisterableStargate(String address, int x, int y, int z, String worldName, boolean isHidden) {
        this.address = address;
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
        this.isHidden = isHidden;
    }
    
    public uPosition getPosition() {
        return new uPosition(x, y, z, UniverseAPI.getDimension(worldName));
    }
    
    public SGBaseTE getStargate() {
        try {
            return SGAddressing.findAddressedStargate(address, ForgeAPI.getForgeWorldForName(worldName));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void tick() {
//        
    }

    @Override
    public String getIdentifier() {
        return this.address;
    }

    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialising stargate with address: " + this.address, ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalising stargate with address: " + this.address, ConsoleMessageType.FINE);
    }

    @Override
    public SGBaseTE getRegisteredObject() {
        return SGAddressing.findAddressedStargate(this.address, ForgeAPI.getForgeWorld(getPosition().getDimension().getName()));
    }
}

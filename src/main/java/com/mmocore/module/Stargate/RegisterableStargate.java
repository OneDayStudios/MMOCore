/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Stargate;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Dimension.RegisterableDimension;
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
    public RegisterableDimension dimension;
    public boolean isHidden;
    
    public RegisterableStargate(String address, int x, int y, int z, RegisterableDimension dimension, boolean isHidden) {
        this.address = address;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.isHidden = isHidden;
    }
    
    public uPosition getPosition() {
        return new uPosition(x, y, z, dimension);
    }
    
    public SGBaseTE getStargate() {
        try {
            return SGAddressing.findAddressedStargate(address, dimension.getRegisteredObject());
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
            try {
                return SGAddressing.findAddressedStargate(this.address, dimension.getRegisteredObject());
            } catch (Exception e) {
                return null;
            }
    }

    @Override
    public boolean canRegister() {
        return (this.dimension != null && MMOCore.getDimensionRegistry().isRegistered(this.dimension.getForgeId()));
    }
}

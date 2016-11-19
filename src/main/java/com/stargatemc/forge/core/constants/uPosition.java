/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.constants;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.UniverseAPI;
import com.stargatemc.forge.core.AbstractObjectCore;
import com.stargatemc.forge.core.Dimension.RegisterableDimension;
import com.stargatemc.forge.core.Galaxy.RegisterableGalaxy;
import com.stargatemc.forge.core.constants.DimensionType;

/**
 *
 * @author draks
 */
public class uPosition extends AbstractObjectCore<uPosition> {

    private int uPosX;
    private int uPosZ;
    private int dPosX;
    private int dPosZ;
    private RegisterableDimension dimension;
    private int dPosY;
    
    public uPosition(int dPosX, int dPosY, int dPosZ, RegisterableDimension dimension) {
        this.dimension = dimension;
        this.dPosX = dPosX;
        this.dPosY = dPosY;
        this.dPosZ = dPosZ;
        if (dimension.getType().equals(DimensionType.Hyperspace) || dimension.getType().equals(DimensionType.Space)) {
            this.uPosX = this.dPosX;
            this.uPosZ = this.dPosZ;
        } else {
            if (this.dimension.getSpawnX() > this.dPosX) this.uPosX = (this.dimension.getX() - (this.dimension.getSpawnX() - this.dPosX));
            if (this.dimension.getSpawnX() < this.dPosX) this.uPosX = (this.dimension.getX() + (this.dPosX - this.dimension.getSpawnX()));
            if (this.dimension.getSpawnZ() > this.dPosZ) this.uPosZ = (this.dimension.getZ() - (this.dimension.getSpawnZ() - this.dPosZ));
            if (this.dimension.getSpawnZ() < this.dPosZ) this.uPosZ = (this.dimension.getZ() + (this.dPosZ - this.dimension.getSpawnZ()));
            if (this.dPosX == this.dimension.getSpawnX()) this.uPosX = this.dimension.getSpawnX();
            if (this.dPosZ == this.dimension.getSpawnZ()) this.uPosZ = this.dimension.getSpawnZ();
        }
    }

    public boolean isInSpace() {
        return (this.dimension.getType().equals(DimensionType.Space));
    }
    
    public RegisterableDimension getDimension() {
        return this.dimension;
    }
    
    public boolean isInHyperSpace() {
        return (this.dimension.getType().equals(DimensionType.Hyperspace));
    }
    
    public boolean isInUniverse() {
        return (this.dimension != null);
    }
    public int getDPosX() {
        return this.dPosX;
    }
    public int getDPosY() {
        return this.dPosY;
    }
    public int getDPosZ() {
        return this.dPosZ;
    }
    public int getUPosX() {
        return this.uPosX;
    }
    
    public int getUPosZ() {
        return this.uPosZ;
    }
    
    public RegisterableGalaxy getGalaxy() {
        return UniverseAPI.getGalaxy(this);
    }
    
    @Override
    public void initialise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

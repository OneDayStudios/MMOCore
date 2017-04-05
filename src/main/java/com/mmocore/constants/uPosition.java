/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.MMOCore;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.AbstractObjectCore;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Galaxy.RegisterableGalaxy;
import com.mmocore.constants.DimensionType;

/**
 *
 * @author draks
 */
public class uPosition extends AbstractObjectCore<uPosition> {

    private double uPosX;
    private double uPosZ;
    private double dPosX;
    private double dPosZ;
    private RegisterableDimension dimension;
    private double dPosY;
    
    public uPosition(double dPosX, double dPosY, double dPosZ, RegisterableDimension dimension) {
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
    public double getDPosX() {
        return this.dPosX;
    }
    public double getDPosY() {
        return this.dPosY;
    }
    public double getDPosZ() {
        return this.dPosZ;
    }
    public double getUPosX() {
        return this.uPosX;
    }
    
    public double getUPosZ() {
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

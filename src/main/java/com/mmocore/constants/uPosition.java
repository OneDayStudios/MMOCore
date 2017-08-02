/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.MMOCore;
import com.mmocore.api.AdvancedRocketryAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.AbstractObjectCore;
import com.mmocore.module.Dimension.RegisterableDimension;
import zmaster587.advancedRocketry.api.stations.ISpaceObject;

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
        // If a position is in hyperspace, this is how we calculate it.
        if (!this.getDimension().getType().equals(DimensionType.StarSystem)){
            this.uPosX = dimension.getX();
            this.uPosZ = dimension.getZ();
            if (this.dPosX > dimension.getSpawnX()) {
                this.uPosX = dimension.getX() + (dPosX - dimension.getSpawnX());
            }
            if (this.dPosX < dimension.getSpawnX()) {
                this.uPosX = dimension.getX() - (dimension.getSpawnX() - dPosX);
            }
            if (this.dPosZ > dimension.getSpawnZ()) {
                this.uPosZ = dimension.getZ() + (dPosZ - dimension.getSpawnZ());
            }
            if (this.dPosZ < dimension.getSpawnZ()) {
                this.uPosZ = dimension.getZ() - (dimension.getSpawnZ() - dPosZ);
            }
        } else {
                ISpaceObject object = AdvancedRocketryAPI.getSpaceObjectForCoordinates((int)dPosX, (int)dPosZ);
                if (object.getOrbitingPlanetId() != -1) {
                    RegisterableDimension orbitingDimension = UniverseAPI.getDimension(object.getOrbitingPlanetId());
                    this.uPosX = orbitingDimension.getX();
                    this.uPosZ = orbitingDimension.getZ();
                    if (this.dPosX > dimension.getSpawnX()) {
                        this.uPosX = orbitingDimension.getX() + (dPosX - dimension.getSpawnX());
                    }
                    if (this.dPosX < dimension.getSpawnX()) {
                        this.uPosX = orbitingDimension.getX() - (dimension.getSpawnX() - dPosX);
                    }
                    if (this.dPosZ > dimension.getSpawnZ()) {
                        this.uPosZ = orbitingDimension.getZ() + (dPosZ - dimension.getSpawnZ());
                    }
                    if (this.dPosZ < dimension.getSpawnZ()) {
                        this.uPosZ = orbitingDimension.getZ() - (dimension.getSpawnZ() - dPosZ);
                    }
                } else {
                    this.uPosX = Double.POSITIVE_INFINITY;
                    this.uPosZ = Double.POSITIVE_INFINITY;
                }
        }
    }
    
    public boolean isInSpace() {
        return (this.dimension.getType().equals(DimensionType.StarSystem));
    }
    
    // This obtains the planet that the position is in orbit of, or on. Returns null otherwise.
    public RegisterableDimension getCelestialBody() {
        if (this.dimension.getType().equals(DimensionType.StarSystem)) {
            ISpaceObject object = AdvancedRocketryAPI.getSpaceObjectForCoordinates((int)dPosX, (int)dPosZ);
            if (object.getOrbitingPlanetId() != -1) {
                RegisterableDimension orbitingDimension = UniverseAPI.getDimension(object.getOrbitingPlanetId());
                if (orbitingDimension != null) return orbitingDimension;
                return null;
            } else {
                return null;
            }
        } else {
            return this.dimension;
        }
    }
    
    public RegisterableDimension getDimension() {
        return this.dimension;
    }
    
    public boolean isInUniverse() {
        return (this.dimension != null && ForgeAPI.distance(dPosX, 0, dPosZ, getDimension().getSpawnX(), 0, getDimension().getSpawnZ()) < dimension.getRadiusBorderX() && ForgeAPI.distance(dPosX, 0, dPosZ, getDimension().getSpawnX(), 0, getDimension().getSpawnZ()) < dimension.getRadiusBorderZ());
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
    
    public String getDisplayString() {
        return (this.getDPosX() + "," + this.getDPosY() + ", " + this.getDPosZ() + " on dimension: " + this.getDimension().getDisplayName() + " with uposX of" + this.getUPosX() + " and uPosZ of" + this.getUPosZ());
    }
    
    public double getUPosZ() {
        return this.uPosZ;
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

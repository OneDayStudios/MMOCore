/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.AbstractObjectCore;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Galaxy.RegisterableGalaxy;

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
        ForgeAPI.sendConsoleEntry("Calculating uPos for : " + this.getDisplayString(), ConsoleMessageType.FINE);
        
        // If a position is in hyperspace, this is how we calculate it.
        if (this.getDimension().getType().equals(DimensionType.Hyperspace)){
            this.uPosX = this.dPosX;
            this.uPosZ = this.dPosZ;
        }
        
        // If a position is in space, this is how we calculate it.
        if (this.getDimension().getType().equals(DimensionType.StarSystem)) {
            this.uPosX = this.getDimension().getPosXInParent(dPosX);
            this.uPosZ = this.getDimension().getPosZInParent(dPosZ);
        }
        
        // If a position is on a planet, or is on a celestial body, this is how we calculate it.
        if (this.getDimension().getType().equals(DimensionType.Planet) || this.getDimension().isFake()) {
            this.uPosX = this.getDimension().getParent().getPosXInParent(this.getDimension().getPosXInParent(dPosX));
            this.uPosZ = this.getDimension().getParent().getPosZInParent(this.getDimension().getPosZInParent(dPosZ));
        }
  
    }
    
    public boolean isInSpace() {
        return (this.dimension.getType().equals(DimensionType.StarSystem));
    }
    
    public RegisterableDimension getCelestialBody() {
        return UniverseAPI.getCelestialBody(this);
    }
    
    public RegisterableDimension getSystem() {
        return UniverseAPI.getSystem(this);
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
    
    public String getDisplayString() {
        return (this.getDPosX() + "," + this.getDPosY() + ", " + this.getDPosZ() + " on dimension: " + this.getDimension().getDisplayName() + " with uposX of" + this.getUPosX() + " and uPosZ of" + this.getUPosZ());
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

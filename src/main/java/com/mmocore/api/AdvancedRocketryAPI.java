/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.api.ForgeAPI;
import zmaster587.advancedRocketry.api.dimension.solar.StellarBody;
import zmaster587.advancedRocketry.api.stations.ISpaceObject;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.stations.SpaceObjectManager;

/**
 *
 * @author draks
 */
public class AdvancedRocketryAPI {
    
    public static DimensionProperties getCelestialForDimId(int id) {
        return DimensionManager.getInstance().getDimensionProperties(id);
    }
    
    public static boolean areDimensionsInSamePlanetSystem(int idSource, int idDest) {
        return DimensionManager.getInstance().areDimensionsInSamePlanetMoonSystem(idSource, idDest);
    }
    
    public static StellarBody getStarForDimension(int id) {
        if (DimensionManager.getInstance().getDimensionProperties(id) == null) return null;
        return DimensionManager.getInstance().getDimensionProperties(id).getStar();
    }
    
    public static boolean areDimensionsInSameStarSystem(int idSource, int idDest) {
        return (DimensionManager.getInstance().getDimensionProperties(idSource).getStar() == DimensionManager.getInstance().getDimensionProperties(idDest).getStar());
    }
    
    public static boolean isInSpace(int id) {
        return (id == zmaster587.advancedRocketry.api.Configuration.spaceDimId);
    }
    
    public static ISpaceObject getSpaceObjectForCoordinates(int x, int z) {
        return SpaceObjectManager.getSpaceManager().getSpaceStationFromBlockCoords(x, z);
    }
    
    public static boolean getIsMoving(int x, int z) {
        ISpaceObject spaceObject = getSpaceObjectForCoordinates(x,z);        
        if (spaceObject != null) {
            return (spaceObject.getTransitionTime() != -1);
        } else {
            return false;
        }
    }   
    
    public static DimensionProperties getPlanetOrbiting(int x, int z) {
        ISpaceObject spaceObject = getSpaceObjectForCoordinates(x,z);
        if (spaceObject != null) {
            return DimensionManager.getInstance().getDimensionProperties(spaceObject.getOrbitingPlanetId());
        }
        return null;
    }
    
    public double getDistanceBetweenStellarBodies(int idSource, int idDest) {
        DimensionProperties source = DimensionManager.getInstance().getDimensionProperties(idSource);
        DimensionProperties dest = DimensionManager.getInstance().getDimensionProperties(idDest);
        // IF the source or destination is invalid, travel is free.
        if (source == null || dest == null) return 0.0;
        double distance = dest.getOrbitalDist() + source.getOrbitalDist(); // OrbitalDist is a max of 200.
        if (source.getStar() != dest.getStar()) {
            double sourceX = source.getStar().getPosX()*1000; //Multiplying Star coordinates by 1000 to guarrantee we have room for all the planets!
            double sourceZ = source.getStar().getPosZ()*1000; //Multiplying Star coordinates by 1000 to guarrantee we have room for all the planets!
            double destX = dest.getStar().getPosX()*1000; //Multiplying Star coordinates by 1000 to guarrantee we have room for all the planets!
            double destZ = dest.getStar().getPosZ()*1000; //Multiplying Star coordinates by 1000 to guarrantee we have room for all the planets!
            distance += ForgeAPI.distance(sourceX, 0, sourceZ, destX, 0, destZ);
        }
        return distance;
    }
}

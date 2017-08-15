/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.api.ForgeAPI;
import java.util.HashMap;
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
    
    public static boolean hasBreathableAtmosphere(int dimensionId) {
        return (getAtmosphere(dimensionId) >= 75);
    }
    
    public static double getBorder(int dimensionId) {
        DimensionProperties properties = AdvancedRocketryAPI.getCelestialForDimId(dimensionId);
        if (properties != null) return (1000 + ((int)properties.getGravitationalMultiplier() * 25));
        return 0.0;
    }
    
    public static int getAtmosphere(int dimensionId) {
        DimensionProperties properties = AdvancedRocketryAPI.getCelestialForDimId(dimensionId);
        if (properties != null) return properties.getAtmosphereDensity();
        return 0;
    }
    
    public static float getGravity(int dimensionId) {
        DimensionProperties properties = AdvancedRocketryAPI.getCelestialForDimId(dimensionId);
        if (properties != null) return properties.getGravitationalMultiplier();
        return 0;
    }
    
    public static String getName(int dimensionId) {
        DimensionProperties properties = AdvancedRocketryAPI.getCelestialForDimId(dimensionId);
        if (properties != null) return properties.getName();
        return "Unknown";
    }
    
    public static HashMap<String,Double> getPlanetPosition(int dimension) {        
        HashMap<String, Double> position = new HashMap<String,Double>();
        if (AdvancedRocketryAPI.isInSpace(dimension)) {
                position.put("x", Double.POSITIVE_INFINITY);
                position.put("z", Double.POSITIVE_INFINITY);
                return position;
        } else {
            DimensionProperties properties = AdvancedRocketryAPI.getCelestialForDimId(dimension);
            double posX = ((Math.cos(properties.getOrbitalDist() * properties.getOrbitTheta()) * properties.getOrbitalDist()) * 1000) + (properties.getStar().getPosX()*10000);
            double posZ = ((Math.sin(properties.getOrbitalDist() * properties.getOrbitTheta()) * properties.getOrbitalDist()) * 1000) + (properties.getStar().getPosZ()*10000);
            position.put("x", posX);
            position.put("z", posZ);
            return position;
        }
    }
}

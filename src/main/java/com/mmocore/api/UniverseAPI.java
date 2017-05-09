/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.constants.uPosition;
import com.mmocore.MMOCore;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Galaxy.RegisterableGalaxy;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author draks
 */
public class UniverseAPI extends AbstractAPI<UniverseAPI> {
    
    public static RegisterableDimension getDimension(String name) {
        for (RegisterableDimension d : getDimensionsReadOnly()) {
            if (d.getName().equals(name)) return d;
        }
        return null;
    }
    
    public static Collection<RegisterableDimension> getDimensionsReadOnly() {
        return MMOCore.getDimensionRegistry().getRegisteredReadOnly().values();
    }
    
    public static Collection<RegisterableGalaxy> getGalaxiesReadOnly() {
        return MMOCore.getGalaxyRegistry().getRegisteredReadOnly().values();
    }
    
    public static ArrayList<RegisterableDimension> getDimensions() {
        return (ArrayList)MMOCore.getDimensionRegistry().getRegisteredReadOnly().values();
    }
    
    public static ArrayList<RegisterableDimension> getDimensions(DimensionType type) {
        ArrayList<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension dim : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (!dim.isFake() && dim.getType().equals(type)) dimensions.add(dim);
        }
        return dimensions;
    }
    
    public static Collection<RegisterableDimension> getDimensions(RegisterableGalaxy galaxy) {
        Collection<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension dim : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (distanceBetweenUPositions(galaxy.getPosition(), dim.getPosition()) < galaxy.getBorder()) dimensions.add(dim);
        }
        return dimensions;
    }
    
    public static RegisterableDimension getDimension(uPosition pos) {
        ArrayList<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension dim : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (dim.isFake() || dim.getType().equals(DimensionType.Hyperspace) || dim.getType().equals(DimensionType.StarSystem)) continue;
            if (distanceBetweenUPositions(dim.getPosition(), pos) < dim.getBorderX()) dimensions.add(dim);
        }
        if (dimensions.isEmpty()) return null;
        return dimensions.get(0);
    }
    
    public static boolean isInInterstellarSpace(uPosition pos) {
        if ((pos.isInHyperSpace() || pos.isInSpace()) && UniverseAPI.getSystem(pos) == null && !UniverseAPI.getGalaxy(pos).getIdentifier().equals("Galactic Void")) return true;
        return false;
    }
    public static boolean isOnDimension(uPosition pos) {
        if (!pos.isInHyperSpace() && !pos.isInSpace() && UniverseAPI.getDimension(pos) != null) return true;
        return false;
    }
    public static boolean isInOrbitOf(uPosition pos) {
        if ((pos.isInHyperSpace() || pos.isInSpace()) && UniverseAPI.getCelestialBody(pos) != null) return true;
        return false;
    }
    public static boolean isInVoidSpace(uPosition pos) {
        if ((pos.isInHyperSpace() || pos.isInSpace()) && UniverseAPI.getCelestialBody(pos) == null && UniverseAPI.getGalaxy(pos).getIdentifier().equals("Galactic Void")) return true;
        return false;
    }
    
    public static RegisterableGalaxy getGalaxy(String name) {
        return MMOCore.getGalaxyRegistry().getRegistered(name);
    }
    
    public static ArrayList<RegisterableGalaxy> getGalaxies() {
        return (ArrayList)MMOCore.getGalaxyRegistry().getRegisteredReadOnly().values();
    }
    
    public static String getLocationMessage(uPosition pos) {
        String location = null;
        if (isOnDimension(pos)) location = pos.getDimension().getDisplayName();
        if (isInVoidSpace(pos)) location = "Void Space";
        if (isInInterstellarSpace(pos)) location = "Interstellar Space";
        if (isInOrbitOf(pos)) location = "Orbit of " + UniverseAPI.getCelestialBody(pos).getDisplayName();
        if (location == null) location = "Unknown location!";
        return (location);
    }
    
    public static String getConditionsMessage(uPosition pos) {
        String conditions = null;
        if (conditions == null && isOnDimension(pos)) conditions = pos.getCelestialBody().getConditions().name();
        if (conditions == null && isInVoidSpace(pos)) conditions = "None";
        if (conditions == null && isInInterstellarSpace(pos)) conditions = "None";
        if (conditions == null && isInOrbitOf(pos)) conditions = pos.getCelestialBody().getConditions().name();
        if (conditions == null) conditions = "Unknown conditions!";
        return conditions;
    }
    public static RegisterableGalaxy getGalaxy(uPosition pos) {
        ArrayList<RegisterableGalaxy> galaxies = new ArrayList<RegisterableGalaxy>();
        for (RegisterableGalaxy gal : MMOCore.getGalaxyRegistry().getRegisteredReadOnly().values()) {
            if (distanceBetweenUPositions(gal.getPosition(), pos) < gal.getBorder()) galaxies.add(gal);
        }
        if (galaxies.isEmpty()) return new RegisterableGalaxy("Galactic Void", 0, 0, 0);
        return galaxies.get(0);
    }
    
    public static RegisterableDimension getSystem(uPosition pos) {
        ArrayList<RegisterableDimension> systems = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension system : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (system.isFake() || !system.getType().equals(DimensionType.StarSystem)) continue;
            if (distanceBetweenUPositions(system.getPosition(), pos) < system.getBorderX()) systems.add(system);
        }
        if (systems.isEmpty()) return null;
        return systems.get(0);
    }
    
    public static RegisterableDimension getCelestialBody(uPosition pos) {
        ArrayList<RegisterableDimension> bodies = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension body : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (!body.isFake() && !body.getType().equals(DimensionType.Planet)) continue;
            if (distanceBetweenUPositions(body.getPosition(), pos) < body.getBorderX()) bodies.add(body);
        }
        if (bodies.isEmpty()) return null;
        return bodies.get(0);
    }
    
    public static RegisterableGalaxy getGalaxy(RegisterableDimension dimension) {
        return getGalaxy(dimension.getPosition());
    }
    
    public static RegisterableDimension getHyperSpace() {
        return UniverseAPI.getDimensions(DimensionType.Hyperspace).get(0);
    }
    
    public static double distanceBetweenUPositions(uPosition pos1, uPosition pos2) {
        return ForgeAPI.distance(pos1.getUPosX(), 100, pos1.getUPosZ(),pos2.getUPosX(), 100, pos2.getUPosZ());
    }
}

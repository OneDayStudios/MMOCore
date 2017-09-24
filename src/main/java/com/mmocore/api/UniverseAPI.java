/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.constants.uPosition;
import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Galaxy.RegisterableGalaxy;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.Dimension.RegisterableDimension;
import net.minecraft.block.*;
import java.util.ArrayList;
import net.minecraft.block.material.Material;
import java.util.Collection;
import java.util.Random;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.gen.structure.*;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
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
    public static RegisterableDimension getDimensionByDisplayName(String displayName) {
        for (RegisterableDimension d : getDimensionsReadOnly()) {
            if (d.getDisplayName().equals(displayName)) return d;
        }
        return null;
    }
    public static Collection<RegisterableDimension> getDimensionsReadOnly() {
        return MMOCore.getDimensionRegistry().getRegisteredReadOnly().values();
    }
    
    public static Collection<RegisterableGalaxy> getGalaxiesReadOnly() {
        return MMOCore.getGalaxyRegistry().getRegisteredReadOnly().values();
    }
    
    public static Collection<RegisterableDimension> getDimensions() {
        return MMOCore.getDimensionRegistry().getRegistered().values();
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
    
    public static uPosition getRandomNearbyPosition(uPosition position, int minDistance, int maxDistance) {
        uPosition oldPosition = position;
        Random r = new Random();
        int offset = maxDistance - minDistance;
        if (offset < 1) offset = 1;
        int distanceX = r.nextInt(offset) + minDistance;
        int distanceZ = r.nextInt(offset) + minDistance;
        boolean lessThanX = (r.nextBoolean());
        boolean lessThanZ = (r.nextBoolean());  
        double tempX = 0;
        double tempZ = 0;
        if (lessThanX) tempX = oldPosition.getDPosX() - distanceX;
        if (!lessThanX) tempX = oldPosition.getDPosX() + distanceX;
        if (lessThanZ) tempZ = oldPosition.getDPosZ() - distanceZ;
        if (!lessThanZ) tempZ = oldPosition.getDPosZ() + distanceZ;
        double origY = oldPosition.getDPosY();
        Block block = position.getDimension().getRegisteredObject().getBlock((int)tempX, (int)origY, (int)tempZ);
        double tempY = origY;
        while (!isBlockSolid(block) && tempY < 256) {
                block = position.getDimension().getRegisteredObject().getBlock((int)tempX, (int)tempY, (int)tempZ);
                tempY +=1;
        }
        if (tempY  == 256) {
            tempY = origY;        
            while (!isBlockSolid(block) && tempY > 0) {
                block = position.getDimension().getRegisteredObject().getBlock((int)tempX, (int)tempY, (int)tempZ);
                tempY -= 1;
            }
        }        
        uPosition newPosition = new uPosition(tempX, tempY+1, tempZ, position.getDimension());
        if (isBlockLiquid(block)) return null;
        block = position.getDimension().getRegisteredObject().getBlock((int)tempX, (int)tempY+1, (int)tempZ);
        if (isBlockLiquid(block)) return null;
        if (origY >= 256 || origY <= 0 || ForgeAPI.distance(oldPosition.getDPosX(), oldPosition.getDPosY(), oldPosition.getDPosZ(), newPosition.getDPosX(), newPosition.getDPosY(), newPosition.getDPosZ()) > 128) return null;
        return newPosition;
    }

    private static boolean isBlockGas(Block b) {
        return (!isBlockSolid(b) && !isBlockLiquid(b));
    }
    private static boolean isBlockSolid(Block block) {
        return block.getMaterial().isSolid();
    }

    private static boolean isBlockLiquid(Block block) {
         return block.getMaterial().isLiquid();
    }
    
    public static RegisterableDimension getDimension(uPosition pos) {
        ArrayList<RegisterableDimension> dimensions = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension dim : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (dim.isFake() || dim.getType().equals(DimensionType.Hyperspace) || dim.getType().equals(DimensionType.StarSystem)) continue;
            if (distanceBetweenUPositions(dim.getPosition(), pos) < dim.getRadiusBorderX()) dimensions.add(dim);
        }
        if (dimensions.isEmpty()) return null;
        return dimensions.get(0);
    }
    public static boolean isInStellarSpace(uPosition pos) {
        if ((pos.isInSpace()) && UniverseAPI.getSystem(pos) != null && !UniverseAPI.getGalaxy(pos).getIdentifier().equals("Galactic Void")) return true;
        return false;
    }
    public static boolean isInInterstellarSpace(uPosition pos) {
        if ((pos.isInHyperSpace() || pos.isInSpace()) && UniverseAPI.getSystem(pos) == null && !UniverseAPI.getGalaxy(pos).getIdentifier().equals("Galactic Void")) return true;
        return false;
    }
    public static boolean isOnPlanet(uPosition pos) {
        if (!pos.isInHyperSpace() && !pos.isInSpace() && UniverseAPI.getCelestialBody(pos) != null && pos.getDimension().equals(pos.getCelestialBody())) return true;
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
        if (isOnPlanet(pos)) location = pos.getDimension().getDisplayName();
        if (getSystem(pos) != null && pos.getCelestialBody() == null) location = pos.getSystem().getDisplayName();
        if (location == null && isInStellarSpace(pos)) location = "Space";
        if (isInOrbitOf(pos)) location = "Orbit of " + UniverseAPI.getCelestialBody(pos).getDisplayName();
        if (pos.isInHyperSpace()) location = "Hyperspace (" + location + ")";
        if (location == null) location = "Unknown location!";
        return (location);
    }
    
    public static String getConditionsMessage(uPosition pos) {
        String conditions = null;
        if (conditions == null && isOnPlanet(pos)) conditions = pos.getDimension().getConditions().name();
        if (conditions == null && isInStellarSpace(pos)) conditions = "Space";
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
            if (distanceBetweenUPositions(system.getPosition(), pos) < system.getRadiusBorderX()) {
                systems.add(system);
            } else {
            }
        }
        if (systems.isEmpty()) return null;
        return systems.get(0);
    }
    
    public static RegisterableDimension getCelestialBody(uPosition pos) {
        ArrayList<RegisterableDimension> bodies = new ArrayList<RegisterableDimension>();
        for (RegisterableDimension body : MMOCore.getDimensionRegistry().getRegisteredReadOnly().values()) {
            if (!body.isFake() && !body.getType().equals(DimensionType.Planet)) continue;
            if (distanceBetweenUPositions(body.getPosition(), pos) < body.getRadiusBorderX()) {
                bodies.add(body);
            }
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
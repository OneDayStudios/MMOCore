/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.constants.FakeDimensionType;
import com.mmocore.module.Dimension.RegisterableDimension;
import cr0s.warpdrive.data.CelestialObjectManager;
import cr0s.warpdrive.data.CelestialObject;
import cr0s.warpdrive.data.CelestialObject.RenderData;
import java.util.ArrayList;
import java.util.Set;
/**
 *
 * @author draks
 */
public class WarpDriveAPI extends AbstractAPI<WarpDriveAPI> {
    
    public static boolean isUniverseLoaded() {
        return CelestialObjectManager.celestialObjects.length != 0;
    }
    
    private static CelestialObject getUniverse() {
        for (CelestialObject s : getReadOnly()) {
            if (s.parent != null) {
                if (s.dimensionId == s.parent.dimensionId) return s;
            }
        }
        ForgeAPI.sendConsoleEntry("Failed to load universe, providing overworld as the universal dimension!", ConsoleMessageType.WARNING);
        if (ForgeAPI.isServer()) { return CelestialObjectManager.get(false, 0, 0, 0); } else { return CelestialObjectManager.get(true, 0, 0, 0); }        
    }
    
    private static ArrayList<CelestialObject> getReadOnly() {
        ArrayList<CelestialObject> cachedCopy = new ArrayList<CelestialObject>();
        if (ForgeAPI.isServer()) {
            for (CelestialObject object : CelestialObjectManager.SERVER.celestialObjects) {
                cachedCopy.add(CelestialObjectManager.get(false, object.getName()));
            }
            
        } else {
            for (CelestialObject object : CelestialObjectManager.CLIENT.celestialObjects) {
                cachedCopy.add(CelestialObjectManager.get(true, object.getName()));
            }
        }
        return cachedCopy;
    }
    
    public static String getName(int dimensionId) {
        if (!isMapped(dimensionId)) return "Unknown";
        return getForDimId(dimensionId).getName();
    }
    
    public static int getBorderX(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).borderRadiusX == 0) return 0;
        return getForDimId(dimensionId).borderRadiusX;
    }
    
    public static int getBorderZ(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).borderRadiusZ == 0) return 0;
        return getForDimId(dimensionId).borderRadiusZ;
    }
    
    public static boolean hasBreathableAtmosphere(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).hasAtmosphere();
    }
    
    public static double getGravity(int dimensionId) {
        if (!isMapped(dimensionId)) return 0.0;
        return getForDimId(dimensionId).getGravity();
    }
    
    public static String getParentName(int dimensionId) {
        if (!isMapped(dimensionId)) return "Unknown";
        return getForDimId(dimensionId).parent.getName();
    }
    
    public static int getParentId(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).parent == null) return 0;
        return getForDimId(dimensionId).parent.dimensionId;
    }
    
    public static int getPosInParentX(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).getParentCenterX() == 0) return 0;
        return getForDimId(dimensionId).getParentCenterX();
    }
    
    public static int getPosInParentZ(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).getParentCenterZ() == 0) return 0;
        return getForDimId(dimensionId).getParentCenterZ();
    }
    
    
    public static int getSpawnX(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).dimensionCenterX == 0) return 0;
        return getForDimId(dimensionId).dimensionCenterX;
    }
    
    public static int getSpawnZ(int dimensionId) {
        if (!isMapped(dimensionId) || getForDimId(dimensionId).dimensionCenterZ == 0) return 0;
        return getForDimId(dimensionId).dimensionCenterZ;
    }
    
    public static boolean isMapped(int dimensionId) {
        return getForDimId(dimensionId) != null;
    }
    
    private static CelestialObject getForDimId(int dimensionId) {
            for (CelestialObject object : getReadOnly()) {
                if (object.dimensionId == dimensionId) return object;
            }
        return null;
    }
    
    public static boolean isSolarSystem(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).parent != null && getForDimId(dimensionId).parent.dimensionId == getUniverse().dimensionId;
    }
    
    public static boolean isPlanet(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).parent != null && getForDimId(dimensionId).parent.dimensionId != getUniverse().dimensionId && getForDimId(dimensionId).dimensionId != getForDimId(dimensionId).parent.dimensionId;
    }
    
    public static DimensionType getType(int dimensionId) {
        if (!isMapped(dimensionId)) return DimensionType.Unknown;
        if (isPlanet(dimensionId)) return DimensionType.Planet;
        if (isHyperspace(dimensionId)) return DimensionType.Hyperspace;
        if (isSolarSystem(dimensionId)) return DimensionType.StarSystem;
        return DimensionType.Unknown;
    }
    
    public static FakeDimensionType getFakeType(Set<RenderData> dataSet) {
        if (!getConditionsForRenderData(dataSet).equals(DimensionConditions.Unknown)) return FakeDimensionType.Planet;
        return FakeDimensionType.Celestial_Body;
    }
    
    public static DimensionConditions getConditionsForRenderData(Set<RenderData> dataSet) {
        for (RenderData data : dataSet) {
            if (data.texture == null || data.texture.isEmpty()) continue;
            if (data.texture.equals("warpdrive:textures/celestial/planet_magma.png")) return DimensionConditions.Unstable;
            if (data.texture.equals("warpdrive:textures/celestial/planet_icy.png")) return DimensionConditions.Frozen;
            if (data.texture.equals("warpdrive:textures/celestial/planet_temperate.png") || data.texture.equals("warpdrive:textures/celestial/planet_oceanic.png")) return DimensionConditions.Optimal;
            if (data.texture.equals("minecraft:textures/blocks/sand.png")) return DimensionConditions.Barren;
            if (data.texture.equals("minecraft:textures/blocks/water_flow.png")) return DimensionConditions.Flooded;
        }
        return DimensionConditions.Unknown;
    }
    
    public static DimensionConditions getConditions(int dimensionId) {
        if (!isMapped(dimensionId)) return DimensionConditions.Unknown;
        if (isHyperspace(dimensionId)) return DimensionConditions.Hyperspace;
        if (isSolarSystem(dimensionId)) return DimensionConditions.Space;
        return getConditionsForRenderData(getForDimId(dimensionId).setRenderData);
    }
    
    public static void onServerStarted() {
            for (CelestialObject o : getReadOnly()) {
                if (!o.isVirtual() && ForgeAPI.getForgeWorld(o.dimensionId) == null) {
                    ForgeAPI.sendConsoleEntry("Error, WarpDrive dimension ID:" + o.dimensionId + " (" + o.getName() + ") is not mapped to a real dimension! It will not be visitable as a result!", ConsoleMessageType.FINE);
                }
                if (o.isVirtual()) {
                    RegisterableDimension dim = new RegisterableDimension(o.getName(), WarpDriveAPI.getFakeType(o.setRenderData), o.borderRadiusX, o.borderRadiusZ, o.getParentCenterX(), o.getParentCenterZ(), WarpDriveAPI.getConditionsForRenderData(o.setRenderData), o.parent.dimensionId);
                    MMOCore.getDimensionRegistry().register(dim);
                }
            }
    }
    
    public static boolean isHyperspace(int dimensionId) {
        return dimensionId == getUniverse().dimensionId;
    }
}
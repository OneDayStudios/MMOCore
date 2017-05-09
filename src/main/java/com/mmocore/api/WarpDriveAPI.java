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
import cr0s.warpdrive.config.CelestialObjectManager;
import cr0s.warpdrive.data.CelestialObject;
import cr0s.warpdrive.data.CelestialObject.RenderData;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
/**
 *
 * @author draks
 */
public class WarpDriveAPI extends AbstractAPI<WarpDriveAPI> {
    
    public static boolean isUniverseLoaded() {
        return !CelestialObjectManager.getCelestialObjectsReadOnly().isEmpty();
    }
    
    private static CelestialObject getUniverse() {
        for (String s : getReadOnly().keySet()) {
            for (CelestialObject o : getReadOnly().get(s).values()) {
                if (o.dimensionId == o.parentDimensionId) return o;
            }
        }
        return null;
    }
    
    private static HashMap<String, HashMap<String, CelestialObject>> getReadOnly() {
        HashMap<String, HashMap<String, CelestialObject>> cachedCopy = CelestialObjectManager.getCelestialObjectsReadOnly();
        for (String s : cachedCopy.keySet()) {
            for (CelestialObject c : cachedCopy.get(s).values()) {
                c.resolveParent();
            }
        }
        return cachedCopy;
    }
    
    public static String getName(int dimensionId) {
        if (!isMapped(dimensionId)) return "Unknown";
        return getForDimId(dimensionId).name;
    }
    
    public static int getBorderX(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).borderRadiusX;
    }
    
    public static int getBorderZ(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).borderRadiusZ;
    }
    
    public static boolean hasBreathableAtmosphere(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).isBreathable;
    }
    
    public static double getGravity(int dimensionId) {
        if (!isMapped(dimensionId)) return 0.0;
        return getForDimId(dimensionId).gravity;
    }
    
    public static String getParentName(int dimensionId) {
        if (!isMapped(dimensionId)) return "Unknown";
        return getForDimId(dimensionId).parentName;
    }
    
    public static int getParentId(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).parentDimensionId;
    }
    
    public static int getPosInParentX(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).parentCenterX;
    }
    
    public static int getPosInParentZ(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).parentCenterZ;
    }
    
    
    public static int getSpawnX(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).dimensionCenterX;
    }
    
    public static int getSpawnZ(int dimensionId) {
        if (!isMapped(dimensionId)) return 0;
        return getForDimId(dimensionId).dimensionCenterZ;
    }
    
    public static boolean isMapped(int dimensionId) {
        return getForDimId(dimensionId) != null;
    }
    
    private static CelestialObject getForDimId(int dimensionId) {
        for (String s : getReadOnly().keySet()) {
            for (CelestialObject object : getReadOnly().get(s).values()) {
                if (object.dimensionId == dimensionId) return object;
            }
        }
        return null;
    }
    
    public static boolean isSolarSystem(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).parentDimensionId == getUniverse().dimensionId;
    }
    
    public static boolean isPlanet(int dimensionId) {
        if (!isMapped(dimensionId)) return false;
        return getForDimId(dimensionId).parentDimensionId != getUniverse().dimensionId && getForDimId(dimensionId).dimensionId != getForDimId(dimensionId).parentDimensionId;
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
        for (String s : getReadOnly().keySet()) {
            for (CelestialObject o : getReadOnly().get(s).values()) {
                if (!o.isVirtual && ForgeAPI.getForgeWorld(o.dimensionId) == null) {
                    ForgeAPI.sendConsoleEntry("Error, WarpDrive dimension ID:" + o.dimensionId + " (" + o.name + ") is not mapped to a real dimension! It will not be visitable as a result!", ConsoleMessageType.FINE);
                }
                if (o.isVirtual) {
                    RegisterableDimension dim = new RegisterableDimension(o.name, WarpDriveAPI.getFakeType(o.setRenderData), o.borderRadiusX, o.borderRadiusZ, o.parentCenterX, o.parentCenterZ, WarpDriveAPI.getConditionsForRenderData(o.setRenderData), o.parentDimensionId);
                    MMOCore.getDimensionRegistry().register(dim);
                }
            }
        }
    }
    
    public static boolean isHyperspace(int dimensionId) {
        return dimensionId == getUniverse().dimensionId;
    }
}

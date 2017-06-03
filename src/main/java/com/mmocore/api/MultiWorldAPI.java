/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import fabricator77.multiworld.DimensionControl;
import fabricator77.multiworld.ModSettings;
import fabricator77.multiworld.MultiWorld;
import fabricator77.multiworld.WorldProviderSimple;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author draks
 */
public class MultiWorldAPI extends AbstractAPI<MultiWorldAPI> {
    
    public static void fixProviderType() {
        DimensionControl.fullOverworldList = new boolean[1000];
        DimensionManager.unregisterProviderType(ModSettings.providerId);
        MultiWorld.instance.unregisterDimensions();
        DimensionManager.registerProviderType(ModSettings.providerId, WorldProviderSimple.class, true);
        MultiWorld.instance.registerDimensions();
    }
}

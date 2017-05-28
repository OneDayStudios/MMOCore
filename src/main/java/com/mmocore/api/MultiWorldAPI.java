/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import fabricator77.multiworld.DimensionControl;
import fabricator77.multiworld.ModSettings;
import fabricator77.multiworld.MultiWorld;
import fabricator77.multiworld.WorldProviderSimple;
import fabricator77.multiworld.api.TeleporterAPI;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author draks
 */
public class MultiWorldAPI extends AbstractAPI<MultiWorldAPI> {
    
    public static void fixProviderType() {
        DimensionManager.unregisterProviderType(ModSettings.providerId);
        DimensionManager.registerProviderType(ModSettings.providerId, WorldProviderSimple.class, true);
        MultiWorld.instance.unregisterDimensions();
        MultiWorld.instance.registerDimensions();
    }
}

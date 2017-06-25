/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.constants.ConsoleMessageType;
import fabricator77.multiworld.DimensionControl;
import fabricator77.multiworld.ModSettings;
import fabricator77.multiworld.MultiWorld;
import fabricator77.multiworld.WorldProviderSimple;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author draks
 */
public class MultiWorldAPI extends AbstractAPI<MultiWorldAPI> {
    
    public static void fixProviderType() {
        try {
            if (Class.forName("fabricator77.multiworld.DimensionControl") != null) {
                DimensionControl.fullOverworldList = new boolean[1000];

                DimensionManager.unregisterProviderType(ModSettings.providerId);

                MultiWorld.instance.unregisterDimensions();

                DimensionManager.registerProviderType(ModSettings.providerId, WorldProviderSimple.class, true);

                MultiWorld.instance.registerDimensions();
                ForgeAPI.sendConsoleEntry("Successfully patched MultiWorld mod.", ConsoleMessageType.FINE);
            } else {
                ForgeAPI.sendConsoleEntry("Skipped patching MultiWorld mod, it isnt installed.", ConsoleMessageType.FINE);
            }
        } catch (ClassNotFoundException ex) {
            ForgeAPI.sendConsoleEntry("MultiWorld not found, skipping runtime patching of the mod.", ConsoleMessageType.FINE);
        }
    }
}

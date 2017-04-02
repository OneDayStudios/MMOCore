/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.NpcFaction;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.AbstractRegistry;
import com.stargatemc.forge.core.constants.IntegratedMod;

/**
 *
 * @author draks
 */
public class NpcFactionRegistry extends AbstractRegistry<NpcFactionRegistry, Integer, RegisterableNpcFaction> {

    @Override
    public void initialise() {
        // Not required.
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs));
    }

}

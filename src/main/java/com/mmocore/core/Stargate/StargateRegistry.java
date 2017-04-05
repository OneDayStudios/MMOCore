/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.core.Stargate;

import com.mmocore.api.ForgeAPI;
import com.mmocore.core.AbstractRegistry;
import com.mmocore.constants.IntegratedMod;

/**
 *
 * @author draks
 */
public class StargateRegistry extends AbstractRegistry<StargateRegistry, String, RegisterableStargate> {

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
        return ForgeAPI.isModLoaded(IntegratedMod.SGCraft);
    }

}

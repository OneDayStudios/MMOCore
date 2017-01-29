/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Dialog;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.AbstractRegistry;
import com.stargatemc.forge.core.constants.IntegratedMod;

/**
 *
 * @author draks
 */
public class DialogRegistry extends AbstractRegistry<DialogRegistry, Integer, RegisterableDialog> {

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
        return ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs);
    }

}

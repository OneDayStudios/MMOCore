/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Galaxy;

import com.mmocore.api.ForgeAPI;
import com.mmocore.module.AbstractRegistry;

/**
 *
 * @author draks
 */
public class GalaxyRegistry extends AbstractRegistry<GalaxyRegistry, String, RegisterableGalaxy> {

    @Override
    public void initialise() {
        if (ForgeAPI.isServer()) {
            RegisterableGalaxy galaxy = new RegisterableGalaxy("MilkyWay", 500000,0,0);
            this.register(galaxy);
        }
        // Not required.
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmcore.core.Galaxy;

import com.mmocore.core.AbstractRegistry;

/**
 *
 * @author draks
 */
public class GalaxyRegistry extends AbstractRegistry<GalaxyRegistry, String, RegisterableGalaxy> {

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
        return true;
    }

}

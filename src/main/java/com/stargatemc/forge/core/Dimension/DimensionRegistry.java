/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Dimension;

import com.stargatemc.forge.core.AbstractRegistry;

/**
 *
 * @author draks
 */
public class DimensionRegistry extends AbstractRegistry<DimensionRegistry, String, RegisterableDimension> {

    @Override
    public void initialise() {
        // Not required.
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

}

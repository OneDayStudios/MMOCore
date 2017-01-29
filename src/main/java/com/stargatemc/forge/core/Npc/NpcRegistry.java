/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.AbstractRegistry;
import com.stargatemc.forge.core.constants.IntegratedMod;
import java.util.UUID;

/**
 *
 * @author draks
 */
public class NpcRegistry extends AbstractRegistry<NpcRegistry, UUID, RegisterableNpc> {

    @Override
    public void initialise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // THIS CLASS HAS NO ADDITIONAL CODE BY DESIGN.

    @Override
    public void finalise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canBeEnabled() {
        return (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs));
    }

}

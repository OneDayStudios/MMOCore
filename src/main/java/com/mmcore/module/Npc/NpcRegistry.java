/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmcore.module.Npc;

import com.mmocore.api.ForgeAPI;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.constants.IntegratedMod;
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

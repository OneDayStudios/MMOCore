/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.Dimension.RegisterableDimension;
import java.util.ArrayList;
import java.util.UUID;

public class NpcRegistry extends AbstractRegistry<NpcRegistry, UUID, RegisterableNpc> {

    @Override
    public void initialise() {
        
    }

    // THIS CLASS HAS NO ADDITIONAL CODE BY DESIGN.

    @Override
    public void finalise() {
        
    }
    
    public void tickForDimension(RegisterableDimension dimension) {
        ArrayList<RegisterableNpc> npcs = NpcAPI.getAll(dimension);        
        ForgeAPI.sendConsoleEntry("Ticking " + npcs.size() + " npcs in NpcRegistry for dimension: " + dimension.getDisplayName() +".", ConsoleMessageType.FINE);
        for (RegisterableNpc npc : npcs) {
            npc.tick();
        }
        for (RegisterableNpc npc : NpcAPI.getAllReadOnly(dimension)) {
            if (npc.getRegisteredObject().getMarkedForRemoval()) {
                this.deregister(npc.getIdentifier());
            }
        }
    }

    @Override
    public boolean canBeEnabled() {
        return (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs));
    }

}

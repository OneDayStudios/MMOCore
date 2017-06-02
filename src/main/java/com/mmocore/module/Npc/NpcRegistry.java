/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.Dimension.RegisterableDimension;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import noppes.npcs.entity.EntityCustomNpc;
import net.minecraft.entity.Entity;

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
        for (RegisterableNpc npc : npcs) {
            npc.tick();
        }
        for (RegisterableNpc npc : NpcAPI.getAllReadOnly(dimension)) {
            if (npc.getRegisteredObject().getMarkedForRemoval()) {
                this.deregister(npc.getIdentifier());
            }
        }
        cleanup(dimension);
    }
    
    private void cleanup(RegisterableDimension dimension) {
        List<Entity> entities = ForgeAPI.getForgeWorld(dimension).loadedEntityList; 
        for (Entity entity : entities) {
            if (entity instanceof EntityCustomNpc) {
                    if (!MMOCore.getNpcRegistry().isRegistered(entity.getUniqueID())) {
                        EntityCustomNpc npc = (EntityCustomNpc)entity;
                        ForgeAPI.sendConsoleEntry("Deleting unregistered NPC: " + npc.getUniqueID() + " at " + entity.posX + "," + entity.posY + "," + entity.posZ + " on dimension : " + dimension.getDisplayName(), ConsoleMessageType.FINE);
                        npc.delete();
                    }
            }
        }
    }
    
    @Override
    public boolean canBeEnabled() {
        return (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs));
    }

}

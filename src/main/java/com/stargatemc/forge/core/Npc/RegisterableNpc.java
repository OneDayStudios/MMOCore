/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc;

import com.stargatemc.forge.core.AbstractRegisterable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableNpc extends AbstractRegisterable<RegisterableNpc, UUID> {
    
    private final Npc npc;
    
    public RegisterableNpc(Npc npc) {
        this.npc = npc;        
    }
    
    @Override
    public void tick() {
        this.npc.tick();
    }

    @Override
    public UUID getIdentifier() {
        return this.npc.getUniqueID();
    }
}

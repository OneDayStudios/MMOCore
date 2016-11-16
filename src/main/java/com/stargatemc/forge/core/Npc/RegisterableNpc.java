/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc;

import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.NpcSpawnMethod;
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
    private NpcSpawnMethod spawnMethod;
    
    public RegisterableNpc(Npc npc, NpcSpawnMethod method) {
        this.npc = npc;        
        this.spawnMethod = method;
    }
    
    @Override
    public void tick() {
       //this.npc.tick();
    }

    @Override
    public UUID getIdentifier() {
        return null;
    }

    @Override
    public void initialise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Player.RegisterablePlayer;

/**
 *
 * @author draks
 */
public class NpcSpawnEvent extends GameEvent {

    private RegisterableNpc npc;
    
    public NpcSpawnEvent(RegisterableNpc npc) {
        super("NpcLoadEvent-" + npc.getBaseOptions().getFaction().getName() + "-" + npc.getBaseOptions().getName() + "-" + npc.getBaseOptions().getTitle());
        this.npc = npc;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (npc.getUPosition().isValid() && npc.getUPosition().getDimension().equals(dimension)) {
            MMOCore.getNpcRegistry().register(npc);
            this.setFlaggedForRemoval();
        }
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        return this.npc.getBaseOptions().getSpawnPosition().getDimension().equals(dimension);
    }

    @Override
    public void cleanup() {
       // This event doesnt clean anything up.
    }
    
}

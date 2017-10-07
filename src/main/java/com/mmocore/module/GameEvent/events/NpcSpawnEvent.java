/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
/**
 *
 * @author draks
 */
public class NpcSpawnEvent extends GameEvent {
    
    private uPosition position;
    private RegisterableNpc npc;
    
    public NpcSpawnEvent(uPosition position, RegisterableNpc npc) {
        super("NpcSpawnEvent_" + npc.getBaseOptions().getName() + "_" + npc.getBaseOptions().getTitle());
        this.position = position;
        this.npc = npc;
    }
    
    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (this.position == null || this.position.getDimension() == null) {
            ForgeAPI.sendConsoleEntry("Error spawning Npc: " + npc.getBaseOptions().getName(), ConsoleMessageType.FINE);            
            this.setFlaggedForRemoval();
            return;
        }
        if (this.position.getDimension().equals(dimension)) {
                MMOCore.getNpcRegistry().register(npc);
        }
        if (NpcAPI.get(npc.getBaseOptions().getName(), npc.getBaseOptions().getTitle(), npc.getBaseOptions().getFaction()) != null) {
            this.setFlaggedForRemoval();
        }
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        if (position.getDimension() == null) return false;
       if (position.getDimension().equals(dimension)) return true;     
       return false;
    }

    @Override
    public void cleanup() {
        // This event does not clean up anything.
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Player.RegisterablePlayer;

/**
 *
 * @author draks
 */
public class TellNearEvent extends GameEvent {

    private String message;
    private RegisterableNpc npc;
    
    public TellNearEvent(String name, RegisterableNpc npc, String message) {
        super(name);
        this.npc = npc;
        this.message = message;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (npc.getUPosition().getDimension().equals(dimension)) {
            npc.tellNear(message);
            this.setFlaggedForRemoval();
        }
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        return this.npc.getUPosition().getDimension().equals(dimension);
    }

    @Override
    public void cleanup() {
       // This event doesnt clean anything up.
    }
    
}

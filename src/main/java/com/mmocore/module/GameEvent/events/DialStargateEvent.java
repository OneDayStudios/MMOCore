/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import gcewing.sg.SGBaseTE;

/**
 *
 * @author draks
 */
public class DialStargateEvent extends GameEvent {

    private String message;
    private RegisterableNpc npc;
    private SGBaseTE stargate;
    
    public DialStargateEvent(String name, RegisterableNpc npc, SGBaseTE stargate) {
        super(name);
        this.npc = npc;
        this.stargate = stargate;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (npc.getUPosition().getDimension().equals(dimension)) {
            npc.tellNear(message);
            //TODO: Actual event code.
        }
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        return (this.npc.getUPosition().getDimension().equals(dimension));
    }

    @Override
    public void cleanup() {
        // This event doesnt clean anything up.
    }
    
}

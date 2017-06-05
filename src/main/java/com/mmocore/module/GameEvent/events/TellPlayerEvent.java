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
public class TellPlayerEvent extends GameEvent {

    private RegisterablePlayer player;
    private String message;
    private RegisterableNpc npc;
    
    public TellPlayerEvent(String name, RegisterableNpc npc, RegisterablePlayer player, String message) {
        super(name);
        this.npc = npc;
        this.player = player;
        this.message = message;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (player.getPosition().getDimension().equals(dimension)) {
            npc.tellNear(message);
            this.setFlaggedForRemoval();
        }
    }
    
}

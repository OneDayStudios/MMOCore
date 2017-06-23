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
public class PlayerJoinEvent extends GameEvent {

    private RegisterablePlayer player;
    
    public PlayerJoinEvent(RegisterablePlayer player) {
        super("PlayerJoinEvent-" + player.getIdentifier());
        this.player = player;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
            MMOCore.getPlayerRegistry().register(player);
            this.setFlaggedForRemoval();
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        return this.player.getRegisteredObject() != null && this.player.getWorld() != null && this.player.getPosition() != null && this.player.getPosition().getDimension().equals(dimension);
    }

    @Override
    public void cleanup() {
       // This event doesnt clean anything up.
    }
    
}

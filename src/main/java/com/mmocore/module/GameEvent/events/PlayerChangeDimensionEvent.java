/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.GuiSlot;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Player.RegisterablePlayer;

/**
 *
 * @author draks
 */
public class PlayerChangeDimensionEvent extends GameEvent {

    private RegisterablePlayer player;
    private RegisterableDimension dimension;
    
    public PlayerChangeDimensionEvent(RegisterablePlayer player, RegisterableDimension dimension) {
        super("PlayerChangeDimensionEvent-" + player.getIdentifier());
        this.player = player;
        this.dimension = dimension;
    }

    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (dimension.getIsLoaded()) {
            GuiAPI.sendGuiElementToClient(player, GuiSlot.Toast, UniverseAPI.getLocationMessage(player.getPosition()), UniverseAPI.getConditionsMessage(player.getPosition()), UniverseAPI.getGalaxy(player.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
            this.setFlaggedForRemoval();
        } else {
            ForgeAPI.sendConsoleEntry("Skipping PlayerChangeDimensionEvent for player: " + player.getName() + " as  the world is not yet loaded.", ConsoleMessageType.FINE);
        }
    }

    @Override
    public boolean ticksForDimension(RegisterableDimension dimension) {
        return this.player.getPosition().getDimension().equals(dimension);
    }

    @Override
    public void cleanup() {
       // This event doesnt clean anything up.
    }
    
}

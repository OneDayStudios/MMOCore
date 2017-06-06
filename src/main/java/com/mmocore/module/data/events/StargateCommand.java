/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.events;

import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.GuiSlot;
import com.mmocore.constants.ServerGui;
import com.mmocore.constants.uPosition;
import com.mmocore.module.GameEvent.events.QuestLocationEvent;

/**
 *
 * @author draks
 */
public class StargateCommand extends QuestLocationEvent {
    
    public StargateCommand() {
        super("Stargate Command",
        new uPosition(-127.0,100.0,-598.0,UniverseAPI.getDimension("P2X-3YZ")),
        250,
        100,
        250
        );
        ServerGui enterGui = new ServerGui("Stargate Command", "Military Facility","P2X-3YZ", (long)2000, GuiSlot.Toast);
        this.setEnterGui(enterGui);
        ServerGui completeGui = new ServerGui("Quest Objective Achieved", "Stargate Command Located","You may now proceed with the next objective/stage.", (long)2000, GuiSlot.MissionObjective);
        this.setCompletedGui(completeGui);
        this.setProtected(true);
    }
    
}

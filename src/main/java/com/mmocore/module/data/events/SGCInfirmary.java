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
public class SGCInfirmary extends QuestLocationEvent {
    
    public SGCInfirmary() {
        super("Visiting Infirmary",
        new uPosition(-136.0,51.0,-638.0,UniverseAPI.getDimensionByDisplayName("P2X-3YZ")),
        16,
        3,
        8
        );
        ServerGui enterGui = new ServerGui("SGC Infirmary", "Level X","Stargate Command", (long)2000, GuiSlot.Toast);
        this.setEnterGui(enterGui);
        ServerGui completeGui = new ServerGui("Infirmary Located", "Level X","Quest Objective Achieved", (long)2000, GuiSlot.MissionObjective);
        this.setCompletedGui(completeGui);
    }
    
}

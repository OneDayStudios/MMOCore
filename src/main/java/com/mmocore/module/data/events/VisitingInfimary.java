/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.events;

import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.GameEvent.events.QuestLocationEvent;

/**
 *
 * @author draks
 */
public class VisitingInfimary extends QuestLocationEvent {
    
    public VisitingInfimary() {
        super("Visiting Infirmary",
        new uPosition(-136.0,51.0,-638.0,UniverseAPI.getDimension("P2X-3YZ")),
        8,
        3,
        8
        );
    }
    
}

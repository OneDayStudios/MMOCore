/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcDoorInteraction;

/**
 *
 * @author draks
 */
public class NpcBehaviourOptions {
    
    public NpcBoolean avoidsSun;
    public NpcBoolean canSwim;    
    private NpcDoorInteraction doorBehaviour;
    
    public NpcDoorInteraction getDoorBehaviour() {
        return this.doorBehaviour;
    }
    
    public void setDoorBehaviour(NpcDoorInteraction setting) {
        this.doorBehaviour = setting;
    }
    
}

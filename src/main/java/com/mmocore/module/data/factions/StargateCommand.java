/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.factions;

import com.mmocore.module.NpcFaction.RegisterableNpcFaction;

/**
 *
 * @author draks
 */
public class StargateCommand extends RegisterableNpcFaction {

    public StargateCommand() {
        super("Stargate Command");
        this.setDefaultPoints(1500);
    }
    
}

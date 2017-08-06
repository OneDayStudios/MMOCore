/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.factions;

import com.mmocore.api.NpcFactionAPI;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class StargateCommand extends RegisterableNpcFaction {

    public StargateCommand() {
        super("Stargate Command");
        this.setDefaultPoints(1000);
        this.setAttackedByMobs(true);
        this.addHostileFaction("Goauld");
        this.addHostileFaction("Bounty Hunters");
    }
    
}

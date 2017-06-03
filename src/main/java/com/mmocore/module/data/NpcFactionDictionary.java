/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.factions.FreeJaffaNation;
import com.mmocore.module.data.factions.Goauld;
import com.mmocore.module.data.factions.StargateCommand;

/**
 *
 * @author draks
 */
public enum NpcFactionDictionary {

    FREE_JAFFA_NATION(FreeJaffaNation.get()),
    GOAULD(Goauld.get()),
    SGC(StargateCommand.get());
    
    private final RegisterableNpcFaction faction;
    
    NpcFactionDictionary(RegisterableNpcFaction faction) {
        this.faction = faction;
    }
    
    public RegisterableNpcFaction get() {
        return this.faction;
    }
}
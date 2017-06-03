/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.factions.Goauld;

/**
 *
 * @author draks
 */
public enum NpcFactionDictionary {

    GOAULD(new Goauld());
    
    private final RegisterableNpcFaction faction;
    
    NpcFactionDictionary(RegisterableNpcFaction faction) {
        this.faction = faction;
    }
    
    public RegisterableNpcFaction get() {
        return this.faction;
    }
}

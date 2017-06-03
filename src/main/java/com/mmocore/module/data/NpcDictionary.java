/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.data.npcs.GoauldJaffaSoldier;
import com.mmocore.module.data.npcs.HankLandry;

/**
 *
 * @author draks
 */
public enum NpcDictionary {

    GOAULD_JAFFA_SOLDIER(new GoauldJaffaSoldier()),
    HANK_LANDRY(new HankLandry());
    
    private final RegisterableNpc npc;
    
    NpcDictionary(RegisterableNpc npc) {
        this.npc = npc;
    }
    
    public RegisterableNpc get() {
        return this.npc;
    }
}

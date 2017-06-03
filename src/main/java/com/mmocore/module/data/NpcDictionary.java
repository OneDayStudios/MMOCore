/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.data.npcs.FreeJaffaSoldier;
import com.mmocore.module.data.npcs.GeneralLandry;
import com.mmocore.module.data.npcs.GoauldJaffaSoldier;
import com.mmocore.module.data.npcs.StargateCommandSoldier;

/**
 *
 * @author draks
 */
public enum NpcDictionary {

    FREE_JAFFA_SOLDIER(FreeJaffaSoldier.get()),
    GOAULD_JAFFA_SOLDIER(GoauldJaffaSoldier.get()),
    SGC_SOLDIER(StargateCommandSoldier.get()),
    GENERAL_LANDRY(GeneralLandry.get());
    
    private final RegisterableNpc npc;
    
    NpcDictionary(RegisterableNpc npc) {
        this.npc = npc;
    }
    
    public RegisterableNpc get() {
        return this.npc;
    }
}

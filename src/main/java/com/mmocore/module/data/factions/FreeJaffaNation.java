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
public class FreeJaffaNation {

    public static RegisterableNpcFaction get() {
            RegisterableNpcFaction faction = new RegisterableNpcFaction("Free Jaffa Nation");
            faction.setDefaultPoints(1000);
            return faction;
    }
    
}

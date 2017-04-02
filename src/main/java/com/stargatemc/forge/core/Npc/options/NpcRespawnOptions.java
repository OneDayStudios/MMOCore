/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc.options;

import com.stargatemc.forge.core.constants.NpcRespawnOption;

/**
 *
 * @author draks
 */
public class NpcRespawnOptions {
    
    private NpcRespawnOption respawnOption;
    private int respawnTimeSeconds;
    
    public void setRespawnOption(NpcRespawnOption option) {
        this.respawnOption = option;
    }
    
    public NpcRespawnOption getRespawnOption() {
        return this.respawnOption;
    }
    
    public int getRespawnTime() {
        return this.respawnTimeSeconds;
    }
    
    public void setRespawnTime(int seconds) {
        this.respawnTimeSeconds = seconds;
    }
}

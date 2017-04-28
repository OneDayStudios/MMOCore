/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcRespawnOption;

/**
 *
 * @author draks
 */
public class NpcRespawnOptions {
    
    private NpcRespawnOption respawnOption = NpcRespawnOption.Never;
    public NpcBoolean hideDeadBody = NpcBoolean.NO;
    private int respawnTimeSeconds = 0;
    
    public void setHideDeadBody(NpcBoolean setting) {
        this.hideDeadBody = setting;
    }
    
    public NpcBoolean getHideDeadBody() {
        return this.hideDeadBody;
    }
    
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

/**
 *
 * @author draks
 */
public enum NpcSound {
    
    NONE("NONE"),
    P90("flansmod:p90"),
    MatokStaff("flansmod:staffShoot"),
    M9("flansmod:M9Shoot");
    
    private String type;

    NpcSound(String typeName) {
        this.type = typeName;
    }
    
    public String getSoundString() {
        return this.type;
    }
    
}

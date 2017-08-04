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
    P90("flansmod:P90Shoot"),
    MatokStaff("flansmod:staffShoot"),
    Sniper("flansmod:M21Shoot"),
    M9("flansmod:M9Shoot"),
    ZatGun("flansmod:zatShoot");
    
    private String type;

    NpcSound(String typeName) {
        this.type = typeName;
    }
    
    public String getSoundString() {
        return this.type;
    }
    
}

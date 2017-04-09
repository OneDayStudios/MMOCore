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
public enum NpcBody {
    DRAGON("noppes.npcs.entity.EntityNpcDragon");
    
    private String type;

    NpcBody(String typeName) {
        this.type = typeName;
    }
    
    public String getClassString() {
        return this.type;
    }
    
}

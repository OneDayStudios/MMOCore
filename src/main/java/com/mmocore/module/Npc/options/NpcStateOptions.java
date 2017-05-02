/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.uPosition;

/**
 *
 * @author draks
 */
public class NpcStateOptions {
    
    private int health = 0;
    private uPosition position = null;
    private boolean isInCombat = false;
    private boolean isNearPlayers = false;
    
    public void setHealth(int number) {
        this.health = number;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public void setPosition(uPosition position) {
        this.position = position;
    }
    
    public uPosition getPosition() {
        return this.position;
    }
    
    public void setIsInCombat(boolean value) {
        this.isInCombat = value;
    }
    
    public void setIsNearPlayers(boolean value) {
        this.isNearPlayers = value;
    }
    
    public boolean isInCombat() {
        return this.isInCombat;
    }
    
    public boolean isNearPlayers() {
        return this.isNearPlayers;
    }
}

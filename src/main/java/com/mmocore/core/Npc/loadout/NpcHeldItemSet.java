/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.core.Npc.loadout;

import com.mmocore.constants.NpcProjectile;

/**
 *
 * @author draks
 */
public class NpcHeldItemSet {
    
    private NpcWornItem mainhand = new NpcWornItem();
    private NpcWornItem offhand = new NpcWornItem();    
    
    public NpcWornItem getOffHand() {
        return this.offhand;
    }
    
    public NpcWornItem getMainHand() {
        return this.mainhand;
    }
    
    public void setOffHand(NpcWornItem item) {
        this.offhand = item;
    }
    
    public void setMainHand(NpcWornItem item) {
        this.mainhand = item;
    }
}

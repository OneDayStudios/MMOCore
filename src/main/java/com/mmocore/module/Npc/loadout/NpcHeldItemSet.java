/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.loadout;

import com.mmocore.constants.NpcProjectile;

/**
 *
 * @author draks
 */
public class NpcHeldItemSet {
    
    private NpcItem mainhand = new NpcItem();
    private NpcItem offhand = new NpcItem();    

    public NpcHeldItemSet() {

    }

    public NpcHeldItemSet(NpcHeldItemSet heldItems) {
        this.mainhand = new NpcItem(heldItems.mainhand);
        this.offhand = new NpcItem(heldItems.offhand);
    }
    
    public NpcItem getOffHand() {
        return this.offhand;
    }
    
    public NpcItem getMainHand() {
        return this.mainhand;
    }
    
    public void setOffHand(NpcItem item) {
        this.offhand = item;
    }
    
    public void setMainHand(NpcItem item) {
        this.mainhand = item;
    }
}

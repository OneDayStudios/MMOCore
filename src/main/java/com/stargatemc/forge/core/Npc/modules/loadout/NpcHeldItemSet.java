/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc.modules.loadout;

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
}

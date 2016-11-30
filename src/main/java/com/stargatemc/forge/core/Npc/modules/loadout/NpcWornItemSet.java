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
public class NpcWornItemSet {
    
    private NpcWornItem head = new NpcWornItem();
    private NpcWornItem chest = new NpcWornItem();
    private NpcWornItem legs = new NpcWornItem();
    private NpcWornItem feet = new NpcWornItem();
    
    public NpcWornItem getHead() {
        return this.head;
    }
    
    public NpcWornItem getChest() {
        return this.chest;
    }
    
    public NpcWornItem getLegs() {
        return this.legs;
    }
    
    public NpcWornItem getFeet() {
        return this.feet;
    }
}

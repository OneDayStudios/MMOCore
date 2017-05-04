/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.loadout;

/**
 *
 * @author draks
 */
public class NpcWornItemSet {
    
    private NpcItem head = new NpcItem();
    private NpcItem chest = new NpcItem();
    private NpcItem legs = new NpcItem();
    private NpcItem feet = new NpcItem();

    public NpcWornItemSet() {
        
    }
    
    public NpcWornItemSet(NpcWornItemSet armor) {
        this.head = new NpcItem(armor.getHead());
        this.chest = new NpcItem(armor.getChest());
        this.legs = new NpcItem(armor.getLegs());
        this.feet = new NpcItem(armor.getFeet());
    }
    
    public NpcItem getHead() {
        return this.head;
    }
    
    public NpcItem getChest() {
        return this.chest;
    }
    
    public NpcItem getLegs() {
        return this.legs;
    }
    
    public NpcItem getFeet() {
        return this.feet;
    }
}

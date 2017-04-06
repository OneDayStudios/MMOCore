/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.loadout;
import net.minecraft.item.ItemStack;

/**
 *
 * @author draks
 */
public class NpcItem {
    
    private ItemStack item;
    
    public void setItem(ItemStack stack) {
        this.item = stack;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public boolean hasItem() {
        return (this.item != null);
    }
}

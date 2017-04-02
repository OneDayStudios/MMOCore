/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.constants;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

/**
 *
 * @author draks
 */

public enum NpcProjectile {
    REDPLASMA("IC2", "itemDust2", 2);
    
    private String mod;
    private String item;
    private int damage;
    
    NpcProjectile(final String mod, final String item, final int damage) {
        this.mod = mod;
        this.item = item;
        this.damage = damage;
    }
    
    public ItemStack getItem() {
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(damage);
        return stack;
    }
}
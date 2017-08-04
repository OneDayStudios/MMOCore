/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import noppes.npcs.constants.EnumParticleType;

/**
 *
 * @author draks
 */

public enum NpcProjectile {
    RED_PLASMA("IC2", "itemDust2", 2, false, 4, 65, false, true, true, EnumParticleType.None, 10, 18, 10, 1, 2),    
    WHITE_PLASMA("IC2", "itemDust2", 6, false, 4, 65, false, true, true, EnumParticleType.None, 10, 18, 15, 1, 2),
    BLUE_PLASMA("IC2", "itemDust2", 12, false, 4, 65, false, true, true, EnumParticleType.None, 10, 18, 15, 1, 2),
    GOLD_PLASMA("IC2", "itemDust2", 4, false, 4, 65, false, true, true, EnumParticleType.None, 10, 18, 15, 1, 2),
    ARROW("minecraft", "arrow", 0, false, 4, 35, false, true, true, EnumParticleType.None, 20, 20, 25, 1, 1),    
    PISTOL_BULLET("customnpcs", "npcBlackBullet", 0, false, 1, 45, true, false, false, EnumParticleType.None, 10, 15, 15, 1, 1),
    SNIPER_BULLET("customnpcs", "npcBlackBullet", 0, false, 1, 100, true, false, false, EnumParticleType.None, 1, 9, 60, 1, 1),
    MACHINEGUN_BULLET("customnpcs", "npcBlackBullet", 0, false, 1, 65, true, false, false, EnumParticleType.None, 3, 9, 3, 1, 5);
    
    private String mod;
    private String item;
    private int damage;
    private boolean sticks;
    private int size;
    private int speed;
    private boolean affectedGravity;
    private boolean glows;
    private boolean spins;
    private EnumParticleType trail;
    private int minDelay;
    private int maxDelay;
    private int fireRate;
    private int shotCount;
    private int burstCount;
    
    
    
    NpcProjectile(final String mod, final String item, final int damage, final boolean sticks, final int size, final int speed, final boolean gravAffected, final boolean glows, final boolean spins, EnumParticleType trail, final int minDelay, final int maxDelay, final int fireRate, final int shotCount, final int burstCount) {
        this.mod = mod;
        this.item = item;
        this.damage = damage;
        this.sticks = sticks;
        this.size = size;
        this.speed = speed;
        this.affectedGravity = gravAffected;
        this.glows = glows;
        this.spins = spins;
        this.trail = trail;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.fireRate = fireRate;
        this.shotCount = shotCount;
        this.burstCount = burstCount;
    }
    
    public EnumParticleType getTrail() {
        return this.trail;
    }
    
    public boolean getSticks() {
        return this.sticks;
    }
    
    public boolean getSpins() {
        return this.spins;
    }
    
    public ItemStack getItem() {
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(damage);
        return stack;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getAffectedByGravity() {
        return this.affectedGravity;
    }
    
    public int getMinDelay() {
        return this.minDelay;
    }

    public int getFireRate() {
        return this.fireRate;
    }
    
    public int getBurstCount() {
        return this.burstCount;
    }
    public int getShotCount() {
        return this.shotCount;
    }

    public int getMaxDelay() {
        return this.maxDelay;
    }
}
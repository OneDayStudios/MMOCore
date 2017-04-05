/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcProjectile;

/**
 *
 * @author draks
 */
public class NpcCombatOptions {
    
    private boolean attacksHostileFactions = false;
    private boolean defendFactionMembers = false;
    private NpcAbstractScale rangedDamage;
    private NpcAbstractScale meleeDamage;
    private NpcAbstractScale projectileResistance;
    private NpcAbstractScale knockBackResistance;
    private NpcAbstractScale meleeResistance;
    private NpcAbstractScale explosionResistance;
    private NpcProjectile projectile;
    
    public NpcAbstractScale getProjectileResistance() {
        return this.projectileResistance;
    }
    
    public void setProjectileResistance(NpcAbstractScale setting) {
        this.projectileResistance = setting;
    }
    
    public NpcAbstractScale getKnockbackResistance() {
        return this.knockBackResistance;
    }
    
    public void setKnockbackResistance(NpcAbstractScale setting) {
        this.knockBackResistance = setting;
    }
    
    public NpcAbstractScale getMeleeResistance() {
        return this.meleeResistance;
    }
    
    public void setMeleeResistance(NpcAbstractScale setting) {
        this.meleeResistance = setting;
    }
    
    public NpcAbstractScale getExplosionResistance() {
        return this.explosionResistance;
    }
    
    public void setExplosionResistance(NpcAbstractScale setting) {
        this.explosionResistance = setting;
    }
    
    public NpcAbstractScale getMeleeDamage() {
        return this.meleeDamage;
    }
    
    public void setMeleeDamage(NpcAbstractScale setting) {
        this.meleeDamage = setting;
    }
    
    public NpcAbstractScale getRangedDamage() {
        return this.rangedDamage;
    }
    
    public void setRangedDamage(NpcAbstractScale setting) {
        this.rangedDamage = setting;
    }
    
    public NpcProjectile getProjectile() {
        return this.projectile;
    }
    
    public void setProjectile(NpcProjectile projectile) {
        this.projectile = projectile;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

/**
 *
 * @author draks
 */
public enum NpcModifier {
    MELEE_BOSS(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Highest,NpcAbstractScale.Highest,NpcAbstractScale.Highest,NpcAbstractScale.Highest),
    RANGED_BOSS(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    MELEE_COMMANDER(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    RANGED_COMMANDER(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    MELEE_SOLDIER(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    RANGED_SOLDIER(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    CIVILIAN(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High),
    CRITTER(NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.Absolute,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High,NpcAbstractScale.High);

    private final NpcAbstractScale health;
    private final NpcAbstractScale meleeDamage;
    private final NpcAbstractScale rangedDamage;
    private final NpcAbstractScale knockbackResistance;
    private final NpcAbstractScale explosiveResistance;
    private final NpcAbstractScale meleeResistance;
    private final NpcAbstractScale projectileResistance;
    
    NpcModifier(NpcAbstractScale health, NpcAbstractScale meleeDamage, NpcAbstractScale rangedDamage, NpcAbstractScale knockbackResistance, NpcAbstractScale explosiveResistance, NpcAbstractScale meleeResistance, NpcAbstractScale projectileResistance) {
        this.health = health;
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.knockbackResistance = knockbackResistance;
        this.explosiveResistance = explosiveResistance;
        this.meleeResistance = meleeResistance;
        this.projectileResistance = projectileResistance;
    }
    
    public NpcAbstractScale getHealth() {
        return this.health;
    }
    
    public NpcAbstractScale getMeleeDamage() {
        return this.meleeDamage;
    }
    
    public NpcAbstractScale getRangedDamage() {
        return this.rangedDamage;
    }
    
    public NpcAbstractScale getMeleeResistance() {
        return this.meleeResistance;
    }
    
    public NpcAbstractScale getProjectileResistance() {
        return this.projectileResistance;
    }
    
    public NpcAbstractScale getExplosiveResistance() {
        return this.explosiveResistance;
    }
    
    public NpcAbstractScale getKnockbackResistance() {
        return this.knockbackResistance;
    }
}

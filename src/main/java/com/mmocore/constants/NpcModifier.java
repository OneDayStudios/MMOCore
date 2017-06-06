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
    
    MELEE_BOSS(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Highest,AbstractScale.Highest,AbstractScale.Highest,AbstractScale.Highest),
    RANGED_BOSS(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High),
    MELEE_COMMANDER(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High),
    RANGED_COMMANDER(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High),
    MELEE_SOLDIER(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High),
    RANGED_SOLDIER(AbstractScale.Low,AbstractScale.Low,AbstractScale.Low,AbstractScale.Low,AbstractScale.Low,AbstractScale.Low,AbstractScale.Low),
    CIVILIAN(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High),
    CRITTER(AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.Absolute,AbstractScale.High,AbstractScale.High,AbstractScale.High,AbstractScale.High);

    private AbstractScale health = AbstractScale.Medium;
    private AbstractScale meleeDamage = AbstractScale.Medium;
    private AbstractScale rangedDamage = AbstractScale.Medium;
    private AbstractScale knockbackResistance = AbstractScale.Medium;
    private AbstractScale explosiveResistance = AbstractScale.Medium;
    private AbstractScale meleeResistance = AbstractScale.Medium;
    private AbstractScale projectileResistance = AbstractScale.Medium;
    
    NpcModifier(AbstractScale health, AbstractScale meleeDamage, AbstractScale rangedDamage, AbstractScale knockbackResistance, AbstractScale explosiveResistance, AbstractScale meleeResistance, AbstractScale projectileResistance) {
        this.health = health;
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.knockbackResistance = knockbackResistance;
        this.explosiveResistance = explosiveResistance;
        this.meleeResistance = meleeResistance;
        this.projectileResistance = projectileResistance;
    }
    
    public AbstractScale getHealth() {
        return this.health;
    }
    
    public AbstractScale getMeleeDamage() {
        return this.meleeDamage;
    }
    
    public AbstractScale getRangedDamage() {
        return this.rangedDamage;
    }
    
    public AbstractScale getMeleeResistance() {
        return this.meleeResistance;
    }
    
    public AbstractScale getProjectileResistance() {
        return this.projectileResistance;
    }
    
    public AbstractScale getExplosiveResistance() {
        return this.explosiveResistance;
    }
    
    public AbstractScale getKnockbackResistance() {
        return this.knockbackResistance;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcCombatResponse;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcRangedUsage;
import com.mmocore.constants.NpcTacticalOption;

/**
 *
 * @author draks
 */
public class NpcCombatOptions {
    
    private NpcBoolean attacksHostileFactions;
    private NpcBoolean defendFactionMembers;
    private NpcAbstractScale rangedDamage;
    private NpcAbstractScale meleeDamage;
    private NpcAbstractScale meleeRange;
    private NpcAbstractScale rangedRange;
    private NpcAbstractScale accuracy;
    private NpcAbstractScale health;
    private NpcAbstractScale aggroRadius;
    private NpcAbstractScale passiveRegen;
    private NpcAbstractScale combatRegen;    
    private NpcAbstractScale projectileResistance;
    private NpcAbstractScale knockBackResistance;
    private NpcAbstractScale meleeResistance;
    private NpcAbstractScale explosionResistance;
    private NpcProjectile projectile;
    private NpcCombatResponse response;
    private NpcRangedUsage rangedUsage;
    private NpcTacticalOption tacticalBehaviour;
    private NpcAbstractScale tacticalDistance;

    public NpcTacticalOption getTacticalBehaviour() {
        return this.tacticalBehaviour;
    }
    
    public void setTacticalBehaviour(NpcTacticalOption behaviour) {
        this.tacticalBehaviour = behaviour;
    }    
    
    public NpcAbstractScale getTacticalDistance() {
        return this.tacticalDistance;
    }
    
    public void setTacticalDistance(NpcAbstractScale setting) {
        this.tacticalDistance = setting;
    }    
    
    public NpcRangedUsage getRangedUsage() {
        return this.rangedUsage;
    }
    
    public void setRangedUsage(NpcRangedUsage setting) {
        this.rangedUsage = setting;
    }    
    
    public NpcAbstractScale getHealth() {
        return this.health;
    }
    
    public void setHealth(NpcAbstractScale setting) {
        this.health = setting;
    }    
    
    public NpcAbstractScale getRangedAccuracy() {
        return this.accuracy;
    }
    
    public void setRangedAccuracy(NpcAbstractScale setting) {
        this.accuracy = setting;
    }    
    
    public NpcAbstractScale getAggroRadius() {
        return this.aggroRadius;
    }
    
    public void setAggroRadius(NpcAbstractScale setting) {
        this.aggroRadius = setting;
    }
    
    public NpcAbstractScale getPassiveRegeneration() {
        return this.passiveRegen;
    }
    
    public void setPassiveRegeneration(NpcAbstractScale setting) {
        this.passiveRegen = setting;
    }
    
    public NpcAbstractScale getCombatRegeneration() {
        return this.combatRegen;
    }
    
    public void setCombatRegeneration(NpcAbstractScale setting) {
        this.combatRegen = setting;
    }
    
    public NpcBoolean getDefendsFactionMembers() {
        return this.defendFactionMembers;
    }
    
    public void setDefendsFactionMembers(NpcBoolean setting) {
       this.defendFactionMembers = setting;
    }
    
    public NpcBoolean getAttacksHostileFactions() {
        return this.attacksHostileFactions;
    }
    
    public void setAttacksHostileFactions(NpcBoolean setting) {
       this.attacksHostileFactions = setting;
    }

    public NpcCombatResponse getCombatResponse() {
        return this.response;
    }
    
    public void setCombatResponse(NpcCombatResponse response) {
        this.response = response;
    }
    
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
    
    public NpcAbstractScale getMeleeRange() {
        return this.meleeRange;
    }
    
    public void setMeleeRange(NpcAbstractScale setting) {
        this.meleeRange = setting;
    }
    
    public NpcAbstractScale getRangedRange() {
        return this.rangedRange;
    }
    
    public void setRangedRange(NpcAbstractScale setting) {
        this.rangedRange = setting;
    }
    
    public NpcProjectile getProjectile() {
        return this.projectile;
    }
    
    public void setProjectile(NpcProjectile projectile) {
        this.projectile = projectile;
    }
}

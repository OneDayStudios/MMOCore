/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcCombatResponse;
import com.mmocore.constants.NpcFireIndirectlyOption;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcRangedUsage;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcTacticalOption;

/**
 *
 * @author draks
 */
public class NpcCombatOptions {
    
    private NpcBoolean attacksHostileFactions = NpcBoolean.NO;
    private NpcBoolean defendFactionMembers  = NpcBoolean.YES;
    private NpcAbstractScale rangedDamage = NpcAbstractScale.Medium;
    private NpcAbstractScale meleeDamage = NpcAbstractScale.Medium;
    private NpcAbstractScale meleeRange = NpcAbstractScale.Medium;
    private NpcAbstractScale rangedRange = NpcAbstractScale.Medium;
    private NpcAbstractScale accuracy = NpcAbstractScale.Medium;
    private NpcAbstractScale health = NpcAbstractScale.Medium;
    private NpcAbstractScale aggroRadius = NpcAbstractScale.Medium;
    private NpcAbstractScale passiveRegen = NpcAbstractScale.Medium;
    private NpcAbstractScale combatRegen = NpcAbstractScale.Medium;
    private NpcAbstractScale projectileResistance = NpcAbstractScale.Medium;
    private NpcAbstractScale knockBackResistance = NpcAbstractScale.Medium;
    private NpcAbstractScale meleeResistance = NpcAbstractScale.Medium;
    private NpcAbstractScale explosionResistance = NpcAbstractScale.Medium;
    private NpcProjectile projectile = NpcProjectile.MACHINEGUN_BULLET;
    private NpcCombatResponse response = NpcCombatResponse.Retaliate;
    private NpcRangedUsage rangedUsage = NpcRangedUsage.UntilClose;
    private NpcTacticalOption tacticalBehaviour = NpcTacticalOption.Surround;
    private NpcAbstractScale tacticalDistance = NpcAbstractScale.Medium;
    private NpcBoolean mustSeeTarget = NpcBoolean.YES;
    private NpcFireIndirectlyOption fireIndirectly = NpcFireIndirectlyOption.Never;
    private NpcBoolean canAttackInvisibleTargets = NpcBoolean.NO;
    private NpcSound fireWeaponSound = NpcSound.NONE;

    public NpcCombatOptions() {
        
    }
    
    public NpcCombatOptions(NpcCombatOptions combatOptions) {
        this.attacksHostileFactions = combatOptions.attacksHostileFactions;
        this.defendFactionMembers = combatOptions.defendFactionMembers;
        this.rangedDamage = combatOptions.rangedDamage;
        this.meleeDamage = combatOptions.meleeDamage;
        this.meleeRange = combatOptions.meleeRange;
        this.rangedRange = combatOptions.rangedRange;
        this.accuracy = combatOptions.accuracy;
        this.health = combatOptions.health;
        this.aggroRadius = combatOptions.aggroRadius;
        this.passiveRegen = combatOptions.passiveRegen;
        this.projectileResistance = combatOptions.projectileResistance;
        this.knockBackResistance = combatOptions.knockBackResistance;
        this.meleeResistance = combatOptions.meleeResistance;
        this.explosionResistance = combatOptions.explosionResistance;
        this.projectile = combatOptions.projectile;
        this.response = combatOptions.response;
        this.rangedUsage = combatOptions.rangedUsage;
        this.tacticalBehaviour = combatOptions.tacticalBehaviour;
        this.tacticalDistance = combatOptions.tacticalDistance;
        this.mustSeeTarget = combatOptions.mustSeeTarget;
        this.fireIndirectly = combatOptions.fireIndirectly;
        this.canAttackInvisibleTargets = combatOptions.canAttackInvisibleTargets;
        this.fireWeaponSound = combatOptions.fireWeaponSound;
    }
    
    public NpcSound getFireWeaponSound() {
        return this.fireWeaponSound;
    }
    
    public void setFireWeaponSound(NpcSound sound) {
        this.fireWeaponSound = sound;
    }
    
    public NpcBoolean getCanAttackInvisibleTargets() {
        return this.canAttackInvisibleTargets;
    }
    
    public void setCanAttackInvisibleTargets(NpcBoolean setting) {
        this.canAttackInvisibleTargets = setting;
    }
    
    public NpcFireIndirectlyOption getFireIndirectlyOption() {
        return this.fireIndirectly;
    }
    
    public void setFireIndirectlyOption(NpcFireIndirectlyOption setting) {
        this.fireIndirectly = setting;
    }    
    
    public NpcBoolean getMustSeeTarget() {
        return this.mustSeeTarget;
    }
    
    public void setMustSeeTarget(NpcBoolean setting) {
        this.mustSeeTarget = setting;
    }    
    
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

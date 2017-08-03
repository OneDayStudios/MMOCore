/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.api.ForgeAPI;
import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.ConsoleMessageType;
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
    private AbstractScale rangedDamage = AbstractScale.Medium;
    private AbstractScale meleeDamage = AbstractScale.Medium;
    private AbstractScale meleeRange = AbstractScale.Medium;
    private AbstractScale rangedRange = AbstractScale.Medium;
    private AbstractScale accuracy = AbstractScale.Medium;
    private AbstractScale health = AbstractScale.Medium;
    private AbstractScale aggroRadius = AbstractScale.Medium;
    private AbstractScale passiveRegen = AbstractScale.Medium;
    private AbstractScale combatRegen = AbstractScale.Lowest;
    private AbstractScale projectileResistance = AbstractScale.Medium;
    private AbstractScale knockBackResistance = AbstractScale.Medium;
    private AbstractScale meleeResistance = AbstractScale.Medium;
    private AbstractScale explosionResistance = AbstractScale.Medium;
    private NpcProjectile projectile = NpcProjectile.MACHINEGUN_BULLET;
    private NpcCombatResponse response = NpcCombatResponse.Retaliate;
    private NpcRangedUsage rangedUsage = NpcRangedUsage.UntilClose;
    private NpcTacticalOption tacticalBehaviour = NpcTacticalOption.Surround;
    private AbstractScale tacticalDistance = AbstractScale.Medium;
    private NpcBoolean mustSeeTarget = NpcBoolean.YES;
    private NpcFireIndirectlyOption fireIndirectly = NpcFireIndirectlyOption.Never;
    private NpcBoolean canAttackInvisibleTargets = NpcBoolean.NO;
    private NpcSound fireWeaponSound = NpcSound.NONE;
    private AbstractScale attackSpeed = AbstractScale.Medium;

    public NpcCombatOptions() {
        
    }
    
    public NpcCombatOptions(NpcCombatOptions combatOptions) {        
        ForgeAPI.sendConsoleEntry("Cloning combat options with attacksHostileFactions: " + combatOptions.attacksHostileFactions.name(), ConsoleMessageType.FINE);
        this.attacksHostileFactions = NpcBoolean.valueOf(combatOptions.attacksHostileFactions.name());
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
        ForgeAPI.sendConsoleEntry("Cloning combat options with sound: " + combatOptions.fireWeaponSound.name(), ConsoleMessageType.FINE);
        this.fireWeaponSound = NpcSound.valueOf(combatOptions.fireWeaponSound.name());
        this.attackSpeed = combatOptions.attackSpeed;
    }
    
    public NpcSound getFireWeaponSound() {
        return this.fireWeaponSound;
    }
    
    public void setAttackSpeed(AbstractScale scale) {
        this.attackSpeed = scale;
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
    
    public AbstractScale getTacticalDistance() {
        return this.tacticalDistance;
    }
    
    public void setTacticalDistance(AbstractScale setting) {
        this.tacticalDistance = setting;
    }    
    
    public NpcRangedUsage getRangedUsage() {
        return this.rangedUsage;
    }
    
    public void setRangedUsage(NpcRangedUsage setting) {
        this.rangedUsage = setting;
    }    
    
    public AbstractScale getHealth() {
        return this.health;
    }
    
    public void setHealth(AbstractScale setting) {
        this.health = setting;
    }    
    
    public AbstractScale getRangedAccuracy() {
        return this.accuracy;
    }
    
    public void setRangedAccuracy(AbstractScale setting) {
        this.accuracy = setting;
    }    
    
    public AbstractScale getAggroRadius() {
        return this.aggroRadius;
    }
    
    public void setAggroRadius(AbstractScale setting) {
        this.aggroRadius = setting;
    }
    
    public AbstractScale getPassiveRegeneration() {
        return this.passiveRegen;
    }
    
    public void setPassiveRegeneration(AbstractScale setting) {
        this.passiveRegen = setting;
    }
    
    public AbstractScale getCombatRegeneration() {
        return this.combatRegen;
    }
    
    public void setCombatRegeneration(AbstractScale setting) {
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
    
    public AbstractScale getProjectileResistance() {
        return this.projectileResistance;
    }
    
    public void setProjectileResistance(AbstractScale setting) {
        this.projectileResistance = setting;
    }
    
    public AbstractScale getKnockbackResistance() {
        return this.knockBackResistance;
    }
    
    public void setKnockbackResistance(AbstractScale setting) {
        this.knockBackResistance = setting;
    }
    
    public AbstractScale getMeleeResistance() {
        return this.meleeResistance;
    }
    
    public void setMeleeResistance(AbstractScale setting) {
        this.meleeResistance = setting;
    }
    
    public AbstractScale getExplosionResistance() {
        return this.explosionResistance;
    }
    
    public void setExplosionResistance(AbstractScale setting) {
        this.explosionResistance = setting;
    }
    
    public AbstractScale getMeleeDamage() {
        return this.meleeDamage;
    }
    
    public void setMeleeDamage(AbstractScale setting) {
        this.meleeDamage = setting;
    }
    
    public AbstractScale getRangedDamage() {
        return this.rangedDamage;
    }
    
    public void setRangedDamage(AbstractScale setting) {
        this.rangedDamage = setting;
    }
    
    public AbstractScale getMeleeRange() {
        return this.meleeRange;
    }
    
    public void setMeleeRange(AbstractScale setting) {
        this.meleeRange = setting;
    }
    
    public AbstractScale getRangedRange() {
        return this.rangedRange;
    }
    
    public void setRangedRange(AbstractScale setting) {
        this.rangedRange = setting;
    }
    
    public NpcProjectile getProjectile() {
        return this.projectile;
    }
    
    public void setProjectile(NpcProjectile projectile) {
        this.projectile = projectile;
    }

    public AbstractScale getAttackSpeed() {
        return this.attackSpeed;
    }
}

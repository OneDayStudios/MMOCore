/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc;
import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcWornItemSet;
import com.mmocore.module.Npc.options.NpcInteractOptions;
import com.mmocore.module.Npc.options.NpcCombatOptions;
import com.mmocore.module.Npc.options.NpcRespawnOptions;
import com.mmocore.module.Npc.options.NpcSpawnOptions;
import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcCombatResponse;
import net.minecraft.item.ItemStack;
import com.mmocore.constants.NpcDoorInteraction;
import com.mmocore.constants.NpcFireIndirectlyOption;
import com.mmocore.constants.NpcGender;
import com.mmocore.constants.NpcLootMode;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcMovementAnimation;
import com.mmocore.constants.NpcRangedUsage;
import com.mmocore.constants.NpcRespawnOption;
import com.mmocore.constants.NpcShelterFromOption;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTacticalOption;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.NpcTextureType;
import com.mmocore.constants.NpcVisibleOption;
import com.mmocore.constants.TextVisibleOption;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcBehaviourOptions;
import com.mmocore.module.Npc.options.NpcLootOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.RoleTrader;
import net.minecraft.entity.Entity;
import noppes.npcs.controllers.FactionController;


/**
 *
 * @author Drakster
 */

@SideOnly(Side.SERVER)
public class Npc {
    
    private EntityCustomNpc entity;
    
    //Marked for a reload from options. This will only occur after a change has been made to the NPCs configurable options.
    private boolean markedForUpdate = false;
    //Marked for removal from the NPC Registry. This will occur on the next NPC tick.
    private boolean markedForRemoval = false;
    
    private NpcBaseOptions baseInfo = new NpcBaseOptions();
    private NpcCombatOptions combatOptions = new NpcCombatOptions();
    private NpcSpawnOptions defaultSpawnOptions = new NpcSpawnOptions();
    private NpcSpawnOptions incursionSpawnOptions = new NpcSpawnOptions();
    private NpcSpawnOptions randomSpawnOptions = new NpcSpawnOptions();
    private NpcInteractOptions interactions = new NpcInteractOptions();
    private NpcWornItemSet armor = new NpcWornItemSet();
    private NpcHeldItemSet passiveHeld = new NpcHeldItemSet();
    private NpcHeldItemSet rangedHeld = new NpcHeldItemSet();
    private NpcHeldItemSet meleeHeld = new NpcHeldItemSet();
    private NpcRespawnOptions respawnBehaviour = new NpcRespawnOptions();
    private NpcLootOptions lootOptions = new NpcLootOptions();
    private NpcInteractOptions interactOptions = new NpcInteractOptions();
    
    private NpcBehaviourOptions behaviours = new NpcBehaviourOptions();
    private NpcMovementOptions movementOptions = new NpcMovementOptions();
    
    public Npc(String name, String title, NpcTexture texture, NpcModifier modifier, NpcSpawnMethod method, uPosition position, RegisterableNpcFaction faction) {
        this.entity = new EntityCustomNpc(ForgeAPI.getForgeWorld(position.getDimension()));
        NpcBaseOptions bOptions = new NpcBaseOptions();
        bOptions.setName(name);
        bOptions.setTexture(texture);
        bOptions.setTitle(title);
        bOptions.setFaction(faction);
        bOptions.setSpawnPosition(position);
        this.setBaseOptions(bOptions);
        NpcCombatOptions cOptions = new NpcCombatOptions();
        cOptions.setExplosionResistance(modifier.getExplosiveResistance());
        cOptions.setMeleeResistance(modifier.getMeleeResistance());
        cOptions.setProjectileResistance(modifier.getProjectileResistance());
        cOptions.setKnockbackResistance(modifier.getKnockbackResistance());
        cOptions.setMeleeDamage(modifier.getMeleeDamage());
        cOptions.setRangedDamage(modifier.getRangedDamage());        
        cOptions.setHealth(modifier.getHealth());
        this.setCombatOptions(cOptions);
        this.register();
    }
    
    public NpcMovementOptions getMovementOptions() {
        return this.movementOptions;
    }
    
    public void setMovementOptions(NpcMovementOptions options) {
        this.movementOptions = options;
        this.setMarkedForUpdate();
    }
    
    public void setMarkedForUpdate() {
        this.markedForUpdate = true;
    }
    
    public void setMarkedForRemoval() {
        this.markedForRemoval = true;
    }
    
    public NpcHeldItemSet getMeleeHeldItems() {
        return this.meleeHeld;
    }
    
    public void setMeleeHeldItems(NpcHeldItemSet items) {
        this.meleeHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcHeldItemSet getRangedHeldItems() {
        return this.rangedHeld;
    }
    
    public void setRangedHeldItems(NpcHeldItemSet items) {
        this.rangedHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcBehaviourOptions getBehaviourOptions() {
        return this.behaviours;
    }
    
    public void setBehaviourOptions(NpcBehaviourOptions options) {
        this.behaviours = options;
        this.setMarkedForUpdate();
    }
    
    public NpcHeldItemSet getPassiveHeldItems() {
        return this.passiveHeld;
    }
    
    public void setPassiveHeldItems(NpcHeldItemSet items) {
        this.passiveHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcWornItemSet getArmor() {
        return this.armor;
    }
    
    public void setArmor(NpcWornItemSet armor) {
        this.armor = armor;
        this.setMarkedForUpdate();
    }
    
    public NpcRespawnOptions getRespawnOptions() {
        return this.respawnBehaviour;
    }
    
    public void setRespawnOptions(NpcRespawnOptions behaviour) {
        this.respawnBehaviour = behaviour;
        this.setMarkedForUpdate();
    }
    
    public NpcCombatOptions getCombatOptions() {
        return this.combatOptions;
    }
    
    public NpcInteractOptions getInteractOptions() {
        return this.interactOptions;
    }
    
    public void setInteractOptions(NpcInteractOptions options) {
        this.interactOptions = options;
        this.setMarkedForUpdate();
    }
    
    public NpcLootOptions getLootOptions() {
        return this.lootOptions;
    }
    
    public void setLootOptions(NpcLootOptions options) {
        this.lootOptions = options;
        this.setMarkedForUpdate();
    }
    
    public void setCombatOptions(NpcCombatOptions options) {
        this.combatOptions = options;
        this.setMarkedForUpdate();
    }
    
    public NpcBaseOptions getBaseOptions() {
        return this.baseInfo;
    }
    
    public void setBaseOptions(NpcBaseOptions base) {
        this.baseInfo = base;
        this.setMarkedForUpdate();
    }

    // This method is the way that MMOCore pushes its configuration options into a defined Npc. 
    // This is an relatively intensive process that should only occur on ticks where an Npc has its options changed.
    
    private void pushOptionsToEntity() {
        entity.inventory.armor.put(3, (getArmor().getFeet().hasItem() ? getArmor().getFeet().getItem() : null));
        entity.inventory.armor.put(2, (getArmor().getLegs().hasItem() ? getArmor().getLegs().getItem() : null));
        entity.inventory.armor.put(1, (getArmor().getChest().hasItem() ? getArmor().getChest().getItem() : null));
        entity.inventory.armor.put(0, (getArmor().getHead().hasItem() ? getArmor().getHead().getItem() : null));
        
        // Begin Base Options
        
        if (!this.getBaseOptions().getName().equals(this.entity.display.name)) { this.entity.display.name = this.getBaseOptions().getName(); }
        if (!this.getBaseOptions().getTitle().equals(this.entity.display.title)) { this.entity.display.title = this.getBaseOptions().getTitle(); }
        if (this.getBaseOptions().getVisibleOption().equals(NpcVisibleOption.Visible) && this.entity.display.visible != 0) this.entity.display.visible = 0;
        if (this.getBaseOptions().getVisibleOption().equals(NpcVisibleOption.Invisible) && this.entity.display.visible != 1) this.entity.display.visible = 1;
        if (this.getBaseOptions().getVisibleOption().equals(NpcVisibleOption.PartiallyVisible) && this.entity.display.visible != 2) this.entity.display.visible = 2;
        if (!this.getBaseOptions().getTexture().asTextureString().equals(getEntity().display.texture) && !this.getBaseOptions().getTexture().getType().equals(NpcTextureType.Web)) this.entity.display.texture = this.getBaseOptions().getTexture().asTextureString();
        if (!this.getBaseOptions().getTexture().asTextureString().equals(getEntity().display.url) && this.getBaseOptions().getTexture().getType().equals(NpcTextureType.Web)) this.entity.display.url = this.getBaseOptions().getTexture().asTextureString();
        if (this.getBaseOptions().getTexture().getType().equals(NpcTextureType.Web)) this.entity.display.skinType = 2;
        if (this.getBaseOptions().getTexture().getType().equals(NpcTextureType.Player)) this.entity.display.skinType = 1;
        if (this.getBaseOptions().getTexture().getType().equals(NpcTextureType.Resource)) this.entity.display.skinType = 0;
        if (!this.getBaseOptions().getFaction().getRegisteredObject().equals(entity.getFaction())) this.entity.setFaction(this.getBaseOptions().getFaction().getIdentifier());
        if (this.getBaseOptions().getGender().equals(NpcGender.Male) && this.entity.modelData.breasts != 0) this.entity.modelData.breasts = 0;
        if (this.getBaseOptions().getGender().equals(NpcGender.Female) && this.entity.modelData.breasts != 2) this.entity.modelData.breasts = 2;
        
        // Begin Respawn Behaviour
        
        if (this.getRespawnOptions().getRespawnOption().equals(NpcRespawnOption.Always) && this.entity.stats.spawnCycle != 0) this.entity.stats.spawnCycle = 0;
        if (this.getRespawnOptions().getRespawnOption().equals(NpcRespawnOption.Day) && this.entity.stats.spawnCycle != 1) this.entity.stats.spawnCycle = 1;
        if (this.getRespawnOptions().getRespawnOption().equals(NpcRespawnOption.Night) && this.entity.stats.spawnCycle != 2) this.entity.stats.spawnCycle = 2;
        if (this.getRespawnOptions().getRespawnOption().equals(NpcRespawnOption.Never) && this.entity.stats.spawnCycle != 3) this.entity.stats.spawnCycle = 3;
        if (this.getRespawnOptions().getHideDeadBody().equals(NpcBoolean.YES)) this.entity.stats.hideKilledBody = true;
        if (this.getRespawnOptions().getHideDeadBody().equals(NpcBoolean.NO)) this.entity.stats.hideKilledBody = false;
        if (this.getRespawnOptions().getRespawnTime() != this.entity.stats.respawnTime) this.entity.stats.respawnTime = this.getRespawnOptions().getRespawnTime();

        if (this.getBaseOptions().getBossBarVisible().equals(TextVisibleOption.Always) && this.entity.display.showBossBar != 1) this.entity.display.showBossBar = 1;
        if (this.getBaseOptions().getBossBarVisible().equals(TextVisibleOption.Never) && this.entity.display.showBossBar != 0) this.entity.display.showBossBar = 0;
        if (this.getBaseOptions().getBossBarVisible().equals(TextVisibleOption.WhenAttacking) && this.entity.display.showBossBar != 2) this.entity.display.showBossBar = 2;
        if (this.getBaseOptions().getNameVisible().equals(TextVisibleOption.Never) && this.entity.display.showName != 1) this.entity.display.showName = 1;
        if (this.getBaseOptions().getNameVisible().equals(TextVisibleOption.Always) && this.entity.display.showName != 0) this.entity.display.showName = 0;
        if (this.getBaseOptions().getNameVisible().equals(TextVisibleOption.WhenAttacking) && this.entity.display.showName != 2) this.entity.display.showName = 2;
        if (this.getBehaviourOptions().getDoorBehaviour().equals(NpcDoorInteraction.Open) && this.entity.ai.doorInteract != 1) this.entity.ai.doorInteract = 1;
        if (this.getBehaviourOptions().getDoorBehaviour().equals(NpcDoorInteraction.None) && this.entity.ai.doorInteract != 2) this.entity.ai.doorInteract = 2;
        if (this.getBehaviourOptions().getDoorBehaviour().equals(NpcDoorInteraction.Break) && this.entity.ai.doorInteract != 0) this.entity.ai.doorInteract = 0;

        if (!this.getCombatOptions().getProjectile().getItem().equals(this.entity.inventory.getProjectile())) {
            this.entity.inventory.setProjectile(this.getCombatOptions().getProjectile().getItem());
            this.entity.stats.pRender3D = true;
            this.entity.stats.pSpin = this.getCombatOptions().getProjectile().getSpins();
            this.entity.stats.pStick = this.getCombatOptions().getProjectile().getSticks();
            this.entity.stats.pTrail = this.getCombatOptions().getProjectile().getTrail();
            this.entity.stats.pSpeed = this.getCombatOptions().getProjectile().getSpeed();
            this.entity.stats.pSize = this.getCombatOptions().getProjectile().getSize();
            this.entity.stats.pPhysics = this.getCombatOptions().getProjectile().getAffectedByGravity();
        }
        
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Absolute) && this.entity.stats.pDamage != 250) this.entity.stats.pDamage = 250;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Highest) && this.entity.stats.pDamage != 100) this.entity.stats.pDamage = 100;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Higher) && this.entity.stats.pDamage != 50) this.entity.stats.pDamage = 50;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.High) && this.entity.stats.pDamage != 25) this.entity.stats.pDamage = 25;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Medium) && this.entity.stats.pDamage != 10) this.entity.stats.pDamage = 10;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Low) && this.entity.stats.pDamage != 5) this.entity.stats.pDamage = 5;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Lower) && this.entity.stats.pDamage != 3) this.entity.stats.pDamage = 3;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.Lowest) && this.entity.stats.pDamage != 1) this.entity.stats.pDamage = 1;
        if (this.getCombatOptions().getRangedDamage().equals(NpcAbstractScale.None) && this.entity.stats.pDamage != 0) this.entity.stats.pDamage = 0;

        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Absolute) && this.entity.stats.getAttackStrength() != 250) this.entity.stats.setAttackStrength(250);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Highest) && this.entity.stats.getAttackStrength() != 100) this.entity.stats.setAttackStrength(100);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Higher) && this.entity.stats.getAttackStrength() != 50) this.entity.stats.setAttackStrength(50);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.High) && this.entity.stats.getAttackStrength() != 25) this.entity.stats.setAttackStrength(25);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Medium) && this.entity.stats.getAttackStrength() != 10) this.entity.stats.setAttackStrength(10);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Low) && this.entity.stats.getAttackStrength() != 5) this.entity.stats.setAttackStrength(5);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Lower) && this.entity.stats.getAttackStrength() != 3) this.entity.stats.setAttackStrength(3);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.Lowest) && this.entity.stats.getAttackStrength() != 1) this.entity.stats.setAttackStrength(1);
        if (this.getCombatOptions().getMeleeDamage().equals(NpcAbstractScale.None) && this.entity.stats.getAttackStrength() != 0) this.entity.stats.setAttackStrength(0);
        
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Absolute) && this.entity.stats.resistances.arrow != 2.0) this.entity.stats.resistances.arrow = (float)2.0;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Highest) && this.entity.stats.resistances.arrow != 1.75) this.entity.stats.resistances.arrow = (float)1.75;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Higher) && this.entity.stats.resistances.arrow != 1.50) this.entity.stats.resistances.arrow = (float)1.50;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.High) && this.entity.stats.resistances.arrow != 1.25) this.entity.stats.resistances.arrow = (float)1.25;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Medium) && this.entity.stats.resistances.arrow != 1.0) this.entity.stats.resistances.arrow = (float)1.0;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Low) && this.entity.stats.resistances.arrow != 0.75) this.entity.stats.resistances.arrow = (float)0.75;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Lower) && this.entity.stats.resistances.arrow != 0.50) this.entity.stats.resistances.arrow = (float)0.50;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.Lowest) && this.entity.stats.resistances.arrow != 0.25) this.entity.stats.resistances.arrow = (float)0.25;
        if (this.getCombatOptions().getProjectileResistance().equals(NpcAbstractScale.None) && this.entity.stats.resistances.arrow != 0.0) this.entity.stats.resistances.arrow = (float)0.0;
        
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Absolute) && this.entity.stats.resistances.knockback != 2.0) this.entity.stats.resistances.knockback = (float)2.0;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Highest) && this.entity.stats.resistances.knockback != 1.75) this.entity.stats.resistances.knockback = (float)1.75;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Higher) && this.entity.stats.resistances.knockback != 1.50) this.entity.stats.resistances.knockback = (float)1.50;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.High) && this.entity.stats.resistances.knockback != 1.25) this.entity.stats.resistances.knockback = (float)1.25;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Medium) && this.entity.stats.resistances.knockback != 1.0) this.entity.stats.resistances.knockback = (float)1.0;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Low) && this.entity.stats.resistances.knockback != 0.75) this.entity.stats.resistances.knockback = (float)0.75;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Lower) && this.entity.stats.resistances.knockback != 0.50) this.entity.stats.resistances.knockback = (float)0.50;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.Lowest) && this.entity.stats.resistances.knockback != 0.25) this.entity.stats.resistances.knockback = (float)0.25;
        if (this.getCombatOptions().getKnockbackResistance().equals(NpcAbstractScale.None) && this.entity.stats.resistances.knockback != 0.0) this.entity.stats.resistances.knockback = (float)0.0;
        
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Absolute) && this.entity.stats.resistances.playermelee != 2.0) this.entity.stats.resistances.playermelee = (float)2.0;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Highest) && this.entity.stats.resistances.playermelee != 1.75) this.entity.stats.resistances.playermelee = (float)1.75;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Higher) && this.entity.stats.resistances.playermelee != 1.50) this.entity.stats.resistances.playermelee = (float)1.50;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.High) && this.entity.stats.resistances.playermelee != 1.25) this.entity.stats.resistances.playermelee = (float)1.25;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Medium) && this.entity.stats.resistances.playermelee != 1.0) this.entity.stats.resistances.playermelee = (float)1.0;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Low) && this.entity.stats.resistances.playermelee != 0.75) this.entity.stats.resistances.playermelee = (float)0.75;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Lower) && this.entity.stats.resistances.playermelee != 0.50) this.entity.stats.resistances.playermelee = (float)0.50;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.Lowest) && this.entity.stats.resistances.playermelee != 0.25) this.entity.stats.resistances.playermelee = (float)0.25;
        if (this.getCombatOptions().getMeleeResistance().equals(NpcAbstractScale.None) && this.entity.stats.resistances.playermelee != 0.0) this.entity.stats.resistances.playermelee = (float)0.0;
        
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Absolute) && this.entity.stats.resistances.explosion != 2.0) this.entity.stats.resistances.explosion = (float)2.0;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Highest) && this.entity.stats.resistances.explosion != 1.75) this.entity.stats.resistances.explosion = (float)1.75;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Higher) && this.entity.stats.resistances.explosion != 1.50) this.entity.stats.resistances.explosion = (float)1.50;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.High) && this.entity.stats.resistances.explosion != 1.25) this.entity.stats.resistances.explosion = (float)1.25;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Medium) && this.entity.stats.resistances.explosion != 1.0) this.entity.stats.resistances.explosion = (float)1.0;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Low) && this.entity.stats.resistances.explosion != 0.75) this.entity.stats.resistances.explosion = (float)0.75;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Lower) && this.entity.stats.resistances.explosion != 0.50) this.entity.stats.resistances.explosion = (float)0.50;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.Lowest) && this.entity.stats.resistances.explosion != 0.25) this.entity.stats.resistances.explosion = (float)0.25;
        if (this.getCombatOptions().getExplosionResistance().equals(NpcAbstractScale.None) && this.entity.stats.resistances.explosion != 0.0) this.entity.stats.resistances.explosion = (float)0.0;
        
        if (this.getLootOptions().getLootMode().equals(NpcLootMode.AUTOPICKUP) && this.entity.inventory.lootMode != 1) this.entity.inventory.lootMode = 1;
        if (this.getLootOptions().getLootMode().equals(NpcLootMode.DEFAULT) && this.entity.inventory.lootMode != 0) this.entity.inventory.lootMode = 0;

        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Absolute)) this.entity.inventory.minExp = 250;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Highest)) this.entity.inventory.minExp = 100;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Higher)) this.entity.inventory.minExp = 50;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.High)) this.entity.inventory.minExp = 25;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Medium)) this.entity.inventory.minExp = 10;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Low)) this.entity.inventory.minExp = 5;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Lower)) this.entity.inventory.minExp = 3;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Lowest)) this.entity.inventory.minExp = 1;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.None)) this.entity.inventory.minExp = 0;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Absolute)) this.entity.inventory.maxExp = 500;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Highest)) this.entity.inventory.maxExp = 200;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Higher)) this.entity.inventory.maxExp = 100;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.High)) this.entity.inventory.maxExp = 50;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Medium)) this.entity.inventory.maxExp = 20;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Low)) this.entity.inventory.maxExp = 10;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Lower)) this.entity.inventory.maxExp = 6;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.Lowest)) this.entity.inventory.maxExp = 2;
        if (this.getLootOptions().getExpDropped().equals(NpcAbstractScale.None)) this.entity.inventory.maxExp = 0;
        if (this.getCombatOptions().getCombatResponse().equals(NpcCombatResponse.Ignore)) this.entity.ai.onAttack = 3;
        if (this.getCombatOptions().getCombatResponse().equals(NpcCombatResponse.Retreat)) this.entity.ai.onAttack = 2;
        if (this.getCombatOptions().getCombatResponse().equals(NpcCombatResponse.Panic)) this.entity.ai.onAttack = 1;
        if (this.getCombatOptions().getCombatResponse().equals(NpcCombatResponse.Retaliate)) this.entity.ai.onAttack = 0;
        if (this.getCombatOptions().getAttacksHostileFactions().equals(NpcBoolean.YES)) this.entity.advanced.attackOtherFactions = true;
        if (this.getCombatOptions().getAttacksHostileFactions().equals(NpcBoolean.NO)) this.entity.advanced.attackOtherFactions = false;
        if (this.getCombatOptions().getDefendsFactionMembers().equals(NpcBoolean.YES)) this.entity.advanced.defendFaction = true;
        if (this.getCombatOptions().getDefendsFactionMembers().equals(NpcBoolean.NO)) this.entity.advanced.defendFaction = false;
        if (this.getCombatOptions().getRangedUsage().equals(NpcRangedUsage.Always)) this.entity.ai.useRangeMelee = 0;
        if (this.getCombatOptions().getRangedUsage().equals(NpcRangedUsage.UntilClose)) this.entity.ai.useRangeMelee = 1;
        if (this.getCombatOptions().getRangedUsage().equals(NpcRangedUsage.WhenMoving)) this.entity.ai.useRangeMelee = 2;
        
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Absolute)) this.entity.stats.rangedRange = 128;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Highest)) this.entity.stats.rangedRange = 112;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Higher)) this.entity.stats.rangedRange = 96;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.High)) this.entity.stats.rangedRange = 80;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Medium)) this.entity.stats.rangedRange = 64;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Low)) this.entity.stats.rangedRange = 48;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Lower)) this.entity.stats.rangedRange = 32;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.Lowest)) this.entity.stats.rangedRange = 16;
        if (this.getCombatOptions().getRangedRange().equals(NpcAbstractScale.None)) this.entity.stats.rangedRange = 0;
        
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Absolute)) this.entity.stats.attackRange = 24;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Highest)) this.entity.stats.attackRange = 21;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Higher)) this.entity.stats.attackRange = 18;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.High)) this.entity.stats.attackRange = 15;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Medium)) this.entity.stats.attackRange = 12;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Low)) this.entity.stats.attackRange = 9;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Lower)) this.entity.stats.attackRange = 6;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.Lowest)) this.entity.stats.attackRange = 3;
        if (this.getCombatOptions().getMeleeRange().equals(NpcAbstractScale.None)) this.entity.stats.attackRange = 0;
        
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Absolute)) this.entity.stats.setMaxHealth(2500);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Highest)) this.entity.stats.setMaxHealth(1000);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Higher)) this.entity.stats.setMaxHealth(500);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.High)) this.entity.stats.setMaxHealth(250);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Medium)) this.entity.stats.setMaxHealth(100);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Low)) this.entity.stats.setMaxHealth(50);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Lower)) this.entity.stats.setMaxHealth(25);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.Lowest)) this.entity.stats.setMaxHealth(5);
        if (this.getCombatOptions().getHealth().equals(NpcAbstractScale.None)) this.entity.stats.setMaxHealth(0);
        
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Absolute)) this.entity.stats.aggroRange = 100;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Highest)) this.entity.stats.accuracy = 90;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Higher)) this.entity.stats.accuracy = 80;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.High)) this.entity.stats.accuracy = 70;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Medium)) this.entity.stats.accuracy = 60;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Low)) this.entity.stats.accuracy = 50;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Lower)) this.entity.stats.accuracy = 40;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.Lowest)) this.entity.stats.accuracy = 30;
        if (this.getCombatOptions().getRangedAccuracy().equals(NpcAbstractScale.None)) this.entity.stats.accuracy = 0;
        
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Absolute)) this.entity.stats.aggroRange = 64;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Highest)) this.entity.stats.aggroRange = 56;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Higher)) this.entity.stats.aggroRange = 48;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.High)) this.entity.stats.aggroRange = 40;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Medium)) this.entity.stats.aggroRange = 32;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Low)) this.entity.stats.aggroRange = 24;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Lower)) this.entity.stats.aggroRange = 16;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.Lowest)) this.entity.stats.aggroRange = 8;
        if (this.getCombatOptions().getAggroRadius().equals(NpcAbstractScale.None)) this.entity.stats.aggroRange = 0;
        
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Absolute)) this.entity.stats.healthRegen = 250;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Highest)) this.entity.stats.healthRegen = 100;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Higher)) this.entity.stats.healthRegen = 50;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.High)) this.entity.stats.healthRegen = 25;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Medium)) this.entity.stats.healthRegen = 10;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Low)) this.entity.stats.healthRegen = 5;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Lower)) this.entity.stats.healthRegen = 3;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.Lowest)) this.entity.stats.healthRegen = 1;
        if (this.getCombatOptions().getPassiveRegeneration().equals(NpcAbstractScale.None)) this.entity.stats.healthRegen = 0;
        
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Absolute)) this.entity.stats.combatRegen = 250;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Highest)) this.entity.stats.combatRegen = 100;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Higher)) this.entity.stats.combatRegen = 50;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.High)) this.entity.stats.combatRegen = 25;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Medium)) this.entity.stats.combatRegen = 10;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Low)) this.entity.stats.combatRegen = 5;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Lower)) this.entity.stats.combatRegen = 3;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.Lowest)) this.entity.stats.combatRegen = 1;
        if (this.getCombatOptions().getCombatRegeneration().equals(NpcAbstractScale.None)) this.entity.stats.combatRegen = 0;
        
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Absolute)) this.entity.ai.tacticalRadius = 64;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Highest)) this.entity.ai.tacticalRadius = 56;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Higher)) this.entity.ai.tacticalRadius = 48;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.High)) this.entity.ai.tacticalRadius = 40;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Medium)) this.entity.ai.tacticalRadius = 32;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Low)) this.entity.ai.tacticalRadius = 24;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Lower)) this.entity.ai.tacticalRadius = 16;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.Lowest)) this.entity.ai.tacticalRadius = 8;
        if (this.getCombatOptions().getTacticalDistance().equals(NpcAbstractScale.None)) this.entity.ai.tacticalRadius = 0;
        
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.None)) this.entity.ai.tacticalVariant = EnumNavType.None;
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.Ambush)) this.entity.ai.tacticalVariant = EnumNavType.Ambush;
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.Dodge)) this.entity.ai.tacticalVariant = EnumNavType.Dodge;
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.HitNRun)) this.entity.ai.tacticalVariant = EnumNavType.HitNRun;
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.Stalk)) this.entity.ai.tacticalVariant = EnumNavType.Stalk;
        if (this.getCombatOptions().getTacticalBehaviour().equals(NpcTacticalOption.Surround)) this.entity.ai.tacticalVariant = EnumNavType.Surround;

        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Absolute)) this.entity.display.modelSize = 8;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Highest)) this.entity.display.modelSize = 7;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Higher)) this.entity.display.modelSize = 6;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.High)) this.entity.display.modelSize = 5;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Medium)) this.entity.display.modelSize = 4;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Low)) this.entity.display.modelSize = 3;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Lower)) this.entity.display.modelSize = 2;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.Lowest)) this.entity.display.modelSize = 1;
        if (this.getBaseOptions().getSize().equals(NpcAbstractScale.None)) this.entity.display.modelSize = 0;
        
        if (this.getBehaviourOptions().getAvoidsSun().equals(NpcBoolean.YES)) this.entity.ai.avoidsSun = true;
        if (this.getBehaviourOptions().getAvoidsSun().equals(NpcBoolean.NO)) this.entity.ai.avoidsSun = false;

        if (this.getBehaviourOptions().getShelterFrom().equals(NpcShelterFromOption.Darkness)) this.entity.ai.findShelter = 0;
        if (this.getBehaviourOptions().getShelterFrom().equals(NpcShelterFromOption.Sun)) this.entity.ai.findShelter = 1;
        if (this.getBehaviourOptions().getShelterFrom().equals(NpcShelterFromOption.None)) this.entity.ai.findShelter = 2;
        if (this.getCombatOptions().getMustSeeTarget().equals(NpcBoolean.YES)) this.entity.ai.directLOS = true;
        if (this.getCombatOptions().getMustSeeTarget().equals(NpcBoolean.NO)) this.entity.ai.directLOS = false;
        if (this.getBehaviourOptions().getAvoidsWater().equals(NpcBoolean.YES)) this.entity.ai.avoidsWater = true;
        if (this.getBehaviourOptions().getAvoidsWater().equals(NpcBoolean.NO)) this.entity.ai.avoidsWater = false;
        if (this.getBehaviourOptions().getReturnToStart().equals(NpcBoolean.YES)) this.entity.ai.returnToStart = true;
        if (this.getBehaviourOptions().getReturnToStart().equals(NpcBoolean.NO)) this.entity.ai.returnToStart = false;
        if (this.getBehaviourOptions().getCanLeap().equals(NpcBoolean.YES)) this.entity.ai.canLeap = true;
        if (this.getBehaviourOptions().getCanLeap().equals(NpcBoolean.NO)) this.entity.ai.canLeap = false;
        if (this.getBehaviourOptions().getCanPassThroughCobwebs().equals(NpcBoolean.YES)) this.entity.ai.ignoreCobweb = true;
        if (this.getBehaviourOptions().getCanPassThroughCobwebs().equals(NpcBoolean.NO)) this.entity.ai.ignoreCobweb = false;
        if (this.getBehaviourOptions().getCanSprint().equals(NpcBoolean.YES)) this.entity.ai.canSprint = true;
        if (this.getBehaviourOptions().getCanSprint().equals(NpcBoolean.NO)) this.entity.ai.canSprint = false;
        
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Absolute)) this.entity.ai.setWalkingSpeed(10);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Highest)) this.entity.ai.setWalkingSpeed(9);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Higher)) this.entity.ai.setWalkingSpeed(8);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.High)) this.entity.ai.setWalkingSpeed(7);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Medium)) this.entity.ai.setWalkingSpeed(5);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Low)) this.entity.ai.setWalkingSpeed(3);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Lower)) this.entity.ai.setWalkingSpeed(2);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.Lowest)) this.entity.ai.setWalkingSpeed(1);
        if (this.getBaseOptions().getMovementSpeed().equals(NpcAbstractScale.None)) this.entity.ai.setWalkingSpeed(0);
        
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.None)) this.entity.ai.animationType = EnumAnimation.NONE;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Sneaking)) this.entity.ai.animationType = EnumAnimation.SNEAKING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Crawling)) this.entity.ai.animationType = EnumAnimation.CRAWLING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Dancing)) this.entity.ai.animationType = EnumAnimation.DANCING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Crying)) this.entity.ai.animationType = EnumAnimation.CRY;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Hugging)) this.entity.ai.animationType = EnumAnimation.HUG;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Waving)) this.entity.ai.animationType = EnumAnimation.WAVING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Sitting)) this.entity.ai.animationType = EnumAnimation.SITTING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.AimingGun)) this.entity.ai.animationType = EnumAnimation.AIMING;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.AimingBow)) this.entity.ai.animationType = EnumAnimation.BOW;
        if (this.getMovementOptions().getMovingAnimation().equals(NpcMovementAnimation.Lying)) this.entity.ai.animationType = EnumAnimation.LYING;
        if (this.getCombatOptions().getFireIndirectlyOption().equals(NpcFireIndirectlyOption.Never)) this.entity.ai.canFireIndirect = 0;
        if (this.getCombatOptions().getFireIndirectlyOption().equals(NpcFireIndirectlyOption.WhenDistant)) this.entity.ai.canFireIndirect = 1;
        if (this.getCombatOptions().getFireIndirectlyOption().equals(NpcFireIndirectlyOption.WhenSuprised)) this.entity.ai.canFireIndirect = 2;
        
        if (this.getLootOptions().getSecondaryFaction() != null) {
            this.entity.advanced.factions.decreaseFaction2Points = this.getLootOptions().getSecondaryFaction().isDecrease();
            this.entity.advanced.factions.faction2Id = this.getLootOptions().getSecondaryFaction().getFaction().getID();
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Absolute)) this.entity.advanced.factions.faction2Points = 250;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Highest)) this.entity.advanced.factions.faction2Points = 100;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Higher)) this.entity.advanced.factions.faction2Points = 50;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.High)) this.entity.advanced.factions.faction2Points = 25;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Medium)) this.entity.advanced.factions.faction2Points = 10;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Low)) this.entity.advanced.factions.faction2Points = 5;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Lower)) this.entity.advanced.factions.faction2Points = 3;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.Lowest)) this.entity.advanced.factions.faction2Points = 1;
            if (this.getLootOptions().getSecondaryFaction().getValue().equals(NpcAbstractScale.None)) this.entity.advanced.factions.faction2Points = 0;
        } else {
            this.entity.advanced.factions.faction2Points = 0;
            this.entity.advanced.factions.faction2Id = 0;
            this.entity.advanced.factions.decreaseFaction2Points = false;
        }        
        
        if (this.getLootOptions().getPrimaryFaction() != null) {
            this.entity.advanced.factions.decreaseFactionPoints = this.getLootOptions().getPrimaryFaction().isDecrease();
            this.entity.advanced.factions.factionId = this.getLootOptions().getPrimaryFaction().getFaction().getID();
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Absolute)) this.entity.advanced.factions.factionPoints = 250;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Highest)) this.entity.advanced.factions.factionPoints = 100;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Higher)) this.entity.advanced.factions.factionPoints = 50;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.High)) this.entity.advanced.factions.factionPoints = 25;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Medium)) this.entity.advanced.factions.factionPoints = 10;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Low)) this.entity.advanced.factions.factionPoints = 5;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Lower)) this.entity.advanced.factions.factionPoints = 3;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.Lowest)) this.entity.advanced.factions.factionPoints = 1;
            if (this.getLootOptions().getPrimaryFaction().getValue().equals(NpcAbstractScale.None)) this.entity.advanced.factions.factionPoints = 0;
        } else {
            this.entity.advanced.factions.factionPoints = 0;
            this.entity.advanced.factions.factionId = 0;
            this.entity.advanced.factions.decreaseFactionPoints = false;
        }
        
        // INTERACT LINE POPULATION //
        
        entity.advanced.interactLines.lines.clear();
        for (String line : this.getInteractOptions().getInteractLines()) {
            int lineNumber = 0;
            if (!this.entity.advanced.interactLines.lines.isEmpty() && !this.entity.advanced.interactLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.interactLines.lines.size();
            Line cNpcsLine = new Line();
            cNpcsLine.hideText = true;
            cNpcsLine.text = line;
            cNpcsLine.sound = "";
            this.entity.advanced.interactLines.lines.put(lineNumber, cNpcsLine);
        }
        
        // KILL LINE POPULATION //
        
        entity.advanced.killLines.lines.clear();
        for (String line : this.getInteractOptions().getKillLines()) {
            int lineNumber = 0;
            if (!this.entity.advanced.killLines.lines.isEmpty() && !this.entity.advanced.killLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.killLines.lines.size();
            Line cNpcsLine = new Line();
            cNpcsLine.hideText = true;
            cNpcsLine.text = line;
            cNpcsLine.sound = "";
            this.entity.advanced.killLines.lines.put(lineNumber, cNpcsLine);
        }
        
        entity.advanced.killedLines.lines.clear();
        for (String line : this.getInteractOptions().getDeathLines()) {
            int lineNumber = 0;
            if (!this.entity.advanced.killedLines.lines.isEmpty() && !this.entity.advanced.killedLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.killedLines.lines.size();
            Line cNpcsLine = new Line();
            cNpcsLine.hideText = true;
            cNpcsLine.text = line;
            cNpcsLine.sound = "";
            this.entity.advanced.killedLines.lines.put(lineNumber, cNpcsLine);
        }
        
        entity.advanced.attackLines.lines.clear();
        for (String line : this.getInteractOptions().getAttackLines()) {
            int lineNumber = 0;
            if (!this.entity.advanced.attackLines.lines.isEmpty() && !this.entity.advanced.attackLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.attackLines.lines.size();
            Line cNpcsLine = new Line();
            cNpcsLine.hideText = true;
            cNpcsLine.text = line;
            cNpcsLine.sound = "";
            this.entity.advanced.attackLines.lines.put(lineNumber, cNpcsLine);
        }
        
        entity.advanced.worldLines.lines.clear();
        for (String line : this.getInteractOptions().getWorldLines()) {
            int lineNumber = 0;
            if (!this.entity.advanced.worldLines.lines.isEmpty() && !this.entity.advanced.worldLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.worldLines.lines.size();
            Line cNpcsLine = new Line();
            cNpcsLine.hideText = true;
            cNpcsLine.text = line;
            cNpcsLine.sound = "";
            this.entity.advanced.worldLines.lines.put(lineNumber, cNpcsLine);
        }
        
        this.entity.ai.orientation = this.getMovementOptions().getRotation().getDegree();
        this.entity.ai.stopAndInteract = (this.getInteractOptions().getStopsOnInteract().equals(NpcBoolean.YES));
        this.entity.stats.aimWhileShooting = (this.getBehaviourOptions().getAimsWhileShooting().equals(NpcBoolean.YES));
        this.entity.ai.canSwim = (this.getBehaviourOptions().getCanSwim().equals(NpcBoolean.YES));
        this.entity.ai.npcInteracting = (this.getInteractOptions().getInteractsWithOtherNpcs().equals(NpcBoolean.YES));
        this.entity.stats.burnInSun = (this.getBehaviourOptions().getBurnsInSun().equals(NpcBoolean.YES));
        this.entity.stats.attackInvisible = (this.getCombatOptions().getCanAttackInvisibleTargets().equals(NpcBoolean.YES));
        this.entity.stats.noFallDamage = (this.getBehaviourOptions().getSuffersFallDamage().equals(NpcBoolean.YES));
        this.entity.stats.canDrown = (this.getBehaviourOptions().getCanDrown().equals(NpcBoolean.YES));
        this.entity.stats.potionImmune = (this.getBehaviourOptions().getImmuneToPotions().equals(NpcBoolean.YES));
        this.entity.stats.immuneToFire = (this.getBehaviourOptions().getImmuneToFire().equals(NpcBoolean.YES));
        
        // START LOOT TABLE REPOPULATION //
        
        entity.inventory.items.clear();
        entity.inventory.dropchance.clear();
        int index = 0;        
        for (NpcItem item : this.getLootOptions().getLootTableReadOnly().keySet()) {
            int chance = this.getLootOptions().getLootTableReadOnly().get(item);
            entity.inventory.items.put(index, item.getItem());
            entity.inventory.dropchance.put(index, chance);
            index++;
        }
        
        // END LOOT TABLE REPOPULATION //

        this.revive();
        // This is important so that the NPC doesnt constantly update.
        this.markedForUpdate = false;
    }
//    
//    public boolean addToLootTable(String mod, String item, int percentChanceDrop, int numberOf, int dmg) {
//        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
//        if (entity.inventory.items.size() > 8) return false;
//        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
//        if (stack.getMaxStackSize() < numberOf) return false;
//        stack.setItemDamage(dmg);
//        int index = entity.inventory.items.size();
//        entity.inventory.items.put(index, stack);
//        entity.inventory.dropchance.put(index, percentChanceDrop);
//        return entity.inventory.items.get(index).equals(stack);
//    }
    
    public boolean isInCombat() {
        return this.entity.isAttacking();
    }
    
//    public String getRandomRepeatableUnavailableLine(String PlayerName) {
//       if (randomRepeatableUnavailableLines.isEmpty()) return "I have nothing for you now, check back later.";
//       if (randomRepeatableUnavailableLines.size() == 1) return randomRepeatableUnavailableLines.get(0).replace("{player}", PlayerName).replace("@dp", PlayerName);
//       Random ran = new Random();
//       int number = ran.nextInt(randomRepeatableUnavailableLines.size());
//       if (number != 0) number = number - 1;
//       return randomRepeatableUnavailableLines.get(number).replace("{player}", PlayerName).replace("@dp", PlayerName);
//    }
//    
//    public void addRandomNotRepeatableLine(String s) {
//        if (!randomRepeatableUnavailableLines.contains(s)) randomRepeatableUnavailableLines.add(s);
//    }
//    
//    public void setForcedSpawnPosition(uPosition position) {
//        this.spawnPosition = position;
//        this.entity = new EntityCustomNpc(this.getForgeWorld(spawnPosition.getDimension().getIdentifier()));
//        this.entity.stats.setMaxHealth(maxHealth);
//        if (this.template != null && !this.template.equals("NONE")) this.setLinkedTemplate(template);
//        this.setPosition(spawnPosition);
//        this.cleanup();
//    }
//    
//    public void setRoleNone() {
//        this.entity.advanced.role = EnumRoleType.None;
//        this.entity.roleInterface = null;
//        this.revive();
//    }
    
//    public void setJobNone() {
//        this.entity.advanced.job = EnumJobType.None;
//        this.entity.jobInterface = null;
//        this.revive();
//    }
//    
//    public void setJobHealer(int range, int speed) {
//        JobHealer job = new JobHealer(entity);
//        this.entity.advanced.job = EnumJobType.Healer;
//        job.speed = speed;
//        job.range = range;
//        entity.jobInterface = (JobInterface)job;  
//        this.revive();
//    }
//    
//    public void setRoleTrader(boolean ignoreDamage, boolean ignoreNBT) {
//        RoleTrader role = new RoleTrader(entity);
//        this.entity.advanced.role = EnumRoleType.Trader;
//        role.ignoreDamage = ignoreDamage;
//        role.ignoreNBT = ignoreNBT;        
//        entity.roleInterface = (RoleInterface)role;
//        this.revive();
//    }
    
    
//    public void addItemToTrader(ItemStack stack1, ItemStack stack2, ItemStack stackResult, int position) {
//        if (position <= 0) return;
//        RoleTrader role = (RoleTrader)entity.roleInterface;
//        //System.out.println("Slot is: " + role.inventorySold.firstFreeSlot());
//        //System.out.println("Currency slot is : " + role.inventoryCurrency.firstFreeSlot());
//        if (stackResult != null) role.inventorySold.items.put(position-1, stackResult);
//        if (stack1 != null && stackResult != null) role.inventoryCurrency.items.put(position-1, stack1);
//        if (stack2 != null && stackResult != null) role.inventoryCurrency.items.put((position-1)+18, stack2);
//        role.toSave = true;
//        entity.roleInterface = (RoleInterface)role;
//        revive();
//    }
//    
//    public void setRoleTransporter(String transportName) {
//        TransportLocation desiredLocation = TransportController.getInstance().getTransport(transportName);
//        this.entity.advanced.role = EnumRoleType.Transporter;
//        RoleTransporter role = new RoleTransporter(entity);
//        role.setTransport(desiredLocation);
//        entity.roleInterface = (RoleInterface)role;
//        this.revive();
//    }
    
    public EntityCustomNpc getEntity() {
        return this.entity;
    }
        
    public String getWorldName() {
        return this.entity.worldObj.getWorldInfo().getWorldName();
    }
    
    public double getPosX() {
        return this.entity.posX;
    }
    
    public double getPosY() {
        return this.entity.posY;
    }
    
    public double getPosZ() {
        return this.entity.posZ;
    }
    
    public uPosition getUPosition() {
        return new uPosition(getPosX(), getPosY(), getPosZ(), MMOCore.getInstance().getDimensionRegistry().getRegistered(getWorldName()));
    }
    
    private boolean spawnInWorld() {
        ForgeAPI.getForgeWorld(this.getBaseOptions().getSpawnPosition().getDimension()).spawnEntityInWorld(entity);
        entity.setPositionAndUpdate(this.getBaseOptions().getSpawnPosition().getDPosX(), this.getBaseOptions().getSpawnPosition().getDPosY(), this.getBaseOptions().getSpawnPosition().getDPosZ());
        return existsInGame();
    }
    
//    public boolean setPassiveWeapon(String modFrom, String name, int dmg) {
//        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        entity.inventory.setWeapon(stack);
//        this.passiveMainHand = stack;
//        //System.out.println("Set: " + entity.inventory.getWeapon().equals(stack));
//        return entity.inventory.getWeapon().equals(stack);
//    }
//    
//    public boolean setCombatWeapon(String modFrom, String name, int dmg) {
//        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        this.combatMainHand = stack;
//        return combatMainHand != null;
//    }
//    public boolean setMeleeWeapon(String modFrom, String name, int dmg) {
//        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        this.meleeMainHand = stack;
//        return meleeMainHand != null;
//    }
//    public boolean setMeleeOffhand(String modFrom, String name, int dmg) {
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        this.meleeOffHand = stack;
//        return meleeOffHand != null;
//    }
//    public boolean setPassiveOffhand(String modFrom, String name, int dmg) {
//        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        this.passiveOffHand = stack;
//        entity.inventory.setOffHand(stack);
//        return entity.inventory.getOffHand().equals(stack);
//    }
//    
//    public boolean setCombatOffhand(String modFrom, String name, int dmg) {
//        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
//        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
//        stack.setItemDamage(dmg);
//        this.combatOffHand = stack;
//        return combatOffHand != null;
//    }
    
    public void refresh() {
        entity.updateClient = true;
        entity.updateAI = true;
    }
    
//    public void moveTo(int posX, int posY, int posZ, int secondsToComplete) {
//        List<int[]> path = new ArrayList<int[]>();
//        int[] currentLocation = { (int)this.actualPosition.getPosInDimX(), (int)this.actualPosition.getPosInDimY(), (int)this.actualPosition.getPosInDimZ() };
//        int[] coordinates = { posX, posY, posZ };
//        path.add(currentLocation);
//        path.add(coordinates);
//        entity.ai.setMovingPath(path);
//        setMovingType(EnumMovingType.MovingPath);
//    }
    
    public boolean isDead() {
        return entity.hasDied;
    }
    
    public boolean revive() {
        entity.updateAI = true;
        entity.updateClient = true;
        entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
        entity.reset();
        return !isDead();
    }
    
    public boolean isPathCompleted() {
        //if (!isMoving() || entity.ai.getMovingPath().isEmpty()) return true;
        int[] lastPoint = entity.ai.getMovingPath().get(entity.ai.getMovingPath().size()-1);
        return entity.ai.getCurrentMovingPath() == lastPoint && entity.ai.getDistanceSqToPathPoint() < 3;
    }
    
    public void followPath(List<int[]> path, int secondsToComplete) {
        entity.ai.setMovingPath(path);
        setMovingType(EnumMovingType.MovingPath);
        entity.ai.movingPos = 0;
    }
    
    public void followPathConstantly(List<int[]> path) {
        entity.ai.setMovingPath(path);
        entity.ai.movingPos = 0;
        setMovingType(EnumMovingType.MovingPath);
    }
    
    public void register() {
        if (this.existsInGame()) this.findCustomNpcInGame().delete();
        this.setPosition(this.getBaseOptions().getSpawnPosition());
        this.spawn();
        refresh();
        revive();
        MMOCore.getInstance().getNpcRegistry().register(new RegisterableNpc(this, NpcSpawnMethod.Static));
    }
    
    public UUID getUniqueID() {
        return this.entity.getUniqueID();
    }
    
    private void spawn() {
        //System.out.println("NPC: " + this.entity.display.name + " is being added to the game with template: " + this.entity.linkedName);
        this.spawnInWorld();
        if (this.existsInGame()) {
            //System.out.println("NPC: " + this.getName() + " has spawned!");
        }
       // if (!this.existsInGame()) //("NPC: " + this.getName() + " has failed to spawn!");
    }
    
    private void setPosition(uPosition position) {
        this.entity.posX = position.getDPosX();
        this.entity.posY = position.getDPosY();
        this.entity.posZ = position.getDPosZ();
        int[] startPos = { (int)position.getDPosX(), (int)position.getDPosY(), (int)position.getDPosZ() };
        this.entity.ai.startPos = startPos;
    }
    
//    public boolean stopMoving() {
//        //if (this.isMoving()) {
//            int[] startPos = { (int)this.actualPosition.getPosInDimX(), (int)this.actualPosition.getPosInDimY(), (int)this.actualPosition.getPosInDimZ() };
//            setStartPosition(startPos);
//            setMovingType(EnumMovingType.Standing);
//            this.isMoving = false;
//            return true;
//        //} else {
//            //return false;
//        //}
//    }

    public EntityCustomNpc findCustomNpcInGame() {        
    List<Entity> entities = ForgeAPI.getForgeWorld(this.getUPosition().getDimension()).loadedEntityList; 
        for (Entity entity : entities) {
            if (entity instanceof EntityCustomNpc) {
                if (entity.getUniqueID().equals(this.getUniqueID())) {
                    return (EntityCustomNpc)entity;
                }
            }
        }
       // PC in-game: " + this.getName());
        return null;
    }
    
    public void setWandering(int range) {
        entity.ai.movingType = EnumMovingType.Wandering;
        entity.ai.walkingRange = range;
    }
    
    private void setMovingType(EnumMovingType type) {
        entity.ai.movingType = type;
    }
    
    public void tick() {
        EntityCustomNpc foundNpc = this.findCustomNpcInGame();
        if (foundNpc != null) {
            this.entity = foundNpc;
            if (this.markedForUpdate) this.pushOptionsToEntity();            
        } else {
            this.setMarkedForRemoval();
        }
    }

//    public void backTrackWhileFollowingPath() {
//        this.entity.ai.movingPattern = 1;
//    }
//    
//    public void loopWhileFollowingPath() {
//        this.entity.ai.movingPattern = 0;
//    }
//    

//
//    
//    public void setMeleeKnockback(int value) {
//        this.entity.stats.knockback = value;
//    }
//    
//    public void setMeleeDamage(int strength) {
//        this.entity.stats.setAttackStrength(strength);
//    }
//    
//    public void pauseWhileFollowingPath(boolean value) {
//        this.entity.ai.movingPause = value;
//    }
//    
//    public void setProjectileKnockBack(int value) {
//        this.entity.stats.pImpact = value;
//    }
    
//    public boolean setProjectileEffect(String type, int duration, int amplifiedBy) {
//        if (EnumPotionType.valueOf(type) == null) return false;
//        this.entity.stats.pEffect = EnumPotionType.valueOf(type);
//        this.entity.stats.pDur = duration;
//        this.entity.stats.pEffAmp = amplifiedBy;
//        return true;
//    }
//    
//    public void setProjectileGlows(boolean value) {
//        this.entity.stats.pGlows = value;
//    }
//    
//    public boolean setJob(String jobName) {
//        if (EnumJobType.valueOf(jobName) == null) return false;
//        this.entity.advanced.job = EnumJobType.valueOf(jobName);
//        return true;
//    }
//    

//    
//    public void setStandingType(String type) {
//        if (EnumStandingType.valueOf(type) == null) return;
//        this.entity.ai.standingType = EnumStandingType.valueOf(type);
//    }
//    
//    public boolean setRole(String roleName) {
//        if (EnumRoleType.valueOf(roleName) == null) return false;
//        this.entity.advanced.role = EnumRoleType.valueOf(roleName);
//        return true;
//    }
//    
//
//    
//    public boolean getNaturallyDespawns() {
//        return this.entity.stats.canDespawn;
//    }
//    
//    public void naturallyDespawns(boolean value) {
//        this.entity.stats.canDespawn = value;
//    }
//    
//    public void setMinDelayRangedFiring(int value) {
//        this.entity.stats.minDelay = value;
//    }
//    
//    public void setMaxDelayRangedFiring(int value) {
//        this.entity.stats.maxDelay = value;
//    }
//    
//    public void setFireRate(int value) {
//        this.entity.stats.fireRate = value;
//    }
//    
//    public void setFireSound(String sound) {
//        this.entity.stats.fireSound = sound;
//    }
//    
//    public void setBurstCount(int number) {
//        this.entity.stats.burstCount = number;
//    }
//    
//    public void setShotCountPerRound(int number) {
//        this.entity.stats.shotCount = number;
//    }
//    
//    public void setCapeTexture(String texture) {
//        if (texture.equals("NONE")) return;
//        this.entity.display.cloakTexture = texture;
//    }
//    
//    public void hasLivingAnimation(boolean yesOrNo) {
//        this.entity.display.disableLivingAnimation = !yesOrNo;
//    }
//    
//    public void setOverlayTexture(String texture) {
//        if (texture.equals("NONE")) return;
//        this.entity.display.glowTexture = texture;
//    }
//    
//    public void tellNear(String message) {
//        Line line = new Line(message);
//        line.hideText = true;
//        line.sound = "";
//        if (entity != null) entity.saySurrounding(line);
//    }
//    
//    public void sayRandomLines(boolean value) {
//        entity.advanced.orderedLines = !value;
//    }
    
//    public List<DialogOption> getOptionsRecursively(DialogOption opt) {
//        List<DialogOption> options = new ArrayList<DialogOption>();
//        if (opt.hasDialog()) {
//            if (!options.contains(opt)) options.add(opt);
//            if (opt.hasDialog() && opt.getDialog().hasOtherOptions()) {
//                options.add(opt);
//                for (DialogOption subopt : opt.getDialog().options.values()) {
//                    if (!options.contains(subopt)) options.add(subopt);
//                    if (subopt.hasDialog() && subopt.getDialog().hasOtherOptions()) {
//                        for (DialogOption subsubopt : subopt.getDialog().options.values()) {
//                            if (!options.contains(subsubopt)) options.add(subsubopt);
//                            if (subsubopt.hasDialog() && subsubopt.getDialog().hasOtherOptions()) {
//                                for (DialogOption subsubsubopt : subsubopt.getDialog().options.values()) {
//                                    if (!options.contains(subsubsubopt)) options.add(subsubsubopt);
//                                    if (subsubsubopt.hasDialog() && subsubsubopt.getDialog().hasOtherOptions()) {
//                                        for (DialogOption subsubsubsubopt : subsubsubopt.getDialog().options.values()) {
//                                            if (!options.contains(subsubsubsubopt)) options.add(subsubsubsubopt);
//                                            if (subsubsubsubopt.hasDialog() && subsubsubsubopt.getDialog().hasOtherOptions()) {
//                                                for (DialogOption subsubsubsubsubopt : subsubsubsubopt.getDialog().options.values()) {
//                                                    if (!options.contains(subsubsubsubsubopt)) options.add(subsubsubsubsubopt);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return options;
//    }
    
//    public List<Quest> getRepeatableQuestsFromOption(DialogOption opt) {
//        List<Quest> quests = new ArrayList<Quest>();
//        for (DialogOption sopt : getOptionsRecursively(opt)) {
//            if (sopt.hasDialog() && sopt.getDialog().hasQuest() || sopt.command.contains("givequest")) {
//                if (sopt.command.contains("givequest")) {
//                    List<String> split = Arrays.asList(sopt.command.split(" "));
//                    String questName = "";
//                    for (String s : split) {
//                        if (s.equals("s") || s.equals("givequest") || s.equals("@dp") || s.equals("{player}")) continue;
//                        if (!"".equals(questName)) questName = questName + " " + s;
//                        if ("".equals(questName)) questName = s;
//                    }
//                    Quest q = StargateMCMod.getInstance().getForgeAPI().getQuestFromTitle(questName);
//                    if (q != null) {
//                        if (q.repeat != EnumQuestRepeat.NONE) {
//                            if (!quests.contains(q)) quests.add(q);
//                        }
//                    } 
//                } else {
//                    if (sopt.getDialog().getQuest().repeat != EnumQuestRepeat.NONE) {
//                        if (!quests.contains(sopt.getDialog().getQuest())) quests.add(sopt.getDialog().getQuest());
//                    }
//                }
//            }
//        }
//        return quests;
//    }
    
//    public List<Quest> getRepeatableQuests() {
//        List<Quest> quests = new ArrayList<Quest>();
//        for (DialogOption dialogOption : entity.dialogs.values()) {
//            quests.addAll(getRepeatableQuestsFromOption(dialogOption));
//        }
//        return quests;
//    }
//    
//    public boolean hasRepeatableQuests() {
//        return !getRepeatableQuests().isEmpty();
//    }
    
    public void despawn() {
        if (existsInGame())  entity.delete();
    }
    
    public boolean existsInGame() {
        return findCustomNpcInGame() != null;
    }
    
//    public boolean teleport(UPosition destination) {
//        this.despawn();
//        this.setPosition(destination);
//        this.spawn();
//        return this.existsInGame();
//    }
    

    
//    public void setModelClass(String modelClass) {
//        try {
//            Class c = Class.forName(modelClass);
//            this.entity.modelData.setEntityClass(c);
//        } catch (Exception e) {
//            //System.out.println("Failed to assign modelData: "  + e.getMessage());
//        }
//    }
    
//    public void addDialogDialogOption(String dialogTitle, int color, int position) {
//        DialogOption dialogOption = new DialogOption();
//        dialogOption.optionType = EnumOptionType.DialogOption;
//        Dialog d = null;
//        for (Dialog dialog : DialogController.instance.dialogs.values()) {
//            if (dialog.title.equals(dialogTitle)) d = dialog;
//        }
//        if (d == null) return;        
//        dialogOption.optionColor = color;
//        dialogOption.dialogId = d.id;
//        dialogOption.title = d.title;
//        dialogOption.command = "NOCOMMAND";
//        entity.dialogs.put(position-1, dialogOption);
//    }    
    
}

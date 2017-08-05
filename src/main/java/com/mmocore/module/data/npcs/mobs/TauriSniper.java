/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.npcs.mobs;

import com.mmocore.MMOCore;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcFactionValue;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTacticalOption;
import com.mmocore.constants.NpcTexture;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.options.NpcCombatOptions;
import com.mmocore.module.Npc.options.NpcLootOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import java.util.Random;

/**
 *
 * @author draks
 */
public class TauriSniper extends RegisterableNpc {
    
    public TauriSniper() {
        super(  "Tauri Sniper",
                "Stargate Command",
                NpcTexture.TAURI_SOLDIER_6,
                NpcModifier.RANGED_SOLDIER,
                NpcSpawnMethod.Static,
                NpcFactionAPI.getRegistered("Stargate Command")
        );
        
        NpcHeldItemSet weapons = this.getRangedHeldItems();
        NpcItem heldItem = new NpcItem("flansmod", "m40a3", 1, 0);
        weapons.setMainHand(heldItem);
        this.setRangedHeldItems(weapons);
        this.setPassiveHeldItems(weapons);
        NpcCombatOptions cOpts = this.getCombatOptions();
        cOpts.setAggroRadius(AbstractScale.Absolute);
        cOpts.setRangedRange(AbstractScale.Absolute);
        cOpts.setRangedAccuracy(AbstractScale.Highest);
        cOpts.setRangedDamage(AbstractScale.Highest);
        cOpts.setTacticalDistance(AbstractScale.Highest);
        cOpts.setTacticalBehaviour(NpcTacticalOption.Stalk);
        cOpts.setFireWeaponSound(NpcSound.Sniper);
        cOpts.setProjectile(NpcProjectile.SNIPER_BULLET);
        cOpts.setAttacksHostileFactions(NpcBoolean.YES);
        NpcLootOptions lOpts = this.getLootOptions();
        NpcItem item = new NpcItem();
        item.setItem("flansmod", "m40a3", 1, 0);
        lOpts.addToLootTable(50, item);
        lOpts.setPrimaryFaction(new NpcFactionValue(NpcFactionAPI.getRegistered("Stargate Command"), AbstractScale.Lower, false));
        lOpts.setSecondaryFaction(new NpcFactionValue(NpcFactionAPI.getRegistered("Goauld"), AbstractScale.Lower, true));
        this.setLootOptions(lOpts);
        this.setCombatOptions(cOpts);
        NpcMovementOptions mOpts = this.getMovementOptions();
        mOpts.setMovementTypeWandering(AbstractScale.Absolute);
        this.setMovementOptions(mOpts);
    }
}

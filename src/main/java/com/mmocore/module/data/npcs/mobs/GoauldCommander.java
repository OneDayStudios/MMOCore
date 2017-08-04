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
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcSpawnMethod;
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
public class GoauldCommander extends RegisterableNpc {
    
    public GoauldCommander() {
        super(  "Goauld Commander",
                "Goauld Loyal Jaffa",
                NpcTexture.JAFFA_FIRST_PRIME,
                NpcModifier.RANGED_COMMANDER,
                NpcSpawnMethod.Static,
                NpcFactionAPI.getRegistered("Goauld")
        );
        
        NpcHeldItemSet weapons = this.getRangedHeldItems();
        NpcItem heldItem = new NpcItem("flansmod", "maTokStaff", 1, 0);
        weapons.setMainHand(heldItem);
        this.setRangedHeldItems(weapons);
        this.setPassiveHeldItems(weapons);
        NpcCombatOptions cOpts = this.getCombatOptions();
        cOpts.setFireWeaponSound(NpcSound.MatokStaff);
        cOpts.setProjectile(NpcProjectile.GOLD_PLASMA);
        NpcMovementOptions mOpts = this.getMovementOptions();
        mOpts.setMovementTypeWandering(AbstractScale.Absolute);
        this.setMovementOptions(mOpts);
        cOpts.setAttacksHostileFactions(NpcBoolean.YES);
        NpcLootOptions lOpts = this.getLootOptions();
        NpcItem item = new NpcItem();
        item.setItem("flansmod", "maTokStaff", 1, 0);
        lOpts.addToLootTable(50, item);
        this.setLootOptions(lOpts);
        this.setCombatOptions(cOpts);
    }
}

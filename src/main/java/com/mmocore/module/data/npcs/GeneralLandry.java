/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.npcs;

import com.mmocore.MMOCore;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcCombatOptions;

/**
 *
 * @author draks
 */
public class GeneralLandry {

    public static RegisterableNpc get() {
            RegisterableNpc npc = new RegisterableNpc("Jaffa Soldier", "Goauld Loyal Jaffa", NpcTexture.TAURI_HANK_LANDRY, NpcModifier.RANGED_SOLDIER, NpcSpawnMethod.Static, NpcFactionAPI.getRegistered("Free Jaffa Nation"));
            NpcHeldItemSet weapons = npc.getRangedHeldItems();
            NpcItem heldItem = new NpcItem("flansmod", "maTokStaff", 1, 0);
            weapons.setMainHand(heldItem);
            npc.setRangedHeldItems(weapons);
            npc.setPassiveHeldItems(weapons);
            NpcCombatOptions cOpts = npc.getCombatOptions();
            cOpts.setFireWeaponSound(NpcSound.MatokStaff);
            cOpts.setProjectile(NpcProjectile.GOLD_PLASMA);
            npc.setCombatOptions(cOpts);
            return npc;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.factions;

import com.mmocore.module.data.npcs.*;
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
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.NpcFactionDictionary;

/**
 *
 * @author draks
 */
public class Goauld {

    public static RegisterableNpcFaction get() {
            RegisterableNpcFaction faction = new RegisterableNpcFaction("Goauld");
            faction.addHostileFaction(NpcFactionDictionary.FREE_JAFFA_NATION.get(), true);
            faction.addHostileFaction(NpcFactionDictionary.SGC.get(), true);
            faction.setDefaultPoints(0);
            return faction;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.GameEventDictionary;
import com.mmocore.module.data.NpcDictionary;
import com.mmocore.module.data.NpcFactionDictionary;

/**
 *
 * @author draks
 */
public class DictionaryAPI extends AbstractAPI<DictionaryAPI> {
    
    public static void loadNpcs() {
        for (NpcDictionary npc : NpcDictionary.values()) {
           if (npc.get().getBaseOptions().getSpawnPosition() != null) MMOCore.getNpcRegistry().register(npc.get());
        }
    }
    
    public static void loadNpcFactions() {
        for (NpcFactionDictionary faction : NpcFactionDictionary.values()) {
            RegisterableNpcFaction registered = NpcFactionAPI.getRegistered(faction.get().getName());
            if (registered == null) MMOCore.getNpcFactionRegistry().register(faction.get());
        }
    }
    
    public static void loadGameEvents() {
        for (GameEventDictionary definedEvent : GameEventDictionary.values()) {
            MMOCore.getGameEventRegistry().register(definedEvent.get());
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.module.data.GameEventDictionary;
import com.mmocore.module.data.NpcDictionary;
import com.mmocore.module.data.NpcFactionDictionary;

/**
 *
 * @author draks
 */
public class DictionaryAPI extends AbstractAPI<DictionaryAPI> {
    
    private static NpcDictionary[ ] npcs = NpcDictionary.values(); 
    private static NpcFactionDictionary[ ] factions = NpcFactionDictionary.values(); 
    private static GameEventDictionary[ ] events = GameEventDictionary.values(); 
    
    public static void loadNpcs() {
        for (NpcDictionary npc : npcs) {
           if (npc.get().getBaseOptions().getSpawnPosition() != null) MMOCore.getNpcRegistry().register(npc.get());
        }
    }
    
    public static void loadNpcFactions() {
        for (NpcFactionDictionary faction : factions) {
            MMOCore.getNpcFactionRegistry().register(faction.get());
        }
    }
    
    public static void loadGameEvents() {
        for (GameEventDictionary definedEvent : events) {
            MMOCore.getGameEventRegistry().register(definedEvent.get());
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data;

import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.data.events.RandomSpawnContestedWorlds;
import com.mmocore.module.data.npcs.FreeJaffaSoldier;
import com.mmocore.module.data.npcs.GeneralLandry;
import com.mmocore.module.data.npcs.GoauldJaffaSoldier;
import com.mmocore.module.data.npcs.StargateCommandSoldier;

/**
 *
 * @author draks
 */
public enum GameEventDictionary {

    RANDOM_SPAWN_CONTESTED_WORLDS(RandomSpawnContestedWorlds.get());
    
    private final GameEvent event;
    
    GameEventDictionary(GameEvent event) {
        this.event = event;
    }
    
    public GameEvent get() {
        return this.event;
    }
}

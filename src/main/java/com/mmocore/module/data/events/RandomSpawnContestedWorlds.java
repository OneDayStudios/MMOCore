/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.events;

import com.mmocore.MMOCore;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.GameEvent.events.RandomSpawnEvent;
import com.mmocore.module.GameEvent.events.options.RandomSpawnEventOptions;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.data.factions.*;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.NpcDictionary;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class RandomSpawnContestedWorlds {

    public static GameEvent get() {
            ArrayList<RegisterableNpc> npcs = new ArrayList<RegisterableNpc>();
            npcs.add(NpcDictionary.GOAULD_JAFFA_SOLDIER.get());
            RandomSpawnEvent gameEvent = new RandomSpawnEvent("Random Spawn - Uncontrolled Worlds", npcs);
            RandomSpawnEventOptions options = gameEvent.getOptions();
            for (RegisterableDimension dimension : UniverseAPI.getDimensionsReadOnly()) {
                if (dimension.getFaction() == null) options.addSpawnDimension(dimension);
            }
            gameEvent.setOptions(options);
            return gameEvent;
    }
    
}

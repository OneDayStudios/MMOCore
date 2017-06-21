/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent;

import com.mmocore.MMOCore;
import com.mmocore.api.EventAPI;
import com.mmocore.module.Galaxy.*;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.events.RandomSpawnEvent;
import com.mmocore.module.GameEvent.events.options.SpawnEventOptions;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcCombatOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author draks
 */
public class GameEventRegistry extends AbstractRegistry<GameEventRegistry, String, GameEvent> {

    @Override
    public void initialise() {
        if (ForgeAPI.isServer()) {

        }
        // Not required.
    }
    
    public void tickForDimension(RegisterableDimension dimension) {
        Collection<GameEvent> events = this.getRegistered().values();
        ArrayList<String> eventsToRemove = new ArrayList<String>();
        for (GameEvent event : EventAPI.getEventsForDimension(dimension)) {
            if (!dimension.getIsLoaded() || dimension.isFake()) continue;
            if (!event.getFlaggedForRemoval()) {            
                event.tickForDimension(dimension);
            } else {
                eventsToRemove.add(event.getIdentifier());
            }
        }
        for (String id : eventsToRemove) {
            this.deregister(id);
        }
    }
    
    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.events;

import com.mmocore.MMOCore;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.AbstractScale;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.GameEvent.events.RandomSpawnEvent;
import com.mmocore.module.GameEvent.events.options.SpawnEventOptions;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.data.factions.*;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.data.AbstractDictionary;
import com.mmocore.module.data.npcs.GoauldJaffaSoldier;
import com.mmocore.module.data.npcs.StargateCommandSoldier;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class RandomSpawnContestedWorlds extends RandomSpawnEvent {

    public RandomSpawnContestedWorlds() {
            super("Random Spawn - Uncontrolled Worlds");
            RegisterableNpc sgcSoldier = AbstractDictionary.getNpcByName("SGC Soldier", "Stargate Command");
            NpcMovementOptions mOpts = sgcSoldier.getMovementOptions();
            mOpts.setMovementTypeWandering(AbstractScale.Medium);
            sgcSoldier.setMovementOptions(mOpts);
            RegisterableNpc jaffaSoldier = AbstractDictionary.getNpcByName("Jaffa Soldier", "Goauld Loyal Jaffa");
            NpcMovementOptions jMOpts = jaffaSoldier.getMovementOptions();
            jMOpts.setMovementTypeWandering(AbstractScale.Medium);
            jaffaSoldier.setMovementOptions(jMOpts);
            this.addNpc(sgcSoldier);
            this.addNpc(jaffaSoldier);
            SpawnEventOptions options = this.getOptions();
            for (RegisterableDimension dimension : UniverseAPI.getDimensions()) {
                if (dimension.getFaction() == null) options.addSpawnDimension(dimension);
            }
            options.setDimensionDensity(25);
            this.setOptions(options);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.events;

import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.NpcRotation;
import com.mmocore.constants.uPosition;
import com.mmocore.module.GameEvent.events.CloneLoadEvent;
import com.mmocore.module.data.AbstractDictionary;
import com.mmocore.module.data.npcs.mobs.TauriSoldier;

/**
 *
 * @author draks
 */
public class StargateCommandMarines extends CloneLoadEvent {
    
    public StargateCommandMarines() {
        super("Stargate Command Marine Spawns");
        addSpawningNpc(AbstractDictionary.getNpcByName("Tauri Soldier", "Stargate Command"));
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-132.0, 7.0, -627.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.NORTH, new uPosition(-144.0, 5.0, -616.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.NORTH, new uPosition(-140.0, 5.0, -616.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-138.0, 5.0, -627.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-142.0, 5.0, -627.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.WEST, new uPosition(-124.0, 15.0, -618.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.WEST, new uPosition(-126.0, 15.0, -610.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.NORTH, new uPosition(-129.0, 15.0, -596.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ")));
    }
    
}

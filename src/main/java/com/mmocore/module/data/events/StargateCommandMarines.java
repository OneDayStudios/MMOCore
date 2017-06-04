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

/**
 *
 * @author draks
 */
public class StargateCommandMarines extends CloneLoadEvent {
    
    public StargateCommandMarines() {
        super("Stargate Command Marine Spawns");
        this.addSpawningNpc(AbstractDictionary.getNpcByName("SGC Soldier", "Stargate Command"));
        uPosition spawnLoc = new uPosition(-141.0, 4.0, -626.0, UniverseAPI.getDimension("P2X-3YZ"));
        this.addSpawnLocation(NpcRotation.SOUTH, spawnLoc);
        spawnLoc = new uPosition(-137.0, 4.0, -626.0, UniverseAPI.getDimension("P2X-3YZ"));
        this.addSpawnLocation(NpcRotation.SOUTH, spawnLoc);
        spawnLoc = new uPosition(-139.0, 4.0, -615.0, UniverseAPI.getDimension("P2X-3YZ"));
        this.addSpawnLocation(NpcRotation.NORTH, spawnLoc);
        spawnLoc = new uPosition(-143.0, 4.0, -615.0, UniverseAPI.getDimension("P2X-3YZ"));
        this.addSpawnLocation(NpcRotation.NORTH, spawnLoc);
        spawnLoc = new uPosition(-131.0, 6.0, -626.0, UniverseAPI.getDimension("P2X-3YZ"));
        this.addSpawnLocation(NpcRotation.SOUTH, spawnLoc);
    }
    
}

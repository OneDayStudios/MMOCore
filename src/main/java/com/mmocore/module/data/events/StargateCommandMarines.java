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
import com.mmocore.module.data.npcs.StargateCommandSoldier;

/**
 *
 * @author draks
 */
public class StargateCommandMarines extends CloneLoadEvent {
    
    public StargateCommandMarines() {
        super("Stargate Command Marine Spawns");
        addSpawningNpc(new StargateCommandSoldier());
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-142.0, 4.0, -627.0, UniverseAPI.getDimension("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-138.0, 4.0, -627.0, UniverseAPI.getDimension("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.NORTH, new uPosition(-144.0, 4.0, -616.0, UniverseAPI.getDimension("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.NORTH, new uPosition(-140.0, 4.0, -616.0, UniverseAPI.getDimension("P2X-3YZ")));
        this.addSpawnLocation(NpcRotation.SOUTH, new uPosition(-132.0, 6.0, -627.0, UniverseAPI.getDimension("P2X-3YZ")));
    }
    
}
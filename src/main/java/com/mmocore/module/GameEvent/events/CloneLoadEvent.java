/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.NpcRotation;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.RandomSpawnMode;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.GameEvent.events.options.RandomSpawnEventOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 *
 * @author draks
 */
public class CloneLoadEvent extends GameEvent {
    
    private HashMap<uPosition, NpcRotation> positionAndOrientation = new HashMap<uPosition, NpcRotation>();
    private ArrayList<RegisterableNpc> npcs = new ArrayList<RegisterableNpc>();
    private ArrayList<uPosition> spawned = new ArrayList<uPosition>();
    
    public CloneLoadEvent(String name) {
        super(name);
    }
    
    public HashMap<uPosition, NpcRotation> getPositionsReadOnly() {
        return new HashMap<uPosition, NpcRotation>(positionAndOrientation);
    }
    
    public HashMap<uPosition, NpcRotation> getPositions() {
        return positionAndOrientation;
    }
    
    public void addSpawnLocation(NpcRotation rotation, uPosition pos) {
        if (!positionAndOrientation.containsKey(pos)) positionAndOrientation.put(pos, rotation);
    }
    
    public void addSpawningNpc(RegisterableNpc npc) {
        if (!npcs.contains(npc)) npcs.add(npc);
    }
    
    public RegisterableNpc getRandom() {
        if (npcs.isEmpty()) return null;
        if (npcs.size() == 1) return npcs.get(0);
        Random r = new Random();
        return npcs.get(r.nextInt(npcs.size()));
    }
    
    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (!getPositions().isEmpty()) {
            for (uPosition pos : getPositions().keySet()) {                    
                ForgeAPI.sendConsoleEntry("Processing: " + pos.getDisplayString(), ConsoleMessageType.FINE);
                if (pos.getDimension().equals(dimension)) {
                    ForgeAPI.sendConsoleEntry("Dimension match!", ConsoleMessageType.FINE);
                    RegisterableNpc npc = null;
                    npc = NpcAPI.simpleClone(getRandom(), NpcSpawnMethod.Clone, pos);
                    NpcMovementOptions opts = npc.getMovementOptions();
                    opts.setRotation(getPositions().get(pos));
                    npc.setMovementOptions(opts);
                    MMOCore.getNpcRegistry().register(npc);
                    spawned.add(pos);
                }        
            }
            for (uPosition sp : spawned) {
                getPositions().remove(sp);
            }
            spawned.clear();
        } else {
            ForgeAPI.sendConsoleEntry("Flagging for removal", ConsoleMessageType.FINE);
            this.setFlaggedForRemoval();
        }
    }
    
}

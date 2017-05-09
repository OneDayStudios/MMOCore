/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.location;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.constants.GuiSlot;
import com.mmocore.module.Gui.RegisterableGui;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.module.Quest.RegisterableQuest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.World;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterableLocation extends AbstractRegisterable<RegisterableLocation, String, RegisterableLocation> {
    
    private String name;
    private String description;
    private uPosition position;
    private int radiusX = 0;
    private int radiusZ = 0;
    private int radiusY = 0;
    // Quest required to enter the location.
    private ArrayList<RegisterableQuest> questsRequiredToEnter = new ArrayList<RegisterableQuest>();
    // Quest required to exit the location.
    private ArrayList<RegisterableQuest> questsRequiredToExit = new ArrayList<RegisterableQuest>();
    // Quest to give (if eligible) when player visits area.
    private ArrayList<RegisterableQuest> questsToGive = new ArrayList<RegisterableQuest>();
    // Npcs to spawn whilst players are in the area.
    private HashMap<RegisterableNpc, Boolean> npcsToSpawn = new HashMap<RegisterableNpc, Boolean>();
    private boolean preventPlayersEntering = false;
    private boolean preventPlayersLeaving = false;
    private RegisterableGui denyEntryMessage = null;
    private RegisterableGui denyExitMessage = null;
    private RegisterableGui entryMessage = null;
    private RegisterableGui exitMessage = null;
    private RegisterableGui failedTimer = null;
    private int maxSeconds = 0;    
    private boolean killAfterExpiry = false;
    private HashMap<RegisterablePlayer, Long> playersInLocation = new HashMap<RegisterablePlayer, Long>();
    
    public RegisterableLocation(RegisterableLocation location) { 
        this.name = location.name;
        this.description = location.description;
        this.radiusX = location.radiusX;
        this.radiusY = location.radiusY;
        this.radiusZ = location.radiusZ;
        this.denyEntryMessage = location.denyEntryMessage;
        this.denyExitMessage = location.denyExitMessage;
        this.entryMessage = location.entryMessage;
        this.exitMessage = location.exitMessage;
        this.questsRequiredToEnter = new ArrayList<RegisterableQuest>(location.questsRequiredToEnter);
        this.questsRequiredToExit = new ArrayList<RegisterableQuest>(location.questsRequiredToExit);
        this.questsToGive = new ArrayList<RegisterableQuest>(location.questsToGive);
        this.npcsToSpawn = new HashMap<RegisterableNpc, Boolean>(location.npcsToSpawn);
        this.preventPlayersEntering = location.preventPlayersEntering;
        this.preventPlayersLeaving = location.preventPlayersLeaving;
    }
    
    public RegisterableLocation(String name, String description, uPosition position, int radiusX, int radiusY, int radiusZ) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }

    @Override
    public void tick() {
        processNewPlayers();
        // Get all players in area.
        // If players in area need quest credit, grant it.
        // If players in area need to be given a quest, give it.
        // If npcs need to be spawned, spawn them.        
        // For players not registered, display entry message or deny entry message depending, and register them if they are let in.
        // For players currently registered but not in the location, display the exit message and deregister them or display the deny exit message and teleport them back.
        processOldPlayers();
    }

    private void processNewPlayers() {
        for (RegisterablePlayer p : getPlayersInArea()) {
            if (!isInArea(p)) playersInLocation.put(p, System.currentTimeMillis());
        }
    }
    private void processOldPlayers() {
        for (RegisterablePlayer p : getPlayersInLocationReadOnly().keySet()) {
            if (!isInArea(p)) playersInLocation.remove(p);
        }
    }
    public boolean isInArea(RegisterablePlayer p) {
        return playersInLocation.get(p) != null;
    }
    
    public HashMap<RegisterablePlayer, Long> getPlayersInLocationReadOnly() {
        return new HashMap<RegisterablePlayer, Long>(playersInLocation);
    }
    
    public ArrayList<RegisterablePlayer> getPlayersInArea() {
        return PlayerAPI.getInArea(this.position.getDPosX() - radiusX, this.position.getDPosY() - radiusY, this.position.getDPosZ() - radiusZ, this.position.getDPosX() + radiusX, this.position.getDPosY() + radiusY, this.position.getDPosZ() + radiusZ, this.position.getDimension());
    }
    
    @Override
    public String getIdentifier() {
        return this.name;
    }

    @Override
    public RegisterableLocation getRegisteredObject() {
        return this;
    }

    @Override
    public void initialise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

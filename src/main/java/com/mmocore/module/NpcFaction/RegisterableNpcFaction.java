/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.NpcFaction;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import java.util.Random;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;

/**
 *
 * @author draks
 */
public class RegisterableNpcFaction extends AbstractRegisterable<RegisterableNpcFaction, Integer, Faction> {
    
    private Faction actualFaction;
    
    public RegisterableNpcFaction(String name) {
        
        actualFaction = null;
        
        if (NpcFactionAPI.get(name) != null) {
            this.actualFaction = NpcFactionAPI.get(name);
        } else {
            actualFaction = new Faction();
            actualFaction.name = name;
            Random r = new Random();
            int id = 1;
            while (NpcFactionAPI.get(id) != null) {
                id = r.nextInt(100000);
            }
            setID(id);
        }
        MMOCore.getInstance().getNpcFactionRegistry().register(this);
        this.save();
    }    
    
    private void setID(int id) {
        actualFaction.id = id;
    }
    
    public void setDefaultPoints(int points) {
        getRegisteredObject().defaultPoints = points;
        this.save();
    }
    public int getDefaultPoints() {
        return getRegisteredObject().defaultPoints;
    }
    public int getFriendlyPoints() {
        return getRegisteredObject().friendlyPoints;
    }
    public void setFriendlyPoints(int points) {
        getRegisteredObject().friendlyPoints = points;
        this.save();
    }
    public void setNeutralPoints(int points) {
        getRegisteredObject().neutralPoints = points;
        this.save();
    }
    public int getNeutralPoints() {
        return getRegisteredObject().neutralPoints;
    }
    public void setAttackedByMobs(boolean value) {
        getRegisteredObject().getsAttacked = value;
        this.save();
    }
    public boolean getAttackedByMobs() {
        return getRegisteredObject().getsAttacked;
    }
    
    public void setIsHidden(boolean value) {
        getRegisteredObject().hideFaction = value;
        this.save();
    }
    
    public boolean getIsHidden() {
        return getRegisteredObject().hideFaction;
    }
    
    public void clearHostileFactions() {
        getRegisteredObject().attackFactions.clear();
    }
    
    public void removeHostileFaction(RegisterableNpcFaction faction, boolean isInitiator) {
            getRegisteredObject().attackFactions.remove(faction.getIdentifier());
            if (isInitiator) faction.removeHostileFaction(this, false);
            this.save();
    }
    
    public void addHostileFaction(RegisterableNpcFaction faction, boolean isInitiator) {
            getRegisteredObject().attackFactions.add(faction.getIdentifier());
            if (isInitiator) faction.addHostileFaction(this, false);
            this.save();
    }
    
    public void setColor(int number) {
        getRegisteredObject().color = number;
        this.save();
    }
    
    public int getColor() {
        return getRegisteredObject().color;
    }
    
    public String getName() {
        return getRegisteredObject().name;
    }
    
    public int getID() {
        return getRegisteredObject().id;
    }
    
    public void save() {
        try {
            if (FactionController.getInstance().factions.get(getRegisteredObject().id) != null) FactionController.getInstance().factions.replace(getRegisteredObject().id, getRegisteredObject());        
            if (FactionController.getInstance().factions.get(getRegisteredObject().id) == null) FactionController.getInstance().factions.put(getRegisteredObject().id, getRegisteredObject());        
            FactionController.getInstance().saveFaction(actualFaction);
        } catch (Exception e) {
            System.out.println("Failed to save faction: " + getRegisteredObject().name);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public Integer getIdentifier() {
        return getID();
    }

    @Override
    public void initialise() {       
        ForgeAPI.sendConsoleEntry("Initialised Npc Faction: " + this.getIdentifier(), ConsoleMessageType.FINE);
        this.save();
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalised Npc Faction: " + this.getIdentifier(), ConsoleMessageType.FINE);
        this.save();
    }

    @Override
    public Faction getRegisteredObject() {
        return this.actualFaction;
    }
}

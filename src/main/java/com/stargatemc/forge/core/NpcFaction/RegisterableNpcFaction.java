/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.NpcFaction;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.api.NpcFactionAPI;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
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
        
    }    
    
    private void setID(int id) {
        actualFaction.id = id;
    }
    
    public void setDefaultPoints(int points) {
        getRegisteredObject().defaultPoints = points;
    }
    public int getDefaultPoints() {
        return getRegisteredObject().defaultPoints;
    }
    public int getFriendlyPoints() {
        return getRegisteredObject().friendlyPoints;
    }
    public void setFriendlyPoints(int points) {
        getRegisteredObject().friendlyPoints = points;
    }
    public void setNeutralPoints(int points) {
        getRegisteredObject().neutralPoints = points;
    }
    public int getNeutralPoints() {
        return getRegisteredObject().neutralPoints;
    }
    public void setAttackedByMobs(boolean value) {
        getRegisteredObject().getsAttacked = value;
    }
    public boolean getAttackedByMobs() {
        return getRegisteredObject().getsAttacked;
    }
    
    public void setIsHidden(boolean value) {
        getRegisteredObject().hideFaction = value;
    }
    
    public boolean getIsHidden() {
        return getRegisteredObject().hideFaction;
    }
    
    public void clearHostileFactions() {
        getRegisteredObject().attackFactions.clear();
    }
    
    public void addHostileFaction(String name) {
        Faction f = NpcFactionAPI.get(name);
        if (f != null) {
            getRegisteredObject().attackFactions.add(f.id);
        }        
    }
    
    public void setColor(int number) {
        getRegisteredObject().color = number;
    }
    
    public int getColor() {
        return getRegisteredObject().color;
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

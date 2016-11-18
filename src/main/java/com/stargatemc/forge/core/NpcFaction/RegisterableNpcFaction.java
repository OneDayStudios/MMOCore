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
public class RegisterableNpcFaction extends AbstractRegisterable<RegisterableNpcFaction, Integer> {
    
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
    
    public Faction getFaction() {
        return this.actualFaction;
    }    
    public void setDefaultPoints(int points) {
        getFaction().defaultPoints = points;
    }
    public int getDefaultPoints() {
        return getFaction().defaultPoints;
    }
    public int getFriendlyPoints() {
        return getFaction().friendlyPoints;
    }
    public void setFriendlyPoints(int points) {
        getFaction().friendlyPoints = points;
    }
    public void setNeutralPoints(int points) {
        getFaction().neutralPoints = points;
    }
    public int getNeutralPoints() {
        return getFaction().neutralPoints;
    }
    public void setAttackedByMobs(boolean value) {
        getFaction().getsAttacked = value;
    }
    public boolean getAttackedByMobs() {
        return getFaction().getsAttacked;
    }
    
    public void setIsHidden(boolean value) {
        getFaction().hideFaction = value;
    }
    
    public boolean getIsHidden() {
        return getFaction().hideFaction;
    }
    
    public void clearHostileFactions() {
        getFaction().attackFactions.clear();
    }
    
    public void addHostileFaction(String name) {
        Faction f = NpcFactionAPI.get(name);
        if (f != null) {
            getFaction().attackFactions.add(f.id);
        }        
    }
    
    public void setColor(int number) {
        getFaction().color = number;
    }
    
    public int getColor() {
        return getFaction().color;
    }
    
    public int getID() {
        return getFaction().id;
    }
    
    public void save() {
        try {
            if (FactionController.getInstance().factions.get(getFaction().id) != null) FactionController.getInstance().factions.replace(getFaction().id, getFaction());        
            if (FactionController.getInstance().factions.get(getFaction().id) == null) FactionController.getInstance().factions.put(getFaction().id, getFaction());        
            FactionController.getInstance().saveFaction(actualFaction);
        } catch (Exception e) {
            System.out.println("Failed to save faction: " + getFaction().name);
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
    }
}

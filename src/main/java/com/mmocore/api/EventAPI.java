/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.GameEvent.events.QuestLocationEvent;
import com.mmocore.module.GameEvent.events.VillagerReplacementEvent;
import com.mmocore.module.data.AbstractDictionary;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class EventAPI extends AbstractAPI<EventAPI> {
    
    public static GameEvent getRegistered(String name) {
        if (AbstractDictionary.getEvents().isEmpty()) AbstractDictionary.loadGameEvents();
        for (GameEvent e : MMOCore.getGameEventRegistry().getRegistered().values()) {
            if (e.getIdentifier().equals(name)) return e;
        }
        for (GameEvent e : AbstractDictionary.getEvents()) {
            if (e.getIdentifier().equals(name)) {
                MMOCore.getGameEventRegistry().register(e);
                return e;
            }
        }
        return null;
    }
    
    public static ArrayList<GameEvent> getEventsForDimension(RegisterableDimension d) {
        ArrayList<GameEvent> events = new ArrayList<GameEvent>();
        for (GameEvent event : MMOCore.getGameEventRegistry().getRegistered().values()) {
            if (event.ticksForDimension(d)) events.add(event);
        }
        return events;
    }
    
    public static ArrayList<VillagerReplacementEvent> getVillagerReplacementEvents() {
        ArrayList<VillagerReplacementEvent> events = new ArrayList<VillagerReplacementEvent>();
        for (GameEvent event : MMOCore.getGameEventRegistry().getRegistered().values()) {
            if (event instanceof VillagerReplacementEvent) {
                events.add((VillagerReplacementEvent)event);
            }
        }
        return events;
    }
    
    public static boolean isAreaProtected(uPosition position) {
        for (GameEvent event : MMOCore.getGameEventRegistry().getRegistered().values()) {
            if (event instanceof QuestLocationEvent) {
                QuestLocationEvent location = (QuestLocationEvent)event;
                ForgeAPI.sendConsoleEntry("Found event: " + location.getName() + " and is protected: " + location.isProtected(), ConsoleMessageType.FINE);
                if (location.isProtected() && location.containsPosition(position)) return true;
            }
        }
        return false;
    }
}

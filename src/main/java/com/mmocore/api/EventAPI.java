/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.data.AbstractDictionary;

/**
 *
 * @author draks
 */
public class EventAPI extends AbstractAPI<EventAPI> {
    
    public static GameEvent getRegistered(String name) {
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author draks
 */
public abstract class AbstractRegistry<T extends AbstractRegistry, U, J extends AbstractRegisterable> extends AbstractObjectCore<AbstractRegistry> {
   
    private Map<U, J> objects = new HashMap<U, J>();
   
    public void register(U identifier, J object) {
        if (objects.keySet().contains(identifier) || objects.values().contains(object)) return;
        objects.put(identifier, object);
    }
    
    public Map<U, J> getRegistered() {
        return this.objects;
    }
    
    public Map<U, J> getRegisteredReadOnly() {
        return new HashMap<U, J>(objects);
    }
    
    public void tick() {
        for (J object : getRegisteredReadOnly().values()) {
            object.tick();
        }
    }
    
    public void deregister(U identifier) {
        if (!objects.keySet().contains(identifier)) return;
        objects.remove(identifier);
    }
}

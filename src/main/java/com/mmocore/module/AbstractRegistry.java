/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author draks
 */
public abstract class AbstractRegistry<T extends AbstractRegistry, U, J extends AbstractRegisterable> extends AbstractObjectCore<AbstractRegistry> {
   
    private Map<U, J> objects = new HashMap<U, J>();
    
    public void register(J object) {
        if (isRegistered((U)object.getIdentifier())) this.deregister((U)object.getIdentifier());        
        object.initialise();
        objects.put((U)object.getIdentifier(), object);
    }
    
    public Map<U, J> getRegistered() {
        return this.objects;
    }
    
    public Map<U, J> getRegisteredReadOnly() {
        return new HashMap<U, J>(objects);
    }
    
    public boolean isRegistered(U identifier) {
        return (getRegistered(identifier) != null);
    }
    
    public void tick() {
        for (J object : getRegisteredReadOnly().values()) {
            object.tick();
        }
    }
    
    public J getRegistered(U identifier) {
        return objects.get(identifier);
    }
    
    public void deregister(U identifier) {
        if (!objects.keySet().contains(identifier)) return;
        objects.get(identifier).finalise();
        objects.remove(identifier);
    }
    
    public abstract boolean canBeEnabled();
    
}

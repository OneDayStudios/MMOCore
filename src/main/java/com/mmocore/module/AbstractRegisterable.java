/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module;

/**
 *
 * @author draks
 */
public abstract class AbstractRegisterable<T extends AbstractRegisterable, U, O extends Object> extends AbstractObjectCore<AbstractRegisterable> {
   
    private U identifier = null;
    
    public abstract void tick();    
    
    public U getIdentifier() {
        return this.identifier;
    }
    
    public void setIdentifier(U identifier) {
        this.identifier = identifier;
    }
    
    public abstract O getRegisteredObject();

}

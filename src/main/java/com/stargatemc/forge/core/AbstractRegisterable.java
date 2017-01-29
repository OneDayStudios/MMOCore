/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core;

/**
 *
 * @author draks
 */
public abstract class AbstractRegisterable<T extends AbstractRegisterable, U, O extends Object> extends AbstractObjectCore<AbstractRegisterable> {
   
    public abstract void tick();
    
    public abstract U getIdentifier();
    
    public abstract O getRegisteredObject();

}

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
public abstract class AbstractObjectCore<T> {
    
    public T get() {
        return (T)this;
    }
   
    public abstract void initialise();
    public abstract void finalise();
}

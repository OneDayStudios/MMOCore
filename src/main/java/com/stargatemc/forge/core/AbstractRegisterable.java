/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.constants.ConsoleMessageType;

/**
 *
 * @author draks
 */
public abstract class AbstractRegisterable<T extends AbstractRegisterable, U> extends AbstractObjectCore<AbstractRegisterable> {
   
    public abstract void tick();
    
    public abstract U getIdentifier();

}

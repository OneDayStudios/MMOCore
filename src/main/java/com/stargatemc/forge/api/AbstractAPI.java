/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api;

import com.stargatemc.forge.SForge;

/**
 *
 * @author draks
 */
public abstract class AbstractAPI<T extends AbstractAPI> {
    
    public String getVersion() {
        return SForge.MODVER;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;

/**
 *
 * @author draks
 */
public abstract class AbstractAPI<T extends AbstractAPI> {
    
    public String getVersion() {
        return MMOCore.MODVER;
    }
}

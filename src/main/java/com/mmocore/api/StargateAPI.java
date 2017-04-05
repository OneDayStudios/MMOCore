/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.core.Stargate.RegisterableStargate;

/**
 *
 * @author draks
 */
public class StargateAPI extends AbstractAPI<StargateAPI> {
    
    
    public static RegisterableStargate get(String address) {
        return MMOCore.getInstance().getStargateRegistry().getRegistered(address);
    }
    
    public static boolean exists(String address) {
        return MMOCore.getInstance().getStargateRegistry().isRegistered(address);
    }
    
    public static boolean isActive(String address) {
        if (!exists(address)) return false;
        return get(address).getStargate().isActive();
    }
    
    public static boolean connnect(String sourceAddress, String destinationAddress) {
        if (!MMOCore.getInstance().getStargateRegistry().isRegistered(sourceAddress) || isActive(destinationAddress)) return false;
        if (!MMOCore.getInstance().getStargateRegistry().isRegistered(destinationAddress) || isActive(destinationAddress)) return false;
//        get(sourceAddress).getStargate().startDiallingStargate(destinationAddress, get(destinationAddress).getStargate(), true);
//        get(destinationAddress).getStargate().startDiallingStargate(sourceAddress, get(sourceAddress).getStargate(), false);
        return true;
    }
}

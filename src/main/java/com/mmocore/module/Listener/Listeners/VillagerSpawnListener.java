/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.EventAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.GameEvent.events.VillagerReplacementEvent;
import com.mmocore.module.Listener.RegisterableListener;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 *
 * @author draks
 */
public class VillagerSpawnListener extends RegisterableListener {
    
    @SubscribeEvent
    public void replaceVanillaVillagers(EntityJoinWorldEvent event){
        if (event.entity instanceof EntityVillager){
            uPosition position = new uPosition(event.entity.posX, event.entity.posY, event.entity.posZ, MMOCore.getDimensionRegistry().getRegistered(event.entity.worldObj.provider.dimensionId));
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
            event.entity.setDead();
            for (VillagerReplacementEvent gameEvent : EventAPI.getVillagerReplacementEvents()) {
                gameEvent.spawn(position);
            }
        }
    }
    
}

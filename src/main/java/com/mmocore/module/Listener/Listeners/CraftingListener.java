/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.api.ForgeAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Listener.RegisterableListener;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
/**
 *
 * @author draks
 */
public class CraftingListener extends RegisterableListener {
    
   @SubscribeEvent(priority=EventPriority.HIGHEST)
   public void onItemCraft(ItemCraftedEvent e) {
       ItemStack stack = (ItemStack)e.crafting;
       EntityPlayer p = (EntityPlayer)e.player;
       ForgeAPI.sendConsoleEntry("Crafted item: " + stack.getDisplayName() + " by " + p.getDisplayName(), ConsoleMessageType.FINE);
   }
}
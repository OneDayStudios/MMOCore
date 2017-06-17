/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import com.comphenix.protocol.utility.MinecraftReflection;
import net.minecraft.item.ItemStack;
/**
 *
 * @author draks
 */
public class FakeCraftEvent extends org.bukkit.event.Event implements Cancellable {
    
    HandlerList handlers = new HandlerList();
    private UUID player;
    private ItemStack stack;    
    private boolean cancelled;
    
    public FakeCraftEvent(UUID player, net.minecraft.item.ItemStack stack) {
        this.player = player;
        this.stack = stack;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }
    
    public org.bukkit.inventory.ItemStack getBukkitItem() {
        return MinecraftReflection.getBukkitItemStack(stack);
    }
    
    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }
    
}

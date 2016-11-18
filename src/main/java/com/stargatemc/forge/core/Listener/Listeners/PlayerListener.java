/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Listener.Listeners;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.api.GuiAPI;
import com.stargatemc.forge.api.UniverseAPI;
import com.stargatemc.forge.core.Dimension.RegisterableDimension;
import com.stargatemc.forge.core.Listener.RegisterableListener;
import com.stargatemc.forge.core.Player.RegisterablePlayer;
import com.stargatemc.forge.core.constants.GuiSlot;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
/**
 *
 * @author draks
 */
public class PlayerListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent e) {
        if (!SForge.getInstance().getPlayerRegistry().isRegistered(((EntityPlayer)e.player).getUniqueID())) SForge.getInstance().getPlayerRegistry().register(new RegisterablePlayer(((EntityPlayer)e.player).getUniqueID()));
    }
    
    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent e) {
        if (SForge.getInstance().getPlayerRegistry().isRegistered(((EntityPlayer)e.player).getUniqueID())) SForge.getInstance().getPlayerRegistry().deregister(((EntityPlayer)e.player).getUniqueID());
    }
    
    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        World w = ForgeAPI.getForgeWorld(e.toDim);
        RegisterablePlayer rPlayer = SForge.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.Toast, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
    }
    
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        RegisterablePlayer rPlayer = SForge.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.TopLeft, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 1000);        
    }
}

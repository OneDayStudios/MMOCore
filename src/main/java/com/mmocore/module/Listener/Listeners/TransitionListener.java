/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

/**
 *
 * @author draks
 */
@SideOnly(Side.SERVER)
public class TransitionListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        //TODO: Make a player teleport, and any passengers/vehicles he is with, teleport, when they are below Y=5 in space or above Y=255 on planet.
    }
    
    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        World w = ForgeAPI.getForgeWorld(e.toDim);
        RegisterablePlayer rPlayer = MMOCore.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.Toast, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
    }
}

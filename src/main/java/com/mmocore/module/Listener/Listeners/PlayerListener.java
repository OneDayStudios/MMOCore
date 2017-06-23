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
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.TextVisibleOption;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
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
        // No code here anymore, because worlds are not loaded before players log in.
    }
    
    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent e) {
        if (MMOCore.getPlayerRegistry().isRegistered(((EntityPlayer)e.player).getUniqueID())) MMOCore.getInstance().getPlayerRegistry().deregister(((EntityPlayer)e.player).getUniqueID());
    }
    
    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        World w = ForgeAPI.getForgeWorld(e.toDim);
        RegisterablePlayer rPlayer = MMOCore.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(w.provider.dimensionId);
        if (dimension.getIsLoaded()) {
            GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.Toast, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
        } else {
            ForgeAPI.sendConsoleEntry("Skipping PlayerChangeDimensionEvent for player: " + rPlayer.getName() + " as  the world is not yet loaded.", ConsoleMessageType.FINE);
        }
    }
    
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        RegisterablePlayer rPlayer = MMOCore.getPlayerRegistry().getRegistered(player.getUniqueID());
        if (rPlayer != null) {
            if (rPlayer.getPosition() != null && rPlayer.getPosition().getDimension() != null && rPlayer.getPosition().getDimension().getIsLoaded()) {
                GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.TopLeft, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier() , 500, 500, 500, 1000);
                NpcAPI.spawnRandomNpcs(rPlayer);
            } else {
                ForgeAPI.sendConsoleEntry("Waiting for dimension to load before ticking: " + rPlayer.getName(), ConsoleMessageType.FINE);
            }
        } else {            
            ForgeAPI.sendConsoleEntry("Registering player: " + player.getUniqueID() + " as  it is their first tick since logging in.", ConsoleMessageType.FINE);
            rPlayer = new RegisterablePlayer(((EntityPlayer)e.player).getUniqueID());
            MMOCore.getPlayerRegistry().register(rPlayer);
        }
    }
}

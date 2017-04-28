/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcDoorInteraction;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.TextVisibleOption;
import com.mmocore.module.Npc.Npc;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.options.NpcBehaviourOptions;
import com.mmocore.module.Npc.options.NpcCombatOptions;
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
        if (!MMOCore.getInstance().getPlayerRegistry().isRegistered(((EntityPlayer)e.player).getUniqueID())) MMOCore.getInstance().getPlayerRegistry().register(new RegisterablePlayer(((EntityPlayer)e.player).getUniqueID()));
        RegisterablePlayer player = MMOCore.getInstance().getPlayerRegistry().getRegistered(((EntityPlayer)e.player).getUniqueID());
        RegisterableNpcFaction faction = new RegisterableNpcFaction("Tauri");
        RegisterableNpcFaction secondFaction = new RegisterableNpcFaction("Wraith");
        secondFaction.addHostileFaction(faction, true);
        Npc tauri_soldier = new Npc("Fred", "Flinstone", NpcTexture.SGC_SOLDIER, NpcModifier.MELEE_SOLDIER, NpcSpawnMethod.Static, player.getPosition(), faction);
        NpcCombatOptions cOptions = tauri_soldier.getCombatOptions();
        cOptions.setAttacksHostileFactions(NpcBoolean.YES);
        tauri_soldier.setCombatOptions(cOptions);
        NpcBaseOptions bOptions = tauri_soldier.getBaseOptions();
        bOptions.setNameVisible(TextVisibleOption.Always);
        bOptions.setBossBarVisible(TextVisibleOption.WhenAttacking);
        tauri_soldier.setBaseOptions(bOptions);
        NpcBehaviourOptions behaviour = tauri_soldier.getBehaviourOptions();
        behaviour.setDoorBehaviour(NpcDoorInteraction.Open);
        tauri_soldier.setBehaviourOptions(behaviour);
        Npc wraith_soldier = new Npc("Wraith Soldier", "Todd's Hive", NpcTexture.WRAITH_SOLDIER, NpcModifier.MELEE_SOLDIER, NpcSpawnMethod.Static, player.getPosition(), secondFaction);
    }
    
    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent e) {
        if (MMOCore.getInstance().getPlayerRegistry().isRegistered(((EntityPlayer)e.player).getUniqueID())) MMOCore.getInstance().getPlayerRegistry().deregister(((EntityPlayer)e.player).getUniqueID());
    }
    
    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        World w = ForgeAPI.getForgeWorld(e.toDim);
        RegisterablePlayer rPlayer = MMOCore.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.Toast, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
    }
    
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        RegisterablePlayer rPlayer = MMOCore.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.TopLeft, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier() , 500, 500, 500, 1000);        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.core.Npc.RegisterableNpc;
import com.stargatemc.forge.core.NpcFaction.RegisterableNpcFaction;
import com.stargatemc.forge.core.Player.RegisterablePlayer;
import com.stargatemc.forge.core.constants.FactionRelationType;
import com.stargatemc.forge.core.constants.NpcVisibleOption;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 *
 * @author draks
 */
public class NpcAPI extends AbstractAPI<NpcAPI> {
    
    public static boolean create(String name, String title, RegisterableNpcFaction faction) {
        if (exists(name,title,faction)) return false;
        // TODO: Create the NPC in game and register it.
        return true;
    }
    
    public static boolean exists(String name, String title, RegisterableNpcFaction faction) {
        return (get(name,title,faction) != null);
    }
    
    public static RegisterableNpc get(String name, String title, RegisterableNpcFaction faction) {
        for (RegisterableNpc npc : SForge.getInstance().getNpcRegistry().getRegisteredReadOnly().values()) {
            if (npc.getRegisteredObject().getName().equals(name) && npc.getRegisteredObject().getTitle().equals(title)) return npc;
        }
        return null;
    }
    
    public static boolean setBootItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        if (npc.getRegisteredObject().getArmor().getFeet().setItem(stack));
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getFeet().getItem().equals(stack));      
    }
    
    public static boolean setLegItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        if (npc.getRegisteredObject().getArmor().getLegs().setItem(stack));
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getLegs().getItem().equals(stack));      
    }
    
    public static boolean setChestItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        if (npc.getRegisteredObject().getArmor().getChest().setItem(stack));
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getChest().getItem().equals(stack));      
    }
    
    public static boolean setHeadItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        if (npc.getRegisteredObject().getArmor().getHead().setItem(stack));
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getHead().getItem().equals(stack));      
    }
    
    public static boolean isFriendlyToPlayer(RegisterablePlayer player, RegisterableNpc npc) {
        return getPlayerRelationToNpc(player, npc).equals(FactionRelationType.FRIENDLY);
    }
    
    public static boolean isHostileToPlayer(RegisterablePlayer player, RegisterableNpc npc) {
        return getPlayerRelationToNpc(player, npc).equals(FactionRelationType.HOSTILE);
    }
    
    public static boolean isNeutralToPlayer(RegisterablePlayer player, RegisterableNpc npc) {
        return getPlayerRelationToNpc(player, npc).equals(FactionRelationType.NEUTRAL);
    }
    
    public static FactionRelationType getPlayerRelationToNpc(RegisterablePlayer player, RegisterableNpc npc) {
        if (NpcFactionAPI.get(npc.getRegisteredObject().getFactionName()).isFriendlyToPlayer(player.getRegisteredObject())) return FactionRelationType.FRIENDLY;
        if (NpcFactionAPI.get(npc.getRegisteredObject().getFactionName()).isNeutralToPlayer(player.getRegisteredObject())) return FactionRelationType.NEUTRAL;
        if (NpcFactionAPI.get(npc.getRegisteredObject().getFactionName()).isAggressiveToPlayer(player.getRegisteredObject())) return FactionRelationType.HOSTILE;
        return FactionRelationType.ERROR;
    }
    
    public static boolean areNpcsHostile(RegisterableNpc sourceNpc, RegisterableNpc targetNpc) {
        if (NpcFactionAPI.get(sourceNpc.getRegisteredObject().getFactionName()).isAggressiveToNpc(targetNpc.getRegisteredObject().getEntity())) return true;
        return false;
    }
    
    public static NpcVisibleOption setNpcVisibility(NpcVisibleOption option, RegisterableNpc npc) {
        if (option != npc.getRegisteredObject().getBaseOptions().getVisibleOption()) {
            npc.getRegisteredObject().getBaseOptions().setVisibleOption(option);
            npc.getRegisteredObject().setMarkedForUpdate();
        }
        return npc.getRegisteredObject().getBaseOptions().getVisibleOption();
    }
}

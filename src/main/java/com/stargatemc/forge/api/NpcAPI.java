/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api;

import com.stargatemc.forge.core.Npc.RegisterableNpc;
import com.stargatemc.forge.core.Player.RegisterablePlayer;
import com.stargatemc.forge.core.constants.FactionRelationType;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 *
 * @author draks
 */
public class NpcAPI extends AbstractAPI<NpcAPI> {
    
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
    
    // Imposes a limitation on NPCs not being able to scan players not on their dimension. Should be acceptable.
    public boolean isHostileToPlayer(String name) {
        EntityPlayer player = findForgePlayerOnWorld(name);
        // Return false if the player is not on the world.
        if (player == null) return false;
        return entity.getFaction().isAggressiveToPlayer(player);
    }
    
    // Imposes a limitation on NPCs not being able to scan players not on their dimension. Should be acceptable.
    public boolean isNeutralToPlayer(String name) {
        EntityPlayer player = findForgePlayerOnWorld(name);
        // Return false if the player is not on the world.
        if (player == null) return false;
        return entity.getFaction().isNeutralToPlayer(player);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.FactionRelationType;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.NpcVisibleOption;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Npc.RegisterableNpc;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import noppes.npcs.entity.EntityCustomNpc;

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
    
    public static RegisterableNpc simpleClone(RegisterableNpc clonedNpc, NpcSpawnMethod method, uPosition position) {
        RegisterableNpc clone = new RegisterableNpc(clonedNpc.getBaseOptions().getName(), clonedNpc.getBaseOptions().getTitle(), clonedNpc.getBaseOptions().getTexture(), clonedNpc.getBaseOptions().getModifier(), method, position, clonedNpc.getBaseOptions().getFaction());
        clone.setBaseOptions(clonedNpc.getBaseOptions());
        clone.setCombatOptions(clonedNpc.getCombatOptions());
        clone.setInteractOptions(clonedNpc.getInteractOptions());
        clone.setArmor(clonedNpc.getArmor());
        clone.setPassiveHeldItems(clonedNpc.getPassiveHeldItems());
        clone.setRangedHeldItems(clonedNpc.getRangedHeldItems());
        clone.setLootOptions(clonedNpc.getLootOptions());
        clone.setMovementOptions(clonedNpc.getMovementOptions());
        clone.setBehaviourOptions(clonedNpc.getBehaviourOptions());
        clone.setRespawnOptions(clonedNpc.getRespawnOptions());
        return clone;
    }
    
    public static boolean exists(String name, String title, RegisterableNpcFaction faction) {
        return (get(name,title,faction) != null);
    }
    
    public static ArrayList<RegisterableNpc> getAllReadOnly(RegisterableDimension dimension) {
        ArrayList<RegisterableNpc> npcs = new ArrayList<RegisterableNpc>();
        for (RegisterableNpc npc : MMOCore.getInstance().getNpcRegistry().getRegistered().values()) {
            if (npc.getRegisteredObject().getWorldName().equals(dimension.getName())) npcs.add(npc);
        }
        return new ArrayList<RegisterableNpc>(npcs);
    }
    
    public static void deregisterAll() {
        for (RegisterableNpc npc : MMOCore.getInstance().getNpcRegistry().getRegisteredReadOnly().values()) {
            MMOCore.getInstance().getNpcRegistry().deregister(npc.getUniqueID());
        }
    }
    
    public static ArrayList<RegisterableNpc> getAll(RegisterableDimension dimension) {
        ArrayList<RegisterableNpc> npcs = new ArrayList<RegisterableNpc>();
        for (RegisterableNpc npc : MMOCore.getInstance().getNpcRegistry().getRegistered().values()) {
            if (npc.getRegisteredObject().getWorldName().equals(dimension.getName())) npcs.add(npc);
        }
        return npcs;
    }
    
    public static RegisterableNpc get(String name, String title, RegisterableNpcFaction faction) {
        for (RegisterableNpc npc : MMOCore.getInstance().getNpcRegistry().getRegisteredReadOnly().values()) {
            if (npc.getRegisteredObject().getBaseOptions().getName().equals(name) && npc.getRegisteredObject().getBaseOptions().getTitle().equals(title)) return npc;
        }
        return null;
    }
    
    public static boolean setBootItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        npc.getRegisteredObject().getArmor().getFeet().setItem(stack);
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getFeet().getItem().equals(stack));      
    }
    
    public static void register(RegisterableNpc npc) {
        MMOCore.getInstance().getNpcRegistry().register(npc);
    }
    
    public static RegisterableNpc clone(RegisterableNpc npc) {
        return new RegisterableNpc(npc);
    }
    
    public static boolean setLegItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        npc.getRegisteredObject().getArmor().getLegs().setItem(stack);
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getLegs().getItem().equals(stack));      
    }
    
    public static boolean setChestItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        npc.getRegisteredObject().getArmor().getChest().setItem(stack);
        npc.getRegisteredObject().setMarkedForUpdate();
        return (npc.getRegisteredObject().getArmor().getChest().getItem().equals(stack));      
    }
    
    public static boolean setHeadItem(String mod, String item, int dmg, RegisterableNpc npc) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, 1);
        stack.setItemDamage(dmg);
        npc.getRegisteredObject().getArmor().getHead().setItem(stack);
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
        if (NpcFactionAPI.get(npc.getRegisteredObject().getBaseOptions().getFaction().getIdentifier()).isFriendlyToPlayer(player.getRegisteredObject())) return FactionRelationType.FRIENDLY;
        if (NpcFactionAPI.get(npc.getRegisteredObject().getBaseOptions().getFaction().getIdentifier()).isNeutralToPlayer(player.getRegisteredObject())) return FactionRelationType.NEUTRAL;
        if (NpcFactionAPI.get(npc.getRegisteredObject().getBaseOptions().getFaction().getIdentifier()).isAggressiveToPlayer(player.getRegisteredObject())) return FactionRelationType.HOSTILE;
        return FactionRelationType.ERROR;
    }
    
    public static boolean areNpcsHostile(RegisterableNpc sourceNpc, RegisterableNpc targetNpc) {
        if (NpcFactionAPI.get(sourceNpc.getRegisteredObject().getBaseOptions().getFaction().getIdentifier()).isAggressiveToNpc(targetNpc.getRegisteredObject().getEntity())) return true;
        return false;
    }
    
    public static NpcVisibleOption setNpcVisibility(NpcVisibleOption option, RegisterableNpc npc) {
        if (option != npc.getRegisteredObject().getBaseOptions().getVisibleOption()) {
            npc.getRegisteredObject().getBaseOptions().setVisibleOption(option);
            npc.getRegisteredObject().setMarkedForUpdate();
        }
        return npc.getRegisteredObject().getBaseOptions().getVisibleOption();
    }

    public static void spawnRandomNpcs(RegisterablePlayer player) {
//        ArrayList<RegisterableNpc> npcs = NpcAPI.getAllReadOnly(player.getPosition().getDimension());
//        for (RegisterableNpc npc : npcs) {
//            if (!npc.getBaseOptions().getSpawnMethod().equals(NpcSpawnMethod.Static)) continue;
//            if (!npc.getRandomSpawnOptions().getSpawnDimensions().contains(player.getPosition().getDimension()) && !npc.getRandomSpawnOptions().getSpawnDimensions().isEmpty()) continue;
//            if (!npc.getRandomSpawnOptions().getSpawnGalaxies().contains(player.getPosition().getGalaxy()) && !npc.getRandomSpawnOptions().getSpawnGalaxies().isEmpty()) continue;
//            if (!npc.getRandomSpawnOptions().)
//            
//        }
    }
}

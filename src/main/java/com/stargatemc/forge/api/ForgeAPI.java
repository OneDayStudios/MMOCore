/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.AbstractAPI;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
import com.stargatemc.forge.core.constants.IntegratedMod;
import cpw.mods.fml.common.Loader;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
/**
 *
 * @author draks
 */
public class ForgeAPI extends AbstractAPI<ForgeAPI> {
    
    public static EntityPlayer getForgePlayer(String name) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player.getGameProfile().getName().equals(name)) return player;
            }
        }
        return null;
    }

    public static double distance(double sx, double sy, double sz, double dx, double dy, double dz) {
        double distance = Math.sqrt(Math.pow(sx-dx,2) + Math.pow(sx-dx,2) + Math.pow(sz-dz,2));
        return distance;
    }
    
    public static EntityPlayer getForgePlayer(UUID uuid) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player.getUniqueID().equals(uuid)) return player;
            }
        }
        return null;
    }
    
    public static boolean chancePassed(int percent) {
        Random r = new Random();
        return (r.nextInt(101) < percent);
    }
    
    public static boolean isModLoaded(IntegratedMod mod) {
        return Loader.isModLoaded(mod.name());
    }
    
    public static void sendConsoleEntry(String message, ConsoleMessageType type) {        
        System.out.println("[" + SForge.MODNAME + "] [" + type.name() + "]: " + message);
    }
    
    public static World getForgeWorld(int id) {
        return (World)DimensionManager.getWorld(id);
    }
    
    public static World getForgeWorld(String name) {
          for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
                WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
                String worldName = worldToProcess.getWorldInfo().getWorldName();
                if (worldName.equals(name)) {
                    return worldToProcess;
                }
          }
          return null;
    }
}

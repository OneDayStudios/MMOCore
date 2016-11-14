/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api.forge;

import com.stargatemc.forge.api.AbstractAPI;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
/**
 *
 * @author draks
 */
public class ForgeAPI extends AbstractAPI<ForgeAPI> {
    
    public EntityPlayer getForgePlayer(String name) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player.getGameProfile().getName().equals(name)) return player;
            }
        }
        return null;
    }
    
    public EntityPlayer getForgePlayer(UUID uuid) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player.getUniqueID().equals(uuid)) return player;
            }
        }
        return null;
    }
    
    public World getForgeWorldForName(String name) {
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

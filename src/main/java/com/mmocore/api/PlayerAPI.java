/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText; 
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
/**
 *
 * @author draks
 */
public class PlayerAPI extends AbstractAPI<PlayerAPI> {

    public static ArrayList<RegisterablePlayer> getNear(uPosition position, int distance) {        
        ArrayList<RegisterablePlayer> rPlayers = new ArrayList<RegisterablePlayer>();
        for (Object entity : ForgeAPI.getForgeWorld(position.getDimension().getId()).playerEntities) {       
            EntityPlayer e = (EntityPlayer)entity;
            uPosition entityPos = new uPosition(e.posX,e.posY,e.posZ,position.getDimension());
            RegisterablePlayer player = MMOCore.getPlayerRegistry().getRegistered(e.getUniqueID());
            if (ForgeAPI.distance(entityPos.getDPosX(), entityPos.getDPosY(), entityPos.getDPosZ(), position.getDPosX(), position.getDPosY(),position.getDPosZ()) <= distance) rPlayers.add(player);
        }
        return rPlayers;
    }
    
    public static int getNearCount(uPosition position, int distance) {
        return getNear(position, distance).size();
    }
    
    public static void sendMessage(RegisterablePlayer player, String message) {
        player.getRegisteredObject().addChatMessage(new ChatComponentText(message));
    }
    
    public static RegisterablePlayer getForName(String name) {
        for (RegisterablePlayer player : MMOCore.getPlayerRegistry().getRegisteredReadOnly().values()) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }
    
    public static ArrayList<RegisterablePlayer> getForDimension(RegisterableDimension dimension) {
        ArrayList<RegisterablePlayer> players = new ArrayList<RegisterablePlayer>();
        for (RegisterablePlayer player : MMOCore.getPlayerRegistry().getRegisteredReadOnly().values()) {
            if (player.getPosition().getDimension().equals(dimension)) players.add(player);
        }
        return players;
    }
    
    public static ArrayList<RegisterablePlayer> getInArea(double x1, double y1, double z1, double x2, double y2, double z2, RegisterableDimension dimension) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
        List entities = dimension.getRegisteredObject().getEntitiesWithinAABB(EntityPlayer.class, box);
        ArrayList<RegisterablePlayer> players = new ArrayList<RegisterablePlayer>();
        for (Object po : entities) {
            EntityPlayer p = (EntityPlayer)po;
            players.add(MMOCore.getPlayerRegistry().getRegistered(p.getUniqueID()));
        }
        return players;
    }
}

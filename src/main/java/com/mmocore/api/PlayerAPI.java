/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
/**
 *
 * @author draks
 */
public class PlayerAPI extends AbstractAPI<PlayerAPI> {

    public static ArrayList<RegisterablePlayer> getNear(uPosition position, int distance) {        
        ArrayList<RegisterablePlayer> rPlayers = new ArrayList<RegisterablePlayer>();
        for (RegisterablePlayer player : MMOCore.getInstance().getPlayerRegistry().getRegisteredReadOnly().values()) {
            if (player.getPosition().getDimension().equals(position.getDimension()) && ForgeAPI.distance(player.getPosition().getDPosX(), player.getPosition().getDPosY(), player.getPosition().getDPosZ(), position.getDPosX(), position.getDPosY(),position.getDPosZ()) <= distance) rPlayers.add(player);
        }
        return rPlayers;
    }
    
    public static int getNearCount(uPosition position, int distance) {
        return getNear(position, distance).size();
    }
}

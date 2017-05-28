/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import org.apache.logging.log4j.*;
import io.netty.channel.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.common.network.*;
import cpw.mods.fml.common.network.*;
import com.mmocore.MMOCore;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.constants.uPosition;
import org.apache.logging.log4j.*;
import io.netty.channel.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

import net.minecraftforge.common.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.common.network.*;
import cpw.mods.fml.common.network.*;
import gcewing.sg.SGBaseTE;
import gcewing.sg.Trans3;
import gcewing.sg.Vector3;
import java.util.ArrayList;
import java.util.HashMap;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_ItemAircraft;
/**
 *
 * @author draks
 */
public class TransitionListener extends RegisterableListener {
    
    public static ArrayList<String> whitelistedVehicles = new ArrayList<String>();
    
    public TransitionListener() {
        whitelistedVehicles.add("Puddle Jumper");
        whitelistedVehicles.add("Wraith Dart");
        whitelistedVehicles.add("Gate Glider");
    }
    
    private MCH_EntityAircraft getMCHeliVehicle(RegisterablePlayer player) {
        Entity mount = player.getRegisteredObject().ridingEntity;
        if (mount == null) return null;
        if (!(mount instanceof MCH_EntityAircraft) && !(mount instanceof MCH_EntitySeat)) return null;
        if (mount instanceof MCH_EntitySeat) {
            return ((MCH_EntitySeat)mount).getParent();
        }
        return ((MCH_EntityAircraft)mount);
    }
    
    
    private void performTeleportForPlayer(RegisterablePlayer player, uPosition destination) {
        if (player.getRegisteredObject().ridingEntity == null) return;
        HashMap<Integer,RegisterablePlayer> playersToMove = new HashMap<Integer,RegisterablePlayer>();
        Entity mount = player.getRegisteredObject().ridingEntity;
        Trans3 dt = new Trans3(destination.getDPosX(), destination.getDPosY(), destination.getDPosZ());            
        Trans3 t = new Trans3(player.getRegisteredObject().posX,player.getRegisteredObject().posY,player.getRegisteredObject().posZ);
        if (mount != null && mount instanceof MCH_EntityAircraft) {            
            MCH_EntityAircraft aircraft = (MCH_EntityAircraft)mount;
            MCH_EntitySeat[] seats = aircraft.getSeats();
            uPosition sourcePos = new uPosition(player.getRegisteredObject().posX, player.getRegisteredObject().posY, player.getRegisteredObject().posZ, player.getPosition().getDimension());
            ArrayList<RegisterablePlayer> nearbyPlayers = PlayerAPI.getNear(sourcePos, 15);
            for (RegisterablePlayer p : nearbyPlayers) {
                if (p.getRegisteredObject().ridingEntity != null && p.getRegisteredObject().ridingEntity instanceof MCH_EntitySeat) {
                    MCH_EntitySeat seat = (MCH_EntitySeat)p.getRegisteredObject().ridingEntity;
                    if (seat.getParent() != null & seat.getParent().equals(aircraft)) continue;
                    playersToMove.put(seat.seatID, p);
                    p.getRegisteredObject().mountEntity(null);
                }
            }
            double throttle = aircraft.getCurrentThrottle();
            double acThrottle = aircraft.getThrottle();
            double speed = aircraft.currentSpeed;
            int fuel = aircraft.currentFuel;
            MCH_EntityAircraft entity = (MCH_EntityAircraft)SGBaseTE.teleportEntityAndRider(mount, t, dt, destination.getDimension().getId(), false);
            entity.setCurrentThrottle(throttle);
            entity.setThrottle(acThrottle);
            entity.currentFuel = fuel;
            entity.currentSpeed = speed;
            for (Integer seatID : playersToMove.keySet()) {
                EntityPlayer entityPlayer = (EntityPlayer)SGBaseTE.teleportEntityAndRider(playersToMove.get(seatID).getRegisteredObject(), t, dt, destination.getDimension().getId(), false);
                entityPlayer.mountEntity(entity.getSeat(seatID));
            }
        } else {
            if (mount != null && !(mount instanceof MCH_EntitySeat)) {
                SGBaseTE.teleportEntityAndRider(mount, t, dt, destination.getDimension().getId(), false);
            } else {
                SGBaseTE.teleportEntityAndRider(player.getRegisteredObject(), t, dt, destination.getDimension().getId(), false);
            }
        }
        
    }
    
    private boolean isInMCheliApprovedVehicle(RegisterablePlayer player) {
        MCH_EntityAircraft aircraft = this.getMCHeliVehicle(player);
        if (aircraft == null) return false;
        MCH_ItemAircraft itemAircraft = (MCH_ItemAircraft)aircraft.getItem();
        return (TransitionListener.whitelistedVehicles.contains(itemAircraft.getAircraftInfo().displayName));
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        // If the entity is not a player, do nothing.
        if (!(e.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer mcPlayer = (EntityPlayer)e.entityLiving;
        RegisterablePlayer player = MMOCore.getPlayerRegistry().getRegistered(mcPlayer.getUniqueID());
        if (player.getPosition().isInHyperSpace() && mcPlayer.ridingEntity != null) {
                PlayerAPI.sendMessage(player, "Your vehicle does not operate in hyperspace!");
                mcPlayer.mountEntity(null);
        }
        if (player.getPosition().isInSpace() && mcPlayer.ridingEntity != null) {
            if (!isInMCheliApprovedVehicle(player)) {
                PlayerAPI.sendMessage(player, "Your vehicle does not operate in space!");
                mcPlayer.mountEntity(null);      
            }
        }
        if (player.getPosition().isInHyperSpace() || player.getPosition().getCelestialBody() == null || player.getPosition().getCelestialBody().isFake()) return;
        if (player.getPosition().isInSpace() && player.getPosition().getDPosY() <= 0) {
            uPosition destination = new uPosition(player.getPosition().getCelestialBody().getSpawnX(), 495, player.getPosition().getCelestialBody().getSpawnZ(), player.getPosition().getCelestialBody());
            this.performTeleportForPlayer(player, destination);
        }
        if (!player.getPosition().isInSpace() && player.getPosition().getDPosY() > 400) {
            Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
            Trans3 dt = new Trans3(player.getPosition().getCelestialBody().getPosition().getDPosX(), 5, player.getPosition().getCelestialBody().getPosition().getDPosZ());
            if (mcPlayer.ridingEntity != null) {
                if (!this.isInMCheliApprovedVehicle(player)) {
                    PlayerAPI.sendMessage(player, "Your vehicle has stalled!");
                    uPosition destination = new uPosition(player.getPosition().getDPosX(), player.getPosition().getDPosY()-5,player.getPosition().getDPosZ(), player.getPosition().getDimension());
                    this.performTeleportForPlayer(player, destination);
                } else {
                    if (player.getPosition().getDPosY() >= 500) {
                        uPosition destination = new uPosition(player.getPosition().getCelestialBody().getPosition().getDPosX(), 5, player.getPosition().getCelestialBody().getPosition().getDPosZ(), player.getPosition().getSystem());
                        this.performTeleportForPlayer(player, destination);
                    }
                }
            } else {
                PlayerAPI.sendMessage(player, "The atmosphere is too thin to go any higher!");
                uPosition destination = new uPosition(player.getPosition().getDPosX(), player.getPosition().getDPosY()-5,player.getPosition().getDPosZ(), player.getPosition().getDimension());
                this.performTeleportForPlayer(player, destination);
            }
        }
    }
}

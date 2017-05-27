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
            Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
            Trans3 dt = new Trans3(player.getPosition().getCelestialBody().getSpawnX(), 495, player.getPosition().getCelestialBody().getSpawnZ());
            if (mcPlayer.ridingEntity != null) {
                    Entity entity = mcPlayer.ridingEntity;
                    if (mcPlayer.ridingEntity instanceof MCH_EntityAircraft) {
                        MCH_EntityAircraft plane = (MCH_EntityAircraft)mcPlayer.ridingEntity;
                        double throttle = plane.getCurrentThrottle();
                        plane = (MCH_EntityAircraft)SGBaseTE.teleportEntityAndRider(entity, t, dt, player.getPosition().getCelestialBody().getId(), false);
                        plane.addCurrentThrottle(throttle);
                    } else {
                        SGBaseTE.teleportEntityAndRider(entity, t, dt, player.getPosition().getCelestialBody().getId(), false);
                    }
            } else {
                mcPlayer = (EntityPlayer)SGBaseTE.teleportEntityAndRider(mcPlayer, t, dt, player.getPosition().getCelestialBody().getId(), false);  
            }
        }
        if (!player.getPosition().isInSpace() && player.getPosition().getDPosY() > 400) {
            Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
            Trans3 dt = new Trans3(player.getPosition().getCelestialBody().getPosition().getDPosX(), 5, player.getPosition().getCelestialBody().getPosition().getDPosZ());
            if (mcPlayer.ridingEntity != null) {
                Entity entity = mcPlayer.ridingEntity;
                if (!this.isInMCheliApprovedVehicle(player)) {
                    PlayerAPI.sendMessage(player, "Your vehicle has stalled!");
                    ForgeAPI.sendConsoleEntry("Mount attempting to transition is: " + entity.getClass().getName(), ConsoleMessageType.FINE);
                    Trans3 newDt = new Trans3(player.getPosition().getDPosX(), player.getPosition().getDPosY()-5, player.getPosition().getDPosZ());
                    SGBaseTE.teleportEntityAndRider(entity, t, newDt, player.getPosition().getCelestialBody().getId(), false);
                } else {
                    if (player.getPosition().getDPosY() >= 500) {
                        MCH_EntityAircraft aircraft = this.getMCHeliVehicle(player);
                        double throttle = aircraft.getCurrentThrottle();
                        SGBaseTE.teleportEntityAndRider(entity, t, dt, player.getPosition().getSystem().getId(), false);
                        aircraft = this.getMCHeliVehicle(player);
                        if (aircraft != null) aircraft.addCurrentThrottle(throttle);
                    }
                }
            } else {
                PlayerAPI.sendMessage(player, "The atmosphere is too thin to go any higher!");
                Trans3 newDt = new Trans3(player.getPosition().getDPosX(), player.getPosition().getDPosY()-5, player.getPosition().getDPosZ());
                SGBaseTE.teleportEntityAndRider(mcPlayer, t, newDt, player.getPosition().getCelestialBody().getId(), false);  
            }
        }
    }
}

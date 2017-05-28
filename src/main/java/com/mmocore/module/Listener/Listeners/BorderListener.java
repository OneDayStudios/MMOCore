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
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_ItemAircraft;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;

/**
 *
 * @author draks
 */
public class BorderListener extends RegisterableListener {
    
    public BorderListener() {
        
    }
    
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        uPosition position = new uPosition(event.x,event.y,event.z,MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
        if (!position.isInUniverse()) event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        uPosition position = new uPosition(event.x,event.y,event.z,MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
        if (!position.isInUniverse()) event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            EntityPlayer mcPlayer = (EntityPlayer)e.entityLiving;
            RegisterablePlayer player = MMOCore.getPlayerRegistry().getRegistered(mcPlayer.getUniqueID());
            if (!player.getPosition().isInUniverse()) {
                Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
                boolean lessX = player.getPosition().getDimension().getSpawnX() > mcPlayer.posX;
                boolean lessZ = player.getPosition().getDimension().getSpawnZ() > mcPlayer.posZ;
                double destinationX;
                double destinationZ;
                if (lessX) { destinationX = mcPlayer.posX + 5; } else { destinationX = mcPlayer.posX - 5; }
                if (lessZ) { destinationZ = mcPlayer.posZ + 5; } else { destinationZ = mcPlayer.posZ - 5; }
                Trans3 dt = new Trans3(destinationX, player.getPosition().getDPosY(), destinationZ);
                uPosition destination = new uPosition(destinationX, mcPlayer.posY, destinationZ, player.getPosition().getDimension());
                if (destination.isInSpace()) PlayerAPI.sendMessage(player, "You have reached the edge of the solar system...");
                if (destination.isInHyperSpace()) PlayerAPI.sendMessage(player, "You have reached the edge of the universe...");
                if (!destination.isInSpace() && !destination.isInHyperSpace()) PlayerAPI.sendMessage(player, "You have reached the edge of this world...");
                if (mcPlayer.ridingEntity != null) {
                        Entity entity = mcPlayer.ridingEntity;
                        if (mcPlayer.ridingEntity instanceof MCH_EntityAircraft) {
                            MCH_EntityAircraft plane = (MCH_EntityAircraft)mcPlayer.ridingEntity;
                            double throttle = plane.getCurrentThrottle();
                            plane = (MCH_EntityAircraft)SGBaseTE.teleportEntityAndRider(entity, t, dt, destination.getDimension().getId(), false);
                            plane.addCurrentThrottle(throttle);
                        } else {
                            SGBaseTE.teleportEntityAndRider(entity, t, dt, destination.getDimension().getId(), false);
                        }
                } else {
                    SGBaseTE.teleportEntityAndRider(mcPlayer, t, dt, destination.getDimension().getId(), false);  
                }
            }
        } else {
            if ((e.entityLiving.riddenByEntity == null)) {
                e.entityLiving.setDead();
            }
        }
    }
}

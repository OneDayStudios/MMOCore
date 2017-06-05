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
import net.minecraft.entity.Entity;
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
import cr0s.warpdrive.config.Dictionary;
import gcewing.sg.SGBaseTE;
import gcewing.sg.Trans3;
import gcewing.sg.Vector3;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_ItemAircraft;
import mcheli.vehicle.MCH_EntityVehicle;
import mcheli.weapon.MCH_WeaponSet;
/**
 *
 * @author draks
 */
public class TransitionListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
//        // If the entity is not a player, do nothing.
        EntityLivingBase entity = e.entityLiving;
        if (entity instanceof EntityPlayer && entity.ridingEntity != null && entity.ridingEntity instanceof MCH_EntityVehicle) {
            MCH_EntityVehicle vehicle = (MCH_EntityVehicle)entity.ridingEntity;
            uPosition position = new uPosition(vehicle.posX, vehicle.posY, vehicle.posZ, MMOCore.getDimensionRegistry().getRegistered(vehicle.worldObj.provider.dimensionId));
            if (position.isInUniverse() && position.getDPosY() >= 500 && !position.isInSpace() && !position.isInHyperSpace()) {
                Trans3 source = new Trans3(vehicle.posX, vehicle.posY, vehicle.posZ);
                Trans3 destination = new Trans3(position.getDimension().getPosition().getDPosX(), 50, position.getDimension().getPosition().getDPosZ());
                SGBaseTE.teleportEntityAndRider(vehicle, source, source, position.getSystem().getId(), false);
                List<Entity> entities = ForgeAPI.getEntitiesInArea(position.getDPosX()-25, position.getDPosY()-25, position.getDPosZ()-25, position.getDPosX()+25, position.getDPosY()+25, position.getDPosZ()+25, position.getDimension());
                for (Entity ent : entities) {
                   if (ent instanceof MCH_EntitySeat) {
                       MCH_EntitySeat seat = (MCH_EntitySeat)ent;
                       if (seat.getParent() != null && seat.getParent().equals(vehicle)) {
                            SGBaseTE.teleportEntityAndRider(seat, source, destination, position.getSystem().getId(), false);
                       }
                   }
                }
            }
        }
    }
}

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
import net.minecraft.entity.EntityList;
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
import cr0s.warpdrive.config.Dictionary;
import gcewing.sg.SGBaseTE;
import gcewing.sg.Trans3;
import gcewing.sg.Vector3;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_ItemAircraft;
import mcheli.weapon.MCH_WeaponSet;
/**
 *
 * @author draks
 */
public class BreathingListener extends RegisterableListener {
    
    public static ArrayList<String> whitelistedVehicles = new ArrayList<String>();

    public BreathingListener() {
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
        return (BreathingListener.whitelistedVehicles.contains(itemAircraft.getAircraftInfo().displayName));
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
//        // If the entity is not a player, do nothing.
        if (!(e.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer mcPlayer = (EntityPlayer)e.entityLiving;
        RegisterablePlayer player = MMOCore.getPlayerRegistry().getRegistered(mcPlayer.getUniqueID());
        if (player.getPosition().isInSpace() || !player.getPosition().getCelestialBody().hasAtmopshere()) {                
            if (!isInMCheliApprovedVehicle(player) && Dictionary.ENTITIES_LIVING_WITHOUT_AIR.contains(EntityList.getEntityString(mcPlayer))) {
                Dictionary.ENTITIES_LIVING_WITHOUT_AIR.remove(EntityList.getEntityString(mcPlayer));
            } else {
                if (isInMCheliApprovedVehicle(player) && !Dictionary.ENTITIES_LIVING_WITHOUT_AIR.contains(EntityList.getEntityString(mcPlayer))) {
                    Dictionary.ENTITIES_LIVING_WITHOUT_AIR.add(EntityList.getEntityString(mcPlayer));                    
                }
            }
        }
    }
}

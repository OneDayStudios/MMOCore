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
import com.mmocore.api.UniverseAPI;
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
/**
 *
 * @author draks
 */
public class TransitionListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        // If the entity is not a player, do nothing.
        if (!(e.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer mcPlayer = (EntityPlayer)e.entityLiving;
        RegisterablePlayer player = MMOCore.getPlayerRegistry().getRegistered(mcPlayer.getUniqueID());
        if (player.getPosition().isInHyperSpace() || player.getPosition().getCelestialBody() == null || player.getPosition().getCelestialBody().isFake()) return;
        if (player.getPosition().isInSpace() && player.getPosition().getDPosY() < 0) {
            Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
            Trans3 dt = new Trans3(player.getPosition().getCelestialBody().getSpawnX(), 255, player.getPosition().getCelestialBody().getSpawnZ());
            if (mcPlayer.ridingEntity != null) {
                while (mcPlayer.ridingEntity != null) {
                    Entity entity = mcPlayer.ridingEntity;
                    SGBaseTE.teleportEntityAndRider(entity, t, dt, player.getPosition().getCelestialBody().getId(), false);
                }
            } else {
                SGBaseTE.teleportEntityAndRider(mcPlayer, t, dt, player.getPosition().getCelestialBody().getId(), false);
            }
        }
        if (!player.getPosition().isInSpace() && player.getPosition().getDPosY() > 300) {
            Trans3 t = new Trans3(mcPlayer.posX,mcPlayer.posY,mcPlayer.posZ);
            Trans3 dt = new Trans3(player.getPosition().getCelestialBody().getPosition().getDPosX(), 15, player.getPosition().getCelestialBody().getPosition().getDPosZ());
            if (mcPlayer.ridingEntity != null) {
                while (mcPlayer.ridingEntity != null) {
                    Entity entity = mcPlayer.ridingEntity;
                    SGBaseTE.teleportEntityAndRider(entity, t, dt, player.getPosition().getSystem().getId(), false);
                }
            } else {
                SGBaseTE.teleportEntityAndRider(mcPlayer, t, dt, player.getPosition().getSystem().getId(), false);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent e) {
        EntityPlayer player = (EntityPlayer)e.player;
        World w = ForgeAPI.getForgeWorld(e.toDim);
        RegisterablePlayer rPlayer = MMOCore.getInstance().getPlayerRegistry().getRegistered(player.getUniqueID());
        GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.Toast, UniverseAPI.getLocationMessage(rPlayer.getPosition()), UniverseAPI.getConditionsMessage(rPlayer.getPosition()), UniverseAPI.getGalaxy(rPlayer.getPosition()).getIdentifier(), 500, 500, 500, 2500);        
    }
}

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
import com.mmocore.api.EventAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.constants.uPosition;
import cpw.mods.fml.common.eventhandler.Event;
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
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraft.entity.Entity; 
import net.minecraft.world.ChunkPosition; 
import net.minecraft.world.Explosion; 
import net.minecraft.world.World; 
/**
 *
 * @author draks
 */
public class ProtectionListener extends RegisterableListener {
    
    public ProtectionListener() {
        
    }
    
    @SubscribeEvent
    public void onExplosionStart(ExplosionEvent.Start event) {
        if (event.explosion != null && event.explosion instanceof Explosion) {
            Explosion explosion = (Explosion)event.explosion;
                uPosition position = new uPosition(explosion.explosionX, explosion.explosionY, explosion.explosionZ, MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
                if (EventAPI.isAreaProtected(position)) {
                    event.setCanceled(true);
                    event.setResult(Event.Result.DENY);
                    ForgeAPI.sendConsoleEntry("Cancelled explosion triggering.", ConsoleMessageType.FINE);
                }
        }
    }
    
    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event) {
        ArrayList<ChunkPosition> blockPositions = new ArrayList<ChunkPosition>();
        ArrayList<Entity> entities = new ArrayList<Entity>();
        if (event.explosion != null && event.explosion instanceof Explosion) {
            for (ChunkPosition pos : event.getAffectedBlocks()) {
                uPosition position = new uPosition(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
                if (EventAPI.isAreaProtected(position)) blockPositions.add(pos);
            }
            for (Entity entity : event.getAffectedEntities()) {
                uPosition position = new uPosition(entity.posX, entity.posY, entity.posZ, MMOCore.getDimensionRegistry().getRegistered(entity.worldObj.provider.dimensionId));
                if (EventAPI.isAreaProtected(position)) entities.add(entity);
            }
        }
        event.getAffectedBlocks().removeAll(blockPositions);
        event.getAffectedEntities().removeAll(entities);
    }
    
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        uPosition position = new uPosition(event.x,event.y,event.z,MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
        if (EventAPI.isAreaProtected(position)) {
            ForgeAPI.sendConsoleEntry("Cancelling place event", ConsoleMessageType.FINE);
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        } else {
          ForgeAPI.sendConsoleEntry("Not cancelling place event", ConsoleMessageType.FINE);
        }
    }
    
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        uPosition position = new uPosition(event.x,event.y,event.z,MMOCore.getDimensionRegistry().getRegistered(event.world.provider.dimensionId));
        if (EventAPI.isAreaProtected(position)) {
            ForgeAPI.sendConsoleEntry("Cancelling break event", ConsoleMessageType.FINE);
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        } else {
          ForgeAPI.sendConsoleEntry("Not cancelling break event", ConsoleMessageType.FINE);
        }
   }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.TextVisibleOption;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraft.entity.*;


/**
 *
 * @author draks
 */
public class BorderListener extends RegisterableListener {
    
    public final static DamageSource source = new DamageSource("mmocore:border");
    
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        Entity entity = e.entity;
        RegisterableDimension dimension = MMOCore.getDimensionRegistry().getRegistered(entity.worldObj.provider.dimensionId);
        if (dimension != null) {
            uPosition position = new uPosition(entity.posX, entity.posY, entity.posZ, dimension);
            double distance = ForgeAPI.distance(position.getDPosX(), 0, position.getDPosZ(), position.getDimension().getSpawnX(), 0, position.getDimension().getSpawnZ());
            double actualDistance = position.getDimension().getRadiusBorderX()-distance;
            if (distance >= (position.getDimension().getRadiusBorderX())) {
                if (e.entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)e.entity;
                    damagePlayer(player, source, 1);
                } else {
                    terminateEntity(entity);
                }
            } else {
                if (distance >= (position.getDimension().getRadiusBorderX()-100)) {    
                    if (e.entity instanceof EntityPlayer) {
                       EntityPlayer player = (EntityPlayer)e.entity;
                       RegisterablePlayer rPlayer = MMOCore.getPlayerRegistry().getRegistered(player.getUniqueID());
                       GuiAPI.sendGuiElementToClient(rPlayer, GuiSlot.MissionObjective, "Dimensional Border Warning", "Distance from border: " + actualDistance, "If you reach the border of this dimension, you will die!", 500, 500, 500, 1000);
                    }
                }
            }
        } else {
            if (e.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)e.entity;
                damagePlayer(player, source, 1);
            } else {
                terminateEntity(entity);
            }
        }
    }
    
    public static void terminateEntity(Entity entity) {
        entity.setDead();
        ForgeAPI.sendConsoleEntry("Terminating entity with class: " + entity.getClass().getName() + " due to collision with world border!", ConsoleMessageType.FINE);
    }
    
    public static void damagePlayer(EntityPlayer entity, DamageSource source, int damage) {
        entity.attackEntityFrom(source, damage);
        ForgeAPI.sendConsoleEntry("Damaging player: " + entity.getUniqueID() + " due to collision with world border!", ConsoleMessageType.FINE);

    }
}

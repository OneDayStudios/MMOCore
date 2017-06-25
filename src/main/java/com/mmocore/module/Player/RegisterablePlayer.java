/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Player;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.uPosition;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class RegisterablePlayer extends AbstractRegisterable<RegisterablePlayer, UUID, EntityPlayer> {
    
    private UUID uuid;
    
    public RegisterablePlayer(UUID playerUUID) {
        this.uuid = playerUUID;        
    }
    
    @Override
    public void tick() {
        // This Object doesnt tick.
    }
    
    @Override
    public UUID getIdentifier() {
        return this.uuid;
    }    
    
    public uPosition getPosition() {
        if (getWorld() == null) {
            ForgeAPI.sendConsoleEntry("Attempted to locate player on world that is no loaded", ConsoleMessageType.FINE);
            return null;
        }
        return new uPosition((int)getPlayer().posX, (int)getPlayer().posY, (int)getPlayer().posZ, UniverseAPI.getDimension(getWorld().getWorldInfo().getWorldName()));
    }

    public World getWorld() {
        return getPlayer().worldObj;
    }
    
    public boolean isOnline() {
        return (uuid != null && getPlayer() != null);
    }
    
    public EntityPlayer getPlayer() {
        return ForgeAPI.getForgePlayer(uuid);
    }
    
    public String getName() {
        return getPlayer().getGameProfile().getName();
    }
    
    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialising player: " + getName() + " (UUID: " + this.getIdentifier() + ")" , ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalising player: " + getName() + " (UUID: " + this.getIdentifier() + ")" , ConsoleMessageType.FINE);
    }

    @Override
    public EntityPlayer getRegisteredObject() {
        return ForgeAPI.getForgePlayer(this.getIdentifier());
    }

    @Override
    public boolean canRegister() {
        return (this.getRegisteredObject() != null && this.getWorld() != null && MMOCore.getDimensionRegistry().isRegistered(getWorld().provider.dimensionId));
    }
}

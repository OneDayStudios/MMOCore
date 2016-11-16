/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Player;

import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.api.UniverseAPI;
import com.stargatemc.forge.api.uPosition;
import com.stargatemc.forge.core.AbstractRegisterable;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
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
public class RegisterablePlayer extends AbstractRegisterable<RegisterablePlayer, UUID> {
    
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
        return new uPosition((int)getPlayer().posX, (int)getPlayer().posY, (int)getPlayer().posZ, UniverseAPI.getDimension(getWorld().getWorldInfo().getWorldName()));
    }

    public World getWorld() {
        return getPlayer().worldObj;
    }
    public boolean isOnline() {
        return (getPlayer() != null);
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
}

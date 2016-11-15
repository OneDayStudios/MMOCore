/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api.gui;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.AbstractAPI;
import com.stargatemc.forge.core.constants.GuiSlot;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author draks
 */
public class GuiAPI extends AbstractAPI<GuiAPI> {
    
    public void sendGuiElementToClient(UUID playerUUID, GuiSlot slot, String title, String subTitle, String description, int titlecolor, int subtitlecolor, int descColor, long milliseconds) {
        EntityPlayer player = SForge.getInstance().getForgeAPI().getForgePlayer(playerUUID);
        if (player != null) SForge.getInstance().getChannel().sendGuiElementToClient(player, slot, title, subTitle, description, titlecolor, subtitlecolor, descColor, milliseconds);
    }
    
}

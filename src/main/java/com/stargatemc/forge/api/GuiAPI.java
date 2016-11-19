/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.api;

import com.stargatemc.forge.SForge;
import com.stargatemc.forge.core.Player.RegisterablePlayer;
import com.stargatemc.forge.core.constants.GuiSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */
public class GuiAPI extends AbstractAPI<GuiAPI> {
    
    @SideOnly(Side.SERVER)
    public static void sendGuiElementToClient(RegisterablePlayer player, GuiSlot slot, String title, String subTitle, String description, int titlecolor, int subtitlecolor, int descColor, long milliseconds) {
        if (player != null) SForge.getInstance().getChannel().sendGuiElementToClient(player.getPlayer(), slot, title, subTitle, description, titlecolor, subtitlecolor, descColor, milliseconds);
    }
    
    
}
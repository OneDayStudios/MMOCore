/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.constants.GuiSlot;
import com.mmocore.module.Gui.GuiElement;
import com.mmocore.module.Gui.RegisterableGui;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */
public class GuiAPI extends AbstractAPI<GuiAPI> {
    
    @SideOnly(Side.SERVER)
    public static void sendGuiElementToClient(RegisterablePlayer player, GuiSlot slot, String title, String subTitle, String description, int titlecolor, int subtitlecolor, int descColor, long milliseconds) {
        if (player != null) MMOCore.getInstance().getChannel().sendGuiElementToClient(player.getRegisteredObject(), slot, title, subTitle, description, titlecolor, subtitlecolor, descColor, milliseconds);
    }
    
    @SideOnly(Side.SERVER)
    public static void sendGuiElementToClient(RegisterablePlayer player, GuiElement gui, long milliseconds) {
        if (player != null) MMOCore.getInstance().getChannel().sendGuiElementToClient(player.getRegisteredObject(), gui.getSlot(), gui.getTitle(), gui.getSubTitle(), gui.getDescription(), gui.getTitleColor(), gui.getSubTitleColor(), gui.getDescriptionColor(), milliseconds);
    }
    
//    @SideOnly(Side.SERVER)
//    public static void sendGuiElementToClient(RegisterablePlayer player, RegisterableGui gui) {
//        if (player != null) MMOCore.getInstance().getChannel().sendGuiElementToClient(player.getPlayer(), gui.getSlot(), gui.getRegisteredObject().getTitle(), gui.getRegisteredObject().getSubTitle(), gui.getRegisteredObject().getDescription(), titlecolor, subtitlecolor, descColor, gui.getRegisteredObject().et);
//    }
}

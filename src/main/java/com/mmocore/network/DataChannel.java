/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.network;

import com.mmocore.MMOCore;
import com.mmocore.module.Gui.RegisterableGui;
import com.mmocore.module.Gui.GuiElement;
import com.mmocore.constants.GuiSlot;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.*;
import io.netty.buffer.*;
import io.netty.channel.*;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.*;

public class DataChannel extends BaseDataChannel {

    protected static BaseDataChannel channel;
    
    public DataChannel(String name) {
        super(name);
        channel = this;
    }
    
    @SideOnly(Side.SERVER)
    public void sendGuiElementToClient(EntityPlayer player, GuiSlot slot, String title, String subtitle, String description, int titleColor, int subtitleColor, int descriptionColor, long msActive) {
        ChannelOutput data = channel.openPlayer(player, "serverRequestedGui");
        data.writeUTF(slot.name());
        data.writeUTF(title);
        data.writeUTF(subtitle);
        data.writeUTF(description);
        data.writeInt(titleColor);
        data.writeInt(subtitleColor);
        data.writeInt(descriptionColor);
        data.writeLong(msActive);
        data.close();
    }
    
    @SideOnly(Side.CLIENT)
    @ClientMessageHandler("serverRequestedGui")
    public void handleRequestedGui(ChannelInput data) {
        String guiSlot = data.readUTF();
        String title = data.readUTF();
        String subtitle = data.readUTF();
        String description = data.readUTF();
        int titleColor = data.readInt();
        int subtitleColor = data.readInt();
        int descriptionColor = data.readInt();
        long milliseconds = data.readLong();
        GuiSlot actualSlot = GuiSlot.valueOf(guiSlot);
        RegisterableGui gui = new GuiElement(actualSlot, title, subtitle, description, titleColor, subtitleColor, descriptionColor).asRegisterable(milliseconds);
        MMOCore.getInstance().getGuiRegistry().register(gui);
    }

}

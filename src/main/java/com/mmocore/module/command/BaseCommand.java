/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.constants.ConsoleMessageType;
import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText; 
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author draks
 */
public class BaseCommand extends CommandBase {
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandName() {
        return "sgmc";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return getCommandName() + " <parameters>";
    }

    @Override
    public List getCommandAliases() {
        return Arrays.asList(new String[] {"stargatemc"});
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] params) {
        if (params.length == 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No command names passed, Try /sgmc help!"));
        } else {
            String cmdName = params[0];
            RegisterableCommand command = MMOCore.getCommandRegistry().getRegistered(cmdName);
            if (command == null) {
                ForgeAPI.sendConsoleEntry("Attempted to look up server command: " + cmdName + " which does not exist.", ConsoleMessageType.WARNING);
            } else {
                if (sender instanceof EntityPlayer) {
                    command.processCommandForPlayer(MMOCore.getInstance().getPlayerRegistry().getRegistered(((EntityPlayer)sender).getUniqueID()), params);
                } else {
                    if (sender instanceof TileEntityCommandBlock) {
                        command.processCommandForCommandBlock(params);
                    } else {
                        command.processCommandForConsole(params);
                    }
                }
            }
        }
    }

    @Override
    public int compareTo(Object obj) {
        return 0;
    }
}

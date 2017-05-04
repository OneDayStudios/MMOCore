/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command.commands;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.module.command.RegisterableCommand;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author draks
 */
public class DimensionSet extends RegisterableCommand {

    public DimensionSet(String name, String helpText) {
        super(name, helpText);
    }

    @Override
    public void processCommandForPlayer(RegisterablePlayer player, String[] parameters) {
        if (parameters.length != 7) {
            PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify: DimensionName, xPos, zPos, Radius, Conditions and Faction to continue.");
        } else {
            RegisterableDimension dim = UniverseAPI.getDimension(parameters[1]);
            if (dim == null) {
                PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify a valid dimension name.");
            } else {
                Integer posX = Integer.valueOf(parameters[2]);
                if (posX < -1900000 || posX > 1900000) {
                    PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify an X coordinate between - 1.9mil and + 1.9mil.");                    
                } else {                    
                    Integer posZ = Integer.valueOf(parameters[3]);
                    if (posZ < -1900000 || posX > 1900000) {
                        PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify an Z coordinate between - 1.9mil and + 1.9mil.");                    
                    } else {
                        Integer radius = Integer.valueOf(parameters[4]);
                        if (radius < 500 || radius > 2500) {
                            PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify a radius between 500 and 2500");
                        } else {
                            String conditions = parameters[5];
                            if (DimensionConditions.valueOf(conditions) == null) {
                                PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify a valid dimension atmosphere, options are: Barren, Unstable, Optimal, Frozen, Flooded,Hyperspace,Space,Unknown.");
                            } else {
                                String type = parameters[6];
                                if (DimensionType.valueOf(type) == null) {
                                    PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify a valid dimension type, options are: Hyperspace, Space, Ship, Planet, Unknown" );
                                } else {
                                    String faction = parameters[7];
                                    if (NpcFactionAPI.getRegistered(faction) == null && !"NONE".equals(faction)) {
                                    PlayerAPI.sendMessage(player, EnumChatFormatting.RED + "You must specify a valid faction name, or NONE to continue.");
                                    } else {
                                        dim.setFaction(faction);
                                        dim.setPosX(posX);
                                        dim.setPosZ(posZ);
                                        dim.setRadius(radius);
                                        dim.setConditions(DimensionConditions.valueOf(conditions));
                                        dim.setType(DimensionType.valueOf(type));
                                        PlayerAPI.sendMessage(player, EnumChatFormatting.GREEN + "Successfully updated dimension: " + dim.getName() + "!");
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
    }

    @Override
    public void processCommandForCommandBlock(String[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processCommandForConsole(String[] parameters) {
        ForgeAPI.sendConsoleEntry("This ia test command!", ConsoleMessageType.FINE);
    }

    
}

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
public class UniversalPosition extends RegisterableCommand {

    public UniversalPosition(String name, String helpText) {
        super(name, helpText);
    }

    @Override
    public void processCommandForPlayer(RegisterablePlayer player, String[] parameters) {
        PlayerAPI.sendMessage(player, "Dimension Coordinates: " + player.getPosition().getDPosX() + "," + player.getPosition().getDPosY() + "," + player.getPosition().getDPosZ());
        PlayerAPI.sendMessage(player, "Universal Coordinates: " + Math.round(player.getPosition().getUPosX()) + "," + Math.round(player.getPosition().getUPosZ()));
        if (player.getPosition().getDimension() != null) PlayerAPI.sendMessage(player, "Dimension:" + player.getPosition().getDimension().getName());
        if (player.getPosition().getCelestialBody() != null) PlayerAPI.sendMessage(player, "Celestial Body:" + player.getPosition().getCelestialBody().getName());
    }

    @Override
    public void processCommandForCommandBlock(String[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processCommandForConsole(String[] parameters) {
        ForgeAPI.sendConsoleEntry("This must be run by players!", ConsoleMessageType.FINE);
    }

    
}

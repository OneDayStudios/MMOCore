/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command.commands;

import com.mmocore.MMOCore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.events.TellPlayerEvent;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.module.command.RegisterableCommand;
import gcewing.sg.SGAddressing;
import gcewing.sg.SGBaseTE;
import gcewing.sg.SGState;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author draks
 */
public class DialStargate extends RegisterableCommand {

    public DialStargate(String name, String helpText) {
        super(name, helpText);
    }

    @Override
    public void processCommandForPlayer(RegisterablePlayer player, String[] parameters) {
        PlayerAPI.sendMessage(player, "This command is only usable by NPCs.");
    }

    @Override
    public void processCommandForCommandBlock(String[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processCommandForConsole(String[] parameters) {
        String sourceAddress = parameters[0];
        String destinationAddress = parameters[1];
        String npcName = parameters[2];
        String npcTitle = parameters[3];
        String playerId = parameters[4];
        RegisterablePlayer player = PlayerAPI.getForName(parameters[4]);
        ForgeAPI.sendConsoleEntry("Command passed as: " + Arrays.toString(parameters), ConsoleMessageType.WARNING);                
        if (player == null) {
            ForgeAPI.sendConsoleEntry("Couldnt find Player", ConsoleMessageType.FINE);
            return;
        }
        RegisterableNpc npc = NpcAPI.get(npcName, npcTitle);
        if (npc == null) {
            ForgeAPI.sendConsoleEntry("Couldnt find NPC", ConsoleMessageType.FINE);
            return;
        }
        try {
            SGBaseTE source = SGAddressing.findAddressedStargate(sourceAddress, npc.getEntity().worldObj);
            try {
                SGBaseTE destination = SGAddressing.findAddressedStargate(destinationAddress, npc.getEntity().worldObj);
                String resultFromDial = source.connect(destinationAddress, null);
                if (resultFromDial != null) {
                    TellPlayerEvent result = new TellPlayerEvent("TellPlayer-" + npcName + npcTitle + playerId, npc, player, "We cant dial out right now, because " + resultFromDial);
                    MMOCore.getGameEventRegistry().register(result);
                } else {
                    TellPlayerEvent result = new TellPlayerEvent("TellPlayer-" + npcName + npcTitle + playerId, npc, player, "Alright, beginning the dialling sequence!");
                    MMOCore.getGameEventRegistry().register(result);
                }
            } catch(Exception e) {
                TellPlayerEvent result = new TellPlayerEvent("TellPlayer-" + npcName + npcTitle + playerId, npc, player, "For some reason the address: " + destinationAddress + " is invalid!");
                MMOCore.getGameEventRegistry().register(result);
            }
        } catch (Exception e) {
            TellPlayerEvent result = new TellPlayerEvent("TellPlayer-" + npcName + npcTitle + playerId, npc, player, "Something is wrong, the gate address: " + sourceAddress + " is invalid!");
            MMOCore.getGameEventRegistry().register(result);
        }           
    }
}

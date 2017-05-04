/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command.commands;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Player.RegisterablePlayer;
import com.mmocore.module.command.RegisterableCommand;

/**
 *
 * @author draks
 */
public class TestCommand extends RegisterableCommand {

    public TestCommand(String name, String helpText) {
        super(name, helpText);
    }

    @Override
    public void processCommandForPlayer(RegisterablePlayer player, String[] parameters) {
        PlayerAPI.sendMessage(player, "Test command successfully run!");
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

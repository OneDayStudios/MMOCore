/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command;

import com.mmocore.api.ForgeAPI;
import com.mmocore.module.AbstractRegisterable;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Player.RegisterablePlayer;

/**
 *
 * @author Drakster
 */
public abstract class RegisterableCommand extends AbstractRegisterable<RegisterableCommand, String, RegisterableCommand> {
    
    public String name;
    public String helpText;
    
    public RegisterableCommand(String name, String helpText) {
        this.name = name;
        this.helpText = helpText;
    }
        
    public abstract void processCommandForPlayer(RegisterablePlayer player, String[] parameters);
    public abstract void processCommandForCommandBlock(String[] parameters);
    public abstract void processCommandForConsole(String[] parameters);
    
    
    public String getHelpText() {
        return this.helpText;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void initialise() {
        ForgeAPI.sendConsoleEntry("Initialising command with name: " + this.name, ConsoleMessageType.FINE);
    }

    @Override
    public void finalise() {
        ForgeAPI.sendConsoleEntry("Finalising command with name: " + this.name, ConsoleMessageType.FINE);
    }

    @Override
    public RegisterableCommand getRegisteredObject() {
        return this;
    }

    @Override
    public String getIdentifier() {
        return this.name;
    }
    
    @Override
    public boolean canRegister() {
        return (this != null);
    }
}

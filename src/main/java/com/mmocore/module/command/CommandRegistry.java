/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command;

import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.command.commands.TestCommand;

/**
 *
 * @author draks
 */
public class CommandRegistry extends AbstractRegistry<CommandRegistry, String, RegisterableCommand> {

    @Override
    public void initialise() {
        this.register(new TestCommand("test", "This is a test command!"));
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return true;
    }

}

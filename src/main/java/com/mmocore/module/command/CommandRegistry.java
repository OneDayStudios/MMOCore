/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.command;

import com.mmocore.module.AbstractRegistry;
import com.mmocore.module.command.commands.DialStargate;
import com.mmocore.module.command.commands.UniversalPosition;

/**
 *
 * @author draks
 */
public class CommandRegistry extends AbstractRegistry<CommandRegistry, String, RegisterableCommand> {

    @Override
    public void initialise() {
        this.register(new UniversalPosition("position", "Provides universal positioning information"));
        this.register(new DialStargate("dialgate", "Provides NPCs and console the ability to force two gates to connect."));
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

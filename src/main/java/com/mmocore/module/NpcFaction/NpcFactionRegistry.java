/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.NpcFaction;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.AbstractRegistry;
import com.mmocore.constants.IntegratedMod;
import java.util.ArrayList;
import noppes.npcs.controllers.Faction;

/**
 *
 * @author draks
 */
public class NpcFactionRegistry extends AbstractRegistry<NpcFactionRegistry, Integer, RegisterableNpcFaction> {

    @Override
    public void initialise() {
        ArrayList<String> factionNames = new ArrayList<String>();
        for (Faction f : NpcFactionAPI.getAllFactionsReadOnly()) {
            factionNames.add(f.name);
            ForgeAPI.sendConsoleEntry("Detected existing Npc Faction: " + f.name + ", queuing for initialisation.", ConsoleMessageType.FINE);
        }
        for (String name : factionNames) {
            RegisterableNpcFaction faction = new RegisterableNpcFaction(name);
            ForgeAPI.sendConsoleEntry("Initialising existing Npc Faction: " + faction.getName(), ConsoleMessageType.FINE);
        }
    }

    @Override
    public void finalise() {
        for (RegisterableNpcFaction faction : NpcFactionAPI.getAllRegisteredFactionsReadOnly()) {
            this.deregister(faction.getID());
            ForgeAPI.sendConsoleEntry("Uninitialising Npc Faction: " + faction.getName(), ConsoleMessageType.FINE);
        }
    }

    @Override
    public boolean canBeEnabled() {
        return (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs));
    }

}

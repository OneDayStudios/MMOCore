/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.QuestAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.AbstractRegistry;
import java.util.HashMap;
import noppes.npcs.controllers.Quest;

/**
 *
 * @author draks
 */
public class QuestRegistry extends AbstractRegistry<QuestRegistry, Integer, RegisterableQuest> {

    @Override
    public void initialise() {
        HashMap<String, String> quests = new HashMap<String, String>();
        for (Quest q : QuestAPI.getAllReadOnly()) {
            quests.put(q.title, q.category.title);
            ForgeAPI.sendConsoleEntry("Detected existing Quest: " + q.title + " (" + q.id + ") with category: " + q.category.title + "), queuing for initialisation.", ConsoleMessageType.FINE);
        }
        for (String QuestName : quests.keySet()) {
            RegisterableQuest Quest = new RegisterableQuest(QuestName, quests.get(QuestName));
            this.register(Quest);
            ForgeAPI.sendConsoleEntry("Initialised existing Quest: " + Quest.getBaseOptions().getTitle() + " (" + Quest.getID() + ") with category: " + Quest.getBaseOptions().getQuestChain() + ".", ConsoleMessageType.FINE);
        }    
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

    @Override
    public boolean canBeEnabled() {
        return ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs);
    }

}

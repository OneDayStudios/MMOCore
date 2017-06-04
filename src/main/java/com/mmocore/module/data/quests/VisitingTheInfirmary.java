/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.quests;

import com.mmocore.api.EventAPI;
import com.mmocore.module.GameEvent.events.QuestLocationEvent;
import com.mmocore.module.Quest.RegisterableQuest;
import com.mmocore.module.Quest.options.QuestObjectiveOptions;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class VisitingTheInfirmary extends RegisterableQuest {
    
    public VisitingTheInfirmary() {
        super(
        "Visiting The Infirmary",
        "Tutorial"
        );
        QuestObjectiveOptions opts = this.getObjectiveOptions();
        ArrayList<QuestLocationEvent> events = new ArrayList<QuestLocationEvent>();
        events.add((QuestLocationEvent)EventAPI.getRegistered("Visiting Infirmary"));
        opts.setOrUpdateQuestTypeLocation(events);
        this.setObjectiveOptions(opts);
    }
    
}

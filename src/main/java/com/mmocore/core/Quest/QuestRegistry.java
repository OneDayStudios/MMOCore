/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.core.Quest;

import com.mmocore.core.AbstractRegistry;

/**
 *
 * @author draks
 */
public class QuestRegistry extends AbstractRegistry<QuestRegistry, Integer, RegisterableQuest> {

    @Override
    public void initialise() {
        // Not required.
    }

    @Override
    public void finalise() {
        // No code required, yet.
    }

}

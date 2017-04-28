/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest;

import com.mmocore.api.ForgeAPI;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.AbstractRegistry;

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

    @Override
    public boolean canBeEnabled() {
        return ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.location.RegisterableLocation;

/**
 *
 * @author draks
 */
public class QuestLocationObjective {
    
    private RegisterableLocation location;
    
    public QuestLocationObjective(RegisterableLocation location) {
        this.location = location;
    }
    
}

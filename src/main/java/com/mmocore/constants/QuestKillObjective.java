/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Npc.RegisterableNpc;

/**
 *
 * @author draks
 */
public class QuestKillObjective {
    
    private RegisterableNpc npc;
    private int numberOf;
    
    public QuestKillObjective(RegisterableNpc npc, int numberOf) {
        this.npc = npc;
        this.numberOf = numberOf;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import com.mmocore.module.Npc.loadout.NpcItem;

/**
 *
 * @author draks
 */
public class QuestItemObjective {
    
    private NpcItem item;
    private int numberOf;
    
    public QuestItemObjective(NpcItem item, int numberOf) {
        this.item = item;
        this.numberOf = numberOf;
    }
    
}

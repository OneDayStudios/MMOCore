/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcLootMode;
import com.mmocore.module.Npc.loadout.NpcItem;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class NpcLootOptions {
    
    private NpcLootMode lootMode = NpcLootMode.DEFAULT;
    private NpcAbstractScale experienceDropped = NpcAbstractScale.Lowest;
    private HashMap<NpcItem, Integer> lootTable = new HashMap<NpcItem, Integer>();
    
    
    public NpcLootMode getLootMode() {
        return this.lootMode;
    }
    
    public void setLootMode(NpcLootMode mode) {
        this.lootMode = mode;
    }
    
    public NpcAbstractScale getExpDropped() {
        return this.experienceDropped;
    }
    
    public void setExpDropped(NpcAbstractScale experience) {
        this.experienceDropped = experience;
    }
    
    public boolean addToLootTable(int chanceToDrop, NpcItem item) {
        if (lootTable.containsKey(item)) return false;
        if (chanceToDrop < 1 || chanceToDrop > 100) return false;
        if (lootTable.size() > 7) return false;
        lootTable.put(item, chanceToDrop);
        return true;
    }
    
    public HashMap<NpcItem, Integer> getLootTable() {
        return this.lootTable;
    }
    
    public HashMap<NpcItem, Integer> getLootTableReadOnly() {
        return new HashMap<NpcItem, Integer>(this.lootTable);
    }
    
    public boolean removeFromLootTable(NpcItem item) {
        if (!lootTable.containsKey(item)) return false;
        lootTable.remove(item);
        return true;
    }
}

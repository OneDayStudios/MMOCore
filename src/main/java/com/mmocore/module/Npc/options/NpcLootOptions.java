/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcFactionValue;
import com.mmocore.constants.NpcLootMode;
import com.mmocore.module.Npc.loadout.NpcItem;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class NpcLootOptions {
    
    private NpcLootMode lootMode = NpcLootMode.DEFAULT;
    private AbstractScale experienceDropped = AbstractScale.Lowest;
    private HashMap<NpcItem, Integer> lootTable = new HashMap<NpcItem, Integer>();
    private NpcFactionValue primaryFactionValue = null;
    private NpcFactionValue secondaryFactionValue = null;

    public NpcLootOptions() {
        
    }
    
    public NpcLootOptions(NpcLootOptions lootOptions) {
        this.lootMode = lootOptions.lootMode;
        this.experienceDropped = lootOptions.experienceDropped;
        this.lootTable = new HashMap<NpcItem, Integer>(lootOptions.lootTable);
        this.primaryFactionValue = lootOptions.primaryFactionValue;
        this.secondaryFactionValue = lootOptions.secondaryFactionValue;
    }
    
    public NpcFactionValue getPrimaryFaction() {
        return this.primaryFactionValue;
    }
    
    public NpcFactionValue getSecondaryFaction() {
        return this.secondaryFactionValue;
    }
    
    public void setPrimaryFaction(NpcFactionValue value) {
        this.primaryFactionValue = value;
    }
    
    public void setSecondaryFaction(NpcFactionValue value) {
        this.secondaryFactionValue = value;
    }
    
    public NpcLootMode getLootMode() {
        return this.lootMode;
    }
    
    public void setLootMode(NpcLootMode mode) {
        this.lootMode = mode;
    }
    
    public AbstractScale getExpDropped() {
        return this.experienceDropped;
    }
    
    public void setExpDropped(AbstractScale experience) {
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

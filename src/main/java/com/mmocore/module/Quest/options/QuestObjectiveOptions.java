/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest.options;

import com.mmocore.constants.QuestType;

/**
 *
 * @author draks
 */
public class QuestObjectiveOptions {
    
    private QuestType type;
    
    
    public void setType(QuestType type) {
        this.type = type;
    }
    
    public QuestType getType() {
        return this.type;
    }
}

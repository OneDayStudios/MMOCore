/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Quest.options;

import com.mmocore.constants.QuestRepeatType;

/**
 *
 * @author draks
 */
public class QuestBaseOptions {
    
    public String title;
    public String questChainName;
    public String questLogText;
    public QuestRepeatType repeatType;
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setQuestChainName(String chainName) {
        this.questChainName = chainName;
    }
    
    public String getQuestChainName() {
        return this.questChainName;
    }
    
    public void setQuestLogText(String text) {
        this.questLogText = text;
    }
    
    public String getQuestLogText() {
        return this.questLogText;
    }
    
    public void setQuestRepeatType(QuestRepeatType type) {
        this.repeatType = type;
    }
    
    public QuestRepeatType getRepeatType() {
        return this.repeatType;
    }
}

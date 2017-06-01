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
    
    public String title = "No Quest Title Defined";
    public String questChain = "No Quest Chain Defined";
    public String questLogText = "No quest log information available.";
    public QuestRepeatType repeatType = QuestRepeatType.Never;
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setQuestChain(String chain) {
        this.questChain = chain;
    }
    
    public String getQuestChain() {
        return this.questChain;
    }
    
    public void setQuestLogText(String text) {
        //TODO: Break this up into lines and add color support.
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

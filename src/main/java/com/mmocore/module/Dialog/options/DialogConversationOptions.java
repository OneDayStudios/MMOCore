/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog.options;

import com.mmocore.constants.DialogConversationOption;

/**
 *
 * @author draks
 */
public class DialogConversationOptions {
    
    private DialogConversationOption optionOne = new DialogConversationOption();
    private DialogConversationOption optionTwo = new DialogConversationOption();
    private DialogConversationOption optionThree = new DialogConversationOption();
    private DialogConversationOption optionFour = new DialogConversationOption();
    private DialogConversationOption optionFive = new DialogConversationOption();
    private DialogConversationOption optionSix = new DialogConversationOption();
    
    public void setDialogOne(DialogConversationOption dialog) {
        this.optionOne = dialog;
    }
    
    public void setDialogTwo(DialogConversationOption dialog) {
        this.optionTwo = dialog;
    }
    
    public void setDialogThree(DialogConversationOption dialog) {
        this.optionThree = dialog;
    }
    
    public void setDialogFour(DialogConversationOption dialog) {
        this.optionFour = dialog;
    }
    
    public void setDialogFive(DialogConversationOption dialog) {
        this.optionFive = dialog;
    }
    
    public void setDialogSix(DialogConversationOption dialog) {
        this.optionSix = dialog;
    }
    
    public DialogConversationOption getOptionOne() {
        return this.optionOne;
    }
    
    public DialogConversationOption getOptionTwo() {
        return this.optionTwo;
    }
    
    public DialogConversationOption getOptionThree() {
        return this.optionThree;
    }
    
    public DialogConversationOption getOptionFour() {
        return this.optionFour;
    }
    
    public DialogConversationOption getOptionFive() {
        return this.optionFive;
    }
    
    public DialogConversationOption getOptionSix() {
        return this.optionSix;
    }
}

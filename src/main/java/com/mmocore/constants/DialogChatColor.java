package com.mmocore.constants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author draks
 */
public enum DialogChatColor {
    
    RED(255);
    
    int number = 0;
    
    DialogChatColor(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return this.number;
    }
    
}

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
    
    LIGHT_BLUE(6114793),
    DARK_BLUE(790271),
    PURPLE(8394640),
    YELLOW(15519286),
    DARK_RED(13569808),
    LIGHT_RED(16125966),
    DARK_GREEN(2129183),
    LIGHT_GREEN(2880298),
    DARK_BROWN(4206107),
    LIGHT_BROWN(726954),
    ORANGE(16758529),
    GRAY(11578530),
    WHITE(16514043),
    BLACK(131330);
    
    int number = 0;
    
    DialogChatColor(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return this.number;
    }
    
}

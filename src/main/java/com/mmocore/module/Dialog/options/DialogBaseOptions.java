/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Dialog.options;

/**
 *
 * @author draks
 */
public class DialogBaseOptions {
    
    private boolean hasWheel = false;
    private String sound = "No sound";
    private String text = "No Text";
    private String title = "No Title";
    private boolean escDisabled = false;
    private boolean hideNpc = false;
    private String category = "NoCategory";
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean getHidesNpc() {
        return this.hideNpc;
    }
    
    public void setHidesNpc(boolean hides) {
        this.hideNpc = hides;
    }
    
    public void setEscDisabled(boolean disabled) {
        this.escDisabled = disabled;
    }
    
    public boolean getEscDisabled() {
        return this.escDisabled;
    }
    
    public void setHasDialogWheel(boolean value) {
        this.hasWheel = value;
    }
    
    public boolean getHasDialogWheel() {
        return this.hasWheel;
    }
    
    public String getSound() {
        return this.sound;
    }
    
    public void setSound(String soundResource) {
        this.sound = soundResource;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    
    
}

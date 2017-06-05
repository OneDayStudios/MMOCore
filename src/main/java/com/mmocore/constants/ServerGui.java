/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

/**
 *
 * @author draks
 */
public class ServerGui {
    
    private String title;
    private String subtitle;
    private String description;
    private DialogChatColor titleColor = DialogChatColor.WHITE;
    private DialogChatColor subtitleColor = DialogChatColor.WHITE;
    private DialogChatColor descriptionColor = DialogChatColor.WHITE;
    private long duration;
    private GuiSlot slot;
    
    public ServerGui(String title, String subtitle, String description, long durationms, GuiSlot slot) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.duration = duration;
        this.slot = slot;
    }
    
    public void setTitleColor(DialogChatColor color) {
        this.titleColor = color;
    }
    
    public void setSubtitleColor(DialogChatColor color) {
        this.subtitleColor = color;
    }
    
    public void setDescriptionColor(DialogChatColor color) {
        this.descriptionColor = color;
    }
    
    public DialogChatColor getTitleColor() {
        return this.titleColor;
    }
    
    public DialogChatColor getSubTitleColor() {
        return this.subtitleColor;
    }
    
    public DialogChatColor getDescriptionColor() {
        return this.descriptionColor;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getSubTitle() {
        return this.subtitle;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public long getDuration() {
        return this.duration;
    }
    
    public void setSlot(GuiSlot slot) {
        this.slot = slot;
    }
    
    public GuiSlot getSlot() {
        return this.slot;
    }
    
    public String getDescription() {
        return this.description;
    }
    
}

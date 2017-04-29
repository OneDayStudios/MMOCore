/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Gui;
import com.mmocore.constants.GuiSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author draks
 */
@SideOnly(Side.CLIENT)
public class GuiElement extends Gui {

    private int width = 0;
    private int height = 0;
    private Minecraft mc;    
    private GuiSlot slot;

    private String title;
    private int titleColor;
    private String subtitle;
    private int subtitleColor;
    private String description;
    private int descriptionColor;
    
    public GuiElement(GuiSlot slot, String title, String subtitle, String description, int titleColor, int subTitleColor, int descriptionColor) {
        this.mc = Minecraft.getMinecraft();
        this.slot = slot;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.titleColor = titleColor;
        this.descriptionColor = descriptionColor;
        this.subtitleColor = subTitleColor;
    }
    
    public void drawRectangle(int posX, int posY, int sizeX, int sizeY, int color) {
        drawRect(posX, posY, sizeX, sizeY, color);
    }
    
    public void drawGradientRectangle(int posX, int posY, int sizeX, int sizeY, int firstColor, int secondColor) {
        drawGradientRect(posX, posY, sizeX, sizeY, firstColor, secondColor);
    }
    
    public void drawStringWithShadow(String text, int posX, int posY, int color) {
        mc.fontRenderer.drawStringWithShadow(text, posX, posY, color);
    }
    
    public int getScaledHeight() {
        ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return scaled.getScaledHeight();
    }
    
    public int getScaledWidth() {
        ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return scaled.getScaledWidth();
    }
    
    public void drawCenteredString(String text, int posX, int posY, int color) {
        this.drawCenteredString(mc.fontRenderer, text, posX, posY, color);
    }
    
    public void drawString(String text, int posX, int posY, int color) {
        this.drawString(mc.fontRenderer, text, posX, posY, color);
    }
    
    public void drawScaledString(String text, int posX, int posY, int color, float scale, boolean centred) {
        
        if (scale != 1.0) {
            GL11.glPushMatrix();
            GL11.glScalef(scale, scale, scale);
            if (scale < 1.0) {
                posX = (int)((1.0 / scale) * posX);
                posY = (int)((1.0 / scale) * posY);
            } else {
                if (scale > 1.0) {
                    posX = (int)(posX / (scale / 1.0));
                    posY = (int)(posY / (scale / 1.0));
                }
            }
        }
        if (centred) this.drawCenteredString(mc.fontRenderer, text, posX, posY, color);
        if (!centred) this.drawString(text, posX, posY, color);
        this.stopScaling();
        // Pop the matrix because that should be whats triggering issues
        GL11.glPopMatrix();
    }
    
    public RegisterableGui asRegisterable(long durationms) {
        return new RegisterableGui(this, durationms);
    }
    
    public void stopScaling() {
        GL11.glPopMatrix();
    }

    public GuiSlot getSlot() {
        return slot;
    }
    
    public void render() {
        if (getSlot().equals(GuiSlot.Toast)) renderAsToast();
        if (getSlot().equals(GuiSlot.TopLeft)) renderAsTopLeft();
    }
    
    private void renderAsToast() {
        this.drawScaledString(title, (this.getScaledWidth() / 2),  + (this.getScaledHeight() / 2) - (this.getScaledHeight() / 10), titleColor, (float)2.0, true);
        this.drawScaledString(subtitle, (this.getScaledWidth() / 2),  + (this.getScaledHeight() / 2), subtitleColor, (float)1.0, true);
        this.drawScaledString(description, (this.getScaledWidth() / 2),  + (this.getScaledHeight() / 2) + (this.getScaledHeight() / 20), descriptionColor, (float)0.5, true);
    }
    
    private void renderAsTopLeft() {
        this.drawScaledString(title, (this.getScaledWidth() / 25),  (this.getScaledHeight() / 25), titleColor, (float)0.8, false);
        this.drawScaledString(subtitle, (this.getScaledWidth() / 25), (this.getScaledHeight() / 14), subtitleColor, (float)0.8, false);
        this.drawScaledString(description, (this.getScaledWidth() / 25), (this.getScaledHeight() / 10), descriptionColor, (float)0.5, false);
    }
}

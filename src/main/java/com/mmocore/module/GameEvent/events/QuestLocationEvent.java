/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.GameEvent.events;

import com.mmocore.api.ForgeAPI;
import com.mmocore.api.GuiAPI;
import com.mmocore.api.PlayerAPI;
import com.mmocore.api.QuestAPI;
import com.mmocore.constants.ServerGui;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.GameEvent.GameEvent;
import com.mmocore.module.Gui.GuiElement;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class QuestLocationEvent extends GameEvent {

    private String name;
    private uPosition position;
    private ArrayList<RegisterablePlayer> players = new ArrayList<RegisterablePlayer>();
    private int radiusX = 0;
    private int radiusZ = 0;
    private int radiusY = 0;
    private ServerGui enterGui = null;
    private ServerGui completeLocationGui = null;
    private ServerGui exitGui = null;
    private boolean isProtected;
    
    public QuestLocationEvent(String name, uPosition position, int radiusX, int radiusY, int radiusZ) {
        super(name);
        this.name = name;
        this.position = position;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }

    public void setRadiusX(int x) {
        this.radiusX = x;
    }
    
    public void setRadiusY(int y) {
        this.radiusY = y;
    }
    
    public void setRadiusZ(int z) {
        this.radiusZ = z;
    }
    
    public int getRadiusX() {
        return this.radiusX;
    }
    
    public int getRadiusY() {
        return this.radiusY;
    }
    
    public int getRadiusZ() {
        return this.radiusZ;
    }
    
    public String getName() {
        return this.name;
    }
    
    public uPosition getPosition() {
        return this.position;
    }
    
    public boolean playerInArea(RegisterablePlayer player) {
        return players.contains(player);
    }
    
    public void addPlayer(RegisterablePlayer player) {
       if (!playerInArea(player)) players.add(player);
    }
    
    public void removePlayer(RegisterablePlayer player) {
       if (playerInArea(player)) players.remove(player);
    }
    
    public ArrayList<RegisterablePlayer> getPlayersInArea() {
        return players;
    }
    
    public ArrayList<RegisterablePlayer> getPlayersInAreaReadOnly() {
        return new ArrayList<RegisterablePlayer>(players);
    }
    
    public ServerGui getEnterGui() {
        return this.enterGui;
    }
    
    public ServerGui getCompletionGui() {
        return this.completeLocationGui;
    }
    
    public ServerGui getExitGui() {
        return this.exitGui;
    }
    
    public void setEnterGui(ServerGui element) {
        this.enterGui = element;
    }
    
    public void setExitGui(ServerGui element) {
        this.exitGui = element;
    }
    
    public void setCompletedGui(ServerGui element) {
        this.completeLocationGui = element;
    }
    
    @Override
    public void tickForDimension(RegisterableDimension dimension) {
        if (!getPosition().getDimension().equals(dimension)) return;
        ArrayList<RegisterablePlayer> playersInArea = PlayerAPI.getInArea(getPosition().getDPosX()-this.getRadiusX(), getPosition().getDPosY()-this.getRadiusY(), getPosition().getDPosZ()-getRadiusZ(), getPosition().getDPosX()+this.getRadiusX(), getPosition().getDPosY()+this.getRadiusY(),this.getPosition().getDPosZ()+this.getRadiusZ(),dimension);
        load(playersInArea);
        grantCreditIfRequired();
        cleanup(playersInArea);
    }
    
    private void grantCreditIfRequired() {
        for (RegisterablePlayer p : this.getPlayersInArea()) {
            if (!p.isOnline()) continue;
            if (QuestAPI.playerHasLocationQuestForLocation(p, this) && !QuestAPI.hasPlayerCompletedLocation(p, this)) {
                if (this.getCompletionGui() != null) GuiAPI.sendGuiElementToClient(p, getCompletionGui());
                QuestAPI.completePlayerLocation(p, this);
            }
        }
    }
    
    private void load(ArrayList<RegisterablePlayer> players) {
        for (RegisterablePlayer p : players) {
            if (!playerInArea(p)) {
                if (this.getEnterGui() != null) GuiAPI.sendGuiElementToClient(p, getEnterGui());
                addPlayer(p);
            }
        }
    }
    
    private void cleanup(ArrayList<RegisterablePlayer> players) {
        for (RegisterablePlayer p : getPlayersInAreaReadOnly()) {
            if (!players.contains(p)) {
                if (this.getExitGui() != null) GuiAPI.sendGuiElementToClient(p, getExitGui());
                removePlayer(p);
            }
        }
    }

    public void setProtected(boolean value) {
        this.isProtected = value;
    }
    
    public boolean isProtected() {
        return this.isProtected;
    }

    public boolean containsPosition(uPosition position) {
        if (!this.getPosition().getDimension().equals(position.getDimension())) return false;
        double distanceX = Math.abs(position.getDPosX() - this.getPosition().getDPosX());
        double distanceY = Math.abs(position.getDPosY() - this.getPosition().getDPosY());
        double distanceZ = Math.abs(position.getDPosZ() - this.getPosition().getDPosZ());
        if (Math.abs(distanceX) > this.getRadiusX()) return false;
        if (Math.abs(distanceY) > this.getRadiusY()) return false;
        if (Math.abs(distanceZ) > this.getRadiusZ()) return false;
        return true;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcBoolean;
import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author draks
 */
public class NpcInteractOptions {

    private List<String> repeatableUnavailableLines = new ArrayList<String>();
    private List<String> worldLines = new ArrayList<String>();
    private List<String> interactLines = new ArrayList<String>();
    private List<String> deathLines = new ArrayList<String>();
    private List<String> killLines = new ArrayList<String>();
    private List<String> attackLines = new ArrayList<String>();
    private NpcBoolean stopsOnInteract = NpcBoolean.YES;
    private NpcBoolean interactsWithOtherNpcs = NpcBoolean.YES;
    
//    // TODO: Move this method to the NpcAPI.
//    public String getRepeatableUnavailableLine(RegisterablePlayer player) {
//       if (repeatableUnavailableLines.isEmpty()) return "I have nothing for you right now, @p.";
//       if (repeatableUnavailableLines.size() == 1) return parseLine(player, repeatableUnavailableLines.get(0));
//       Random ran = new Random();
//       int number = ran.nextInt(repeatableUnavailableLines.size());
//       if (number != 0) number = number - 1;
//       return parseLine(player, repeatableUnavailableLines.get(number));
//    }
    
    public NpcBoolean getInteractsWithOtherNpcs() {
        return this.interactsWithOtherNpcs;
    }
    
    public void setInteractsWithOtherNpcs(NpcBoolean setting) {
        this.interactsWithOtherNpcs = setting;
    }
    
    public NpcBoolean getStopsOnInteract() {
        return this.stopsOnInteract;
    }
    
    public void setStopsOnInteract(NpcBoolean setting) {
        this.stopsOnInteract = setting;
    }
    
    public List<String> getRepeatableUnavailableLines() {
        return this.repeatableUnavailableLines;
    }
    
    public void addRandomRepeatableUnavailableLine(String s) {
        if (!repeatableUnavailableLines.contains(s)) repeatableUnavailableLines.add(s);
    }
    
    public void removeRepeatableUnavailableLine(String s) {
        if (repeatableUnavailableLines.contains(s)) repeatableUnavailableLines.remove(s);
    }
    
    public List<String> getWorldLines() {
        return this.worldLines;
    }
    
    public void addWorldLine(String s) {
        if (!worldLines.contains(s)) worldLines.add(s);
    }
    
    public void removeWorldLine(String s) {
        if (worldLines.contains(s)) worldLines.remove(s);
    }
    
    public List<String> getDeathLines() {
        return this.deathLines;
    }
    
    public void addDeathLine(String s) {
        if (!deathLines.contains(s)) deathLines.add(s);
    }
    
    public void removeDeathLine(String s) {
        if (deathLines.contains(s)) deathLines.remove(s);
    }
    
    public List<String> getKillLines() {
        return this.killLines;
    }
    
    public void addKillLine(String s) {
        if (!killLines.contains(s)) killLines.add(s);
    }
    
    public void removeKillLine(String s) {
        if (killLines.contains(s)) killLines.remove(s);
    }
    
    public List<String> getInteractLines() {
        return this.interactLines;
    }
    
    public void addInteractLine(String s) {
        if (!interactLines.contains(s)) interactLines.add(s);
    }
    
    public void removeInteractLine(String s) {
        if (interactLines.contains(s)) interactLines.remove(s);
    }
    
    public List<String> getAttackLines() {
        return this.attackLines;
    }
    
    public void addAttackLine(String s) {
        if (!attackLines.contains(s)) attackLines.add(s);
    }
    
    public void removeAttackLine(String s) {
        if (attackLines.contains(s)) attackLines.remove(s);
    }
    
}

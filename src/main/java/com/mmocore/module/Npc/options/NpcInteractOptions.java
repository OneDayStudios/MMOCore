/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcDialogOption;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<NpcDialogOption> dialogs = new ArrayList<NpcDialogOption>();
    private NpcBoolean sayRandomLines = NpcBoolean.YES;
    
    public void setSayRandomLines(NpcBoolean setting) {
        this.sayRandomLines = setting;
    }
    
    public NpcBoolean getSayRandomLines() {
        return this.sayRandomLines;
    }
    
    public ArrayList<NpcDialogOption> getDialogs() {
        return this.dialogs;
    }
    
    public void addDialog(NpcDialogOption dialog) {
        if (!this.dialogs.contains(dialog)) this.dialogs.add(dialog);
    }
    
    public void removeDialog(NpcDialogOption dialog) {
        if (this.dialogs.contains(dialog)) this.dialogs.remove(dialog);
    }
    
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

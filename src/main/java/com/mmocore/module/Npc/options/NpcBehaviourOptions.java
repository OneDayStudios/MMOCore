/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcDoorInteraction;
import com.mmocore.constants.NpcShelterFromOption;

/**
 *
 * @author draks
 */
public class NpcBehaviourOptions {
    
    private NpcBoolean avoidsSun;
    private NpcBoolean canSwim;    
    private NpcBoolean avoidsWater;
    private NpcDoorInteraction doorBehaviour;
    private NpcBoolean canLeap;
    private NpcBoolean canSprint;
    private NpcBoolean passThroughCobwebs;
    private NpcBoolean returnToStart;
    private NpcShelterFromOption shelterFrom;
    private NpcBoolean canDrown;
    private NpcBoolean burnsInSun;
    private NpcBoolean suffersFallDamage;
    private NpcBoolean immuneToFire;
    private NpcBoolean immuneToPotions;
    private NpcBoolean aimWhileShooting;
    private NpcBoolean naturallyDespawns;
    
    
    public NpcShelterFromOption getShelterFrom() {
        return this.shelterFrom;
    }
    
    public void setShelterFrom(NpcShelterFromOption option) {
        this.shelterFrom = option;
    }
    
    public NpcBoolean getCanSprint() {
        return this.canSprint;        
    }
    
    public void setCanSprint(NpcBoolean setting) {
        this.canSprint = setting;
    }
    
    public NpcBoolean getReturnToStart() {
        return this.returnToStart;
    }
    
    public void setReturnToStart(NpcBoolean setting) {
        this.returnToStart = setting;
    }
    
    public NpcBoolean getCanPassThroughCobwebs() {
        return this.passThroughCobwebs;        
    }
    
    public void setCanPassThroughCobwebs(NpcBoolean setting) {
        this.passThroughCobwebs = setting;
    }
    
    public NpcBoolean getCanLeap() {
        return this.canLeap;        
    }
    
    public void setCanLeap(NpcBoolean setting) {
        this.canLeap = setting;
    }
    
    public NpcBoolean getCanSwim() {
        return this.canSwim;
    }
    
    public void setCanSwim(NpcBoolean setting) {
        this.canSwim = setting;
    }
    
    public NpcBoolean getAvoidsWater() {
        return this.avoidsWater;
    }
    
    public void setAvoidsWater(NpcBoolean setting) {
        this.avoidsWater = setting;
    }
    
    public NpcBoolean getAvoidsSun() {
        return this.avoidsSun;
    }
    
    public void setAvoidsSun(NpcBoolean setting) {
        this.avoidsSun = setting;
    }
    
    public NpcDoorInteraction getDoorBehaviour() {
        return this.doorBehaviour;
    }
    
    public void setDoorBehaviour(NpcDoorInteraction setting) {
        this.doorBehaviour = setting;
    }
    
}

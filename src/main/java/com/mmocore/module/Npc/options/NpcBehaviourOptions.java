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
    
    private NpcBoolean avoidsSun = NpcBoolean.NO;
    private NpcBoolean canSwim = NpcBoolean.YES; 
    private NpcBoolean avoidsWater = NpcBoolean.YES; 
    private NpcDoorInteraction doorBehaviour = NpcDoorInteraction.None;
    private NpcBoolean canLeap = NpcBoolean.NO;
    private NpcBoolean canSprint = NpcBoolean.NO;
    private NpcBoolean passThroughCobwebs = NpcBoolean.NO;
    private NpcBoolean returnToStart = NpcBoolean.YES;
    private NpcShelterFromOption shelterFrom = NpcShelterFromOption.None;
    private NpcBoolean canDrown = NpcBoolean.YES;
    private NpcBoolean burnsInSun = NpcBoolean.NO;
    private NpcBoolean suffersFallDamage = NpcBoolean.YES;
    private NpcBoolean immuneToFire = NpcBoolean.NO;
    private NpcBoolean immuneToPotions = NpcBoolean.NO;
    private NpcBoolean aimWhileShooting = NpcBoolean.YES;
    private NpcBoolean naturallyDespawns = NpcBoolean.NO;
    
    public NpcBoolean getImmuneToPotions() {
        return this.immuneToPotions;
    }
    
    public void setImmuneToPotions(NpcBoolean setting) {
        this.immuneToPotions = setting;
    }
    
    public NpcBoolean getImmuneToFire() {
        return this.immuneToFire;
    }
    
    public void setImmuneToFire(NpcBoolean setting) {
        this.immuneToFire = setting;
    }
    
    public NpcBoolean getAimsWhileShooting() {
        return this.aimWhileShooting;
    }
    
    public void setAimsWhileShooting(NpcBoolean setting) {
        this.aimWhileShooting = setting;
    }
    
    public NpcBoolean getBurnsInSun() {
        return this.burnsInSun;
    }
    
    public void setBurnsInSun(NpcBoolean option) {
        this.burnsInSun = option;
    }
    
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
    
    public NpcBoolean getCanDrown() {
        return this.canDrown;
    }
    
    public void setCanDrown(NpcBoolean setting) {
        this.canDrown = setting;
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

    public NpcBoolean getSuffersFallDamage() {
        return this.suffersFallDamage;
    }
    
    public void setSuffersFallDamage(NpcBoolean setting) {
        this.suffersFallDamage = setting;
    }
}

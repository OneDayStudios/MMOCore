/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcMovementAnimation;
import com.mmocore.constants.NpcMovementType;
import com.mmocore.constants.NpcRotation;
import java.util.ArrayList;

/**
 *
 * @author draks
 */
public class NpcMovementOptions {
    
    private NpcMovementAnimation movingAnimation = NpcMovementAnimation.None;
    private NpcRotation rotation = NpcRotation.NORTH;
    private NpcMovementType movingType = NpcMovementType.Standing;
    private NpcAbstractScale wanderingRadius = NpcAbstractScale.None;
    private ArrayList<int[]> pathCoordinates = new ArrayList<int[]>();
    
    public NpcRotation getRotation() {
        return this.rotation;
    }
    
    public void setRotation(NpcRotation rotation) {
        this.rotation = rotation;
    }
    
    public NpcMovementType getMovementType() {
        return this.movingType;
    }
    
    public NpcMovementAnimation getMovingAnimation() {
        return this.movingAnimation;
    }
    
    public void setMovementTypeStanding() {
        this.wanderingRadius = NpcAbstractScale.None;
        this.movingType = NpcMovementType.Standing;
    }
    
    public void setMovementTypeWandering(NpcAbstractScale wanderingRadius) {
        this.wanderingRadius = wanderingRadius;
        this.movingType = NpcMovementType.Wandering;
    }
    
    public void setMovementTypeFollowPath(ArrayList<int[]> coordinateArray) {
        this.movingType = NpcMovementType.FollowingPath;
        this.pathCoordinates = coordinateArray;
    }
    
    public void setMovingAnimation(NpcMovementAnimation animation) {
        this.movingAnimation = animation;
    }
    
    public ArrayList<int[]> getMovingPath() {
        return this.pathCoordinates;
    }
    
    public NpcAbstractScale getWanderingRadius() {
        return this.wanderingRadius;
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.constants.NpcFollowPathBehaviour;
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
    private NpcFollowPathBehaviour pathBehaviour = NpcFollowPathBehaviour.LoopPath;

    public NpcMovementOptions() {
        
    }
    
    public NpcMovementOptions(NpcMovementOptions movementOptions) {
        this.movingAnimation = movementOptions.movingAnimation;
        this.rotation = movementOptions.rotation;
        this.movingType = movementOptions.movingType;
        this.wanderingRadius = movementOptions.wanderingRadius;
        this.pathCoordinates = new ArrayList<int[]>(movementOptions.pathCoordinates);
        this.pathBehaviour = movementOptions.pathBehaviour;
    }
    
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
        this.movingType = NpcMovementType.Standing;
    }
    
    public void setMovementTypeWandering(NpcAbstractScale wanderingRadius) {
        this.wanderingRadius = wanderingRadius;
        this.movingType = NpcMovementType.Wandering;
    }
    
    public void setMovementTypeFollowPath(ArrayList<int[]> coordinateArray, NpcFollowPathBehaviour behaviour) {
        this.movingType = NpcMovementType.FollowingPath;
        this.pathBehaviour = behaviour;
        this.pathCoordinates = coordinateArray;
    }
    
    public NpcFollowPathBehaviour getFollowPathBehaviour() {
        return this.pathBehaviour;
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

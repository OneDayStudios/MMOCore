/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcMovementAnimation;
import com.mmocore.constants.NpcRotation;

/**
 *
 * @author draks
 */
public class NpcMovementOptions {
    
    private NpcMovementAnimation movingAnimation = NpcMovementAnimation.None;
    private NpcRotation rotation = NpcRotation.NORTH;
    
    public NpcRotation getRotation() {
        return this.rotation;
    }
    
    public void setRotation(NpcRotation rotation) {
        this.rotation = rotation;
    }
    
    public NpcMovementAnimation getMovingAnimation() {
        return this.movingAnimation;
    }
    
    public void setMovingAnimation(NpcMovementAnimation animation) {
        this.movingAnimation = animation;
    }
    
}

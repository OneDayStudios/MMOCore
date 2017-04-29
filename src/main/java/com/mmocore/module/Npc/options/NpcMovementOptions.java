/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcMovementAnimation;

/**
 *
 * @author draks
 */
public class NpcMovementOptions {
    
    private NpcMovementAnimation movingAnimation = NpcMovementAnimation.None;
    
    
    public NpcMovementAnimation getMovingAnimation() {
        return this.movingAnimation;
    }
    
    public void setMovingAnimation(NpcMovementAnimation animation) {
        this.movingAnimation = animation;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc.options;

import com.stargatemc.forge.core.constants.NpcProjectile;

/**
 *
 * @author draks
 */
public class NpcCombatOptions {
    
    private boolean attacksHostileFactions = false;
    private boolean defendFactionMembers = false;
    
    private NpcProjectile projectile;
    
    public NpcProjectile getProjectile() {
        return this.projectile;
    }
    
    public void setProjectile(NpcProjectile projectile) {
        this.projectile = projectile;
    }
}

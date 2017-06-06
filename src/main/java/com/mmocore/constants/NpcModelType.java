/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

/**
 *
 * @author draks
 */
public enum NpcModelType {
    
    NONE("NONE"),
    DRAGON("noppes.npcs.entity.EntityNpcDragon"),
    GOLEM("noppes.npcs.entity.EntityNPCGolem"),
    CRYSTAL("noppes.npcs.entity.EntityNpcCrystal"),
    SLIME("nopppes.npcs.entity.EntityNpcSlime"),
    CREEPER("net.minecraft.entity.monster.EntityCreeper"),
    ZOMBIE("net.minecraft.entity.monster.EntityZombie"),
    SPIDER("net.minecraft.entity.monster.EntitySpider"),
    SKELETON("net.minecraft.entity.monster.EntitySkeleton"),
    GIANT_ZOMBIE("net.minecraft.entity.monster.EntityGiantZombie"),
    CHICKEN("net.minecraft.entity.animal.EntityChicken"),
    COW("net.minecraft.entity.animal.EntityCow"),
    PIG("net.minecraft.entity.animal.EntityPig"),
    SHEEP("net.minecraft.entity.animal.EntitySheep"),
    WOLF("net.minecraft.entity.animal.EntityWolf");
    
    private String className;
    
    NpcModelType(String className) {
        this.className = className;
    }
    
    public Class getClassName() {
        try {
            Class c = Class.forName(this.className);
            return c;
        } catch (Exception e) {
            return null;
        }
    }
}

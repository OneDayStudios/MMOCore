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
public enum NpcRotation {
    
    NORTH(180),
    SOUTH(0),
    EAST(270),
    WEST(90),
    NNE(225),
    SSE(315),
    NNW(135),
    SSW(45);
    
    private final int degree;
    
    NpcRotation(int degree) {
        this.degree = degree;
    }
    
    public int getDegree() {
        return this.degree;
    }
}

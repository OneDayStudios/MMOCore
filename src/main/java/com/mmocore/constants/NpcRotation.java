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
    
    NORTH(0),
    SOUTH(180),
    EAST(270),
    WEST(90),
    NNE(45),
    SSE(75),
    NNW(185),
    SSW(65);
    
    private final int degree;
    
    NpcRotation(int degree) {
        this.degree = degree;
    }
    
    public int getDegree() {
        return this.degree;
    }
}

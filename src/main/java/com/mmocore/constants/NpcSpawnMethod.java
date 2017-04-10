/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.constants;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public enum NpcSpawnMethod {
    Static,
    Random,
    Incursion, 
    Quest,
    Location
}

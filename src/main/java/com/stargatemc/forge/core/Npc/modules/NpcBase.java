/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc.modules;

import com.stargatemc.forge.core.NpcFaction.RegisterableNpcFaction;
import com.stargatemc.forge.core.constants.NpcTextureType;
import com.stargatemc.forge.core.constants.uPosition;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author draks
 */
public class NpcBase {
    
    private String name;
    private String title;
    private uPosition initialPosition;
    private FileConfiguration configuration;
    private String texture;
    private NpcTextureType textureType;
    private RegisterableNpcFaction faction;
    
}

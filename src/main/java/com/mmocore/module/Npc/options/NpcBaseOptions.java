/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.NpcAbstractScale;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.constants.NpcGender;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.NpcVisibleOption;
import com.mmocore.constants.TextVisibleOption;
import com.mmocore.constants.uPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author draks
 */

@SideOnly(Side.SERVER)
public class NpcBaseOptions {
    
    private String name = null;
    private String title = null;
    private uPosition spawnPosition = null;
    private NpcTexture texture = null;
    private RegisterableNpcFaction faction = null;
    private NpcVisibleOption visibility = NpcVisibleOption.Visible;
    private NpcGender gender = NpcGender.Male;
    private TextVisibleOption bossBarVisible = TextVisibleOption.Never;
    private TextVisibleOption nameVisible = TextVisibleOption.Never;
    private NpcAbstractScale movementSpeed = NpcAbstractScale.Medium;
    private NpcAbstractScale size = NpcAbstractScale.Medium;
    
    public NpcAbstractScale getSize() {
        return this.size;
    }
    
    public void setSize(NpcAbstractScale option) {
        this.size = option;
    }
    
    public NpcAbstractScale getMovementSpeed() {
        return this.movementSpeed;
    }
    
    public void setMovementSpeed(NpcAbstractScale option) {
        this.movementSpeed = option;
    }
    
    public TextVisibleOption getBossBarVisible() {
        return this.bossBarVisible;
    }
    
    public void setBossBarVisible(TextVisibleOption option) {
        this.bossBarVisible = option;
    }
    
    public TextVisibleOption getNameVisible() {
        return this.nameVisible;
    }
    
    public void setNameVisible(TextVisibleOption option) {
        this.nameVisible = option;
    }
    
    public NpcGender getGender() {
        return this.gender;
    }
    
    public void setGender(NpcGender gender) {
        this.gender = gender;
    }

    public uPosition getSpawnPosition() {
        return this.spawnPosition;
    }
    
    public void setSpawnPosition(uPosition position) {
        this.spawnPosition = position;
    }
    
    public void setTexture(NpcTexture texture) {
        this.texture = texture;
    }
    
    public NpcTexture getTexture() {
        return this.texture;
    }
    
    public void setFaction(RegisterableNpcFaction faction) {
            this.faction = faction;
    }
    
    public RegisterableNpcFaction getFaction() {
        return this.faction;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setVisibleOption(NpcVisibleOption option) {
        this.visibility = option;
    }
    
    public NpcVisibleOption getVisibleOption() {
        return visibility;
    }
}

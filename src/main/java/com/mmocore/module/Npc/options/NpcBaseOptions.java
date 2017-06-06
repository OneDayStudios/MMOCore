/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.constants.AbstractScale;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.module.NpcFaction.RegisterableNpcFaction;
import com.mmocore.constants.NpcGender;
import com.mmocore.constants.NpcModelType;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcSpawnMethod;
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
    private AbstractScale movementSpeed = AbstractScale.Medium;
    private AbstractScale size = AbstractScale.Medium;
    private NpcModifier modifier = null;
    private NpcSpawnMethod method = null;
    private NpcModelType modelType = NpcModelType.NONE;

    public NpcBaseOptions() {
        
    }
    
    public NpcBaseOptions(NpcBaseOptions baseOptions) {
        this.name = baseOptions.name;
        this.title = baseOptions.title;
        this.spawnPosition = baseOptions.spawnPosition;
        this.texture = baseOptions.texture;
        this.faction = baseOptions.faction;
        this.visibility = baseOptions.visibility;
        this.gender = baseOptions.gender;
        this.bossBarVisible = baseOptions.bossBarVisible;
        this.nameVisible = baseOptions.nameVisible;
        this.movementSpeed = baseOptions.movementSpeed;
        this.size = baseOptions.size;
        this.modifier = baseOptions.modifier;
        this.method = baseOptions.method;
        this.modelType = baseOptions.modelType;
    }
    
    public void setModelType(NpcModelType type) {
        this.modelType = type;
    }
    
    public NpcModelType getModelType() {
        return this.modelType;
    }
    
    public NpcSpawnMethod getSpawnMethod() {
        return this.method;
    }
    
    public void setSpawnMethod(NpcSpawnMethod method) {
        this.method = method;
    }
    
    public NpcModifier getModifier() {
        return this.modifier;
    }
    
    public void setModifier(NpcModifier modifier) {
        this.modifier = modifier;
    }
    
    public AbstractScale getSize() {
        return this.size;
    }
    
    public void setSize(AbstractScale option) {
        this.size = option;
    }
    
    public AbstractScale getMovementSpeed() {
        return this.movementSpeed;
    }
    
    public void setMovementSpeed(AbstractScale option) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge.core.Npc;
import com.stargatemc.forge.SForge;
import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.Npc.modules.NpcBase;
import com.stargatemc.forge.core.Npc.modules.loadout.NpcHeldItemSet;
import com.stargatemc.forge.core.Npc.modules.loadout.NpcWornItemSet;
import com.stargatemc.forge.core.Npc.modules.NpcInteractions;
import com.stargatemc.forge.core.Npc.modules.behaviour.NpcBaseBehaviour;
import com.stargatemc.forge.core.Npc.modules.options.NpcSpawnOptions;
import com.stargatemc.forge.core.constants.NpcSpawnMethod;
import com.stargatemc.forge.core.constants.uPosition;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.RoleTrader;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Drakster
 */

@SideOnly(Side.SERVER)
public class Npc {
    
    private EntityCustomNpc entity;
    
    //Marked for a reload from options. This will only occur after a change has been made to the NPCs configurable options.
    private boolean markedForUpdate = false;
    //Marked for removal from the NPC Registry. This will occur on the next NPC tick.
    private boolean markedForRemoval = false;
    
    private NpcBase baseInfo = new NpcBase();
    private NpcSpawnOptions defaultSpawnOptions = new NpcSpawnOptions();
    private NpcSpawnOptions incursionSpawnOptions = new NpcSpawnOptions();
    private NpcSpawnOptions randomSpawnOptions = new NpcSpawnOptions();
    private NpcInteractions interactions = new NpcInteractions();
    private NpcWornItemSet armor = new NpcWornItemSet();
    private NpcHeldItemSet passiveHeld = new NpcHeldItemSet();
    private NpcHeldItemSet rangedHeld = new NpcHeldItemSet();
    private NpcHeldItemSet meleeHeld = new NpcHeldItemSet();
    private NpcBaseBehaviour behaviorBase = new NpcBaseBehaviour();
    
    private int id;
    private String name;
    public String title;
    private String template;
    private uPosition spawnPosition;
    private uPosition actualPosition;
    private long startedMovingTime;
    private int secsToCompleteMove;
    private boolean lastMoveSucceeded;
    private boolean flaggedForRemoval;
    private boolean shouldRespawn;
    private boolean shouldRespawnAtHome;
    private boolean shouldRevive;
    private int reviveInSeconds;
    private uPosition oldPosition;
    private long deathTime;
    private boolean hasTicked = false;
    private boolean isStuck;
    private uPosition newPosition;
    private boolean isMoving;
    private UUID entityUniqueID;    
    private int maxHealth = 0;    
    List<String> randomRepeatableUnavailableLines = new ArrayList<String>();    
    private boolean wasInCombat = false;
    
    public Npc(String name, String title, String template, uPosition spawnPosition, boolean shouldRespawn, boolean shouldRespawnAtHome, FileConfiguration config, int maxHealth) {
        this.name = name;
        this.title = title;
        this.template = template;
        this.spawnPosition = spawnPosition;
        this.actualPosition = spawnPosition;
        this.shouldRespawn = shouldRespawn;
        this.shouldRespawnAtHome = shouldRespawnAtHome;
        this.configuration = config;
        this.maxHealth = maxHealth;
        this.setForcedSpawnPosition(spawnPosition);
    }
    
    public void setMarkedForUpdate() {
        this.markedForUpdate = true;
    }
    
    public void setMarkedForRemoval() {
        this.markedForRemoval = true;
    }
    
    public NpcHeldItemSet getMeleeHeldItems() {
        return this.meleeHeld;
    }
    
    public void setMeleeHeldItems(NpcHeldItemSet items) {
        this.meleeHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcHeldItemSet getRangedHeldItems() {
        return this.rangedHeld;
    }
    
    public void setRangedHeldItems(NpcHeldItemSet items) {
        this.rangedHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcHeldItemSet getPassiveHeldItems() {
        return this.passiveHeld;
    }
    
    public void setPassiveHeldItems(NpcHeldItemSet items) {
        this.passiveHeld = items;
        this.setMarkedForUpdate();
    }
    
    public NpcWornItemSet getArmor() {
        return this.armor;
    }
    
    public void setArmor(NpcWornItemSet armor) {
        this.armor = armor;
        this.setMarkedForUpdate();
    }
    
    // This method is the way that SForge pushes its configuration options into a defined Npc. 
    // This is an relatively intensive process that should only occur on ticks where an Npc has its options changed.
    private void pushOptionsToEntity() {
        entity.inventory.armor.put(3, (getArmor().getFeet().hasItem() ? getArmor().getFeet().getItem() : null));
        entity.inventory.armor.put(2, (getArmor().getLegs().hasItem() ? getArmor().getLegs().getItem() : null));
        entity.inventory.armor.put(1, (getArmor().getChest().hasItem() ? getArmor().getChest().getItem() : null));
        entity.inventory.armor.put(0, (getArmor().getHead().hasItem() ? getArmor().getHead().getItem() : null));
        // This is important so that the NPC doesnt constantly update.
        this.markedForUpdate = false;
    }
    
    
    // ANY METHOD BELOW THIS IS UNVERIFIED AND NOT UPDATED.
    
    public boolean isInCombat() {
        return this.entity.isAttacking();
    }
    
    public String getRandomRepeatableUnavailableLine(String PlayerName) {
       if (randomRepeatableUnavailableLines.isEmpty()) return "I have nothing for you now, check back later.";
       if (randomRepeatableUnavailableLines.size() == 1) return randomRepeatableUnavailableLines.get(0).replace("{player}", PlayerName).replace("@dp", PlayerName);
       Random ran = new Random();
       int number = ran.nextInt(randomRepeatableUnavailableLines.size());
       if (number != 0) number = number - 1;
       return randomRepeatableUnavailableLines.get(number).replace("{player}", PlayerName).replace("@dp", PlayerName);
    }
    
    public void addRandomNotRepeatableLine(String s) {
        if (!randomRepeatableUnavailableLines.contains(s)) randomRepeatableUnavailableLines.add(s);
    }
    
    public FileConfiguration getConfig() {
        return this.configuration;
    }
    
    public void setName(String name) {
        this.name = name;
        if (this.entity != null) this.entity.display.name = name;
        if (this.existsInGame()) {
            this.revive();
        }
    }
    
    public void setTitle(String title) {
        this.title = title;
        if (this.entity != null) this.entity.display.title = title;
        if (this.existsInGame()) {
            this.revive();
        }
    }
    
    public void setForcedSpawnPosition(uPosition position) {
        this.spawnPosition = position;
        this.entity = new EntityCustomNpc(this.getForgeWorld(spawnPosition.getDimension().getIdentifier()));
        this.entity.stats.setMaxHealth(maxHealth);
        if (this.template != null && !this.template.equals("NONE")) this.setLinkedTemplate(template);
        this.setPosition(spawnPosition);
        this.cleanup();
    }
    
    public void setRoleNone() {
        this.entity.advanced.role = EnumRoleType.None;
        this.entity.roleInterface = null;
        this.revive();
    }
    
    public void setJobNone() {
        this.entity.advanced.job = EnumJobType.None;
        this.entity.jobInterface = null;
        this.revive();
    }
    
    public void setJobHealer(int range, int speed) {
        JobHealer job = new JobHealer(entity);
        this.entity.advanced.job = EnumJobType.Healer;
        job.speed = speed;
        job.range = range;
        entity.jobInterface = (JobInterface)job;  
        this.revive();
    }
    
    public void setRoleTrader(boolean ignoreDamage, boolean ignoreNBT) {
        RoleTrader role = new RoleTrader(entity);
        this.entity.advanced.role = EnumRoleType.Trader;
        role.ignoreDamage = ignoreDamage;
        role.ignoreNBT = ignoreNBT;        
        entity.roleInterface = (RoleInterface)role;
        this.revive();
    }
    
    public ItemStack getItemStackFromValues(String itemMod, String itemName, int numberOf, int damage) {
        //System.out.println("Looking for : " + itemMod + " , " + itemName + " , " + numberOf +  " , " + damage);
        if (ForgeAPI.isItemValidInForge(itemMod, itemName)) {
            ItemStack itemStack = GameRegistry.findItemStack(itemMod, itemName, numberOf);
           // if (itemStack.getMaxStackSize() < numberOf) return null;
            itemStack.setItemDamage(damage);
            //System.out.println("Found it!");
            return itemStack;
        } else {
            return null;
        }
    }
    
    public void addItemToTrader(ItemStack stack1, ItemStack stack2, ItemStack stackResult, int position) {
        if (position <= 0) return;
        RoleTrader role = (RoleTrader)entity.roleInterface;
        //System.out.println("Slot is: " + role.inventorySold.firstFreeSlot());
        //System.out.println("Currency slot is : " + role.inventoryCurrency.firstFreeSlot());
        if (stackResult != null) role.inventorySold.items.put(position-1, stackResult);
        if (stack1 != null && stackResult != null) role.inventoryCurrency.items.put(position-1, stack1);
        if (stack2 != null && stackResult != null) role.inventoryCurrency.items.put((position-1)+18, stack2);
        role.toSave = true;
        entity.roleInterface = (RoleInterface)role;
        revive();
    }
    
    public void setRoleTransporter(String transportName) {
        TransportLocation desiredLocation = TransportController.getInstance().getTransport(transportName);
        this.entity.advanced.role = EnumRoleType.Transporter;
        RoleTransporter role = new RoleTransporter(entity);
        role.setTransport(desiredLocation);
        entity.roleInterface = (RoleInterface)role;
        this.revive();
    }
    
    public boolean shouldRevive() {
        return this.shouldRevive;
    }
    
    public int getReviveSeconds() {
        return this.reviveInSeconds;
    }
    
    public boolean flaggedForRemoval() {
        return this.flaggedForRemoval;
    }
    
    public void setFlaggedForRemoval() {
        this.flaggedForRemoval = true;
    }
    
    public int getID() {
        return this.id;
    }
    
    public void setCanSpawnNotMajority(boolean val) {
        this.canSpawnNotMajority = val;
    }
    
    public boolean getCanSpawnNotMajority() {
        return this.canSpawnNotMajority;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public void setPartiallyVisible() {
        this.entity.display.visible = 2;
    }
    
    public void showBossBar() {
        this.entity.display.showBossBar = 1;
    }
    
    public void hideBossBar() {
        this.entity.display.showBossBar = 0;
    }
    
    public void showBossBarWhenAttacking() {
        this.entity.display.showBossBar = 2;
    }
    
    public void setVisible() {
        this.entity.display.visible = 0;
    }
    
    public void setInvisible() {
        this.entity.display.visible = 1;
    }
    
    public boolean isMoving() {
        return this.isMoving;
    }
    
    public EntityCustomNpc getEntity() {
        return this.entity;
    }
    
    public void attackHostileFactions(boolean value) {
        this.entity.advanced.attackOtherFactions = value;
    }
    
    public boolean addWorldLine(String text) {
        if (this.entity.advanced.worldLines.lines.size() > 7) return false;
        int lineNumber = 0;
        if (!this.entity.advanced.worldLines.lines.isEmpty()) lineNumber = this.entity.advanced.worldLines.lines.size();
        Line line = new Line();
        line.hideText = true;
        line.text = text;
        line.sound = "";
        this.entity.advanced.worldLines.lines.put(lineNumber, line);
        return true;
    }

    public boolean clearInteractLines() {
        this.entity.advanced.interactLines.lines.clear();
        return this.entity.advanced.interactLines.lines.isEmpty();
    }
    
    public boolean addInteractLine(String text) {
        int lineNumber = 0;
        if (this.entity.advanced.interactLines.lines.size() > 7) return false;
        if (!this.entity.advanced.interactLines.lines.isEmpty() && !this.entity.advanced.interactLines.lines.get(0).text.equals("Please report me to the StargateMC admins, as I am an NPC with no logic!")) lineNumber = this.entity.advanced.interactLines.lines.size();
        Line line = new Line();
        line.hideText = true;
        line.text = text;
        line.sound = "";
        this.entity.advanced.interactLines.lines.put(lineNumber, line);
        return true;
    }

    public boolean addKilledLine(String text) {
        int lineNumber = 0;
        if (this.entity.advanced.killedLines.lines.size() > 7) return false;
        if (!this.entity.advanced.killedLines.lines.isEmpty()) lineNumber = this.entity.advanced.killedLines.lines.size();
        Line line = new Line();
        line.hideText = true;
        line.text = text;
        line.sound = "";
        this.entity.advanced.killedLines.lines.put(lineNumber, line);
        return true;
    }
    
    public boolean addKillLine(String text) {
        int lineNumber = 0;
        if (this.entity.advanced.killLines.lines.size() > 7) return false;
        if (!this.entity.advanced.killLines.lines.isEmpty()) lineNumber = this.entity.advanced.killLines.lines.size();
        Line line = new Line();
        line.hideText = true;
        line.text = text;
        line.sound = "";
        this.entity.advanced.killLines.lines.put(lineNumber, line);
        return true;
    }
    
    public boolean addAttackLine(String text) {
        int lineNumber = 0;
        if (this.entity.advanced.attackLines.lines.size() > 7) return false;
        if (!this.entity.advanced.attackLines.lines.isEmpty()) lineNumber = this.entity.advanced.attackLines.lines.size();
        Line line = new Line();
        line.hideText = true;
        line.text = text;
        line.sound = "";
        this.entity.advanced.attackLines.lines.put(lineNumber, line);
        return true;
    }
    
    public void defendFactionMembers(boolean value) {
        this.entity.advanced.defendFaction = value;
    }
    
    public String getWorldName() {
        return actualPosition.getDimension().getName();
    }
    
    public double getPosX() {
        return actualPosition.getDPosX();
    }
    
    public double getPosY() {
        return actualPosition.getDPosY();
    }
    
    public double getPosZ() {
        return actualPosition.getDPosZ();
    }
    
    public uPosition getUPosition() {
        return new uPosition(getPosX(), getPosY(), getPosZ(), SForge.getInstance().getDimensionRegistry().getRegistered(getWorldName()));
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLinkedTemplate() {
        return this.template;
    }
    
    private void setLinkedTemplate(String template) {
        if (!this.hasValidTemplate()) return;
        this.entity.linkedName = template;        
        LinkedNpcController lnc = new LinkedNpcController();
        lnc.loadNpcData((EntityNPCInterface)this.entity);
        stopMoving(); // Stop moving in the off chance the template has moving assigned.
    }
    
    public boolean hasValidTemplate() {
        LinkedNpcController lnc = new LinkedNpcController();
        return lnc.getData(this.getLinkedTemplate()) != null;
    }
    
    private boolean spawnInWorld() {
        getForgeWorld(spawnPosition.getDimension().getName()).spawnEntityInWorld(entity);
        entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
        return existsInGame();
    }
    
    public boolean setPassiveWeapon(String modFrom, String name, int dmg) {
        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        entity.inventory.setWeapon(stack);
        this.passiveMainHand = stack;
        //System.out.println("Set: " + entity.inventory.getWeapon().equals(stack));
        return entity.inventory.getWeapon().equals(stack);
    }
    
    public boolean setCombatWeapon(String modFrom, String name, int dmg) {
        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        this.combatMainHand = stack;
        return combatMainHand != null;
    }
    public boolean setMeleeWeapon(String modFrom, String name, int dmg) {
        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        this.meleeMainHand = stack;
        return meleeMainHand != null;
    }
    public boolean setMeleeOffhand(String modFrom, String name, int dmg) {
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        this.meleeOffHand = stack;
        return meleeOffHand != null;
    }
    public boolean setPassiveOffhand(String modFrom, String name, int dmg) {
        //System.out.println("Loading: " + modFrom + " : " + name + dmg);
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        //System.out.println("Valid: " + modFrom + " : " + name + dmg);
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        this.passiveOffHand = stack;
        entity.inventory.setOffHand(stack);
        return entity.inventory.getOffHand().equals(stack);
    }
    
    public boolean setCombatOffhand(String modFrom, String name, int dmg) {
        if (!(ForgeAPI.isItemValidInForge(modFrom, name))) return false; 
        ItemStack stack = GameRegistry.findItemStack(modFrom, name, 1);
        stack.setItemDamage(dmg);
        this.combatOffHand = stack;
        return combatOffHand != null;
    }
    
    public void refresh() {
        entity.updateClient = true;
        entity.updateAI = true;
    }
    
    private void cleanup() {
        this.entity.linkedLast = 0;
        this.entity.linkedData = null;
        this.entity.linkedName = "";
        this.resetName();
    }
    
    public void resetName() {
        this.entity.display.name = this.name;
        this.entity.display.title = this.title;
    }
    
    public void moveTo(int posX, int posY, int posZ, int secondsToComplete) {
        List<int[]> path = new ArrayList<int[]>();
        int[] currentLocation = { (int)this.actualPosition.getPosInDimX(), (int)this.actualPosition.getPosInDimY(), (int)this.actualPosition.getPosInDimZ() };
        int[] coordinates = { posX, posY, posZ };
        path.add(currentLocation);
        path.add(coordinates);
        entity.ai.setMovingPath(path);
        setMovingType(EnumMovingType.MovingPath);
        this.isMoving = true;
        this.startedMovingTime = System.currentTimeMillis();
        this.secsToCompleteMove = secondsToComplete;
    }
    
    public boolean isDead() {
        return entity.hasDied;
    }
    
    public boolean revive() {
        entity.updateAI = true;
        entity.updateClient = true;
        entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
        entity.reset();
        return !isDead();
    }
    
    public boolean isPathCompleted() {
        if (!isMoving() || entity.ai.getMovingPath().isEmpty()) return true;
        int[] lastPoint = entity.ai.getMovingPath().get(entity.ai.getMovingPath().size()-1);
        return entity.ai.getCurrentMovingPath() == lastPoint && entity.ai.getDistanceSqToPathPoint() < 3;
    }
    
    public void followPath(List<int[]> path, int secondsToComplete) {
        entity.ai.setMovingPath(path);
        setMovingType(EnumMovingType.MovingPath);
        entity.ai.movingPos = 0;
        this.isMoving = true;
        this.startedMovingTime = System.currentTimeMillis();
        this.secsToCompleteMove = secondsToComplete;
    }
    
    public void followPathConstantly(List<int[]> path) {
        entity.ai.setMovingPath(path);
        entity.ai.movingPos = 0;
        setMovingType(EnumMovingType.MovingPath);
    }
    
    public void register() {
        if (this.existsInGame()) this.findCustomNpcInGame().delete();
        this.spawn();
        refresh();
        revive();
        SForge.getInstance().getNpcRegistry().register(this);
    }
    
    public void setUniqueID(UUID id) {
        this.entityUniqueID = id;
    }
    
    public UUID getUniqueID() {
        return this.entityUniqueID;
    }
    
    private void spawn() {
        //System.out.println("NPC: " + this.entity.display.name + " is being added to the game with template: " + this.entity.linkedName);
        this.spawnInWorld();
        setUniqueID(this.entity.getUniqueID());
        if (this.existsInGame()) {
            //System.out.println("NPC: " + this.getName() + " has spawned!");
        }
       // if (!this.existsInGame()) //("NPC: " + this.getName() + " has failed to spawn!");
    }
    
    public boolean getShouldRespawnAtHome() {
        return this.shouldRespawnAtHome;
    }
    
    public void respawn() {
        if (this.existsInGame()) this.despawn();
        if (this.getShouldRespawnAtHome()) this.actualPosition = this.spawnPosition;
        this.spawn();
    }
    
    private void setStartPosition(int[] position) {
        entity.ai.startPos = position;
    }
    
    public void setPosition(UPosition position) {
        this.entity.posX = position.getPosInDimX();
        this.entity.posY = position.getPosInDimY();
        this.entity.posZ = position.getPosInDimZ();
        int[] startPos = { (int)position.getPosInDimX(), (int)position.getPosInDimY(), (int)position.getPosInDimZ() };
        this.entity.ai.startPos = startPos;
        this.actualPosition = position;
    }
    
    public boolean stopMoving() {
        //if (this.isMoving()) {
            int[] startPos = { (int)this.actualPosition.getPosInDimX(), (int)this.actualPosition.getPosInDimY(), (int)this.actualPosition.getPosInDimZ() };
            setStartPosition(startPos);
            setMovingType(EnumMovingType.Standing);
            this.isMoving = false;
            return true;
        //} else {
            //return false;
        //}
    }
    
    public void setLootModeDefault() {
        this.entity.inventory.lootMode = 0;
    }
    
    public void setLootModeAutoPickup() {
        this.entity.inventory.lootMode = 1;
    }
    
    public boolean shouldRespawn() {
        return this.shouldRespawn;
    }
    
    public void setShouldRespawn(boolean yesOrNo) {
        this.shouldRespawn = yesOrNo;
    }
    
    public EntityCustomNpc findCustomNpcInGame() {        
    List<Entity> entities = StargateMCMod.getInstance().getForgeAPI().getForgeWorld(actualPosition.getDimension().getName()).loadedEntityList; 
        for (Entity entity : entities) {
            if (entity instanceof EntityCustomNpc) {
                if (entity.getUniqueID().equals(this.getUniqueID())) {
                    return (EntityCustomNpc)entity;
                }
            }
        }
       // PC in-game: " + this.getName());
        return null;
    }
    
    public void setSpeed(int speed) {
        entity.ai.setWalkingSpeed(speed);
    }
    
    public void startWandering(int range, int secondsToWander) {
        entity.ai.movingType = EnumMovingType.Wandering;
        entity.ai.walkingRange = range;
        this.isMoving = true;
        this.startedMovingTime = System.currentTimeMillis();
        this.secsToCompleteMove = secondsToWander;
    }
    
    public void setWandering(int range) {
        entity.ai.movingType = EnumMovingType.Wandering;
        entity.ai.walkingRange = range;
    }
    
    public void setTextureWeb(String url) {
        entity.display.url = url;
        entity.display.skinType = 2;
    }
    
    public void setTextureResource(String resource) {
        this.entity.display.texture = resource;
        this.entity.display.skinType = 0;
    }
    
    public void showNameAlways() {
        entity.display.showName = 0;
    }
    
    public void showNameNever() {
        entity.display.showName = 1;
    }
    
    public void showNameWhenAttacking() {
        entity.display.showName = 2;
    }
    
    private void setMovingType(EnumMovingType type) {
        entity.ai.movingType = type;
    }
    
    private void updatePosition() {
        
        // If the position of the real NPC differs, then update it from the NPC.
        if (this.entity.posX != this.actualPosition.getPosInDimX() || this.entity.posY != this.actualPosition.getPosInDimY() || this.entity.posZ != this.actualPosition.getPosInDimZ() || this.actualPosition.getDimension().getName() != this.entity.worldObj.getWorldInfo().getWorldName()) {
            this.oldPosition = this.actualPosition;
            this.actualPosition = new UPosition(this.entity.posX, this.entity.posY, this.entity.posZ, this.entity.worldObj.getWorldInfo().getWorldName());
        }
        
    }

    private World getForgeWorld(String name) {
        return StargateMCMod.getInstance().getForgeWorldForName(name);
    }
    
    public void setWasInCombat(boolean value) {
        this.wasInCombat = value;
    }
    public boolean getWasInCombat() {
        return this.wasInCombat;
    }
    
    public void performHeldItemStateActions() {
        if (!this.getWasInCombat() && this.isInCombat()) this.entity.inventory.setWeapon((getRangedHeldItems().getMainHand().hasItem() ? getRangedHeldItems().getMainHand().getItem() : null));
        if (!this.getWasInCombat() && this.isInCombat()) this.entity.inventory.setOffHand((getRangedHeldItems().getOffHand().hasItem() ? getRangedHeldItems().getOffHand().getItem() : null));
        if (this.getWasInCombat() && !this.isInCombat()) this.entity.inventory.setWeapon((getPassiveHeldItems().getMainHand().hasItem() ? getPassiveHeldItems().getMainHand().getItem() : null));
        if (this.getWasInCombat() && !this.isInCombat()) this.entity.inventory.setOffHand((getPassiveHeldItems().getOffHand().hasItem() ? getPassiveHeldItems().getOffHand().getItem() : null));
    }
    
    public void tick() {
        EntityCustomNpc foundNpc = this.findCustomNpcInGame();
        if (foundNpc != null) {
            this.entity = foundNpc;
            if (this.markedForUpdate) this.pushOptionsToEntity();
            performHeldItemStateActions();
            setWasInCombat(isInCombat());
            this.updatePosition();
        } else {
            this.setMarkedForRemoval();
        }
    }
    
    public void setAvoidsSun(boolean value) {
        this.entity.ai.avoidsSun = value;
    }
    
    public void retaliateWhenAttacked() {
        this.entity.ai.onAttack = 0;
    }
    
    public void panicWhenAttacked() {
        this.entity.ai.onAttack = 1;
    }
    
    public void retreatWhenAttacked() {
        this.entity.ai.onAttack = 2;
    }
    
    public void ignoreWhenAttacked() {
        this.entity.ai.onAttack = 3;
    }
    
    public void cantOpenDoors() {
        this.entity.ai.doorInteract = 0;
    }
    
    public void breaksDoors() {
        this.entity.ai.doorInteract = 1;
    }
    
    public void opensDoors() {
        this.entity.ai.doorInteract = 2;
    }
    
    public void canSwim(boolean value) {
        this.entity.ai.canSwim = value;
    }
    
    public void shelterFromNothing() {
        this.entity.ai.findShelter = 2;
    }
    
    public void canFireIndirectlyWhenSuprising() {
        this.entity.ai.canFireIndirect = 2;
    }
    
    public void canFireIndirectlyWhenFar() {
        this.entity.ai.canFireIndirect = 1;
    }
    
    public void cantFireIndirectly() {
        this.entity.ai.canFireIndirect = 0;
    }
    
    public void useRangedAlways() {
        this.entity.ai.useRangeMelee = 0;
    }
    
    public void useRangedUntilClose() {
        this.entity.ai.useRangeMelee = 1;
    }
    
    public void useRangedWhenMoving() {
        this.entity.ai.useRangeMelee = 2;
    }
    
    public void tacticalSurround() {
        this.entity.ai.tacticalVariant = EnumNavType.Surround;
    }
    
    public void tacticalHitnRun() {
        this.entity.ai.tacticalVariant = EnumNavType.HitNRun;
    }
    
    public void tacticalAmbush() {
        this.entity.ai.tacticalVariant = EnumNavType.Ambush;
    }
    
    public void tacticalStalk() {
        this.entity.ai.tacticalVariant = EnumNavType.Stalk;
    }
    
    public void tacticalDodge() {
        this.entity.ai.tacticalVariant = EnumNavType.Dodge;
    }
    
    public void tacticalNone() {
        this.entity.ai.tacticalVariant = EnumNavType.None;
    }
    
    public void setDistanceTactical(int distance) {
        this.entity.ai.tacticalRadius = distance;
    }
    
    public void setStanding() {
        if (this.isMoving()) stopMoving();
        this.entity.ai.movingType = EnumMovingType.Standing;
    }
    
    public void interactsWithOtherNPCs(boolean value) {
        this.entity.ai.npcInteracting = value;
    }
    
    public void stopOnInteract(boolean value) {
        this.entity.ai.stopAndInteract = value;
    }
    
    public void setXP(int min, int max) {
        this.entity.inventory.minExp = min;
        this.entity.inventory.maxExp = max;
    }
    
    public void setAnimationNormal() {
        this.entity.ai.animationType = EnumAnimation.NONE;
    }
    
    public void setAnimationWaving() {
        this.entity.ai.animationType = EnumAnimation.WAVING;
    }
    
    public void setAnimationHugging() {
        this.entity.ai.animationType = EnumAnimation.HUG;
    }
    
    public void setAnimationDancing() {
        this.entity.ai.animationType = EnumAnimation.DANCING;
    }
    
    public void setAnimationCrawling() {
        this.entity.ai.animationType = EnumAnimation.CRAWLING;
    }
    
    public void setAnimationCrying() {
        this.entity.ai.animationType = EnumAnimation.CRY;
    }
    
    public void setAnimationSneaking() {
        this.entity.ai.animationType = EnumAnimation.SNEAKING;
    }
    
    public void setAnimationSitting() {
        this.entity.ai.animationType = EnumAnimation.SITTING;
    }
    
    public void backTrackWhileFollowingPath() {
        this.entity.ai.movingPattern = 1;
    }
    
    public void loopWhileFollowingPath() {
        this.entity.ai.movingPattern = 0;
    }
    
    public void hidedDeadBody(boolean value) {
        this.entity.stats.hideKilledBody = value;
    }
    
    public void setMaxHealth(int value) {
        this.entity.stats.maxHealth = value;
    }
    
    public int getMaxHealth() {
        return this.entity.stats.maxHealth;
    }
    
    public void setAccuracy(int accuracy) {
        if (accuracy < 0 || accuracy > 100) return;
        this.entity.stats.accuracy = accuracy;
    }
    
    public void setAggroRange(int range) {
        if (range < 0 || range > 64) return;
        this.entity.stats.aggroRange = range;
    }
    
    public void canAttackInvisible(boolean value) {
        this.entity.stats.attackInvisible = value;
    }
    
    public void suffersFallDamage(boolean value) {
        this.entity.stats.noFallDamage = !value;
    }
    
    public void burnsInSun(boolean value) {
        this.entity.stats.burnInSun = value;
    }
    
    public void canDrown(boolean value) {
        this.entity.stats.canDrown = value;
    }
    
    public void immuneToPotions(boolean value) {
        this.entity.stats.potionImmune = value;
    }
    
    public void immuneToFire(boolean value) {
        this.entity.stats.immuneToFire = value;
    }
    
    public void setArrowResistance(float value) {
        this.entity.stats.resistances.arrow = value;
    }
    
    public void setKnockBackResistance(float value) {
        this.entity.stats.resistances.knockback = value;
    }
    
    public void setMeleeResistance(float value) {
        this.entity.stats.resistances.playermelee = value;
    }
    
    public void setExplosionResistance(float value) {
        this.entity.stats.resistances.explosion = value;
    }
    
    public void setMeleeKnockback(int value) {
        this.entity.stats.knockback = value;
    }
    
    public void setResistanceToDamage(float value) {
        this.setExplosionResistance(value);
        this.setKnockBackResistance(value);
        this.setArrowResistance(value);
        this.setMeleeResistance(value);
    }
    
    public void setProjectileSpins() {
        this.setProjectile3D(true);
        this.entity.stats.pSpin = true;
    }
    
    public void setMeleeDamage(int strength) {
        this.entity.stats.setAttackStrength(strength);
    }
    
    public void setProjectile3D(boolean value) {
        this.entity.stats.pRender3D = true;
        if (!value) this.entity.stats.pStick = false;
        if (!value) this.entity.stats.pSpin = false;
    }
    
    public boolean setProjectileTrailType(String type) {
        if (EnumParticleType.valueOf(type) == null) return false;
        this.entity.stats.pTrail = EnumParticleType.valueOf(type);
        return true;
    }
    
    public void setProjectileSticks() {
        this.setProjectile3D(true);
        this.entity.stats.pStick = true;
    }
    
    public void makeInvulnerable() {
        this.setResistanceToDamage((float)2.0);
    }
    
    public void setProjectileDamage(int value) {
        this.entity.stats.pDamage = value;
    }
    
    public void increaseNumberSpawned() {
        this.clonesSpawned += 1;
    }
    
    public void decreaseNumberSpawned() {
        this.clonesSpawned -= 1;
    }
    
    public void setProjectileSize(int value) {
        this.entity.stats.pSize = value;
    }
    
    public void setProjectileSpeed(int value) {
        this.entity.stats.pSpeed = value;
    }
    
    public void setProjectileAffectedByGravity(boolean value) {
        this.entity.stats.pPhysics = value;
    }
    
    public void setHealthRegen(int perSecHP) {
        this.entity.stats.healthRegen = perSecHP;
    }
    
    public void setCombatRegen(int perSecHP) {
        this.entity.stats.combatRegen = perSecHP;
    }
    
    public void pauseWhileFollowingPath(boolean value) {
        this.entity.ai.movingPause = value;
    }
    
    public void setProjectileKnockBack(int value) {
        this.entity.stats.pImpact = value;
    }
    
    public void setProjectileIsExplosive(boolean value, int explosionSize) {
        this.entity.stats.pExplode = value;
        this.entity.stats.pArea = Math.min(explosionSize,2);
    }
    
    public boolean setProjectileEffect(String type, int duration, int amplifiedBy) {
        if (EnumPotionType.valueOf(type) == null) return false;
        this.entity.stats.pEffect = EnumPotionType.valueOf(type);
        this.entity.stats.pDur = duration;
        this.entity.stats.pEffAmp = amplifiedBy;
        return true;
    }
    
    public void setProjectileGlows(boolean value) {
        this.entity.stats.pGlows = value;
    }
    
    public void aimWhileShooting(boolean value) {
        this.entity.stats.aimWhileShooting = value;
    }
    
    public void meleeRange(int value) {
        this.entity.stats.attackRange = value;
    }
    
    public void rangedRange(int value) {
        this.entity.stats.rangedRange = value;
    }
    
    public void respawnTime(int value) {
        this.entity.stats.respawnTime = value;
    }
    
    public boolean setJob(String jobName) {
        if (EnumJobType.valueOf(jobName) == null) return false;
        this.entity.advanced.job = EnumJobType.valueOf(jobName);
        return true;
    }
    
    public void setRotation(int number) {
        this.entity.ai.orientation = number;
    }
    
    public void setStandingType(String type) {
        if (EnumStandingType.valueOf(type) == null) return;
        this.entity.ai.standingType = EnumStandingType.valueOf(type);
    }
    
    public boolean setRole(String roleName) {
        if (EnumRoleType.valueOf(roleName) == null) return false;
        this.entity.advanced.role = EnumRoleType.valueOf(roleName);
        return true;
    }
    
    public boolean setFactionPointsOnDeathPrimary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        this.entity.advanced.factions.decreaseFactionPoints = (points < 0);
        this.entity.advanced.factions.factionId = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) this.entity.advanced.factions.factionPoints = points * -1;
        if (points >= 0) this.entity.advanced.factions.factionPoints = points;
        return true;
    }
    
    public boolean setFactionPointsOnDeathSecondary(String factionName, int points) {
        if (FactionController.getInstance().getFactionFromName(factionName) == null) return false;
        this.entity.advanced.factions.decreaseFaction2Points = (points < 0);
        this.entity.advanced.factions.faction2Id = FactionController.getInstance().getFactionFromName(factionName).id;
        if (points < 0) this.entity.advanced.factions.faction2Points = points * -1;
        if (points >= 0) this.entity.advanced.factions.faction2Points = points;
        return true;
    }
    
    public void respawnsInGame(boolean value, int time, boolean day, boolean night) {
        if (night == day && !value) this.entity.stats.spawnCycle = 3;
        if (day && !night) this.entity.stats.spawnCycle = 1;
        if (night && !day) this.entity.stats.spawnCycle = 2;
        if (night == day && value) this.entity.stats.spawnCycle = 0;
        this.respawnTime(time);
    }
    
    public boolean getNaturallyDespawns() {
        return this.entity.stats.canDespawn;
    }
    public void naturallyDespawns(boolean value) {
        this.entity.stats.canDespawn = value;
    }
    
    public void setMinDelayRangedFiring(int value) {
        this.entity.stats.minDelay = value;
    }
    
    public void setMaxDelayRangedFiring(int value) {
        this.entity.stats.maxDelay = value;
    }
    
    public void setFireRate(int value) {
        this.entity.stats.fireRate = value;
    }
    
    public void setFireSound(String sound) {
        this.entity.stats.fireSound = sound;
    }
    
    public void setBurstCount(int number) {
        this.entity.stats.burstCount = number;
    }
    
    public void setShotCountPerRound(int number) {
        this.entity.stats.shotCount = number;
    }
    
    public void setCapeTexture(String texture) {
        if (texture.equals("NONE")) return;
        this.entity.display.cloakTexture = texture;
    }
    
    public void hasLivingAnimation(boolean yesOrNo) {
        this.entity.display.disableLivingAnimation = !yesOrNo;
    }
    
    public void setOverlayTexture(String texture) {
        if (texture.equals("NONE")) return;
        this.entity.display.glowTexture = texture;
    }
    
    public void setSize(int value) {
        if (value < 1 || value > 30) return;
        this.entity.display.modelSize = value;
    }
    
    public int getGroupArea() {
        return this.groupArea;
    }
    
    public void setGroupArea(int number) {
        this.groupArea = number;
    }
    
    public void shelterFromSun() {
        this.entity.ai.findShelter = 1;
    }
    
    public void shelterFromDarkness() {
        this.entity.ai.findShelter = 0;
    }
    
    public void mustSeeTarget(boolean value) {
        this.entity.ai.directLOS = value;
    }
    
    public void avoidsWater(boolean value) {
        this.entity.ai.avoidsWater = value;
    }
    
    public void returnToStart(boolean value) {
        this.entity.ai.returnToStart = value;
    }
    
    public void canLeap(boolean value) {
        this.entity.ai.canLeap = value;
    }
    
    public void ignoreCobwebs(boolean value) {
        this.entity.ai.ignoreCobweb = !value;
    }
    
    public void canSprint(boolean value) {
        this.entity.ai.canSprint = value;
    }
    
    public void tellNear(String message) {
        Line line = new Line(message);
        line.hideText = true;
        line.sound = "";
        if (entity != null) entity.saySurrounding(line);
    }
    
    public void setProjectileBullet() {
        ItemStack stack = GameRegistry.findItemStack("customnpcs","npcBlackBullet", 1);
        entity.inventory.setProjectile(stack);
    }
    
    public void setProjectileRedPlasma() {
        ItemStack stack = GameRegistry.findItemStack("IC2","itemDust2", 1);
        stack.setItemDamage(2);
        entity.inventory.setProjectile(stack);
    }    
    
    public void setProjectileWhitePlasma() {
        ItemStack stack = GameRegistry.findItemStack("IC2","itemDust", 1);
        stack.setItemDamage(6);
        entity.inventory.setProjectile(stack);
    }    
    
    public void setProjectileBluePlasma() {
        ItemStack stack = GameRegistry.findItemStack("IC2","itemDust", 1);
        stack.setItemDamage(12);
        entity.inventory.setProjectile(stack);
    }    
    
    public void setProjectileGoldPlasma() {
        ItemStack stack = GameRegistry.findItemStack("IC2","itemDust", 1);
        stack.setItemDamage(4);
        entity.inventory.setProjectile(stack);
    }    
    
    public void setProjectileArrow() {
        ItemStack stack = GameRegistry.findItemStack("minecraft","arrow", 1);
        entity.inventory.setProjectile(stack);
    }
    
    public boolean addToLootTable(String mod, String item, int percentChanceDrop, int numberOf, int dmg) {
        if (!ForgeAPI.isItemValidInForge(mod, item)) return false;
        if (entity.inventory.items.size() > 8) return false;
        ItemStack stack = GameRegistry.findItemStack(mod, item, numberOf);
        if (stack.getMaxStackSize() < numberOf) return false;
        stack.setItemDamage(dmg);
        int index = entity.inventory.items.size();
        entity.inventory.items.put(index, stack);
        entity.inventory.dropchance.put(index, percentChanceDrop);
        return entity.inventory.items.get(index).equals(stack);
    }
    
    public void sayRandomLines(boolean value) {
        entity.advanced.orderedLines = !value;
    }
    
    // Imposes a limitation on NPCs not being able to scan players not on their dimension. Should be acceptable.
    public boolean isHostileToPlayer(String name) {
        EntityPlayer player = findForgePlayerOnWorld(name);
        // Return false if the player is not on the world.
        if (player == null) return false;
        return entity.getFaction().isAggressiveToPlayer(player);
    }
    
    // Imposes a limitation on NPCs not being able to scan players not on their dimension. Should be acceptable.
    public boolean isNeutralToPlayer(String name) {
        EntityPlayer player = findForgePlayerOnWorld(name);
        // Return false if the player is not on the world.
        if (player == null) return false;
        return entity.getFaction().isNeutralToPlayer(player);
    }
    
    // Imposes a limitation on NPCs not being able to scan players not on their dimension. Should be acceptable.
    public boolean isFriendlyToPlayer(String name) {
        EntityPlayer player = findForgePlayerOnWorld(name);
        // Return false if the player is not on the world or they dont exist.
        if (player == null) return false;
        return entity.getFaction().isFriendlyToPlayer(player);
    }
    
    public List<DialogOption> getOptionsRecursively(DialogOption opt) {
        List<DialogOption> options = new ArrayList<DialogOption>();
        if (opt.hasDialog()) {
            if (!options.contains(opt)) options.add(opt);
            if (opt.hasDialog() && opt.getDialog().hasOtherOptions()) {
                options.add(opt);
                for (DialogOption subopt : opt.getDialog().options.values()) {
                    if (!options.contains(subopt)) options.add(subopt);
                    if (subopt.hasDialog() && subopt.getDialog().hasOtherOptions()) {
                        for (DialogOption subsubopt : subopt.getDialog().options.values()) {
                            if (!options.contains(subsubopt)) options.add(subsubopt);
                            if (subsubopt.hasDialog() && subsubopt.getDialog().hasOtherOptions()) {
                                for (DialogOption subsubsubopt : subsubopt.getDialog().options.values()) {
                                    if (!options.contains(subsubsubopt)) options.add(subsubsubopt);
                                    if (subsubsubopt.hasDialog() && subsubsubopt.getDialog().hasOtherOptions()) {
                                        for (DialogOption subsubsubsubopt : subsubsubopt.getDialog().options.values()) {
                                            if (!options.contains(subsubsubsubopt)) options.add(subsubsubsubopt);
                                            if (subsubsubsubopt.hasDialog() && subsubsubsubopt.getDialog().hasOtherOptions()) {
                                                for (DialogOption subsubsubsubsubopt : subsubsubsubopt.getDialog().options.values()) {
                                                    if (!options.contains(subsubsubsubsubopt)) options.add(subsubsubsubsubopt);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return options;
    }
    
    public List<Quest> getRepeatableQuestsFromOption(DialogOption opt) {
        List<Quest> quests = new ArrayList<Quest>();
        for (DialogOption sopt : getOptionsRecursively(opt)) {
            if (sopt.hasDialog() && sopt.getDialog().hasQuest() || sopt.command.contains("givequest")) {
                if (sopt.command.contains("givequest")) {
                    List<String> split = Arrays.asList(sopt.command.split(" "));
                    String questName = "";
                    for (String s : split) {
                        if (s.equals("s") || s.equals("givequest") || s.equals("@dp") || s.equals("{player}")) continue;
                        if (!"".equals(questName)) questName = questName + " " + s;
                        if ("".equals(questName)) questName = s;
                    }
                    Quest q = StargateMCMod.getInstance().getForgeAPI().getQuestFromTitle(questName);
                    if (q != null) {
                        if (q.repeat != EnumQuestRepeat.NONE) {
                            if (!quests.contains(q)) quests.add(q);
                        }
                    } 
                } else {
                    if (sopt.getDialog().getQuest().repeat != EnumQuestRepeat.NONE) {
                        if (!quests.contains(sopt.getDialog().getQuest())) quests.add(sopt.getDialog().getQuest());
                    }
                }
            }
        }
        return quests;
    }
    
    public List<Quest> getRepeatableQuests() {
        List<Quest> quests = new ArrayList<Quest>();
        for (DialogOption dialogOption : entity.dialogs.values()) {
            quests.addAll(getRepeatableQuestsFromOption(dialogOption));
        }
        return quests;
    }
    
    public boolean hasRepeatableQuests() {
        return !getRepeatableQuests().isEmpty();
    }
    
    private EntityPlayer findForgePlayerOnWorld(String playerName) {
        World world = getForgeWorld(actualPosition.getDimension().getName());
        for (Object playerObj : world.playerEntities) {
            EntityPlayer player = (EntityPlayer)playerObj;
            if (player.getGameProfile().getName().equals(playerName)) return player;
        }
        return null;
    }
    
    public boolean setFactionByName(String name) {
        //System.out.println("Attempting to set Faction: " + name + " on npc: " + this.getName());
        if (FactionController.getInstance().getFactionFromName(name) == null) return false;
        this.factionName = name;
        this.entity.setFaction(FactionController.getInstance().getFactionFromName(name).id);
        return true;
    }
    
    public String getFactionName() {
        return this.factionName;
    }
    
    public void despawn() {
        if (existsInGame())  entity.delete();
    }
    
    public boolean existsInGame() {
        return findCustomNpcInGame() != null;
    }
    
    public boolean teleport(UPosition destination) {
        this.despawn();
        this.setPosition(destination);
        this.spawn();
        return this.existsInGame();
    }
    
    public void setGender(boolean isMale) {
        if (!isMale) this.entity.modelData.breasts = 2;
        if (isMale) this.entity.modelData.breasts = 0;
    }
    
    public void setModelClass(String modelClass) {
        try {
            Class c = Class.forName(modelClass);
            this.entity.modelData.setEntityClass(c);
        } catch (Exception e) {
            //System.out.println("Failed to assign modelData: "  + e.getMessage());
        }
    }
    
    public void addDialogDialogOption(String dialogTitle, int color, int position) {
        DialogOption dialogOption = new DialogOption();
        dialogOption.optionType = EnumOptionType.DialogOption;
        Dialog d = null;
        for (Dialog dialog : DialogController.instance.dialogs.values()) {
            if (dialog.title.equals(dialogTitle)) d = dialog;
        }
        if (d == null) return;        
        dialogOption.optionColor = color;
        dialogOption.dialogId = d.id;
        dialogOption.title = d.title;
        dialogOption.command = "NOCOMMAND";
        entity.dialogs.put(position-1, dialogOption);
    }    
    
}

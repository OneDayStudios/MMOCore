/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import com.mmocore.MMOCore;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.constants.IntegratedMod;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Player.RegisterablePlayer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import io.netty.channel.ChannelFutureListener;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.common.network.ForgeMessage;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

/**
 *
 * @author draks
 */
public class ForgeAPI extends AbstractAPI<ForgeAPI> {
    
    // Is item valid in Forge.
    public static boolean isItemValidInForge(String mod, String name) {
        return GameRegistry.findItemStack(mod, name, 1) != null;
    }
    
    public static EntityPlayer getForgePlayer(String name) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player != null && player.getGameProfile().getName().equals(name)) return player;
            }
        }
        return null;
    }
    
    public static boolean isClient() {
        return !isServer();
    }
    
    public static void removeRecipesFor(ItemStack stack) {        
        ArrayList<?> recipes = (ArrayList<?>) CraftingManager.getInstance().getRecipeList();        
        stack.stackSize = 1;        
        for (int scan = 0; scan < recipes.size(); scan++) {            
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);            
            if (tmpRecipe == null) continue;            
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();     
            if (recipeResult != null) {
                ForgeAPI.sendConsoleEntry("Processing item: " + recipeResult.getDisplayName() + " and comparing to : " + stack.getDisplayName(), ConsoleMessageType.FINE);
                recipeResult.stackSize = 1; 
                if (ItemStack.areItemStacksEqual(recipeResult, stack)) {
                    ForgeAPI.sendConsoleEntry("Processed item: " + recipeResult.getDisplayName() + " and comparing to : " + stack.getDisplayName(), ConsoleMessageType.FINE);
                    recipes.remove(tmpRecipe);
                }
            }
            
        }    
    }
    
    public static List<Entity> getEntitiesInArea(double x1, double y1, double z1, double x2, double y2, double z2, RegisterableDimension dimension) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
        return dimension.getRegisteredObject().getEntitiesWithinAABB(Entity.class, box);
    }
    
    public static boolean isServer() {
        return FMLCommonHandler.instance().getSide().equals(Side.SERVER);
    }
    
    public static boolean blockExists(String name, String mod) {
        return GameRegistry.findBlock(mod, name) != null;
    }
    
    public static Block getBlock(String name, String mod) {
        return GameRegistry.findBlock(mod, name);
    }
    
    public static double distance(double sx, double sy, double sz, double dx, double dy, double dz) {
        double distance = Math.sqrt(Math.pow(sx-dx,2) + Math.pow(sx-dx,2) + Math.pow(sz-dz,2));
        return distance;
    }
    
    public static EntityPlayer getForgePlayer(UUID uuid) {        
        for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
            WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
            List<EntityPlayer> players = worldToProcess.playerEntities;
            for (EntityPlayer player : players) {
                if (player != null && player.getUniqueID().equals(uuid)) return player;
            }
        }
        return null;
    }
    

    public static List<String> registerAllDimensionsFor(RegisterablePlayer rPlayer) {
        List<String> dimensionsRegistered = new ArrayList<String>();
        if (rPlayer == null || !rPlayer.isOnline()) return dimensionsRegistered;
        try {
            for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {

                    WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
                    sendDimensionRegister(rPlayer, worldToProcess.provider.dimensionId);
                    dimensionsRegistered.add(worldToProcess.getWorldInfo().getWorldName());
            }
            ForgeAPI.sendConsoleEntry("Succeeeded registering dimensions for player : " + rPlayer.getIdentifier() + ", their client will not crash when changing dimensions.", ConsoleMessageType.FINE);
        } catch (Exception e) {
            ForgeAPI.sendConsoleEntry("Failed to register dimensions for player : " + rPlayer.getIdentifier() + ", their client may crash when changing dimensions as a result.", ConsoleMessageType.FINE);
        }
        return dimensionsRegistered;
    }

   private static void sendDimensionRegister(RegisterablePlayer player, int dimensionID) {
        int providerID = DimensionManager.getProviderType(dimensionID);
        ForgeMessage msg = new ForgeMessage.DimensionRegisterMessage(dimensionID, providerID);
        FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("FORGE", Side.SERVER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set((EntityPlayerMP)player.getRegisteredObject());
        channel.writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
   }
   
    public static boolean chancePassed(int percent) {
        Random r = new Random();
        return (r.nextInt(101) < percent);
    }
    
    public static boolean isModLoaded(IntegratedMod mod) {
        return Loader.isModLoaded(mod.name());
    }
    
    public static void sendConsoleEntry(String message, ConsoleMessageType type) {        
        System.out.println("[" + MMOCore.MODNAME + "] [" + type.name() + "]: " + message);
    }
    
    public static World getForgeWorld(int id) {
        return (World)DimensionManager.getWorld(id);
    }
    
    public static World getForgeWorld(RegisterableDimension dimension) {
        return getForgeWorld(dimension.getName());
    }
    
    public static World getForgeWorld(String name) {
          for(int id = 0; id<MinecraftServer.getServer().worldServers.length; id++) {
                WorldServer worldToProcess = MinecraftServer.getServer().worldServers[id];
                String worldName = worldToProcess.getWorldInfo().getWorldName();
                if (worldName.equals(name)) {
                    return worldToProcess;
                }
          }
          return null;
    }
    
    public static ItemStack getItemStackFromValues(String itemMod, String itemName, int numberOf, int damage) {
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
}

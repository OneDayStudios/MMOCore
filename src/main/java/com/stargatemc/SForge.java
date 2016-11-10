/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc;
import net.minecraft.util.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

/**
 *
 * @author Drakster
 */

@Mod(modid=SForge.MODID, name=SForge.MODNAME, version=SForge.MODVER, dependencies = "before:SGCraft, after:IC2, after:DefenseTech, after:CustomNpcs", acceptableRemoteVersions = "*")

public class SForge {
    
   @Instance(value = SForge.MODID)
   public static SForge instance;
    
   public static final String MODID = "sforge";
   public static final String MODNAME = "SForge";
   public static final String MODVER = "1.0.0";

   
   @Mod.EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
       FMLCommonHandler.instance().bus().register(this);
       System.out.println("Starting " + MODNAME + " v" + MODVER);
   }
   
   @Mod.EventHandler
   public void load(FMLInitializationEvent event) {
       System.out.println("Loading " + MODNAME + " v" + MODVER);       
   }
   
   @Mod.EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
       System.out.println("Initialising " + MODNAME + " v" + MODVER);
       MinecraftForge.EVENT_BUS.register(this);
   }
   
   @SubscribeEvent
   public void onWorldUnload(WorldEvent.Unload event) {
       
   }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge;
import com.stargatemc.forge.api.ForgeAPI;
import com.stargatemc.forge.core.Dialog.DialogRegistry;
import com.stargatemc.forge.core.Dimension.DimensionRegistry;
import com.stargatemc.forge.core.Galaxy.GalaxyRegistry;
import com.stargatemc.forge.core.Gui.GuiRegistry;
import com.stargatemc.forge.core.Listener.ListenerRegistry;
import com.stargatemc.forge.core.Player.PlayerRegistry;
import com.stargatemc.forge.core.constants.IntegratedMod;
import com.stargatemc.forge.network.DataChannel;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 *
 * @author Drakster
 */

@Mod(modid=SForge.MODID, name=SForge.MODNAME, version=SForge.MODVER, dependencies = "before:SGCraft, afterY:IC2, after:DefenseTech, after:CustomNpcs", acceptableRemoteVersions = "*")

public class SForge {
    
   @Instance(value = SForge.MODID)
   public static SForge instance;
    
   public static final String MODID = "sforge";
   public static final String MODNAME = "SForge";
   public static final String MODVER = "1.0.0";

   private GuiRegistry guiRegistry;
   private DimensionRegistry dimensionRegistry;
   private ListenerRegistry listenerRegistry;
   private PlayerRegistry playerRegistry;
   private DialogRegistry dialogRegistry;
   private GalaxyRegistry galaxyRegistry;
   
   
   private DataChannel channel;
   
   public GalaxyRegistry getGalaxyRegistry() {
       if (galaxyRegistry == null) galaxyRegistry = new GalaxyRegistry();
       return galaxyRegistry;
   }
   public GuiRegistry getGuiRegistry() {
       if (guiRegistry == null) guiRegistry = new GuiRegistry();
       return guiRegistry;
   }
   public PlayerRegistry getPlayerRegistry() {
       if (playerRegistry == null) playerRegistry = new PlayerRegistry();
       return playerRegistry;
   }
   public DimensionRegistry getDimensionRegistry() {
       if (dimensionRegistry == null) dimensionRegistry = new DimensionRegistry();
       return dimensionRegistry;
   }
   
   public ListenerRegistry getListenerRegistry() {
       if (listenerRegistry == null)  listenerRegistry = new ListenerRegistry();
       return listenerRegistry;
   }
   public DialogRegistry getDialogRegistry() {
       if (dialogRegistry == null)  dialogRegistry = new DialogRegistry();
       return dialogRegistry;
   }
   public DataChannel getChannel() {
       return this.channel;
   }
   
   @Mod.EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
       System.out.println("Starting " + MODNAME + " v" + MODVER);
       channel = new DataChannel(SForge.MODID);
   }
   
   @Mod.EventHandler
   public void load(FMLInitializationEvent event) {
       System.out.println("Loading " + MODNAME + " v" + MODVER);       
   }
   
   @Mod.EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
       System.out.println("Initialising " + MODNAME + " v" + MODVER);
       SForge.getInstance().getGuiRegistry().initialise();
       SForge.getInstance().getDimensionRegistry().initialise();
       SForge.getInstance().getGalaxyRegistry().initialise();
       SForge.getInstance().getPlayerRegistry().initialise();
       SForge.getInstance().getListenerRegistry().initialise();
       SForge.getInstance().getDialogRegistry().initialise();
   }
   
   public static SForge getInstance() {
       return SForge.instance;
   }
}

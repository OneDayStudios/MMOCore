/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stargatemc.forge;
import com.stargatemc.forge.api.forge.ForgeAPI;
import com.stargatemc.forge.api.gui.GuiAPI;
import com.stargatemc.forge.core.Gui.GuiRegistry;
import com.stargatemc.forge.core.constants.GuiSlot;
import com.stargatemc.forge.network.DataChannel;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLCommonHandler;

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
   private ForgeAPI ForgeAPI;
   private GuiAPI GuiAPI;
   
   private DataChannel channel;
   
   public GuiRegistry getGuiRegistry() {
       if (guiRegistry == null) guiRegistry = new GuiRegistry();
       return guiRegistry;
   }
   
   public DataChannel getChannel() {
       return this.channel;
   }
   
   @Mod.EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
       System.out.println("Starting " + MODNAME + " v" + MODVER);
       FMLCommonHandler.instance().bus().register(this);
       channel = new DataChannel(SForge.MODID);
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
   public void onRenderGui(RenderGameOverlayEvent.Post e) {
       getGuiRegistry().tick();
   }
   
   @SubscribeEvent
   public void onPlayerInteract(PlayerInteractEvent e) {
       if (FMLCommonHandler.instance().getSide().isServer()) {
           getGuiAPI().sendGuiElementToClient(((EntityPlayer)e.entityPlayer).getUniqueID(), GuiSlot.Toast, "MyTitle", "MySubTitle", "MyDescription", 100, 100, 100, 2000);
           getGuiAPI().sendGuiElementToClient(((EntityPlayer)e.entityPlayer).getUniqueID(), GuiSlot.TopLeft, "MyTitle", "MySubTitle", "MyDescription", 100, 100, 100, 2000);
       }
   }
   
   public ForgeAPI getForgeAPI() {
       if (this.ForgeAPI == null) ForgeAPI = new ForgeAPI();
       return this.ForgeAPI;
   }
   public GuiAPI getGuiAPI() {
       if (this.GuiAPI == null) GuiAPI = new GuiAPI();
       return this.GuiAPI;
   }
   public static SForge getInstance() {
       return SForge.instance;
   }
}

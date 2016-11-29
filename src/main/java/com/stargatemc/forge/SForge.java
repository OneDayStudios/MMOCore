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
import com.stargatemc.forge.core.Npc.NpcRegistry;
import com.stargatemc.forge.core.NpcFaction.NpcFactionRegistry;
import com.stargatemc.forge.core.Player.PlayerRegistry;
import com.stargatemc.forge.core.Quest.QuestRegistry;
import com.stargatemc.forge.core.Stargate.StargateRegistry;
import com.stargatemc.forge.core.constants.ConsoleMessageType;
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
   private NpcFactionRegistry npcFactionRegistry;
   private NpcRegistry npcRegistry;
   
   
   private DataChannel channel;
   
   private StargateRegistry stargateRegistry;
   private QuestRegistry questRegistry;
   
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
   public NpcRegistry getNpcRegistry() {
       if (npcRegistry == null) npcRegistry = new NpcRegistry();
       return npcRegistry;
   }
   public ListenerRegistry getListenerRegistry() {
       if (listenerRegistry == null)  listenerRegistry = new ListenerRegistry();
       return listenerRegistry;
   }
   
   public StargateRegistry getStargateRegistry() {
       if (stargateRegistry == null)  stargateRegistry = new StargateRegistry();
       return stargateRegistry;
   }
   
   public DialogRegistry getDialogRegistry() {
       if (dialogRegistry == null)  dialogRegistry = new DialogRegistry();
       return dialogRegistry;
   }
   
   public QuestRegistry getQuestRegistry() {
       if (questRegistry == null)  questRegistry = new QuestRegistry();
       return questRegistry;
   }
   
   public NpcFactionRegistry getNpcFactionRegistry() {
       if (npcFactionRegistry == null) npcFactionRegistry = new NpcFactionRegistry();
       return npcFactionRegistry;
   }
   
   public DataChannel getChannel() {
       return this.channel;
   }
   
   @Mod.EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Starting " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
       channel = new DataChannel(SForge.MODID);
   }
   
   @Mod.EventHandler
   public void load(FMLInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Loading " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
   }
   
   @Mod.EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Initialising " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
       SForge.getInstance().getGuiRegistry().initialise();
       SForge.getInstance().getDimensionRegistry().initialise();
       SForge.getInstance().getGalaxyRegistry().initialise();
       SForge.getInstance().getPlayerRegistry().initialise();
       SForge.getInstance().getListenerRegistry().initialise();
       if (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs)) SForge.getInstance().getDialogRegistry().initialise();
       if (ForgeAPI.isModLoaded(IntegratedMod.SGCraft)) SForge.getInstance().getStargateRegistry().initialise();
       if (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs)) SForge.getInstance().getQuestRegistry().initialise();
       if (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs)) SForge.getInstance().getNpcFactionRegistry().initialise();
       if (ForgeAPI.isModLoaded(IntegratedMod.CustomNpcs)) SForge.getInstance().getNpcRegistry().initialise();
   }
   
   public static SForge getInstance() {
       return SForge.instance;
   }
}

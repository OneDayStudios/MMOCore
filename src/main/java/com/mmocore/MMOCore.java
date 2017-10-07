/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore;
import com.mmocore.api.DictionaryAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.api.WarpDriveAPI;
import com.mmocore.module.Dialog.DialogRegistry;
import com.mmocore.module.Dimension.DimensionRegistry;
import com.mmocore.module.Gui.GuiRegistry;
import com.mmocore.module.Listener.ListenerRegistry;
import com.mmocore.module.Npc.NpcRegistry;
import com.mmocore.module.NpcFaction.NpcFactionRegistry;
import com.mmocore.module.Player.PlayerRegistry;
import com.mmocore.module.Quest.QuestRegistry;
import com.mmocore.module.Stargate.StargateRegistry;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Galaxy.GalaxyRegistry;
import com.mmocore.module.GameEvent.GameEventRegistry;
import com.mmocore.module.command.BaseCommand;
import com.mmocore.module.command.CommandRegistry;
import com.mmocore.network.DataChannel;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;


/**
 *
 * @author Drakster
 */

@Mod(modid=MMOCore.MODID, name=MMOCore.MODNAME, version=MMOCore.MODVER, acceptableRemoteVersions = MMOCore.MODVER, dependencies = "after:WarpDrive;" )

public class MMOCore {
    
   @Instance(value = MMOCore.MODID)
   public static MMOCore instance;
    
   public static final String MODID = "mmocore";
   public static final String MODNAME = "MMOCore";
   public static final String MODVER = "1.0.9";

   private static GuiRegistry guiRegistry;
   private static DimensionRegistry dimensionRegistry;
   private static ListenerRegistry listenerRegistry;
   private static PlayerRegistry playerRegistry;
   private static DialogRegistry dialogRegistry;
   private static NpcFactionRegistry npcFactionRegistry;
   private static NpcRegistry npcRegistry;
   private static GameEventRegistry gameEventRegistry;
   private static CommandRegistry cmdRegistry;
   private static GalaxyRegistry galaxyRegistry;
   
   private static DataChannel channel;
   
   private static StargateRegistry stargateRegistry;
   private static QuestRegistry questRegistry;
   
   public static CommandRegistry getCommandRegistry() {
       if (cmdRegistry == null) cmdRegistry = new CommandRegistry();
       return cmdRegistry;
   }
   
   public static GameEventRegistry getGameEventRegistry() {
       if (gameEventRegistry == null) gameEventRegistry = new GameEventRegistry();
       return gameEventRegistry;
   }
   
   public static GuiRegistry getGuiRegistry() {
       if (guiRegistry == null) guiRegistry = new GuiRegistry();
       return guiRegistry;
   }
   public static PlayerRegistry getPlayerRegistry() {
       if (playerRegistry == null) playerRegistry = new PlayerRegistry();
       return playerRegistry;
   }
   public static DimensionRegistry getDimensionRegistry() {
       if (dimensionRegistry == null) dimensionRegistry = new DimensionRegistry();
       return dimensionRegistry;
   }
   public static NpcRegistry getNpcRegistry() {
       if (npcRegistry == null) npcRegistry = new NpcRegistry();
       return npcRegistry;
   }
   public static ListenerRegistry getListenerRegistry() {
       if (listenerRegistry == null)  listenerRegistry = new ListenerRegistry();
       return listenerRegistry;
   }
   
   public static GalaxyRegistry getGalaxyRegistry() {
       if (galaxyRegistry == null)  galaxyRegistry = new GalaxyRegistry();
       return galaxyRegistry;
   }
   
   public static StargateRegistry getStargateRegistry() {
       if (stargateRegistry == null)  stargateRegistry = new StargateRegistry();
       return stargateRegistry;
   }
   
   public static DialogRegistry getDialogRegistry() {
       if (dialogRegistry == null)  dialogRegistry = new DialogRegistry();
       return dialogRegistry;
   }
   
   public static QuestRegistry getQuestRegistry() {
       if (questRegistry == null)  questRegistry = new QuestRegistry();
       return questRegistry;
   }
   
   public static NpcFactionRegistry getNpcFactionRegistry() {
       if (npcFactionRegistry == null) npcFactionRegistry = new NpcFactionRegistry();
       return npcFactionRegistry;
   }
   
   public static DataChannel getChannel() {
       return channel;
   }
   
   @Mod.EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Starting " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
       MMOCore.channel = new DataChannel(MMOCore.MODID);     
   }
   
   @Mod.EventHandler
   public void load(FMLInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Loading " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);       
   }
   
   @Mod.EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Initialising " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);         
       MMOCore.getListenerRegistry().initialise();   
   }
   
   @Mod.EventHandler
   public void onServerStarting(FMLServerStartingEvent e) {       
    e.registerServerCommand(new BaseCommand());
   }
   
   @Mod.EventHandler
   public void onServerStopping(FMLServerStoppingEvent e) {       
    if (ForgeAPI.isServer()) NpcAPI.deregisterAll();
   }
   
   @Mod.EventHandler
   public void onServerStarted(FMLServerStartedEvent e) {       
       if (ForgeAPI.isClient()) {
//           MMOCore.getGuiRegistry().initialise();
       }
       if (ForgeAPI.isServer()) {       
            DictionaryAPI.loadQuests();
            DictionaryAPI.loadDialogs();
            MMOCore.getDimensionRegistry().initialise();
            MMOCore.getNpcFactionRegistry().initialise();      
            MMOCore.getPlayerRegistry().initialise();
            MMOCore.getDialogRegistry().initialise();       
            MMOCore.getCommandRegistry().initialise();
            MMOCore.getQuestRegistry().initialise();  
            MMOCore.getGameEventRegistry().initialise();
            MMOCore.getNpcRegistry().initialise();
            // The below reference loads all fake dimensions (aka gas giants and stars) into the Dimension Registry.
            WarpDriveAPI.onServerStarted();
       }
   }
   
   public static MMOCore getInstance() {
       return MMOCore.instance;
   }
}

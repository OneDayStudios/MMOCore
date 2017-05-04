/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.module.Dialog.DialogRegistry;
import com.mmocore.module.Dimension.DimensionRegistry;
import com.mmocore.module.Galaxy.GalaxyRegistry;
import com.mmocore.module.Gui.GuiRegistry;
import com.mmocore.module.Listener.ListenerRegistry;
import com.mmocore.module.Npc.NpcRegistry;
import com.mmocore.module.NpcFaction.NpcFactionRegistry;
import com.mmocore.module.Player.PlayerRegistry;
import com.mmocore.module.Quest.QuestRegistry;
import com.mmocore.module.Stargate.StargateRegistry;
import com.mmocore.constants.ConsoleMessageType;
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

@Mod(modid=MMOCore.MODID, name=MMOCore.MODNAME, version=MMOCore.MODVER, acceptableRemoteVersions = MMOCore.MODVER)

public class MMOCore {
    
   @Instance(value = MMOCore.MODID)
   public static MMOCore instance;
    
   public static final String MODID = "mmocore";
   public static final String MODNAME = "MMOCore";
   public static final String MODVER = "1.0.0";

   private GuiRegistry guiRegistry;
   private DimensionRegistry dimensionRegistry;
   private ListenerRegistry listenerRegistry;
   private PlayerRegistry playerRegistry;
   private DialogRegistry dialogRegistry;
   private GalaxyRegistry galaxyRegistry;
   private NpcFactionRegistry npcFactionRegistry;
   private NpcRegistry npcRegistry;
   private CommandRegistry cmdRegistry;
   
   
   private DataChannel channel;
   
   private StargateRegistry stargateRegistry;
   private QuestRegistry questRegistry;
   
   public CommandRegistry getCommandRegistry() {
       if (cmdRegistry == null) cmdRegistry = new CommandRegistry();
       return cmdRegistry;
   }
   
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
       channel = new DataChannel(MMOCore.MODID);
   }
   
   @Mod.EventHandler
   public void load(FMLInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Loading " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
   }
   
   @Mod.EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
       ForgeAPI.sendConsoleEntry("Initialising " + MODNAME + " v" + MODVER, ConsoleMessageType.FINE);
       MMOCore.getInstance().getListenerRegistry().initialise();
   }
   
   @Mod.EventHandler
   public void onServerStarting(FMLServerStartingEvent e) {       
    e.registerServerCommand(new BaseCommand());
   }
   
   @Mod.EventHandler
   public void onServerStopping(FMLServerStoppingEvent e) {       
    NpcAPI.deregisterAll();
   }
   
   @Mod.EventHandler
   public void onServerStarted(FMLServerStartedEvent e) {       
       MMOCore.getInstance().getGuiRegistry().initialise();
       MMOCore.getInstance().getDimensionRegistry().initialise();
       MMOCore.getInstance().getGalaxyRegistry().initialise();
       MMOCore.getInstance().getPlayerRegistry().initialise();
       MMOCore.getInstance().getDialogRegistry().initialise();
       //MMOCore.getInstance().getStargateRegistry().initialise();
       MMOCore.getInstance().getCommandRegistry().initialise();
       MMOCore.getInstance().getQuestRegistry().initialise();
       MMOCore.getInstance().getNpcFactionRegistry().initialise();
       MMOCore.getInstance().getNpcRegistry().initialise();
   }

   public static MMOCore getInstance() {
       return MMOCore.instance;
   }
}

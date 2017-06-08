///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mmocore.module.Listener.Listeners;
//
//import com.mmocore.MMOCore;
//import com.mmocore.module.Listener.RegisterableListener;
//import com.mmocore.module.Npc.RegisterableNpc;
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.event.entity.player.EntityInteractEvent;
//import noppes.npcs.entity.EntityCustomNpc;
//
///**
// *
// * @author draks
// */
//public class NpcInteractListener extends RegisterableListener {
//
//    
//   @SubscribeEvent
//   public void onPlayerInteract(EntityInteractEvent e) {
//       if (e.target instanceof EntityCustomNpc) {
//           EntityCustomNpc npc = (EntityCustomNpc)e.target;
//           if (MMOCore.getNpcRegistry().getRegistered(npc.getUniqueID())) {
//               RegisterableNpc tNpc = MMOCore.getNpcRegistry().getRegistered(npc.getUniqueID());
//               if (tNpc == null) return;
//               if (tNpc.hasRepeatableQuests()) {
//                System.out.println("Interacting with npc with repeatable quests!");
//                if (tNpc.getRepeatableQuests().isEmpty()) return;
//                    for (Quest q : tNpc.getRepeatableQuests()) {
//                        if (!PlayerQuestController.canQuestBeAccepted(q, (EntityPlayer)e.entityPlayer)) {
//                            System.out.println("Player cant accept: " + q.title);
//                            tNpc.tellNear(tNpc.getRandomRepeatableUnavailableLine(((EntityPlayer)e.entityPlayer).getGameProfile().getName()));
//                            sendChatMessage(((EntityPlayer)e.entityPlayer), (EnumChatFormatting.RED + "You cannot interact with " + tNpc.getName() + " until you can hand in (to this NPC) or accept the " + q.title + " quest again."));
//                            e.setCanceled(true);
//
//                        } else {
//                            System.out.println("Player can accept: " + q.title);
//                        }
//
//                    }
//
//               }
//
//           }
//
//       }
//
//   }
//}

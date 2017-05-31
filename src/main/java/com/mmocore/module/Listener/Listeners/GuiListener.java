/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.module.Listener.RegisterableListener;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 *
 * @author draks
 */
public class GuiListener extends RegisterableListener {
    
   @SubscribeEvent(priority=EventPriority.HIGHEST)
   public void onRenderGui(RenderGameOverlayEvent.Pre e) {
       if (e.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
           MMOCore.getGuiRegistry().tick();
       }
   }
}
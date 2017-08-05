/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Listener.Listeners;

import com.mmocore.MMOCore;
import com.mmocore.api.AdvancedRocketryAPI;
import com.mmocore.api.DictionaryAPI;
import com.mmocore.api.ForgeAPI;
import com.mmocore.api.NpcAPI;
import com.mmocore.constants.ConsoleMessageType;
import com.mmocore.module.Dimension.RegisterableDimension;
import com.mmocore.module.Listener.RegisterableListener;
import com.mmocore.constants.DimensionConditions;
import com.mmocore.constants.DimensionType;
import com.mmocore.module.Npc.RegisterableNpc;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import java.util.ArrayList;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.world.World;
import cpw.mods.fml.common.gameevent.TickEvent;
import java.util.HashMap;

/**
 *
 * @author draks
 */
public class ServerListener extends RegisterableListener {
    
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        MMOCore.getNpcFactionRegistry().tick();
    }
    
}

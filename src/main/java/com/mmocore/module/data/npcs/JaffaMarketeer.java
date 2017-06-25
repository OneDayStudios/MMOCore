/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.data.npcs;

import com.mmocore.api.DialogAPI;
import com.mmocore.api.NpcFactionAPI;
import com.mmocore.api.UniverseAPI;
import com.mmocore.constants.DialogChatColor;
import com.mmocore.constants.DialogConversationOption;
import com.mmocore.constants.NpcBoolean;
import com.mmocore.constants.NpcModifier;
import com.mmocore.constants.NpcMovementAnimation;
import com.mmocore.constants.NpcProjectile;
import com.mmocore.constants.NpcRotationType;
import com.mmocore.constants.NpcSound;
import com.mmocore.constants.NpcSpawnMethod;
import com.mmocore.constants.NpcTexture;
import com.mmocore.constants.uPosition;
import com.mmocore.module.Dialog.RegisterableDialog;
import com.mmocore.module.Npc.RegisterableNpc;
import com.mmocore.module.Npc.loadout.NpcHeldItemSet;
import com.mmocore.module.Npc.loadout.NpcItem;
import com.mmocore.module.Npc.options.NpcBaseOptions;
import com.mmocore.module.Npc.options.NpcCombatOptions;
import com.mmocore.module.Npc.options.NpcInteractOptions;
import com.mmocore.module.Npc.options.NpcLootOptions;
import com.mmocore.module.Npc.options.NpcMovementOptions;

/**
 *
 * @author draks
 */
public class JaffaMarketeer extends RegisterableNpc {
    
    public JaffaMarketeer() {
        super(  "Loken Valo",
                "Jaffa Marketeer",
                NpcTexture.JAFFA_GREY_ARMOR,
                NpcModifier.RANGED_BOSS,
                NpcSpawnMethod.Static,
                NpcFactionAPI.getRegistered("Jaffa Marketeers")
        );
        
        NpcHeldItemSet weapons = this.getRangedHeldItems();
        NpcItem heldItem = new NpcItem("flansmod", "zatGun", 1, 0);
        NpcBaseOptions options = this.getBaseOptions();
        uPosition position = new uPosition(-132.0,28.0,-616.0, UniverseAPI.getDimensionByDisplayName("P2X-3YZ"));
        options.setSpawnPosition(position);
        options.setSpawnMethod(NpcSpawnMethod.Static);
        weapons.setMainHand(heldItem);
        this.setRangedHeldItems(weapons);
        NpcHeldItemSet passiveHeld = this.getPassiveHeldItems();
        NpcItem passiveItem = new NpcItem("minecraft", "book", 1, 0);
        passiveHeld.setMainHand(passiveItem);
        this.setPassiveHeldItems(passiveHeld);
        NpcCombatOptions cOpts = this.getCombatOptions();
        cOpts.setFireWeaponSound(NpcSound.ZatGun);
        cOpts.setProjectile(NpcProjectile.WHITE_PLASMA);
        cOpts.setAttacksHostileFactions(NpcBoolean.YES);
        NpcLootOptions lOpts = this.getLootOptions();
        NpcMovementOptions movingOpts = this.getMovementOptions();
        movingOpts.setRotationType(NpcRotationType.None);
        movingOpts.setMovingAnimation(NpcMovementAnimation.Sitting);
        this.setMovementOptions(movingOpts);
        DialogConversationOption convoOpt = new DialogConversationOption();
        RegisterableDialog dialog = DialogAPI.getRegistered("Jaffa Marketeer Intro", "Economy");
        NpcInteractOptions intOpts = this.getInteractOptions();
        convoOpt.setDialogOption("Welcome", dialog, DialogChatColor.YELLOW);
        intOpts.addDialog(convoOpt);
        this.setInteractOptions(intOpts);
        NpcItem item = new NpcItem();
        item.setItem("flansmod", "zatGun", 1, 0);
        lOpts.addToLootTable(50, item);
        this.setLootOptions(lOpts);
        this.setCombatOptions(cOpts);
    }
}

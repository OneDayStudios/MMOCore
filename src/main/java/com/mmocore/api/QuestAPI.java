/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.api;

import java.util.UUID;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.QuestData;

/**
 *
 * @author draks
 */
public class QuestAPI extends AbstractAPI<QuestAPI> {
    
    public static boolean giveToPlayer(UUID playerUUID, String quest) {        
        try {
            Quest q = get(quest);
            if (q == null) return false;
            PlayerQuestController.addActiveQuest(q, ForgeAPI.getForgePlayer(playerUUID));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean playerCanObtain(UUID playerUUID, String quest) {
        Quest q = get(quest);
        if (q == null) return false;
        if (PlayerQuestController.canQuestBeAccepted(q, ForgeAPI.getForgePlayer(playerUUID))) {
            return true;
        }
        return false;
    }
    
    public static boolean playerHasQuest(String playerName, String questTitle) {
        PlayerData data = PlayerDataController.instance.getDataFromUsername(playerName);
        for (QuestData qd : data.questData.activeQuests.values()) {
            if (qd.quest.title.equals(questTitle) && !qd.isCompleted) return true;
        }
        return false;
    }
    
    public static boolean questCategoryExists(String title) {
        for (int id : QuestController.instance.categories.keySet()) {
            QuestCategory beingProcessed = QuestController.instance.categories.get(id);
            if (beingProcessed == null || beingProcessed.title == null) continue;
            if (beingProcessed.title.equals(title)) return true;
        }
        return false;
    }
    
    public static QuestCategory getQuestCategoryForTitle(String title) {
        for (int id : QuestController.instance.categories.keySet()) {
            QuestCategory beingProcessed = QuestController.instance.categories.get(id);
            if (beingProcessed == null) continue;
            if (beingProcessed.title.equals(title)) return beingProcessed;
        }
        return null;
    }
    
    public static boolean playerCompletedQuest(String playerName, String questTitle) {
        PlayerData data = PlayerDataController.instance.getDataFromUsername(playerName);
        Quest q = get(questTitle);
        if (q != null && data.questData.finishedQuests.containsKey(q.id)) return true;
        return false;
    }
    
    public static Quest get(String title) {
        for (Quest q : QuestController.instance.quests.values()) {
            if (q.title.equals(title)) return q;
        }
        return null;
    }
    
    public static boolean exists(String title) {
        for (Quest q : QuestController.instance.quests.values()) {
            if (q.title.equals(title)) return true;
        }
        return false;
    }
    
    public static Quest get(int id) {
        for (Quest q : QuestController.instance.quests.values()) {
            if (q.id == id) return q;
        }
        return null;
    }
    
    public static boolean exists(int id) {
        for (Quest q : QuestController.instance.quests.values()) {
            if (q.id == id) return true;
        }
        return false;
    }
    
    public static void complete(String playerName, String questTitle, boolean creditThemWithCompletion) {
        PlayerData data = PlayerDataController.instance.getDataFromUsername(playerName);
        if (playerHasQuest(playerName, questTitle)) {
            for (Integer i : data.questData.activeQuests.keySet()) {
                if (creditThemWithCompletion) {
                    data.questData.activeQuests.get(i).isCompleted = true;
                    if (!data.questData.finishedQuests.containsKey(i)) data.questData.finishedQuests.put(i, System.currentTimeMillis());
                    if (data.questData.finishedQuests.containsKey(i)) data.questData.finishedQuests.replace(i, System.currentTimeMillis());
                } else {
                    data.questData.activeQuests.remove(i);
                }
            }
        } 
    }
}
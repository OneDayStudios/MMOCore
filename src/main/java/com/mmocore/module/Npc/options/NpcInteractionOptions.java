/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmocore.module.Npc.options;

import com.mmocore.module.Player.RegisterablePlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author draks
 */
public class NpcInteractionOptions {

    private List<String> randomRepeatableUnavailableLines = new ArrayList<String>();
    
    public String getRandomRepeatableUnavailableLine(RegisterablePlayer player) {
       if (randomRepeatableUnavailableLines.isEmpty()) return "I have no RandomRepeatableUnavailable lines. Please report this to a developer.";
       if (randomRepeatableUnavailableLines.size() == 1) return parseLine(player, randomRepeatableUnavailableLines.get(0));
       Random ran = new Random();
       int number = ran.nextInt(randomRepeatableUnavailableLines.size());
       if (number != 0) number = number - 1;
       return parseLine(player, randomRepeatableUnavailableLines.get(number));
    }
    
    public void addRandomRepeatableUnavailableLine(String s) {
        if (!randomRepeatableUnavailableLines.contains(s)) randomRepeatableUnavailableLines.add(s);
    }
    
    private String parseLine(RegisterablePlayer player, String line) {
        line = line.replace("@player@",player.getName());
        line = line.replace("@dimension@", player.getPosition().getDimension().getIdentifier());
        line = line.replace("@galaxy@", player.getPosition().getDimension().getIdentifier());
        return line;
    }
    
}

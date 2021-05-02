package com.minerival.develop.osadvancements.RewardTypes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandReward implements Reward {
    String command;
    public CommandReward(String command){
        this.command = command;
    }
    @Override
    public void giveReward(Player p) {
        String cmd = command;
        if (cmd.contains("%player%")){
            cmd = cmd.replaceAll("%player%", p.getName());
        }
        cmd = ChatColor.translateAlternateColorCodes('&', cmd);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
    @Override
    public String toString(){
        return "Command Reward: "+command;
    }
}

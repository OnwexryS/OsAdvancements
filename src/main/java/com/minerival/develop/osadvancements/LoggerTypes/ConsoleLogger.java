package com.minerival.develop.osadvancements.LoggerTypes;

import com.minerival.develop.osadvancements.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
public class ConsoleLogger implements Logger {

    public ConsoleLogger(){}
    @Override
    public void log(String message) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',main.prefix + message));
    }

}

package com.minerival.develop.osadvancements.LoggerTypes;

import com.minerival.develop.osadvancements.main;
import org.bukkit.ChatColor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger implements Logger {
    private File logFile;
    private PrintWriter logWriter = null;

    public FileLogger(main plugin){
        logFile = new File(plugin.getDataFolder(), "logs.txt");
        if (!(logFile.exists())){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void log(String message) {
        try {
            logWriter = new PrintWriter(new FileOutputStream(logFile, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        logWriter.println("[" + format.format(now) + "] "+ ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
        logWriter.close();
    }

}

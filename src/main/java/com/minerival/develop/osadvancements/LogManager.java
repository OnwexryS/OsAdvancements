package com.minerival.develop.osadvancements;

import com.minerival.develop.osadvancements.LoggerTypes.ConsoleLogger;
import com.minerival.develop.osadvancements.LoggerTypes.LogCauses;
import com.minerival.develop.osadvancements.LoggerTypes.Logger;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LogManager {
    ArrayList<Logger> loggers;


    public LogManager(){
        loggers = new ArrayList<>();
    }

    public void addLogger(Logger logger){
        this.loggers.add(logger);
    }
    public void log(String message){
        for(Logger logger : loggers){
            logger.log(message);
        }
    }

    public void clear(){
        loggers.clear();
    }

    public void log(String message, Player p){
        String msg = message;
        msg.replaceAll("%player%", p.getDisplayName());
        log(msg);
    }

    public void log(LogCauses logCauses, String message){
        for(Logger logger : loggers){
            if (logger instanceof ConsoleLogger && logCauses == LogCauses.SYSTEM){
                logger.log(message);
            }else{
                logger.log(logCauses.toString() + " " + message);
            }
        }
    }

    public void log(LogCauses logCauses, String message, Player p){
        String msg = message;
        msg.replaceAll("%player%", p.getDisplayName());
        log(logCauses, msg);
    }

}

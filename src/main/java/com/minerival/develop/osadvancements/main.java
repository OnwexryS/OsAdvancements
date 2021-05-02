package com.minerival.develop.osadvancements;

import com.minerival.develop.osadvancements.LoggerTypes.ConsoleLogger;
import com.minerival.develop.osadvancements.LoggerTypes.FileLogger;
import com.minerival.develop.osadvancements.LoggerTypes.LogCauses;
import com.minerival.develop.osadvancements.RewardTypes.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;



public final class main extends JavaPlugin implements Listener {
    public AdvancementManager advancements = new AdvancementManager(this);
    public RewardManager rewards = new RewardManager(this);
    public LogManager logger = new LogManager();

    public static Boolean logFinishedAdvancements= true;
    public static Boolean logOnlyDefinedAdvancementsOnFinish = true;
    public static Boolean logToConsole = true;
    public static Boolean logToFile = true;
    public static Boolean debug = true;

    public static String advancementRewardMessage = "&6Giving advancement reward to &e";
    public static String prefix= "&1[&bO's &9Advancements&1] ";
    public static String reloadingMessage = "&e&lReloading..";
    public static String reloadedMessage = "&a&lReloaded !";
    public static String loadingAdvancementsMessage = "&9&lAdvancement is loading.";

    public static String newAdvancementMessage = "Advancement with key ";
    public static String newAdvancementMessageResume = " is detected.";


    public static String newItemMessage = "Item with key ";
    public static String newItemMessageResume = " is detected.";

    public static String finishedAdvancementMessage= "&b&lCompleted advancement: ";
    public static String commandMessage= "Command is executing... Command is: ";
    public static String noPermisisonMessage = "&f&lYou dont have permission.";

    public static String itemLoadedMessage = "&7Item &f";
    public static String itemLoadedMessageResume = "&7 is loaded";

    public static String itemAlreadyDefinedErrorMessage = "&cThe item &f";
    public static String itemAlreadyDefinedErrorMessageResume = " &cis defined more than once";

    public static String advancementRewardItemNotFound = "&cItem with ID &f";
    public static String advancementRewardItemNotFoundResume = " &cdoesn't exist";

    public static int totalAdvancements = 0;



    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //System.out.println(prefix+ChatColor.BLUE+"PlaceholderAPI Detected, Registering Placeholders");
        } else {
            //getLogger().warning(prefix+" Could not find PlaceholderAPI!.");
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        loadConfig();

        getServer().getPluginManager().registerEvents(this, this);

        logger.log(LogCauses.SYSTEM, "&5&lv1.1 &d&lLoaded !");
    }

    public void loadConfig(){
        reloadConfig();
        saveDefaultConfig();

        logToConsole = getConfig().getBoolean("logToConsole");
        logToFile = getConfig().getBoolean("logToFile");
        logger.clear();
        if (logToConsole){
            logger.addLogger(new ConsoleLogger());
        }
        if (logToFile){
            logger.addLogger(new FileLogger(this));
        }

        debug = getConfig().getBoolean("debug");
        logFinishedAdvancements= getConfig().getBoolean("logFinishedAdvancements");
        logOnlyDefinedAdvancementsOnFinish = getConfig().getBoolean("logOnlyDefinedAdvancementsOnFinish");

        reloadingMessage = getConfig().getString("reloadingMessage");
        reloadedMessage = getConfig().getString("reloadedMessage");
        loadingAdvancementsMessage = getConfig().getString("loadingAdvancementsMessage");
        newAdvancementMessage = getConfig().getString("newAdvancementMessage");
        newAdvancementMessageResume = getConfig().getString("newAdvancementMessageResume");
        newItemMessage = getConfig().getString("newItemMessage");
        newItemMessageResume = getConfig().getString("newItemMessageResume");
        finishedAdvancementMessage = getConfig().getString("finishedAdvancementMessage");
        advancementRewardMessage = getConfig().getString("advancementRewardMessage");
        commandMessage = getConfig().getString("commandMessage");
        noPermisisonMessage = getConfig().getString("noPermisisonMessage");
        itemLoadedMessage = getConfig().getString("itemLoadedMessage");
        itemLoadedMessageResume = getConfig().getString("itemLoadedMessageResume");
        itemAlreadyDefinedErrorMessage = getConfig().getString("itemAlreadyDefinedErrorMessage");
        itemAlreadyDefinedErrorMessageResume = getConfig().getString("itemAlreadyDefinedErrorMessageResume");
        advancementRewardItemNotFound = getConfig().getString("advancementRewardItemNotFound");
        advancementRewardItemNotFoundResume = getConfig().getString("advancementRewardItemNotFoundResume");

        rewards.loadItems();
        advancements.loadAdvancements();
        //prefix= getConfig().getString("prefix");


    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (label.equalsIgnoreCase("osadvancements")){
            if (!sender.hasPermission("osadvancements.reload") || !sender.isOp()){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+ noPermisisonMessage));
                return true;
            }
            if (args.length == 0){
                sender.sendMessage(ChatColor.WHITE+"/osadvancements reload");
                return true;
            }
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("reload")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+ reloadingMessage));;
                    loadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+ reloadedMessage));
                    return true;
                }
            }

        }
        return false;
    }

    @EventHandler
    public void onAdvancementComplete(PlayerAdvancementDoneEvent e){
        String finishedAdvancement= e.getAdvancement().getKey().toString();
        if (logFinishedAdvancements){
            if (logOnlyDefinedAdvancementsOnFinish){
                if (advancements.containsKey(finishedAdvancement)){
                    logger.log(LogCauses.DEBUG,finishedAdvancementMessage+finishedAdvancement);
                }
            }else{
                logger.log(LogCauses.DEBUG,finishedAdvancementMessage+finishedAdvancement);
            }
        }
        if (!(advancements.containsKey(finishedAdvancement))){
            return;
        }
        Advancement adv = advancements.getAdvancement(finishedAdvancement);
        adv.giveRewards(e.getPlayer());
        for(Reward r : adv.getRewards()){
            logger.log(LogCauses.LOG, advancementRewardMessage +e.getPlayer().getName() + " &9" + r.toString());
        }

        //PlaceholderAPI.setPlaceholders(e.getPlayer(), e.getPlayer());
    }



}

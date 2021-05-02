package com.minerival.develop.osadvancements;

import com.minerival.develop.osadvancements.LoggerTypes.LogCauses;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RewardManager {

    public static Map<String, ItemStack> rewardItems;
    private final main plugin;


    public RewardManager(main plugin){
        this.plugin = plugin;
        this.rewardItems = new HashMap<>();
    }


    public void addItem(String nameSpacedID, ItemStack item){
        this.rewardItems.put(nameSpacedID, item);
    }

    public void clearItems(){
        this.rewardItems = new HashMap<>();
    }

    public boolean contains(String nameSpacedID){
        return this.rewardItems.containsKey(nameSpacedID);
    }

    public ItemStack getItem(String nameSpacedID){
        if (!this.rewardItems.containsKey(nameSpacedID)){
            plugin.logger.log(LogCauses.ERROR, main.advancementRewardItemNotFound + nameSpacedID + main.advancementRewardItemNotFoundResume);
            return new ItemStack(Material.STONE);
        }
        return this.rewardItems.get(nameSpacedID);
    }



    public void loadItems(){
        Boolean isStartup = rewardItems.isEmpty();
        Set<String> toDetectNewRewards = rewardItems.keySet();
        clearItems();



        File parentFolder = new File(plugin.getDataFolder(), "Items");
        File tempFolder;
        if (!(parentFolder.exists())){
            parentFolder.mkdirs();
            tempFolder = new File(parentFolder, "example.yml");
            plugin.saveResource("Items/example.yml", false);

            tempFolder = new File(parentFolder, "secondexample.yml");
            plugin.saveResource("Items/secondexample.yml", false);
        }

        FileConfiguration nextConfig;
        for (File nextFile : parentFolder.listFiles()){
            nextConfig = new YamlConfiguration();
            try{
                nextConfig.load(nextFile);
            }catch(IOException | InvalidConfigurationException e){
                e.printStackTrace();
            }



            for (String nameSpacedID : nextConfig.getKeys(false)){
                String it = nextConfig.getString(nameSpacedID + "." + "type");
                ItemStack item = new ItemStack(Material.valueOf(it));


                if (isStartup && main.debug){
                    plugin.logger.log(LogCauses.DEBUG, main.itemLoadedMessage+nameSpacedID+main.itemLoadedMessageResume);
                }
                if (!isStartup && main.debug && !toDetectNewRewards.contains(nameSpacedID)){
                    plugin.logger.log(LogCauses.DEBUG, main.newItemMessage + nameSpacedID + main.newItemMessageResume);
                }
                if (contains(nameSpacedID)){
                    plugin.logger.log(LogCauses.ERROR, main.itemAlreadyDefinedErrorMessage+nameSpacedID+main.itemAlreadyDefinedErrorMessageResume);
                }

                this.addItem(nameSpacedID, new ItemStack(item));
            }





        }


    }



}

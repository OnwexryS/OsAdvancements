package com.minerival.develop.osadvancements;

import com.minerival.develop.osadvancements.LoggerTypes.LogCauses;
import com.minerival.develop.osadvancements.RewardTypes.CommandReward;
import com.minerival.develop.osadvancements.RewardTypes.ExperienceReward;
import com.minerival.develop.osadvancements.RewardTypes.ItemReward;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class AdvancementManager {

    public static Map<String, Advancement> advancements;
    private final main plugin;

    public AdvancementManager(main plugin){
        this.plugin = plugin;
        advancements = new HashMap<>();
    }

    public void clearAdvancements(){
        advancements = new HashMap<>();
    }
    public void addAdvancement(String id, Advancement advancement){
        this.advancements.put(id, advancement);
    }


    public Advancement getAdvancement(String key){
        if (advancements.containsKey(key)){
            return advancements.get(key);
        }
        return null;
    }
    public Advancement getFromNameSpacedID(String nameSpacedID){
        for(Advancement adv : advancements.values()){
            if (nameSpacedID.equals(adv.nameSpacedID)){
                return adv;
            }
        }
        return null;
    }

    public boolean containsKey(String key){
        return this.advancements.containsKey(key);
    }

    public void loadAdvancements(){
        Boolean isStartup = advancements.isEmpty();
        Set<String> toDetectNewAdvancements = advancements.keySet();
        clearAdvancements();

        Set<String> readedAdvancements = null;
        FileConfiguration config = plugin.getConfig();
        readedAdvancements = config.getConfigurationSection("advancements").getKeys(false);

        /*ItemStack it = new ItemStack(Material.DIAMOND, 5);
        ItemMeta itm = it.getItemMeta();
        itm.setDisplayName("asdsa");
        itm.setUnbreakable(true);
        itm.setCustomModelData(1);
        it.setItemMeta(itm);
        config.set("test", it);
        plugin.saveConfig();
        */

        for (String key : readedAdvancements) {


            Advancement newAdvancement = new Advancement();

            String nameSpacedID = config.getString("advancements" + "." + key + "." + "nameSpacedID");

            if (isStartup && main.debug){
                plugin.logger.log(LogCauses.DEBUG, "&b&l"+key + main.loadingAdvancementsMessage);
            }
            if (!isStartup && !toDetectNewAdvancements.contains(nameSpacedID) && main.debug){
                plugin.logger.log(LogCauses.DEBUG, main.newAdvancementMessage + key + main.newAdvancementMessageResume);
            }


            newAdvancement.setNameSpacedID(nameSpacedID);
            List<String> commands = config.getStringList("advancements" + "." + key + "." + "commands");
            for(String command : commands){
                newAdvancement.addReward(new CommandReward(command));
            }
            List<String> items = config.getStringList("advancements" + "." + key + "." + "items");
            for(String item : items){
                newAdvancement.addReward(new ItemReward(plugin.rewards.getItem(item)));
            }

            int experience = config.getInt("advancements" + "." + key + "." + "experience");
            if (experience != 0){
                newAdvancement.addReward(new ExperienceReward(experience));
            }


            advancements.put(nameSpacedID, newAdvancement);
        }

    }




}

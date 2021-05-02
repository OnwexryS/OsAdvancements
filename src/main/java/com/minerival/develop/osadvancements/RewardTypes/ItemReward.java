package com.minerival.develop.osadvancements.RewardTypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class ItemReward implements Reward {
    ItemStack item;
    public ItemReward(){
        this.item = new ItemStack(Material.STONE);
    }
    public ItemReward(ItemStack item){
        this.item = item;
    }
    @Override
    public void giveReward(Player p) {

        PlayerInventory inventory = p.getInventory();
        HashMap<Integer, ItemStack> excess = inventory.addItem(item);
        for (Map.Entry<Integer, ItemStack> me : excess.entrySet()) {
            p.getWorld().dropItem(p.getLocation(), me.getValue());
        }
    }
    @Override
    public String toString(){
        int amount = item.getAmount();
        if(item.hasItemMeta()){
            if(item.getItemMeta().hasDisplayName()){
                return amount + "x "+item.getItemMeta().getDisplayName();
            }
        }
        return "Item Reward: "+ amount + "x "+item.getType().toString();
    }
}

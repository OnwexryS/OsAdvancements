package com.minerival.develop.osadvancements.RewardTypes;

import org.bukkit.entity.Player;

public interface Reward {
    public void giveReward(Player p);
    public String toString();
}

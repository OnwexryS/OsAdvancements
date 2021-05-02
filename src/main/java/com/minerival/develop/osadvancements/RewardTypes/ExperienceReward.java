package com.minerival.develop.osadvancements.RewardTypes;

import org.bukkit.entity.Player;

public class ExperienceReward implements Reward {

    int reward;
    public ExperienceReward(){
        this.reward = 0;
    }
    public ExperienceReward(int reward){
        this.reward = reward;
    }
    @Override
    public void giveReward(Player p) {
        p.giveExp(reward);
    }

    @Override
    public String toString(){
        return "Experience reward: "+reward+" experience";
    }
}

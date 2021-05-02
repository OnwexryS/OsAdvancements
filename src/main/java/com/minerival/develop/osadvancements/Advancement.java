package com.minerival.develop.osadvancements;

import com.minerival.develop.osadvancements.RewardTypes.Reward;
import org.bukkit.entity.Player;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class Advancement {
    public String getNameSpacedID() {
        return nameSpacedID;
    }

    public void setNameSpacedID(String nameSpacedID) {
        this.nameSpacedID = nameSpacedID;
    }

    public String nameSpacedID;
    ArrayList<Reward> rewards = new ArrayList<>();

    public Advancement(){
        this.nameSpacedID = "none";
    }
    public Advancement(String nameSpacedID){
        this.nameSpacedID = nameSpacedID;
    }
    public Advancement(String nameSpacedID, ArrayList<Reward> rewards){
        this.nameSpacedID = nameSpacedID;
        this.rewards = rewards;
    }

    public void addReward(Reward reward){
        this.rewards.add(reward);
    }
    public void addRewards(ArrayList<Reward> rewards){
        for (Reward r : rewards){
            addReward(r);
        }
    }
    public void setReward(Reward reward){
        this.rewards.clear();
        this.rewards.add(reward);
    }

    public void giveRewards(Player p){
        for(Reward r : rewards){
            r.giveReward(p);
        }
    }
    public ArrayList<Reward> getRewards(){
        return this.rewards;
    }
}

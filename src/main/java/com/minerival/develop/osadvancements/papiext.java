package com.minerival.develop.osadvancements;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class papiext extends PlaceholderExpansion{
    private final String VERSION = "1.1";
    @Override
    public String getIdentifier() {
        return "osadvancements";
    }

    @Override
    public String getAuthor() {
        return "OnwexryS";
    }

    @Override
    public String getVersion() {
        return this.VERSION;
    }

    public String onRequest(OfflinePlayer player, String identifier) {
        switch (identifier) {
            case "totalAdvancements":
                return Integer.toString(main.totalAdvancements);
            default:
                return "";
        }
    }
}

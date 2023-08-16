package net.neppixel.spigot.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TimeEmoji extends PlaceholderExpansion {
    public TimeEmoji() {
    }

    public boolean canRegister() {
        return true;
    }

    public String getAuthor() {
        return "birajrai";
    }

    public String getIdentifier() {
        return "time-emoji";
    }

    public String getVersion() {
        return "1.0";
    }

    public String onRequest(OfflinePlayer player, String identifier) {
        if (player.isOnline()) {
            Player p = player.getPlayer();
            if (identifier.equals("emoji")) {
                long time = p.getWorld().getTime();
                return time >= 12300L && time <= 23850L ? ChatColor.AQUA + "☾" : ChatColor.YELLOW + "☀";
            } else {
                return null;
            }
        } else {
            return "No Player";
        }
    }
}

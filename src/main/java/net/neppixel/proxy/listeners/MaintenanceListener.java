package net.neppixel.proxy.listeners;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.neppixel.proxy.Main;

@RequiredArgsConstructor
public class MaintenanceListener implements Listener {

    private final Main plugin;

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();

        if (plugin.getMaintenance().get().getBoolean("maintenance")) {
            response.setVersion(new ServerPing.Protocol(ChatColor.RED + "Maintenance", 1));

            response.setPlayers(new ServerPing.Players(0, 0, new ServerPing.PlayerInfo[]{
                    new ServerPing.PlayerInfo(ChatColor.YELLOW + "Follow " + ChatColor.GOLD + "@misterbiraj" + ChatColor.YELLOW + " on Twitter for updates!", "")
            }));
        }
    }
}

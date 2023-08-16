package net.neppixel.proxy.listeners;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.neppixel.proxy.Main;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class MOTDListener implements Listener {

    private final Main plugin;

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();

        response.setDescriptionComponent(parseMotd());
        event.setResponse(response);
    }

    private TextComponent parseMotd() {
        Configuration config = plugin.getMotd().get();
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        List<String> motds = config.getStringList("motds");

        // Get the actual MOTD
        String str = motds.get(tlr.nextInt(0, motds.size()));
        // Parsing variables/placeholders
        str = str.replace("\\n", "\n");
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', str));
    }
}

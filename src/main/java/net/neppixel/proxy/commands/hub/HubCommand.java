package net.neppixel.proxy.commands.hub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (!player.getServer().getInfo().getName().equals("hub")) {
                player.sendMessage(new ComponentBuilder("Sending you to the hub!").color(ChatColor.GREEN).create());

                ServerInfo target = ProxyServer.getInstance().getServerInfo("hub");

                player.connect(target);
            } else {
                player.sendMessage(new ComponentBuilder("You are already connected to the hub!").color(ChatColor.RED).create());
            }
        } else {
            sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.RED).create());
        }
    }
}

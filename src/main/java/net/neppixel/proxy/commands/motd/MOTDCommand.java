package net.neppixel.proxy.commands.motd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.neppixel.proxy.Main;

public class MOTDCommand extends Command {

    private final Main plugin;

    public MOTDCommand(Main plugin) {
        super("motd", "bungeecore.admin");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "MOTD");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_GRAY + "▸ " + ChatColor.YELLOW + "/motd reload " + ChatColor.GRAY + "Reload the config");
            sender.sendMessage("");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getMotd().reload();
                sender.sendMessage(ChatColor.GREEN + "The config has been reloaded successfully!");
                break;
        }
    }
}

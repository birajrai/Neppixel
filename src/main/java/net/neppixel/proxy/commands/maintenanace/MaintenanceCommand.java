package net.neppixel.proxy.commands.maintenanace;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.neppixel.proxy.Main;

public class MaintenanceCommand extends Command {

    private final Main plugin;

    public MaintenanceCommand(Main plugin) {
        super("maintenance", "bungeecore.admin");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "Maintenance");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_GRAY + "▸ " + ChatColor.YELLOW + "/maintenance reload " + ChatColor.GRAY + "Reload the config");
            sender.sendMessage("");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getMaintenance().reload();
                sender.sendMessage(ChatColor.GREEN + "The config has been reloaded successfully!");
                break;
        }
    }
}

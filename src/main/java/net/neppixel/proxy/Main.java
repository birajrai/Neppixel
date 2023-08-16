package net.neppixel.proxy;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.neppixel.proxy.commands.hub.HubCommand;
import net.neppixel.proxy.commands.maintenanace.MaintenanceCommand;
import net.neppixel.proxy.commands.motd.MOTDCommand;
import net.neppixel.proxy.listeners.MOTDListener;
import net.neppixel.proxy.listeners.MaintenanceListener;
import net.neppixel.proxy.utils.ConfigHelper;

@Getter
@Setter
public class Main extends Plugin {

    // Maintenance
    @Getter
    public static Boolean serverInMaintenance;
    @Getter
    private static Main plugin;
    // Config's
    private ConfigHelper motd;
    private ConfigHelper maintenance;

    @Override
    public void onEnable() {
        plugin = this;

        /**
         * Plugin manager for register everything
         */
        PluginManager pluginManager = getProxy().getPluginManager();

        /**
         * Register the Configs
         */
        motd = new ConfigHelper(this, "motd.yml", true);
        maintenance = new ConfigHelper(this, "maintenance.yml", true);

        /**
         * Register the listeners
         */
        pluginManager.registerListener(this, new MaintenanceListener(this));
        pluginManager.registerListener(this, new MOTDListener(this));

        /**
         * Register the commands
         */
        pluginManager.registerCommand(this, new MaintenanceCommand(this));
        pluginManager.registerCommand(this, new MOTDCommand(this));
        pluginManager.registerCommand(this, new HubCommand());
    }

    @Override
    public void onDisable() {
        /**
         * Save the config
         */
        motd.save();
        maintenance.save();
    }
}

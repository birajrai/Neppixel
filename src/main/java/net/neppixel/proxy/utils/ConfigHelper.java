package net.neppixel.proxy.utils;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class ConfigHelper {

    private static final ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private final Plugin plugin;
    private final File cfgFile;
    private Configuration cfg;

    /**
     * @param filename Name of file
     * @param copy     Should we copy this resource from the jar?
     */
    public ConfigHelper(Plugin plugin, String filename, boolean copy) {
        if (filename == null || filename.isEmpty()) {
            throw new RuntimeException("Filename was either null or empty.");
        }
        if (plugin == null) {
            throw new RuntimeException("Plugin was null.");
        }
        this.plugin = plugin;
        if (copy) {
            saveResource(filename);
        }
        try {
            this.cfgFile = new File(plugin.getDataFolder(), filename);
            this.cfg = cfgProvider.load(this.cfgFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + filename, e);
        }
    }

    /**
     * @return Configuration or null if the configuration can't be loaded
     */
    public Configuration get() {
        if (cfg != null) {
            return cfg;
        } else {
            return null;
        }
    }

    /**
     * Save config, from memory to hard-file.
     */
    public void save() {
        try {
            cfgProvider.save(cfg, cfgFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save " + cfgFile.getName(), e);
        }
    }

    /**
     * Delete config from memory, then load hard-file into memory.
     */
    public void reload() {
        try {
            cfg = cfgProvider.load(cfgFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + cfgFile.getName());
        }
    }

    /**
     * Copies file from jar, into the data folder.
     *
     * @param filename String name of file
     */
    private void saveResource(String filename) {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir()) {
                throw new RuntimeException("Unable to create data folder.");
            }
        }
        File configFile = new File(plugin.getDataFolder(), filename);
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile()) {
                    throw new IOException("File already exists.");
                }
                try (InputStream is = plugin.getResourceAsStream(filename);
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                    is.close();
                    os.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }
}

package net.neppixel.spigot;

import net.md_5.bungee.api.ChatColor;
import net.neppixel.spigot.placeholders.TimeEmoji;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends JavaPlugin {
    public static String prefix = "[Neppixel] ";
    ArrayList<String> list = new ArrayList();
    ArrayList<String> worlds = new ArrayList();

    private int range;
    private String msg;


    @Override
    public void onEnable() {


        //  Placeholders
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + prefix + "loading placeholders..");
        (new TimeEmoji()).register();

        //  Config
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + prefix + "loading configs..");
        this.loadConfig();

        //  Data
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + prefix + "loading data..");
        this.loadData();

        //  Commands
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + prefix + "loading commands..");

        //  Enabled
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + prefix + "is enabled");
        super.onEnable();
    }

    private void loadData() {
        this.list.clear();
        this.worlds.clear();
        Iterator var2 = this.getConfig().getStringList("Dont-Merge").iterator();

        String s;
        while (var2.hasNext()) {
            s = (String) var2.next();

            try {
                this.list.add(s.toUpperCase());
            } catch (NullPointerException var5) {
                System.out.println("error loading " + s);
            }
        }

        var2 = this.getConfig().getStringList("Denied-Worlds").iterator();
        while (var2.hasNext()) {
            s = (String) var2.next();

            try {
                this.worlds.add(s);
            } catch (NullPointerException var4) {
                System.out.println("error loading " + s);
            }
        }

        this.range = this.getConfig().getInt("Range");
        this.msg = this.getConfig().getString("No-Permission");
    }

    private void loadConfig() {

        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }

            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                this.getLogger().info("config.yml not found, creating one for you <3");
                this.saveDefaultConfig();
            } else {
                this.getLogger().info("confing.yml found, loading it UwU");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + prefix + "is disabled");
        super.onDisable();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String a, String[] args) {
        if (cmd.getName().equalsIgnoreCase("itemstacker")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length != 1) {
                    if (p.hasPermission("is.view")) {
                        p.sendMessage("ItemStacker in this server seems SUS 0-o");
                        p.sendMessage("/itemstacker reload - Reloads Configuration");
                        return true;
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.msg));
                    return true;
                }
                if (!p.hasPermission("is.reload")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.msg));
                    return true;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    this.reloadConfig();
                    this.loadData();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.prefix + "configuration file successfully reloaded!"));
                    return true;
                }
            } else {
                if (args.length != 1) {
                    Bukkit.getConsoleSender().sendMessage("Neppixel: ItemStacker sounds hot in your sender <3");
                    Bukkit.getConsoleSender().sendMessage("/itemstacker reload - Reloads Configuration");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    this.reloadConfig();
                    this.loadData();
                    Bukkit.getConsoleSender().sendMessage("ItemStacker Configuration Reloaded!");
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onEvent(InventoryPickupItemEvent e) {
        if (!(!this.worlds.contains(e.getItem().getLocation().getWorld().getName()) | e.getInventory().getType() != InventoryType.HOPPER)) {
            int amt = e.getItem().getItemStack().getAmount();
            ItemStack titem = e.getItem().getItemStack();
            if (amt >= 65) {
                int a = 0;

                int fun;
                for (fun = 0; fun < e.getInventory().getSize(); ++fun) {
                    ItemStack item = e.getInventory().getItem(fun);
                    if (item == null) {
                        fun = e.getItem().getItemStack().getAmount();
                        titem.setAmount(64);
                        e.getInventory().addItem(titem);
                        e.getItem().getItemStack().setAmount(fun - 64);
                        e.setCancelled(true);
                        return;
                    }

                    if (item.getType() == titem.getType() && item.getItemMeta() == titem.getItemMeta()) {
                        a += item.getAmount();
                    }
                }

                if (a < 64) {
                    fun = e.getItem().getItemStack().getAmount();
                    titem.setAmount(a);
                    e.getInventory().addItem(titem);
                    e.getItem().getItemStack().setAmount(fun - a);
                } else {
                    fun = e.getItem().getItemStack().getAmount();
                    titem.setAmount(64);
                    e.getInventory().addItem(titem);
                    e.getItem().getItemStack().setAmount(fun - 64);
                }

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEvent(ItemSpawnEvent e) {
        if (this.worlds.contains(e.getEntity().getWorld().getName())) {
            if (!this.list.contains(e.getEntity().getItemStack().getType().toString())) {
                Iterator var3 = e.getEntity().getNearbyEntities(this.range, this.range, this.range).iterator();

                while (var3.hasNext()) {
                    Entity ent = (Entity) var3.next();
                    if (ent.getType() == EntityType.DROPPED_ITEM) {
                        ItemStack a = ((Item) ent).getItemStack();
                        ItemStack b = e.getEntity().getItemStack();
                        if (a.getType() == b.getType() && a.getItemMeta().getLore() == b.getItemMeta().getLore() && a.getEnchantments() == b.getEnchantments() && a.getItemMeta().getDisplayName() == b.getItemMeta().getDisplayName()) {
                            ((Item) ent).getItemStack().setAmount(((Item) ent).getItemStack().getAmount() + e.getEntity().getItemStack().getAmount());
                            e.setCancelled(true);
                            break;
                        }
                    }
                }
            }

        }
    }


}

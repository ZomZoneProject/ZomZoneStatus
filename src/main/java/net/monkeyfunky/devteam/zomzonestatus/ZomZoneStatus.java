package net.monkeyfunky.devteam.zomzonestatus;

import dev.m1n1don.simplesql.db.Database;
import dev.m1n1don.simplesql.sqlite.SQLite;
import net.monkeyfunky.devteam.zomzonestatus.db.SQLQuery;
import net.monkeyfunky.devteam.zomzonestatus.db.StatusAPI;
import net.monkeyfunky.devteam.zomzonestatus.listeners.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZomZoneStatus extends JavaPlugin {
    private static ZomZoneStatus plugin;
    private static Database database;

    public ZomZoneStatus() {
        plugin = this;
        database = new Database(this, new SQLite("status.db", getDataFolder() + "/database/"));
        database.setup();
        database.executeStatement(SQLQuery.CREATE_TABLE_STATUS);
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.shutdown();
    }
    public static ZomZoneStatus getPlugin()
    {
        return plugin;
    }

    public static Database getDatabase()
    {
        return database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("show")) {
            StatusAPI statusAPI = new StatusAPI();
            sender.sendMessage(ChatColor.AQUA + sender.getName() + statusAPI.getStatus(player).toString());
        }
        if(cmd.getName().equalsIgnoreCase("set")){
            if(args.length != 2) return false; // example: /set bow 2
            StatusAPI statusAPI = new StatusAPI();
            Status status = statusAPI.getStatus(player);
            status.setStatus(StatusTypes.valueOf(args[0]), Integer.parseInt(args[1]));
            statusAPI.setStatus(player, status);
        }
        return true;
    }
}
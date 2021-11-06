package net.monkeyfunky.devteam.zomzonestatus.listeners;

import net.monkeyfunky.devteam.zomzonestatus.Status;
import net.monkeyfunky.devteam.zomzonestatus.db.StatusAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        StatusAPI statusAPI = new StatusAPI();
        if(statusAPI.existsStatus(event.getPlayer())) return;
        statusAPI.setStatus(event.getPlayer(), new Status()); // Statusのデフォルトの値は0, 0, 0, 0...なので問題ナシ.
    }
}

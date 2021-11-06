package net.monkeyfunky.devteam.zomzonestatus.db;

import net.monkeyfunky.devteam.zomzonestatus.Status;
import org.bukkit.entity.Player;

public interface API {
    Status getStatus(Player player);
    void setStatus(Player player, Status status);
}

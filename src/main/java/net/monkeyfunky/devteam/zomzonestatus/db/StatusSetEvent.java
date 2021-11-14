package net.monkeyfunky.devteam.zomzonestatus.db;

import net.monkeyfunky.devteam.zomzonestatus.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatusSetEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Status status;
    private final Status oldStatus;

    public StatusSetEvent(Status status, Status oldStatus, Player player) {
        this.player = player;
        this.status = status;
        this.oldStatus = oldStatus;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Status getStatus() {
        return status;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Status getOldStatus() {
        return oldStatus;
    }
}

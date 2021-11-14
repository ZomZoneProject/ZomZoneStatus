package net.monkeyfunky.devteam.zomzonestatus.db;

import dev.m1n1don.simplesql.db.Database;
import dev.m1n1don.simplesql.sqlite.SQLite;
import net.monkeyfunky.devteam.zomzonestatus.Status;
import net.monkeyfunky.devteam.zomzonestatus.StatusTypes;
import net.monkeyfunky.devteam.zomzonestatus.ZomZoneStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusAPI implements API {
    /**
     *
     * @param player
     * @return Return the player's Status
     */
    @Override
    public Status getStatus(Player player) {
        Database database;
        database = new Database(ZomZoneStatus.getPlugin(), new SQLite("status.db", Bukkit.getPluginManager().getPlugin("ZomZoneStatus").getDataFolder().toString() + "/database/"));
        database.setup();
        database.executeStatement(SQLQuery.CREATE_TABLE_STATUS);
        ResultSet rs = database.executeResultStatement(SQLQuery.SELECT_STATUS, player.getUniqueId().toString());
        try {
            rs.last();
            if (rs.getRow() == 0) {
                setStatus(player, new Status());
                return getStatus(player);
            }
            rs.first();
            Status status = new Status();
            for (StatusTypes statusType : StatusTypes.values()) {
                status.setStatus(statusType, rs.getInt(statusType.getName()));
            }
            return status;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Status();
    }

    /**
     *
     * Set the player status to Database(status.db).
     * @param player
     * @param status
     */
    @Override
    public void setStatus(Player player, Status status) {
        Database database;
        database = new Database(ZomZoneStatus.getPlugin(), new SQLite("status.db", Bukkit.getPluginManager().getPlugin("ZomZoneStatus").getDataFolder().toString() + "/database/"));
        database.setup();
        database.executeStatement(SQLQuery.CREATE_TABLE_STATUS);
        if (existsStatus(player)) {
            database.executeStatement(SQLQuery.UPDATE_STATUS, status.getStatus(StatusTypes.BOW), status.getStatus(StatusTypes.GUN), status.getStatus(StatusTypes.SWORD),
                    status.getStatus(StatusTypes.SHIELD), status.getStatus(StatusTypes.HEALTH), status.getStatus(StatusTypes.POINT), player.getUniqueId().toString());
        } else {
            database.executeStatement(SQLQuery.INSERT_STATUS, player.getUniqueId().toString(), status.getStatus(StatusTypes.BOW), status.getStatus(StatusTypes.GUN), status.getStatus(StatusTypes.SWORD),
                    status.getStatus(StatusTypes.SHIELD), status.getStatus(StatusTypes.HEALTH), status.getStatus(StatusTypes.POINT));
        }
        Bukkit.getPluginManager().callEvent(new StatusSetEvent(status, getStatus(player), player));
    }

    /**
     *
     * @param player
     * @return Return is the player exits on Database.
     */
    public boolean existsStatus(Player player) {
        try {
            Database database;
            database = new Database(ZomZoneStatus.getPlugin(), new SQLite("status.db", Bukkit.getPluginManager().getPlugin("ZomZoneStatus").getDataFolder().toString() + "/database/"));
            database.setup();
            database.executeStatement(SQLQuery.CREATE_TABLE_STATUS);
            ResultSet rs = database.executeResultStatement(SQLQuery.SELECT_STATUS, player.getUniqueId().toString());
            rs.last();
            int count = rs.getRow();
            rs.beforeFirst();
            return count != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}

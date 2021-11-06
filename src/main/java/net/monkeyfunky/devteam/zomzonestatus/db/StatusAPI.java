package net.monkeyfunky.devteam.zomzonestatus.db;

import net.monkeyfunky.devteam.zomzonestatus.Status;
import net.monkeyfunky.devteam.zomzonestatus.StatusTypes;
import net.monkeyfunky.devteam.zomzonestatus.ZomZoneStatus;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusAPI implements API {
    @Override
    public Status getStatus(Player player) {
        ResultSet rs = ZomZoneStatus.getDatabase().executeResultStatement(SQLQuery.SELECT_STATUS, player.getUniqueId().toString());
        try {
            rs.last();
            if (rs.getRow() == 0) return null;// まだステータスが存在しない。
            rs.first();
            Status status = new Status();
            for (StatusTypes statusType : StatusTypes.values()) {
                status.setStatus(statusType, rs.getInt(statusType.getName()));
            }
            return status;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert false;
        return null;
    }

    @Override
    public void setStatus(Player player, Status status) {
        if (existsStatus(player)) {
            ZomZoneStatus.getPlugin().getDatabase().executeStatement(SQLQuery.UPDATE_STATUS, status.getStatus(StatusTypes.BOW), status.getStatus(StatusTypes.GUN), status.getStatus(StatusTypes.SWORD),
                    status.getStatus(StatusTypes.SHIELD), status.getStatus(StatusTypes.HEALTH), player.getUniqueId().toString());
        } else {
            ZomZoneStatus.getPlugin().getDatabase().executeStatement(SQLQuery.INSERT_STATUS, player.getUniqueId().toString(), status.getStatus(StatusTypes.BOW), status.getStatus(StatusTypes.GUN), status.getStatus(StatusTypes.SWORD),
                    status.getStatus(StatusTypes.SHIELD), status.getStatus(StatusTypes.HEALTH));
        }
    }
    public boolean existsStatus(Player player) {
        try {
            ResultSet rs = ZomZoneStatus.getPlugin().getDatabase().executeResultStatement(SQLQuery.SELECT_STATUS, player.getUniqueId().toString());
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

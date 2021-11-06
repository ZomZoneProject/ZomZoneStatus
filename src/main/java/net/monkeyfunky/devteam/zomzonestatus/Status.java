package net.monkeyfunky.devteam.zomzonestatus;

import java.util.HashMap;

public class Status {
    HashMap<StatusTypes, Integer> status = new HashMap<>();

    public Status() {
        for (StatusTypes statusType : StatusTypes.values()) {
            status.put(statusType, 0);
        }
    }

    public void setStatus(StatusTypes statusType, Integer newStatus) {
        status.put(statusType, newStatus);
    }

    public int getStatus(StatusTypes statusType) {
        return status.get(statusType);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Integer statusValue : status.values()) {
            ret.append(statusValue.toString());
            ret.append("|");
        }
        return ret.toString();
    }
}

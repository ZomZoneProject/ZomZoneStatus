package net.monkeyfunky.devteam.zomzonestatus.db;

import dev.m1n1don.simplesql.db.IQuery;

public enum SQLQuery implements IQuery {
    CREATE_TABLE_STATUS(
            "CREATE TABLE IF NOT EXISTS Status (" +
                    "uuid TEXT PRIMARY KEY," +
                    "bow INTEGER," +
                    "gun INTEGER," +
                    "sword INTEGER," +
                    "shield INTEGER," +
                    "health INTEGER, " +
                    "point INTEGER)"
    ),
    INSERT_STATUS(
            "INSERT INTO Status " +
                    "(uuid, bow, gun, sword, shield, health, point) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
    ),
    UPDATE_STATUS(
            "UPDATE Status SET " +
                    "bow = ?, gun = ?, sword = ?, shield = ?, health = ?, point = ? WHERE uuid = ?"
    ),
    SELECT_STATUS(
            "SELECT * FROM Status WHERE uuid = ?"
    );

    private final String sql;

    SQLQuery(String sql)
    {
        this.sql = sql;
    }

    @Override
    public String toString()
    {
        return sql;
    }
}

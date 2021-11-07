package net.monkeyfunky.devteam.zomzonestatus;

public enum StatusTypes {
    BOW("bow"),
    GUN("gun"),
    SWORD("sword"),
    SHIELD("shield"),
    HEALTH("health"),
    POINT("point");

    private final String name;

    private StatusTypes(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

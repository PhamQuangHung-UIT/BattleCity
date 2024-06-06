package com.uit.battlecity.enums;

public enum PowerUpType {
    GRENADE, SHOVEL, LIVE, TIME_STOP, SHIELD, UPGRADE;

    public static PowerUpType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}

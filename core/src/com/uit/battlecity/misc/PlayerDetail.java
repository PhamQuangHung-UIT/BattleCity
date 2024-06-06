package com.uit.battlecity.misc;

public class PlayerDetail {
    public int level;
    public int remainingLives;

    public PlayerDetail(int level, int remainingLives) {
        this.level = level;
        this.remainingLives = remainingLives;
    }

    public PlayerDetail() {
        this(0, 2);
    }
}

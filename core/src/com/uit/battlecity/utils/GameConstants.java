package com.uit.battlecity.utils;

public class GameConstants {
    public static final float SCALE = 3;
    public static final int VIEWPORT_WIDTH = (int) (256 * SCALE);
    public static final int VIEWPORT_HEIGHT = (int) (224 * SCALE);
    public static final float TANK_ANIM_DURATION = 1 / 16f;
    public static final float SHIELD_ANIM_DURATION = 1 / 16f;
    public static final float EXPlODE_DURATION = 0.15f;
    public static final float DESTROY_ANIM_DURATION = 0.3f;
    public static final float PLAYER_SPEED = 48 * SCALE;
    public static final float TANK_SPEED_DEFAULT = 32 * SCALE;
    public static final float BULLET_SPEED = 96 * SCALE;
    public static final Point BASE_POSITION = new Point(120 * SCALE, 16 * SCALE);
    public static final Point FIRST_PLAYER_POS = new Point(80 * SCALE, 8 * SCALE);
    public static final Point SECOND_PLAYER_POS = new Point(144 * SCALE, 8 * SCALE);
    public static final float SPAWN_INTERVAL = 4.0f;
    public static final int MAX_ENEMY_ON_FIELD = 4;
    public static final int POWER_UP_APPEAR_TIME = 15;
    public static final float POWER_UP_DROP_CHANCE = 0.15f;
    public static final float MAX_SHIELD_TIME = 8;
    public static final int MAX_AVAILABLE_LEVEL = 4;
    public static final boolean DEBUG_MODE = false;
}
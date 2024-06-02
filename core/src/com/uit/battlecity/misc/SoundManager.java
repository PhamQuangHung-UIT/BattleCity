package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    public static final Sound START_LEVEL = Gdx.audio.newSound(Gdx.files.internal("sfx/level_start.ogg"));
    public static final Sound BASE_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sfx/base_explosion.ogg"));
    public static final Sound BONUS = Gdx.audio.newSound(Gdx.files.internal("sfx/bonus.ogg"));
    public static final Sound BRICK_HIT = Gdx.audio.newSound(Gdx.files.internal("sfx/brick_hit.ogg"));
    public static final Sound GAME_OVER = Gdx.audio.newSound(Gdx.files.internal("sfx/game_over.ogg"));
    public static final Sound LIFE = Gdx.audio.newSound(Gdx.files.internal("sfx/life.ogg"));
    public static final Sound MOVING = Gdx.audio.newSound(Gdx.files.internal("sfx/moving.ogg"));
    public static final Sound N_MOVING = Gdx.audio.newSound(Gdx.files.internal("sfx/n_moving.ogg"));
    public static final Sound PAUSE = Gdx.audio.newSound(Gdx.files.internal("sfx/pause.ogg"));
    public static final Sound SHIELD_HIT = Gdx.audio.newSound(Gdx.files.internal("sfx/shield_hit.ogg"));
    public static final Sound STEEL_HIT = Gdx.audio.newSound(Gdx.files.internal("sfx/steel_hit.ogg"));
    public static final Sound SHOOT = Gdx.audio.newSound(Gdx.files.internal("sfx/shoot.ogg"));
    public static final Sound TANK_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sfx/tank_explosion.ogg"));
}

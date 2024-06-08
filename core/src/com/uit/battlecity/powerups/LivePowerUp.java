package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.SoundManager;


public class LivePowerUp extends PowerUp {
    public final Texture texture = new Texture("miscellaneous/power_up/live.png");

    public LivePowerUp(Point position) {
        create(position, texture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.LIFE.play();
        ((LevelStage) getStage()).addLife(player);
    }
}

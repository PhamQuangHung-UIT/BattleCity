package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.tank.Tank;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.SoundManager;


public class GrenadePowerUp extends PowerUp {
    public final Texture grenadeTexture = new Texture("miscellaneous/power_up/grenade.png");

    public GrenadePowerUp(Point position) {
        create(position, grenadeTexture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.BONUS.play();
        for (Tank enemy : ((LevelStage) getStage()).getEnemyGroup().getChildren().toArray(Tank.class)) {
            if (enemy.getStage() != null)
                enemy.getHit(999);
        }
    }
}

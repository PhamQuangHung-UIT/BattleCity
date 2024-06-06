package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.SoundManager;


public class UpgradePowerUp extends PowerUp {
    public final Texture grenadeTexture = new Texture("miscellaneous/power_up/upgrade.png");

    public UpgradePowerUp(Point position) {
        create(position, grenadeTexture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.BONUS.play();
        player.upgrade();
    }
}

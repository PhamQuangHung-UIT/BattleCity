package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.MAX_SHIELD_TIME;


public class ShieldPowerUp extends PowerUp {
    public final Texture grenadeTexture = new Texture("miscellaneous/power_up/shield.png");

    public ShieldPowerUp(Point position) {
        create(position, grenadeTexture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.BONUS.play();
        player.startShield(MAX_SHIELD_TIME);
    }
}

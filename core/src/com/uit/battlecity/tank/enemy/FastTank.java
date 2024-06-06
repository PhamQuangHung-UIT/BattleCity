package com.uit.battlecity.tank.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.interfaces.EnemyListener;
import com.uit.battlecity.tank.Enemy;
import com.uit.battlecity.utils.GameConstants;

public class FastTank extends Enemy {

    public FastTank(EnemyListener listener, Direction startDir, float posX, float posY) {
        super(listener, Array.with(
                new Sprite(new Texture("tanks/tank_white_fast_up_0.png")),
                new Sprite(new Texture("tanks/tank_white_fast_up_1.png"))
        ), 200, 1, startDir, posX, posY, GameConstants.TANK_SPEED_DEFAULT * 2);
    }
}

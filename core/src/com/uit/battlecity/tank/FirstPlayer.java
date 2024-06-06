package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;

public class FirstPlayer extends Player {
    static final Array<Array<Sprite>> ANIMATION_ARR = Array.with(
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_normal_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_normal_up_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_upgraded_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_upgraded_up_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_advanced_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_advanced_up_1.png"))));
    ;

    public FirstPlayer(Direction startDir, int tankLevel, float posX, float posY, float speed) {
        super(startDir, ANIMATION_ARR, tankLevel, posX, posY, speed);
    }
}

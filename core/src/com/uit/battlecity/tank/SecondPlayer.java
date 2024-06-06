package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;

public class SecondPlayer extends Player {
    static final Array<Array<Sprite>> ANIMATION_ARR = Array.with(
            Array.with(
                    new Sprite(new Texture("tanks/tank_red_normal_0.png")),
                    new Sprite(new Texture("tanks/tank_red_normal_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_red_upgraded_0.png")),
                    new Sprite(new Texture("tanks/tank_red_upgraded_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_red_advanced_0.png")),
                    new Sprite(new Texture("tanks/tank_red_advanced_1.png"))));
    ;

    public SecondPlayer(Direction startDir, int tankLevel, float posX, float posY, float speed) {
        super(startDir, ANIMATION_ARR, tankLevel, posX, posY, speed);
    }
}

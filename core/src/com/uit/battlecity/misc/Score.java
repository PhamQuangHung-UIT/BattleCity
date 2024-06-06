package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;

public class Score extends Actor {
    public static final Texture SCORE_100 = new Texture("ui/scores/point_100.png");
    public static final Texture SCORE_200 = new Texture("ui/scores/point_200.png");
    public static final Texture SCORE_300 = new Texture("ui/scores/point_300.png");
    public static final Texture SCORE_400 = new Texture("ui/scores/point_400.png");
    public static final Texture SCORE_500 = new Texture("ui/scores/point_500.png");
    private Sprite sprite;
    private float timer;

    public void initialize(int score, Point point) {
        timer = 0;
        switch (score) {
            case 100:
                sprite = new Sprite(SCORE_100);
                break;
            case 200:
                sprite = new Sprite(SCORE_200);
                break;
            case 300:
                sprite = new Sprite(SCORE_300);
                break;
            case 400:
                sprite = new Sprite(SCORE_400);
                break;
            default:
                sprite = new Sprite(SCORE_500);
                break;
        }
        sprite.setPosition(point.getX(), point.getY());
        sprite.setOrigin(0, 0);
        sprite.setScale(GameConstants.SCALE);
    }

    @Override
    public void act(float delta) {
        timer += delta;
        if (timer > 0.75f) {
            remove();
            Pools.free(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}

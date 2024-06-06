package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import com.uit.battlecity.interfaces.GameOverListener;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;

public class GameOverTitle {
    private static final Point position = new Point(Gdx.graphics.getWidth() / 2f, 0);
    private static final float floatingSpeed = 64 * GameConstants.SCALE;
    private final Sprite sprite;

    public GameOverTitle(GameOverListener listener) {
        sprite = new Sprite(new Texture("ui/game_over.png"));
        sprite.setScale(3);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                listener.onAnimationEnd();
            }
        }, 5);
    }

    public void draw(Batch batch, float delta) {
        if (position.getY() < Gdx.graphics.getHeight() / 2f) {
            position.setY(position.getY() + floatingSpeed * delta);
        } else position.setY(Gdx.graphics.getHeight() / 2f);
        batch.begin();
        sprite.setCenter(position.getX(), position.getY());
        sprite.draw(batch);
        batch.end();
    }
}

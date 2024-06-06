package com.uit.battlecity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.uit.battlecity.BattleCity;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class GameOverScreen implements Screen {
    private final Sprite gameOverSprite;
    private final SpriteBatch batch = new SpriteBatch();

    public GameOverScreen() {
        gameOverSprite = new Sprite(new Texture("ui/game_over_screen.png"));
        gameOverSprite.setScale(SCALE / 2);
        gameOverSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 60 - gameOverSprite.getHeight() * gameOverSprite.getScaleY() / 2);
    }

    @Override
    public void show() {
        SoundManager.GAME_OVER.play();
        Timer.schedule(new Task() {
            @Override
            public void run() {
                BattleCity.getInstance().setScreen(new TitleScreen(BattleCity.getInstance()));
                dispose();
            }
        }, 1.25f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        gameOverSprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameOverSprite.getTexture().dispose();
        batch.dispose();
    }
}

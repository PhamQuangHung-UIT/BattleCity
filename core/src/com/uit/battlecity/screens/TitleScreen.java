package com.uit.battlecity.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uit.battlecity.misc.PlayerDetail;
import com.uit.battlecity.utils.Point;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class TitleScreen implements Screen {

    private final Game game;
    private final SpriteBatch batch;
    private final Sprite startScreen;
    private boolean isTwoPlayer = false;
    private static final float floatingSpeed = 32 * SCALE;
    private float yPos = -Gdx.graphics.getHeight();
    private final Animation<Sprite> tankAnimation;
    private float time;
    private boolean animationEnd = false;

    public TitleScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        startScreen = new Sprite(new Texture("ui/start_screen.png"));
        startScreen.setScale(SCALE);
        tankAnimation = new Animation<>(0.075f, Array.with(
                new Sprite(new Texture("tanks/tank_yellow_normal_right_0.png")),
                new Sprite(new Texture("tanks/tank_yellow_normal_right_1.png"))));
        for (Sprite sprite : tankAnimation.getKeyFrames()) {
            sprite.setScale(SCALE);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        time += delta;
        if (!animationEnd) {
            yPos += delta * floatingSpeed;
            if (yPos > 0) {
                animationEnd = true;
            }
        } else {
            yPos = 0;
        }
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        startScreen.setCenter(Gdx.graphics.getWidth() / 2f, yPos + Gdx.graphics.getHeight() / 2f);
        startScreen.draw(batch);
        if (animationEnd) {
            Point pos;
            if (isTwoPlayer) {
                pos = new Point(72 * SCALE, 76 * SCALE);
            } else pos = new Point(72 * SCALE, 92 * SCALE);
            Sprite sprite = tankAnimation.getKeyFrame(time, true);
            sprite.setCenter(pos.getX(), pos.getY());
            sprite.draw(batch);
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            if (animationEnd) {
                // Go to the next screen
                game.setScreen(new LevelScreen(Array.with(new PlayerDetail(), new PlayerDetail()), 1, isTwoPlayer));
            } else {
                animationEnd = true;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            isTwoPlayer = !isTwoPlayer;
        }
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
        batch.dispose();
        startScreen.getTexture().dispose();
        for (Sprite sprite : tankAnimation.getKeyFrames()) {
            sprite.getTexture().dispose();
        }
    }
}

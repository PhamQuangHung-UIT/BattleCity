package com.uit.battlecity.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.uit.battlecity.screens.LevelScreen;

public class TitleScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture tankTexture;

    public TitleScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        tankTexture = new Texture(Gdx.files.internal("tank_yellow_advanced_right_1.png"));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        batch.begin();


        font.draw(batch, "BATTLE", 150, 300);
        font.draw(batch, "CITY", 150, 250);


        batch.draw(tankTexture, 120, 200);
        font.draw(batch, "1 PLAYER", 150, 220);


        font.draw(batch, "PRESS ENTER", 100, 150);

        batch.end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            // Go to the next screen
            game.setScreen(new LevelScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        tankTexture.dispose();
    }
}

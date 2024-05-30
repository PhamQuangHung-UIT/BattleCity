package com.uit.battlecity.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.tank.Tank;
import com.uit.battlecity.utils.Point;

public class LevelScreen implements Screen {
    final Point playerPos = new Point(104, 0);
    int level;

    private float time;

    Tank firstPlayerTank;

    boolean startLevelAnimationEnd = false;

    Stage stage;

    final Sound startLevelSound = Gdx.audio.newSound(Gdx.files.internal("sfx/level_start.mp3"));

    public LevelScreen(Game level) {
        this.level = level;

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 480);
        stage = new Stage(new ScreenViewport(camera));

        firstPlayerTank = new Player(this, playerPos.getX(), playerPos.getY(), 32);
        stage.addActor(firstPlayerTank);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }

    public float getTime() {
        return time;
    }
}

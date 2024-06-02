package com.uit.battlecity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uit.battlecity.misc.*;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;

public class LevelScreen implements Screen {
    static LevelScreen instance;
    final SpriteBatch batch = Pools.obtain(SpriteBatch.class);
    int level;
    boolean startLevelAnimationEnd = false;

    OrthographicCamera camera;

    Stage stage;

    LevelMap levelMap;

    LevelBoard levelBoard;

    OrthogonalTiledMapRenderer mapRenderer;

    private float time;

    public LevelScreen(int level, boolean isTwoPlayer) {
        this.level = level;
        instance = this;

        //Load the object pooling

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        //camera.position.set(levelMapPos.getX() + GameConstants.MAP_SIZE / 2, levelMapPos.getY() + GameConstants.MAP_SIZE / 2, 0);

        // Load the tile map and map renderer
        levelMap = new LevelMap(level);
        mapRenderer = new OrthogonalTiledMapRenderer(levelMap.getMap(), batch);
        mapRenderer.setView(camera);

        levelBoard = new LevelBoard(level, isTwoPlayer);

        stage = new LevelStage(isTwoPlayer, new ScreenViewport(camera), batch);
        Gdx.input.setInputProcessor(stage);
    }

    public static LevelScreen getInstance() {
        return instance;
    }

    @Override
    public void show() {
        SoundManager.START_LEVEL.play();
    }

    @Override
    public void render(float delta) {
        time += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);

        // Draw the level board, block and ice layers
        batch.begin();
        levelBoard.draw(batch);
//        mapRenderer.renderTileLayer((TiledMapTileLayer) levelMap.getMap().getLayers().get("block"));
//        mapRenderer.renderTileLayer((TiledMapTileLayer) levelMap.getMap().getLayers().get("ice"));
        batch.end();
        mapRenderer.render();

        // Draw stage and its actors
        stage.act(delta);
        stage.draw();

        // Draw the bush layer
        batch.begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) levelMap.getMap().getLayers().get("bush"));
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
        stage.dispose();
        levelBoard.dispose();
    }

    public float getTime() {
        return time;
    }

    public PointInt screenToCellPos(Point screenPos) {
        return new PointInt((int) (screenPos.getX() / 24),
                (int) (screenPos.getY() / 24));
    }

    public Point cellToScreenPos(PointInt cellPos) {
        return new Point(cellPos.getX() * 24,
                cellPos.getY() * 24);
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }
}

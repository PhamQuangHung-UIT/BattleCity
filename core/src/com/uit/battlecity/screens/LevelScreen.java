package com.uit.battlecity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uit.battlecity.BattleCity;
import com.uit.battlecity.misc.GameOverTitle;
import com.uit.battlecity.misc.LevelMap;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.misc.PlayerDetail;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class LevelScreen implements Screen {
    static LevelScreen instance;
    final SpriteBatch batch = Pools.obtain(SpriteBatch.class);
    int level;
    boolean startLevelAnimationEnd = false;

    OrthographicCamera camera;

    Stage stage;

    LevelMap levelMap;

    OrthogonalTiledMapRenderer mapRenderer;
    private GameOverTitle gameOverTitle;
    private boolean isPaused = false;
    private Sprite pauseSprite = new Sprite(new Texture("ui/pause.png"));

    public LevelScreen(Array<PlayerDetail> players, int level, boolean isTwoPlayer) {
        this.level = level;
        instance = this;

        //Load the object pooling

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        //camera.position.set(levelMapPos.getX() + GameConstants.MAP_SIZE / 2, levelMapPos.getY() + GameConstants.MAP_SIZE / 2, 0);

        // Load the tile map and map renderer
        levelMap = new LevelMap(level);
        mapRenderer = new OrthogonalTiledMapRenderer(levelMap.getMap(), GameConstants.SCALE, batch);
        mapRenderer.setView(camera);

        // Set up the pause sprite
        pauseSprite.setScale(SCALE);
        pauseSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        stage = new LevelStage(level, players, isTwoPlayer, new ScreenViewport(camera), batch);
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
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        // Draw the level block and ice layers
        mapRenderer.render();

        // Draw stage and its actors
        stage.act(delta);
        stage.draw();

        // Draw the bush layer
        batch.begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) levelMap.getBushMap().getLayers().get(0));
        batch.end();

        if (gameOverTitle != null) {
            gameOverTitle.draw(batch, delta);
        }
        if (isPaused) {
            batch.begin();
            pauseSprite.draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        isPaused = !isPaused;
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
        mapRenderer.dispose();
        Gdx.input.setInputProcessor(null);
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

    public void showGameOverTitle() {
        gameOverTitle = new GameOverTitle(() -> {
            BattleCity.getInstance().setScreen(new GameOverScreen());
            dispose();
        });
    }

    public void goToTheNextLevel(Array<PlayerDetail> playerDetails, int level, boolean isTwoPlayer) {
        dispose();
        BattleCity.getInstance().setScreen(new LevelScreen(playerDetails, level, isTwoPlayer));
    }
}

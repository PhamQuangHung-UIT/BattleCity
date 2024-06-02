package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.events.MoveEvent;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;

public class LevelStage extends Stage {
    final Point firstPlayerPos = new Point(80 * 3, 8 * 3);

    final Animation<Sprite> pauseAnim;

    Player firstPlayer;
    boolean firstPlayerEnabled;

    boolean gameOver = false;
    boolean paused = false;
    private float time;

    public LevelStage(boolean isTwoPlayer, Viewport viewport, Batch batch) {
        super(viewport, batch);
        firstPlayer = new Player(Direction.UP, firstPlayerPos.getX(), firstPlayerPos.getY(), GameConstants.PLAYER_SPEED);
        Group playerGroup = new Group();
        playerGroup.addActor(firstPlayer);
        addActor(playerGroup);

        Array<Sprite> pauseSprites = new Array<>();

        pauseSprites.add(new Sprite(new Texture("ui/pause.png")));
        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0); // Set fully transparent color
        pixmap.fill();
        pauseSprites.add(new Sprite(new Texture(pixmap)));

        pauseAnim = new Animation<>(0.5f, pauseSprites);
        CollisionDetection.getInstance();
    }

    @Override
    public void act(float delta) {
        if (!paused) {
            time += delta;
            super.act(delta);

            // Handle input
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                firstPlayer.handle(new MoveEvent(Direction.UP, Gdx.graphics.getDeltaTime()));
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                firstPlayer.handle(new MoveEvent(Direction.DOWN, Gdx.graphics.getDeltaTime()));
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                firstPlayer.handle(new MoveEvent(Direction.LEFT, Gdx.graphics.getDeltaTime()));
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                firstPlayer.handle(new MoveEvent(Direction.RIGHT, Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                firstPlayer.shoot();
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (paused) {
            Sprite pauseSprite = pauseAnim.getKeyFrame(System.currentTimeMillis() / 1000f, true);
            pauseSprite.setCenter(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Input.Keys.ENTER:
                paused = !paused;
                if (paused) {
                    SoundManager.PAUSE.play();
                }
                break;
        }
        return super.keyDown(keyCode);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public float getTime() {
        return time;
    }
}

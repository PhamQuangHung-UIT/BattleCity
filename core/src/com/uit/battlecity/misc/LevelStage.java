package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uit.battlecity.BattleCity;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.events.MoveEvent;
import com.uit.battlecity.interfaces.EnemyListener;
import com.uit.battlecity.screens.GameOverScreen;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.tank.Enemy;
import com.uit.battlecity.tank.FirstPlayer;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.tank.SecondPlayer;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.*;

public class LevelStage extends Stage implements EnemyListener {

    private final LevelBoard levelBoard;
    private final EnemyGroup enemyGroup;
    private final Group playerGroup;
    private final EnemySpawner spawner;

    private boolean hasSpawnedSecondPlayer = false;
    private boolean hasSpawnedFirstPlayer = false;

    private Player firstPlayer, secondPlayer;

    private boolean gameOver = false;
    private boolean paused = false;
    private float time;

    public LevelStage(int level, Array<PlayerDetail> players, boolean isTwoPlayer, Viewport viewport, Batch batch) {
        super(viewport, batch);

        enemyGroup = new EnemyGroup();
        addActor(enemyGroup);

        spawner = new EnemySpawner(level, this);
        // Initialize level board
        levelBoard = new LevelBoard(level,
                isTwoPlayer,
                spawner.getEnemyCount());
        levelBoard.setFirstPlayerRemainingLives(players.get(0).remainingLives);
        if (isTwoPlayer) {
            levelBoard.setSecondPlayerRemainingLives(players.get(1).remainingLives);
        }
        addActor(spawner);
        addActor(levelBoard);

        // Initialize players
        playerGroup = new Group();
        addActor(playerGroup);

        spawnFirstPlayer(players.get(0).level);

        if (isTwoPlayer) {
            spawnSecondPlayer(players.get(1).level);
        }
    }

    private void spawnFirstPlayer(int tankLevel) {
        SpawnSpark spark = Pools.obtain(SpawnSpark.class);
        spark.initialize(FIRST_PLAYER_POS, () -> {
            firstPlayer = new FirstPlayer(Direction.UP, tankLevel, FIRST_PLAYER_POS.getX(), FIRST_PLAYER_POS.getY(), PLAYER_SPEED);
            firstPlayer.setListener(() -> {
                hasSpawnedFirstPlayer = false;
                levelBoard.decreaseFirstPlayerLives();
                if (!levelBoard.firstPlayerLastLive) {
                    spawnFirstPlayer(0);
                } else checkForGameOverCondition();
            });
            playerGroup.addActor(firstPlayer);
            hasSpawnedFirstPlayer = true;
        });
        addActor(spark);
    }

    private void spawnSecondPlayer(int tankLevel) {
        SpawnSpark spark = Pools.obtain(SpawnSpark.class);
        spark.initialize(SECOND_PLAYER_POS, () -> {
            secondPlayer = new SecondPlayer(Direction.UP, tankLevel, SECOND_PLAYER_POS.getX(), SECOND_PLAYER_POS.getY(), PLAYER_SPEED);
            secondPlayer.setListener(() -> {
                hasSpawnedSecondPlayer = false;
                levelBoard.decreaseSecondPlayerLives();
                if (!levelBoard.secondPlayerLastLive) {
                    spawnSecondPlayer(0);
                } else checkForGameOverCondition();
            });
            playerGroup.addActor(secondPlayer);
            hasSpawnedSecondPlayer = true;
        });
        addActor(spark);
    }

    private void checkForGameOverCondition() {
        if (levelBoard.isTwoPlayer) {
            if (levelBoard.getFirstPlayerRemainingLives() == 0 && levelBoard.getSecondPlayerRemainingLives() == 0 && levelBoard.firstPlayerLastLive && levelBoard.secondPlayerLastLive) {
                gameOver();
            }
        } else if (levelBoard.getFirstPlayerRemainingLives() == 0) {
            gameOver();
        }
    }

    @Override
    public void act(float delta) {
        if (!paused) {
            time += delta;
            CollisionDetection.getInstance().check();
            super.act(delta);

            if (!gameOver) {
                // Handle first player input
                if (hasSpawnedFirstPlayer) {
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

                if (hasSpawnedSecondPlayer) {
                    // Handle second player input
                    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                        secondPlayer.handle(new MoveEvent(Direction.UP, Gdx.graphics.getDeltaTime()));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                        secondPlayer.handle(new MoveEvent(Direction.DOWN, Gdx.graphics.getDeltaTime()));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                        secondPlayer.handle(new MoveEvent(Direction.LEFT, Gdx.graphics.getDeltaTime()));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                        secondPlayer.handle(new MoveEvent(Direction.RIGHT, Gdx.graphics.getDeltaTime()));
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
                        secondPlayer.shoot();
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ENTER) {
            paused = !paused;
            LevelScreen.getInstance().pause();
            if (paused) {
                SoundManager.PAUSE.play();
            }
        }
        return super.keyDown(keyCode);
    }

    public boolean isPaused() {
        return paused;
    }

    public float getTime() {
        return time;
    }

    public EnemyGroup getEnemyGroup() {
        return enemyGroup;
    }

    public void gameOver() {
        if (!gameOver) {
            gameOver = true;
            LevelScreen.getInstance().showGameOverTitle();
        }
    }

    @Override
    public void onDestroy(Enemy enemy) {
        if (spawner.getEnemyCount() == 0) {
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    completeTheLevel();
                }
            }, 3f);
        }
    }

    private void completeTheLevel() {
        Array<PlayerDetail> playerDetails = new Array<>();
        playerDetails.add(new PlayerDetail(firstPlayer.getTankLevel(), levelBoard.firstPlayerRemainingLives));
        if (levelBoard.isTwoPlayer) {
            playerDetails.add(new PlayerDetail(secondPlayer.getTankLevel(), levelBoard.secondPlayerRemainingLives));
        }
        if (levelBoard.getLevel() < MAX_AVAILABLE_LEVEL) {
            LevelScreen.getInstance().goToTheNextLevel(playerDetails, levelBoard.getLevel() + 1, levelBoard.isTwoPlayer);
        } else {
            BattleCity.getInstance().setScreen(new GameOverScreen());
            LevelScreen.getInstance().dispose();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        levelBoard.dispose();
        CollisionDetection.getInstance().dispose();
    }

    public void addLife(Player player) {
        if (player == firstPlayer) {
            levelBoard.addFirstPlayerLive();
        } else levelBoard.addSecondPlayerLive();
    }

    public LevelBoard getLevelBoard() {
        return levelBoard;
    }
}
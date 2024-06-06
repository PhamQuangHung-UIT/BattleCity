package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.interfaces.EnemyListener;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.tank.Enemy;
import com.uit.battlecity.tank.enemy.AdvancedTank;
import com.uit.battlecity.tank.enemy.FastTank;
import com.uit.battlecity.tank.enemy.NormalTank;
import com.uit.battlecity.tank.enemy.SteelTank;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;

import static com.uit.battlecity.utils.GameConstants.MAX_ENEMY_ON_FIELD;
import static com.uit.battlecity.utils.GameConstants.SPAWN_INTERVAL;


public class EnemySpawner extends Actor implements EnemyListener {
    private final Array<Array<Enemy>> enemyList = new Array<>();
    private final Array<Point> spawnPos;
    private final EnemyListener listener;
    private int currentLane = 0;
    private int enemyOnFields = 0;
    private int enemyRemainCount = 0;
    private float timer = SPAWN_INTERVAL;

    public EnemySpawner(int level, EnemyListener listener) {
        this.listener = listener;
        String[] enemyLanes = Gdx.files.internal("levels/" + level + "/enemy").readString().split("\r\n");

        spawnPos = Array.with(
                LevelScreen.getInstance().cellToScreenPos(new PointInt(2, 25)),
                LevelScreen.getInstance().cellToScreenPos(new PointInt(14, 25)),
                LevelScreen.getInstance().cellToScreenPos(new PointInt(26, 25))
        );

        for (int i = 0; i < 3; i++) {
            Array<Enemy> enemyLane = new Array<>();
            String[] enemyStrings = enemyLanes[i].split(",");
            for (String enemyString : enemyStrings) {
                switch (enemyString) {
                    // normal tank
                    case "n":
                        enemyLane.add(new NormalTank(this, Direction.DOWN, spawnPos.get(i).getX(), spawnPos.get(i).getY()));
                        break;
                    // fast tank
                    case "f":
                        enemyLane.add(new FastTank(this, Direction.DOWN, spawnPos.get(i).getX(), spawnPos.get(i).getY()));
                        break;
                    // upgraded tank
                    case "a":
                        enemyLane.add(new AdvancedTank(this, Direction.DOWN, spawnPos.get(i).getX(), spawnPos.get(i).getY()));
                        break;
                    // steel tank
                    case "s":
                        enemyLane.add(new SteelTank(this, Direction.DOWN, spawnPos.get(i).getX(), spawnPos.get(i).getY()));
                        break;
                }
                enemyRemainCount++;
            }
            enemyList.add(enemyLane);
        }
    }

    @Override
    public void act(float delta) {
        if (enemyOnFields < MAX_ENEMY_ON_FIELD && enemyRemainCount > 0) {
            timer += delta;
            if (timer > SPAWN_INTERVAL) {
                currentLane = (currentLane + 1) % 3;
                timer = 0;
                Enemy enemy = enemyList.get(currentLane).removeIndex(0);
                SpawnSpark spark = Pools.obtain(SpawnSpark.class);
                spark.initialize(spawnPos.get(currentLane), () -> {
                    ((LevelStage) getStage()).getEnemyGroup().addActor(enemy);
                    ((LevelStage) getStage()).getLevelBoard().removeEnemy();
                    CollisionDetection.getInstance().getEnemyTankList().add(enemy);
                });
                getStage().addActor(spark);
                enemyOnFields++;
                enemyRemainCount--;
            }
        }
    }

    @Override
    public void onDestroy(Enemy enemy) {
        CollisionDetection.getInstance().getEnemyTankList().removeValue(enemy, true);
        enemyOnFields--;
        listener.onDestroy(enemy);
    }

    public int getEnemyCount() {
        return enemyRemainCount + enemyOnFields;
    }
}

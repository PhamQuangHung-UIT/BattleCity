package com.uit.battlecity.misc;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.uit.battlecity.tank.Bullet;
import com.uit.battlecity.tank.Tank;
import com.uit.battlecity.utils.GameConstants;

public class CollisionDetection {
    private static CollisionDetection instance = null;
    private final Array<Bullet> enemyBulletList = new Array<>();
    private final Array<Bullet> playerBulletList = new Array<>();
    private final Array<Tank> enemyTankList = new Array<>();
    private final Array<Tank> playerTankList = new Array<>();


    private CollisionDetection() {
        Task task = new Task() {
            @Override
            public void run() {
                check();
            }
        };
        Timer.schedule(task, 0, 1 / GameConstants.COLLISION_DETECTION_RATE);
    }

    /**
     * Start collision detection task
     *
     * @return new instance or existing instance if it has
     */
    public static CollisionDetection getInstance() {
        if (instance == null) {
            instance = new CollisionDetection();
        }
        return instance;
    }

    public void check() {
        bulletLoop:
        for (Bullet enemyBullet : enemyBulletList) {
            for (Tank player : playerTankList) {
                if (player.getBound().overlaps(enemyBullet.getBound())) {
                    player.getHit(enemyBullet.getDamage());
                    enemyBullet.destroy();
                    continue bulletLoop;
                }
            }
            for (Bullet playerBullet : playerBulletList) {
                if (playerBullet.getBound().overlaps(enemyBullet.getBound())) {
                    playerBullet.destroy();
                    enemyBullet.destroy();
                }
            }
        }
        for (Bullet playerBullet : playerBulletList) {
            for (Tank enemyTank : enemyTankList) {
                if (enemyTank.getBound().overlaps(playerBullet.getBound())) {
                    enemyTank.getHit(playerBullet.getDamage());
                    playerBullet.destroy();
                    break;
                }
            }
        }
        //System.out.println(Calendar.getInstance().toString() + ": Collision Detection Running...");
    }

    public Array<Bullet> getEnemyBulletList() {
        return enemyBulletList;
    }

    public Array<Tank> getEnemyTankList() {
        return enemyTankList;
    }

    public Array<Bullet> getPlayerBulletList() {
        return playerBulletList;
    }
}

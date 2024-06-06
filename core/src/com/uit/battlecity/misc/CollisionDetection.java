package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.tank.Tank;

import static com.uit.battlecity.utils.GameConstants.DEBUG_MODE;

public class CollisionDetection {
    private static CollisionDetection instance = null;
    private final Array<Bullet> enemyBulletList = new Array<>();
    private final Array<Bullet> playerBulletList = new Array<>();
    private final Array<Tank> enemyTankList = new Array<>();
    private final Array<Tank> playerTankList = new Array<>();
    private final ShapeRenderer sr = new ShapeRenderer();


    private CollisionDetection() {
        sr.setAutoShapeType(true);
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
                Rectangle playerBound = player.getBound();
                Rectangle enemyBulletBound = enemyBullet.getBound();
                if (playerBound.overlaps(enemyBulletBound) || playerBound.contains(enemyBulletBound)) {
                    player.getHit(enemyBullet.getDamage());
                    enemyBullet.destroy();
                    continue bulletLoop;
                }
            }
            for (Bullet playerBullet : playerBulletList) {
                if (playerBullet.getBound().overlaps(enemyBullet.getBound())) {
                    playerBullet.destroy();
                    enemyBullet.destroy();
                    continue bulletLoop;
                }
            }
        }
        bulletLoop:
        for (Bullet playerBullet : playerBulletList) {
            for (Tank enemyTank : enemyTankList) {
                Rectangle enemyBound = enemyTank.getBound();
                Rectangle playerBulletBound = playerBullet.getBound();
                if (enemyBound.overlaps(playerBulletBound) && enemyTank.getStage() != null) {
                    enemyTank.getHit(playerBullet.getDamage());
                    playerBullet.destroy();
                    continue bulletLoop;
                }
            }
        }
        if (DEBUG_MODE) {
            sr.begin();
            sr.setColor(Color.GREEN);
            sr.set(ShapeRenderer.ShapeType.Line);
            for (Bullet playerBullet : playerBulletList) {
                Rectangle bulletBound = playerBullet.getBound();
                sr.rect(bulletBound.x, bulletBound.y, bulletBound.width, bulletBound.height);
            }

            for (Bullet enemyBullet : enemyBulletList) {
                Rectangle bulletBound = enemyBullet.getBound();
                sr.rect(bulletBound.x, bulletBound.y, bulletBound.width, bulletBound.height);
            }
            for (Tank playerTank : playerTankList) {
                Rectangle tankBound = playerTank.getBound();
                sr.rect(tankBound.x, tankBound.y, tankBound.width, tankBound.height);
            }
            for (Tank enemyTank : enemyTankList) {
                Rectangle tankBound = enemyTank.getBound();
                sr.rect(tankBound.x, tankBound.y, tankBound.width, tankBound.height);
            }
            sr.end();
        }
        //System.out.println(Calendar.getInstance().toString() + ": Collision Detection Running...");
    }

    public boolean isCollidingWithOther(Tank tank, Direction dir) {
        Rectangle tankBound = tank.getBound();
        for (Tank enemy : enemyTankList) {
            if (enemy.getBound().overlaps(tankBound) && enemy != tank) {
                switch (dir) {
                    case UP:
                        return enemy.getBound().y > tankBound.y;
                    case DOWN:
                        return enemy.getBound().y < tankBound.y;
                    case LEFT:
                        return enemy.getBound().x < tankBound.x;
                    case RIGHT:
                        return enemy.getBound().x > tankBound.x;
                }
            }

        }
        for (Tank player : playerTankList) {
            if (player.getBound().overlaps(tankBound) && player != tank)
                switch (dir) {
                    case UP:
                        return player.getBound().y > tankBound.y;
                    case DOWN:
                        return player.getBound().y < tankBound.y;
                    case LEFT:
                        return player.getBound().x < tankBound.x;
                    case RIGHT:
                        return player.getBound().x > tankBound.x;
                }
        }
        return false;
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

    public Array<Tank> getPlayerTankList() {
        return playerTankList;
    }

    public void dispose() {
        playerTankList.clear();
        playerBulletList.clear();
        enemyTankList.clear();
        enemyBulletList.clear();
    }
}

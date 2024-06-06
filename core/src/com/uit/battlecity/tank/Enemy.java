package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.enums.PowerUpType;
import com.uit.battlecity.events.MoveEvent;
import com.uit.battlecity.events.ShootEvent;
import com.uit.battlecity.interfaces.EnemyListener;
import com.uit.battlecity.misc.Score;
import com.uit.battlecity.powerups.*;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.POWER_UP_DROP_CHANCE;

public class Enemy extends Tank {
    private final int score;
    public EnemyListener listener;
    public float shootTimer;
    public Direction faceDir;
    private float movementTimer;

    public Enemy(EnemyListener listener, Array<Sprite> tankSpriteArr, int score, int health, Direction startDir, float posX, float posY, float speed) {
        super(tankSpriteArr, health, startDir, posX, posY, speed);
        this.listener = listener;
        this.score = score;
        faceDir = startDir;
    }

    @Override
    public void act(float delta) {
        handleMovement(delta);
        handleShooting(delta);
        if (!SoundManager.N_MOVING.isPlaying())
            SoundManager.N_MOVING.play();
    }

    private void handleShooting(float delta) {
        shootTimer += delta;
        if (shootTimer > 1.2f) {
            shootTimer = 0;
            handle(new ShootEvent(1, false));
        }
    }

    private void handleMovement(float delta) {
        handle(new MoveEvent(faceDir, delta));
        if (!isTankMoving) {
            shootTimer += delta * 3;
            if (movementTimer > 0.25f) {
                movementTimer = 0;
                faceDir = Direction.getRandom();
            } else movementTimer += delta;
        }
    }

    @Override
    public void getHit(int damage) {
        health -= damage;
        if (health <= 0) {
            destroy();
            listener.onDestroy(this);
        }
    }

    @Override
    public void destroy() {
        if (Math.random() < POWER_UP_DROP_CHANCE) {
            createPowerUp();
        } else {
            Score scoreUI = Pools.obtain(Score.class);
            scoreUI.initialize(score, tankPos);
            getStage().addActor(scoreUI);
        }
        super.destroy();
    }

    private void createPowerUp() {
        PowerUpType type = PowerUpType.getRandom();
        PowerUp powerUp;
        switch (type) {
            case SHOVEL:
                powerUp = new ShovelPowerUp(tankPos);
                break;
            case GRENADE:
                powerUp = new GrenadePowerUp(tankPos);
                break;
            case LIVE:
                powerUp = new LivePowerUp(tankPos);
                break;
            case SHIELD:
                powerUp = new ShieldPowerUp(tankPos);
                break;
            case UPGRADE:
                powerUp = new UpgradePowerUp(tankPos);
                break;
            default:
                powerUp = new TimeStopPowerUp(tankPos);
                break;
        }
        getStage().addActor(powerUp);
        SoundManager.TANK_BONUS.play();
    }
}

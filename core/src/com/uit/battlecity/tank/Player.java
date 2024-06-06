package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.events.ShootEvent;
import com.uit.battlecity.interfaces.PlayerListener;
import com.uit.battlecity.misc.CollisionDetection;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.*;

// Base implement for tank (enemy & player)
public abstract class Player extends Tank {

    final Array<Sprite> shields = Array.with(
            new Sprite(new Texture("miscellaneous/shield/shield_anim_0.png")),
            new Sprite(new Texture("miscellaneous/shield/shield_anim_1.png"))
    );

    Array<Array<Sprite>> tankAnimationArr;
    private final Animation<Sprite> shieldAnim;
    private final int[] damagePerLevel = new int[]{1, 1, 4};
    private final int[] bulletSpeedRatioPerLevel = new int[]{1, 2, 2};
    private float maxShieldTime;

    private int tankLevel = 0;

    boolean hasShield = false;
    float shieldTime = 0;
    private boolean canShoot = true;
    private float shootTimer = 0;
    private PlayerListener listener;

    public Player(Direction startDir, Array<Array<Sprite>> tankAnimationArr, int tankLevel, float posX, float posY, float speed) {
        super(tankAnimationArr.get(tankLevel), 1, startDir, posX, posY, speed);
        this.tankAnimationArr = tankAnimationArr;
        this.tankLevel = tankLevel;
        for (Sprite shield : shields) {
            shield.setScale(SCALE);
            shield.setOrigin(0, 0);
        }
        shieldAnim = new Animation<>(SHIELD_ANIM_DURATION, shields);
        startShield(MAX_SHIELD_TIME / 2);

        CollisionDetection.getInstance().getPlayerTankList().add(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (hasShield) {
            shieldTime += delta;
            if (shieldTime > maxShieldTime) {
                stopShield();
            }
        }
        if (!canShoot) {
            shootTimer += delta;
            if (shootTimer > 0.75f) {
                shootTimer = 0;
                canShoot = true;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (hasShield) {
            Sprite shieldSprite = shieldAnim.getKeyFrame(shieldTime, true);
            shieldSprite.setPosition(tankPos.getX(), tankPos.getY());
            shieldSprite.draw(batch);
        }
    }

    public void startShield(float maxShieldTime) {
        this.maxShieldTime = maxShieldTime;
        hasShield = true;
        shieldTime = 0;
    }

    private void stopShield() {
        hasShield = false;
    }

    public void upgrade() {
        tankLevel++;
        if (tankLevel >= 3) {
            tankLevel--;
        }
        Direction oldDir = getDir();
        setDir(null);
        setTankSpriteAnim(tankAnimationArr.get(tankLevel), oldDir);
    }

    public void setListener(PlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public void getHit(int damage) {
        if (hasShield) {
            SoundManager.SHIELD_HIT.play();
            return;
        }
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        CollisionDetection.getInstance().getPlayerTankList().removeValue(this, true);
        listener.onPlayerDestroyed();
    }

    public void shoot() {
        if (canShoot) {
            canShoot = false;
            SoundManager.SHOOT.play();
            bulletSpeedRatio = bulletSpeedRatioPerLevel[tankLevel];
            handle(new ShootEvent(damagePerLevel[tankLevel], tankLevel > 1));
        }
    }

    public int getTankLevel() {
        return tankLevel;
    }

    public void resetShootTrigger() {
        if (!canShoot)
            shootTimer += 0.5f;
    }
}

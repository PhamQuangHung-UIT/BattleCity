package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.events.ShootEvent;
import com.uit.battlecity.misc.SoundManager;

// Base implement for tank (enemy & player)
public class Player extends Tank {

    final Array<Sprite> shields = Array.with(
            new Sprite(new Texture("miscellaneous/shield/shield_anim_0.png")),
            new Sprite(new Texture("miscellaneous/shield/shield_anim_1.png"))
    );

    static final Array<Array<Sprite>> moveAnimArray = Array.with(
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_normal_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_normal_up_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_upgraded_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_upgraded_up_1.png"))),
            Array.with(
                    new Sprite(new Texture("tanks/tank_yellow_advanced_up_0.png")),
                    new Sprite(new Texture("tanks/tank_yellow_advanced_up_1.png"))));

    private Animation<Sprite> shieldAnim;
    private final int[] damagePerLevel = new int[]{1, 1, 3};
    private static final float shieldTransitionDuration = 1/ 10f;

    int tankLevel = 0;

    boolean hasShield = false;
    float shieldTime = 0;
    float maxShieldTime = 8;
    private final int[] bulletSpeedPerLevel = new int[]{16 * 3, 32 * 3, 32 * 3};

    public Player(Direction startDir, float posX, float posY, float speed) {
        super(PLAYER, 1, startDir, posX, posY, speed);
        setTankSpriteAnim(moveAnimArray.get(tankLevel));
        for (Sprite shield : shields) {
            shield.setScale(3);
        }
        shieldAnim = new Animation<>(shieldTransitionDuration, shields);
        startShield();
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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (hasShield) {
            Sprite shieldSprite = shieldAnim.getKeyFrame(shieldTime, true);
            shieldSprite.setPosition(tankPos.getX() + shieldSprite.getWidth(), tankPos.getY() + shieldSprite.getHeight());
            shieldSprite.draw(batch);
        }
    }

    public void startShield() {
        hasShield = true;
        shieldTime = 0;
    }

    private void stopShield() {
        hasShield = false;
    }

    public void upgrade() {
        tankLevel++;
        SoundManager.BONUS.play();
    }

    @Override
    public void getHit(int damage) {
        if (hasShield) {
            SoundManager.SHIELD_HIT.play();
            return;
        }

    }

    public void shoot() {
        SoundManager.SHOOT.play();
        handle(new ShootEvent(damagePerLevel[tankLevel], bulletSpeedPerLevel[tankLevel], tankLevel > 1));
    }
}

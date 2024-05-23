package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.screens.LevelScreen;

// Base implement for tank (enemy & player)
public class Player extends Tank implements EventListener {

    final Array<Texture> shields = Array.with(
            new Texture("miscellaneous/shield/shield_anim_0"),
            new Texture("miscellaneous/shield/shield_anim_1")
    );

    final Array<Animation<Texture>> moveUpAnims = Array.with(
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_normal_up_0"),
                    new Texture("tanks/tank_yellow_normal_up_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_upgraded_up_0"),
                    new Texture("tanks/tank_yellow_upgraded_up_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_advanced_up_0"),
                    new Texture("tanks/tank_yellow_advanced_up_1")));
    final Array<Animation<Texture>> moveDownAnims = Array.with(
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_normal_down_0"),
                    new Texture("tanks/tank_yellow_normal_down_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_upgraded_down_0"),
                    new Texture("tanks/tank_yellow_upgraded_down_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_advanced_down_0"),
                    new Texture("tanks/tank_yellow_advanced_down_1")));
    final Array<Animation<Texture>> moveLeftAnims = Array.with(
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_normal_left_0"),
                    new Texture("tanks/tank_yellow_normal_left_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_upgraded_left_0"),
                    new Texture("tanks/tank_yellow_upgraded_left_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_advanced_left_0"),
                    new Texture("tanks/tank_yellow_advanced_left_1")));
    final Array<Animation<Texture>> moveRightAnims = Array.with(
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_normal_right_0"),
                    new Texture("tanks/tank_yellow_normal_right_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_upgraded_right_0"),
                    new Texture("tanks/tank_yellow_upgraded_right_1")),
            new Animation<>(0.25f,
                    new Texture("tanks/tank_yellow_advanced_right_0"),
                    new Texture("tanks/tank_yellow_advanced_right_1")));

    final Animation<Texture> shieldAnim = new Animation<>(0.2f, shields);

    final int[] damagePerLevel = new int[]{1, 1, 3};

    int tankLevel = 0;

    boolean hasShield = false;
    float shieldTime = 0;
    float maxShieldTime = 10;

    public Player(LevelScreen screen, float posX, float posY, float speed) {
        super(screen, posX, posY, speed);
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
            batch.draw(shieldAnim.getKeyFrame(screen.getTime(), true), posX, posY);
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
    }

    @Override
    public boolean notify(Event event, boolean capture) {
        return super.notify(event, capture);
    }

    /**
     *  Use for implement custom move-up tank texture
     */
    @Override
    protected void onTankMoveUp() {

    }

    /**
     *  Use for implement custom move-down tank texture
     */
    protected void onTankMoveDown() {

    }

    /**
     *  Use for implement custom move-left tank texture
     */
    protected void onTankMoveLeft() {

    }

    /**
     *  Use for implement custom move-right tank texture
     */
    protected void onTankMoveRight() {

    }

    public int getTankLevel() {
        return tankLevel;
    }
}

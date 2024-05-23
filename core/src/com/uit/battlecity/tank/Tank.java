package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.events.MoveEvent;
import com.uit.battlecity.events.TankEvent;

// Base implement for tank (enemy & player)
public abstract class Tank extends Actor implements EventListener {
    float posX;
    float posY;
    Direction dir;
    float speed;

    Animation<Texture> tankTextureAnim;

    final LevelScreen screen;

    public Tank(LevelScreen screen, float posX, float posY, float speed) {
        this.speed = speed;
        this.posX = posX;
        this.posY = posY;
        this.screen = screen;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tankTextureAnim.getKeyFrame(screen.getTime()), posX, posY);
    }

    @Override
    public final boolean handle(Event event) {
        if (event instanceof TankEvent) {
            if (event instanceof MoveEvent) {
                MoveEvent moveEvent = (MoveEvent) event;
                float delta = moveEvent.getDelta();
                dir = moveEvent.getDirection();
                switch (dir) {
                    case UP:
                        onTankMoveUp();
                        posY += speed * delta;
                        break;
                    case DOWN:
                        onTankMoveDown();
                        posY -= speed * delta;
                        break;
                    case LEFT:
                        onTankMoveLeft();
                        posX -= speed * delta;
                        break;
                    case RIGHT:
                        onTankMoveRight();
                        posX += speed * delta;
                        break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     *  Use for implement custom move-up tank texture
     */
    protected abstract void onTankMoveUp();

    /**
     *  Use for implement custom move-down tank texture
     */
    protected abstract void onTankMoveDown();

    /**
     *  Use for implement custom move-left tank texture
     */
    protected abstract void onTankMoveLeft();

    /**
     *  Use for implement custom move-right tank texture
     */
    protected abstract void onTankMoveRight();

    public void setTankTextureAnim(Animation<Texture> tankTextureAnim) {
        this.tankTextureAnim = tankTextureAnim;
    }
}

package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;

public class SmallExplosion extends Actor {
    private static Array<Sprite> spriteArr = new Array<>();
    private static Animation<Sprite> animation;
    private float time = 0;
    private Point position;

    public void initialize(Point position, ObstacleType obstacleType) {
        this.position = position;
        time = 0;
        if (spriteArr.isEmpty()) {
            FileHandle handle = Gdx.files.internal("miscellaneous/explode/small/");
            for (FileHandle file : handle.list()) {
                Sprite sprite = new Sprite(new Texture(file));
                sprite.scale(1.5f);
                spriteArr.add(sprite);
            }
            animation = new Animation<>(GameConstants.EXPlODE_DURATION / spriteArr.size, spriteArr);
        }
        switch (obstacleType) {
            case BRICK:
                SoundManager.BRICK_HIT.play();
                break;
            case STEEL:
                SoundManager.SHIELD_HIT.play();
                break;
        }
    }

    @Override
    public void act(float delta) {
        time += delta;
        if (time >= GameConstants.EXPlODE_DURATION) {
            remove();
            Pools.free(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite sprite = animation.getKeyFrame(time);
        sprite.setCenter(position.getX(), position.getY());
        sprite.draw(batch, parentAlpha);
    }
}

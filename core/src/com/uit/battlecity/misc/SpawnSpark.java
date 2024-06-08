package com.uit.battlecity.misc;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.interfaces.EffectListener;
import com.uit.battlecity.utils.ImportUtils;
import com.uit.battlecity.utils.Point;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class SpawnSpark extends Actor {
    private Animation<Sprite> anim;
    private Point pos;
    private float time;
    private EffectListener listener;

    public void initialize(Point pos, EffectListener listener) {
        this.pos = pos;
        this.listener = listener;

        time = 0;

        if (anim == null) {
            Array<Sprite> sprites = new Array<Sprite>();
            for (FileHandle file : ImportUtils.list("miscellaneous/spark")) {
                Sprite sprite = new Sprite(new Texture(file));
                sprite.setSize(sprite.getWidth() * SCALE, sprite.getHeight() * SCALE);
                sprites.add(sprite);
            }
            anim = new Animation<>(1 / 16f, sprites);
            anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }
    }

    @Override
    public void act(float delta) {
        time += delta;
        if (time > 1) {
            listener.onEffectEnd();
            remove();
            Pools.free(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite currentSprite = anim.getKeyFrame(time);
        currentSprite.setPosition(pos.getX(), pos.getY());
        currentSprite.draw(batch, parentAlpha);
    }
}

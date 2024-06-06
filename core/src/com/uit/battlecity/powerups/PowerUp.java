package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.misc.CollisionDetection;
import com.uit.battlecity.misc.Score;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.tank.Tank;
import com.uit.battlecity.utils.MathUtils;
import com.uit.battlecity.utils.Point;

import static com.uit.battlecity.utils.GameConstants.POWER_UP_APPEAR_TIME;
import static com.uit.battlecity.utils.GameConstants.SCALE;

public abstract class PowerUp extends Actor {
    private Sprite sprite;
    private Animation<Sprite> animation;
    private Rectangle rect;
    private Point position;
    private float timer = 0;

    public void create(Point position, Texture texture) {
        this.position = position;
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(MathUtils.roundFloat(position.getX(), 8 * SCALE),
                MathUtils.roundFloat(position.getY(), 8 * SCALE));
        sprite.setScale(SCALE);
        rect = new Rectangle(position.getX(), position.getY(), sprite.getWidth() * SCALE, sprite.getHeight() * SCALE);
        animation = new Animation<>(0.2f, sprite, null);
    }

    @Override
    public void act(float delta) {
        timer += delta;
        if (timer > POWER_UP_APPEAR_TIME) {
            remove();
        }
        for (Tank player : CollisionDetection.getInstance().getPlayerTankList()) {
            if (player.getBound().overlaps(rect)) {
                handle((Player) player);
                Score score = Pools.obtain(Score.class);
                score.initialize(500, position);
                getStage().addActor(score);
                remove();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite currentSprite = animation.getKeyFrame(timer, true);
        if (currentSprite != null) {
            currentSprite.draw(batch);
        }
    }

    public abstract void handle(Player player);
}

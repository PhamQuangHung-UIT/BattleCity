package com.uit.battlecity.tank;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.events.MoveEvent;
import com.uit.battlecity.events.ShootEvent;
import com.uit.battlecity.events.TankEvent;
import com.uit.battlecity.interfaces.Obstacle;
import com.uit.battlecity.misc.BigExplosion;
import com.uit.battlecity.misc.Bullet;
import com.uit.battlecity.misc.CollisionDetection;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.utils.*;

import static com.uit.battlecity.utils.GameConstants.SCALE;

// Base implement for tank (enemy & player)
public abstract class Tank extends Actor implements EventListener {
    protected Point tankPos;
    protected float speed;
    protected int health;
    private Animation<Sprite> tankSpriteAnim;
    private Sprite currentTankSprite;
    private final Rectangle bound = new Rectangle();
    protected boolean isTankMoving = true;
    protected Direction dir;
    protected float bulletSpeedRatio = 1.0f;

    public Tank(Array<Sprite> tankSpriteArr, int health, Direction startDir, float posX, float posY, float speed) {
        this.speed = speed;
        this.health = health;
        tankPos = new Point(posX, posY);
        setTankSpriteAnim(tankSpriteArr, startDir);
    }

    public void setTankSpriteAnim(Array<Sprite> tankSpriteArr, Direction startDir) {
        tankSpriteAnim = new Animation<>(GameConstants.TANK_ANIM_DURATION, tankSpriteArr);
        switch (startDir) {
            case UP:
                setMoveUpAnimation();
                break;
            case DOWN:
                setMoveDownAnimation();
                break;
            case LEFT:
                setMoveLeftAnimation();
                break;
            case RIGHT:
                setMoveRightAnimation();
                break;
        }
        currentTankSprite = tankSpriteAnim.getKeyFrames()[0];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentTankSprite.setScale(3);
        currentTankSprite.setPosition(tankPos.getX() + currentTankSprite.getWidth(),
                tankPos.getY() + currentTankSprite.getHeight());
        currentTankSprite.draw(batch);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof TankEvent) {
            // Check if this is movement event
            if (event instanceof MoveEvent) {
                MoveEvent moveEvent = (MoveEvent) event;
                LevelStage stage = (LevelStage) getStage();
                if (!stage.isPaused()) {
                    currentTankSprite = tankSpriteAnim.getKeyFrame(stage.getTime(), true);
                }
                float delta = moveEvent.getDelta();
                isTankMoving = true;
                PointInt cellPos;
                TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");
                TiledMapTileLayer.Cell cell;
                switch (moveEvent.getDirection()) {
                    case UP:
                        setMoveUpAnimation();
                        tankPos.setX(MathUtils.roundFloat(tankPos.getX(), 8 * 3));
                        tankPos.setY(tankPos.getY() + speed * delta);
                        if (CollisionDetection.getInstance().isCollidingWithOther(this, Direction.UP)) {
                            tankPos.setY(tankPos.getY() - speed * delta);
                            isTankMoving = false;
                            break;
                        }
                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + i, cellPos.getY() + 2);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setY(tankPos.getY() - speed * delta);
                                isTankMoving = false;
                                break;
                            }
                        }
                        break;
                    case DOWN:
                        setMoveDownAnimation();
                        tankPos.setX(MathUtils.roundFloat(tankPos.getX(), 8 * 3));
                        tankPos.setY(tankPos.getY() - speed * delta);
                        if (CollisionDetection.getInstance().isCollidingWithOther(this, Direction.DOWN)) {
                            tankPos.setY(tankPos.getY() + speed * delta);
                            isTankMoving = false;
                            break;
                        }
                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + i, cellPos.getY());
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setY(tankPos.getY() + speed * delta);
                                isTankMoving = false;
                                break;
                            }
                        }
                        break;
                    case LEFT:
                        setMoveLeftAnimation();
                        tankPos.setY(MathUtils.roundFloat(tankPos.getY(), 8 * 3));
                        tankPos.setX(tankPos.getX() - speed * delta);
                        if (CollisionDetection.getInstance().isCollidingWithOther(this, Direction.LEFT)) {
                            tankPos.setX(tankPos.getX() + speed * delta);
                            isTankMoving = false;
                            break;
                        }
                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX(), cellPos.getY() + i);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setX(tankPos.getX() + speed * delta);
                                isTankMoving = false;
                                break;
                            }
                        }
                        break;
                    case RIGHT:
                        setMoveRightAnimation();
                        tankPos.setY(MathUtils.roundFloat(tankPos.getY(), 8 * 3));
                        tankPos.setX(tankPos.getX() + speed * delta);
                        if (CollisionDetection.getInstance().isCollidingWithOther(this, Direction.RIGHT)) {
                            tankPos.setX(tankPos.getX() - speed * delta);
                            isTankMoving = false;
                            break;
                        }

                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + 2, cellPos.getY() + i);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setX(tankPos.getX() - speed * delta);
                                isTankMoving = false;
                                break;
                            }
                        }
                        break;
                }
            } else if (event instanceof ShootEvent) {
                // Generate the bullet
                Bullet bullet = getBullet((ShootEvent) event);
                getStage().addActor(bullet);
            }
            return true;
        }
        return false;
    }

    private Bullet getBullet(ShootEvent event) {
        Point bulletPos;
        switch (dir) {
            case UP:
                bulletPos = new Point(8 * 3, 16 * 3);
                break;
            case DOWN:
                bulletPos = new Point(8 * 3, 0);
                break;
            case LEFT:
                bulletPos = new Point(0, 8 * 3);
                break;
            default:
                bulletPos = new Point(16 * 3, 8 * 3);
                break;
        }
        bulletPos.setX(bulletPos.getX() + tankPos.getX());
        bulletPos.setY(bulletPos.getY() + tankPos.getY());
        Bullet bullet = Pools.obtain(Bullet.class);
        bullet.initialize(this, bulletPos, dir,
                GameConstants.BULLET_SPEED * bulletSpeedRatio,
                event.getDamage(),
                event.canDestroySteel());
        return bullet;
    }

    /**
     * Use for implement custom move-up tank texture
     */
    protected void setMoveUpAnimation() {
        if (dir != Direction.UP) {
            for (Sprite frame : tankSpriteAnim.getKeyFrames()) {
                frame.setRotation(0);
            }
            dir = Direction.UP;
        }
    }

    /**
     * Use for implement custom move-down tank texture
     */
    protected void setMoveDownAnimation() {
        if (dir != Direction.DOWN) {
            for (Sprite frame : tankSpriteAnim.getKeyFrames()) {
                frame.setRotation(180);
            }
            dir = Direction.DOWN;
        }
    }

    /**
     * Use for implement custom move-left tank texture
     */
    protected void setMoveLeftAnimation() {
        if (dir != Direction.LEFT) {
            for (Sprite frame : tankSpriteAnim.getKeyFrames()) {
                frame.setRotation(90);
            }
            dir = Direction.LEFT;
        }
    }

    /**
     * Use for implement custom move-right tank texture
     */
    protected void setMoveRightAnimation() {
        if (dir != Direction.RIGHT) {
            for (Sprite frame : tankSpriteAnim.getKeyFrames()) {
                frame.setRotation(-90);
            }
            dir = Direction.RIGHT;
        }
    }

    public Rectangle getBound() {
        bound.set(tankPos.getX(), tankPos.getY(), currentTankSprite.getWidth() * SCALE, currentTankSprite.getHeight() * SCALE);
        return bound;
    }

    public abstract void getHit(int damage);

    public void destroy() {
        System.out.println(this + " destroyed");
        BigExplosion explosion = Pools.obtain(BigExplosion.class);
        explosion.initialize(new Point(tankPos.getX() + 8 * SCALE, tankPos.getY() + 8 * SCALE));
        getStage().addActor(explosion);
        SoundManager.TANK_EXPLOSION.play();
        remove();
    }
}

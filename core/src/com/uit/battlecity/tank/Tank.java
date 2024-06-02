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
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.misc.Obstacle;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.utils.MathUtils;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;

// Base implement for tank (enemy & player)
public abstract class Tank extends Actor implements EventListener {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    Point tankPos;
    int owner;
    private float speed;
    private int health;
    private Animation<Sprite> tankSpriteAnim;
    private Sprite currentTankSprite;
    private Rectangle bound;
    private Direction dir = null;
    private boolean isTankMoving = true;

    public Tank(int owner, int health, Direction startDir, float posX, float posY, float speed) {
        this.speed = speed;
        this.health = health;
        this.dir = startDir;
        tankPos = new Point(posX, posY);
    }

    @Override
    public void act(float delta) {
        LevelStage stage = (LevelStage) getStage();
        if (!stage.isPaused() && isTankMoving) {
            currentTankSprite = tankSpriteAnim.getKeyFrame(stage.getTime(), true);
            isTankMoving = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentTankSprite != null) {
            currentTankSprite.setScale(3);
            currentTankSprite.setPosition(tankPos.getX() + currentTankSprite.getWidth(),
                    tankPos.getY() + currentTankSprite.getHeight());
            currentTankSprite.draw(batch);
        }
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof TankEvent) {
            // Check if this is movement event
            if (event instanceof MoveEvent) {
                MoveEvent moveEvent = (MoveEvent) event;
                isTankMoving = true;
                float delta = moveEvent.getDelta();
                PointInt cellPos;
                Point cellScreenPos;
                TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");
                TiledMapTileLayer.Cell cell;
                ObstacleType type;
                switch (moveEvent.getDirection()) {
                    case UP:
                        setMoveUpAnimation();
                        tankPos.setX(MathUtils.roundFloat(tankPos.getX(), 8 * 3));
                        tankPos.setY(tankPos.getY() + speed * delta);
                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + i, cellPos.getY() + 2);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setY(tankPos.getY() - speed * delta);
                                break;
                            }
                        }
                        break;
                    case DOWN:
                        setMoveDownAnimation();
                        tankPos.setX(MathUtils.roundFloat(tankPos.getX(), 8 * 3));
                        tankPos.setY(tankPos.getY() - speed * delta);

                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + i, cellPos.getY());
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setY(tankPos.getY() + speed * delta);
                                break;
                            }
                        }
                        break;
                    case LEFT:
                        setMoveLeftAnimation();
                        tankPos.setY(MathUtils.roundFloat(tankPos.getY(), 8 * 3));
                        tankPos.setX(tankPos.getX() - speed * delta);

                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX(), cellPos.getY() + i);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setX(tankPos.getX() + speed * delta);
                                break;
                            }
                        }
                        break;
                    case RIGHT:
                        setMoveRightAnimation();
                        tankPos.setY(MathUtils.roundFloat(tankPos.getY(), 8 * 3));
                        tankPos.setX(tankPos.getX() + speed * delta);

                        cellPos = LevelScreen.getInstance().screenToCellPos(tankPos);
                        for (int i = 0; i < 2; i++) {
                            cell = layer.getCell(cellPos.getX() + 2, cellPos.getY() + i);
                            if (cell == null || ((Obstacle) cell.getTile()).getObstacleType()
                                    != ObstacleType.NONE) {
                                tankPos.setX(tankPos.getX() - speed * delta);
                                break;
                            }
                        }
                        break;
                }
            } else if (event instanceof ShootEvent) {
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
        bullet.initialize(owner, bulletPos, dir,
                GameConstants.BULLET_SPEED,
                event.getDamage(),
                event.canDestroySteel());
        return bullet;
    }

    /**
     * Use for implement custom move-up tank texture
     */
    protected void setMoveUpAnimation() {
        if (getDir() != Direction.UP) {
            for (Sprite frame : tankSpriteAnim.getKeyFrames()) {
                frame.setRotation(0);
            }
            setDir(Direction.UP);
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

    public void setTankSpriteAnim(Array<Sprite> tankSpriteArr) {
        tankSpriteAnim = new Animation<>(GameConstants.TANK_ANIM_DURATION, tankSpriteArr);
        Direction oldDir = dir;
        dir = null;
        switch (oldDir) {
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
    }

    public Rectangle getBound() {
        bound.set(tankPos.getX(), tankPos.getY(), currentTankSprite.getWidth(), currentTankSprite.getHeight());
        return bound;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public abstract void getHit(int damage);
}

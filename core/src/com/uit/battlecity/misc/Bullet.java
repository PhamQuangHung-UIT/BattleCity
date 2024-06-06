package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pools;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.interfaces.Obstacle;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.tank.Enemy;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.tank.Tank;
import com.uit.battlecity.utils.GameConstants;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;
import com.uit.battlecity.utils.SoundManager;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class Bullet extends Actor {
    private float speed;
    private int damage;
    private Tank owner;
    private boolean canDestroySteel;
    private Direction direction;
    private Sprite sprite;
    private Point position;
    private ObstacleType hitObstacleType;

    public void initialize(Tank owner, Point position, Direction direction, float speed, int damage, boolean canDestroySteel) {
        this.speed = speed;
        this.damage = damage;
        this.canDestroySteel = canDestroySteel;
        this.position = position;
        this.direction = direction;
        hitObstacleType = ObstacleType.NONE;
        this.owner = owner;
        if (owner instanceof Player) {
            CollisionDetection.getInstance().getPlayerBulletList().add(this);
        } else {
            CollisionDetection.getInstance().getEnemyBulletList().add(this);
        }
        if (sprite == null) {
            sprite = new Sprite(new Texture("miscellaneous/bullet/bullet.png"));
            sprite.setScale(3);
        }
        switch (direction) {
            case UP:
                sprite.setRotation(0);
                break;
            case DOWN:
                sprite.setRotation(180);
                break;
            case LEFT:
                sprite.setRotation(90);
                break;
            default:
                sprite.setRotation(-90);
                break;
        }
    }

    @Override
    public void act(float delta) {
        boolean canDestroy;
        switch (direction) {
            case UP:
                canDestroy = checkAndDestroyHorizontalObstacle();
                if (canDestroy) {
                    destroy();
                } else position.setY(position.getY() + speed * delta);
                break;
            case DOWN:
                canDestroy = checkAndDestroyHorizontalObstacle();
                if (canDestroy) {
                    destroy();
                } else position.setY(position.getY() - speed * delta);
                break;
            case LEFT:
                canDestroy = checkAndDestroyVerticalObstacle();
                if (canDestroy) {
                    destroy();
                } else position.setX(position.getX() - speed * delta);
                break;
            case RIGHT:
                canDestroy = checkAndDestroyVerticalObstacle();
                if (canDestroy) {
                    destroy();
                } else position.setX(position.getX() + speed * delta);
                break;
        }
    }


    /**
     * Check and destroy collide obstacles horizontally
     *
     * @return true if successfully detect any obstacles
     */
    private boolean checkAndDestroyHorizontalObstacle() {
        PointInt cellPos = LevelScreen.getInstance().screenToCellPos(position);
        TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");
        Cell cell;
        TiledMapTile tile;
        ObstacleType type;
        boolean canDestroy = false;
        for (int i = 0; i < 2; i++) {
            cell = layer.getCell(cellPos.getX() - i, cellPos.getY());
            if (cell == null) {
                canDestroy = true;
                break;
            }
            tile = cell.getTile();
            type = ((Obstacle) tile).getObstacleType();
            if (type != ObstacleType.NONE && type != ObstacleType.RIVER) {
                canDestroy = checkColliderAndDestroyObstacle(cellPos, cell, tile, type);
            }
        }
        return canDestroy;
    }

    /**
     * Check and destroy collide obstacles vertically
     *
     * @return true if successfully detect any obstacles
     */
    private boolean checkAndDestroyVerticalObstacle() {
        PointInt cellPos = LevelScreen.getInstance().screenToCellPos(position);
        TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");
        Cell cell;
        TiledMapTile tile;
        ObstacleType type;
        boolean canDestroy = false;
        for (int i = 0; i < 2; i++) {
            cell = layer.getCell(cellPos.getX(), cellPos.getY() - i);
            if (cell == null) {
                canDestroy = true;
                break;
            }
            tile = cell.getTile();
            type = ((Obstacle) tile).getObstacleType();
            if (type != ObstacleType.NONE && type != ObstacleType.RIVER) {
                canDestroy = checkColliderAndDestroyObstacle(cellPos, cell, tile, type);
            }
        }
        return canDestroy;
    }

    private boolean checkColliderAndDestroyObstacle(PointInt cellPos, Cell cell, TiledMapTile tile, ObstacleType type) {
        TextureRegion region;
        Point offset;
        Rectangle rect;
        region = tile.getTextureRegion();
        offset = LevelScreen.getInstance().cellToScreenPos(cellPos);
        rect = new Rectangle(tile.getOffsetX() + offset.getX(),
                tile.getOffsetY() + offset.getY(),
                region.getRegionWidth(),
                region.getRegionHeight());
        if (sprite.getBoundingRectangle().overlaps(rect)) {
            hitObstacleType = hitObstacleType == ObstacleType.STEEL ? ObstacleType.STEEL : type;
            if (type != ObstacleType.STEEL) {
                if (type == ObstacleType.BASE) {
                    Base.destroyBase();
                    BigExplosion explosion = Pools.obtain(BigExplosion.class);
                    explosion.initialize(GameConstants.BASE_POSITION);
                    getStage().addActor(explosion);
                    ((LevelStage) getStage()).gameOver();
                } else {
                    Brick.destroyBrick(cell, direction);
                }
            } else {
                if (canDestroySteel) {
                    Steel.destroy(cell);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setCenter(position.getX(), position.getY());
        sprite.draw(batch);
    }

    public Direction getDirection() {
        return direction;
    }

    public void destroy() {
        if (owner instanceof Enemy)
            CollisionDetection.getInstance().getEnemyBulletList().removeValue(this, true);
        else {
            CollisionDetection.getInstance().getPlayerBulletList().removeValue(this, true);
            ((Player) owner).resetShootTrigger();
            switch (hitObstacleType) {
                case BRICK:
                    SoundManager.BRICK_HIT.play();
                    break;
                case STEEL:
                    SoundManager.STEEL_HIT.play();
                    break;
            }
        }
        SmallExplosion explosion = Pools.obtain(SmallExplosion.class);
        explosion.initialize(new Point(position.getX(), position.getY()));
        getStage().addActor(explosion);

        remove();
        Pools.free(this);
    }

    public Rectangle getBound() {
        float width = sprite.getWidth() * SCALE;
        float height = sprite.getHeight() * SCALE;
        return new Rectangle(position.getX() - width / 2, position.getY() - height / 2, width, height);
    }

    public int getDamage() {
        return damage;
    }
}

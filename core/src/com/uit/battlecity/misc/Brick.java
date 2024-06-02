package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.Direction;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.utils.ImportUtils;

public class Brick {
    private static Texture texture;

    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture(
                    ImportUtils.importPixmap(Gdx.files.internal("miscellaneous/brick/brick_8x8.png"), 3));
        }

        staticTile = new ObstacleStaticTiledMapTile(ObstacleType.BRICK, new TextureRegion(texture));
        return staticTile;
    }

    public static void destroyBrick(TiledMapTileLayer.Cell cell, Direction bulletDirection) {
        ObstacleType type = ((Obstacle) cell.getTile()).getObstacleType();
        if (type == ObstacleType.BRICK) {
            TextureRegion textureRegion = new TextureRegion(texture);
            switch (bulletDirection) {
                case UP:
                    textureRegion.setRegion(0, 0, texture.getWidth(), texture.getHeight() / 2);
                    cell.getTile().setOffsetX(0);
                    cell.getTile().setOffsetY(texture.getHeight() / 2f);
                    break;
                case DOWN:
                    textureRegion.setRegion(0, texture.getHeight() / 2, texture.getWidth(), texture.getHeight() / 2);
                    cell.getTile().setOffsetX(0);
                    cell.getTile().setOffsetY(0);
                    break;
                case LEFT:
                    textureRegion.setRegion(texture.getWidth() / 2, 0, texture.getWidth() / 2, texture.getHeight());
                    cell.getTile().setOffsetX(0);
                    cell.getTile().setOffsetY(0);
                    break;
                case RIGHT:
                    textureRegion.setRegion(0, 0, texture.getWidth() / 2, texture.getHeight());
                    cell.getTile().setOffsetX(texture.getWidth() / 2f);
                    cell.getTile().setOffsetY(0);
                    break;
            }
            cell.getTile().setTextureRegion(textureRegion);
            ((Obstacle) cell.getTile()).setObstacleType(ObstacleType.BRICK_HALF);
        } else {
            cell.setTile(Space.create());
            cell.getTile().setOffsetX(0);
            cell.getTile().setOffsetY(0);
        }

    }
}

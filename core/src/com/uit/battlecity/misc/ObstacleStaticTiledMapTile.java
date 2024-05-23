package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.ObstacleType;

public class ObstacleStaticTiledMapTile extends StaticTiledMapTile implements Obstacle{
    ObstacleType type;

    public ObstacleStaticTiledMapTile(ObstacleType type, TextureRegion textureRegion) {
        super(textureRegion);
        this.type = type;
    }

    public ObstacleStaticTiledMapTile(ObstacleType type, Texture texture) {
        super(new TextureRegion(texture));
        this.type = type;
    }

    @Override
    public ObstacleType getObstacleType() {
        return type;
    }
}

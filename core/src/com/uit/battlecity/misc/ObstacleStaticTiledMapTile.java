package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.interfaces.Obstacle;

public class ObstacleStaticTiledMapTile extends StaticTiledMapTile implements Obstacle {
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

    @Override
    public void setObstacleType(ObstacleType type) {
        this.type = type;
    }


}

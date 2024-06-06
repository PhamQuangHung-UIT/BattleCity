package com.uit.battlecity.misc;

import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.interfaces.Obstacle;

public class ObstacleAnimatedTiledMapTile extends AnimatedTiledMapTile implements Obstacle {
    ObstacleType type;

    public ObstacleAnimatedTiledMapTile(ObstacleType type, float interval, Array<StaticTiledMapTile> array) {
        super(interval, array);
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

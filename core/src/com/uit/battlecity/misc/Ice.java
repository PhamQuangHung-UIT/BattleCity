package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;

public class Ice {
    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        Texture texture = new Texture("miscellaneous/ice/ice_8x8.png");
        Sprite sprite = new Sprite(texture);
        sprite.scale(3);

        staticTile = new ObstacleStaticTiledMapTile(ObstacleType.ICE, sprite);
        return staticTile;
    }
}

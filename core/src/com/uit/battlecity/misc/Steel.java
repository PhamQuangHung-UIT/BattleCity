package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;

public class Steel {
    public static Texture texture;

    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture("miscellaneous/steel/steel_8x8.png");
        }

        staticTile = new ObstacleStaticTiledMapTile(ObstacleType.STEEL, new TextureRegion(texture));
        return staticTile;
    }

    public static void destroy(TiledMapTileLayer.Cell cell) {
        cell.setTile(Space.create());
    }
}

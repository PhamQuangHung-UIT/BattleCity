package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;

public class Space {
    static Texture texture;

    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture("miscellaneous/space/space.png");
        }

        staticTile = new ObstacleStaticTiledMapTile(ObstacleType.NONE, new TextureRegion(texture));
        return staticTile;
    }
}

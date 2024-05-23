package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.ObstacleType;

public class Brick {
    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        Texture texture = new Texture("miscellaneous/brick/brick_8x8.png");

        staticTile = new StaticTiledMapTile(new TextureRegion(texture));
        return staticTile;
    }
}

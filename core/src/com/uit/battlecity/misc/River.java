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

public class River {
    public static TiledMapTile create() {
        Array<StaticTiledMapTile> tiles = new Array<>();

        FileHandle handle = Gdx.files.internal("miscellaneous/river");
        for (FileHandle file : handle.list()) {
            tiles.add(new StaticTiledMapTile(new TextureRegion(new Texture(file))));
        }

        return new ObstacleAnimatedTiledMapTile(ObstacleType.RIVER, 0.5f, tiles);
    }
}

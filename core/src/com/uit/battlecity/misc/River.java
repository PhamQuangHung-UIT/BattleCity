package com.uit.battlecity.misc;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.utils.ImportUtils;

public class River {
    static Array<StaticTiledMapTile> tiles = new Array<>();

    public static TiledMapTile create() {

        if (tiles.isEmpty()) {
            for (FileHandle file : ImportUtils.list("miscellaneous/river")) {
                Texture texture = new Texture(file);
                tiles.add(new StaticTiledMapTile(new TextureRegion(texture)));
            }
        }

        return new ObstacleAnimatedTiledMapTile(ObstacleType.RIVER, 0.5f, tiles);
    }
}

package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.utils.ImportUtils;

public class Space {
    static Texture texture;

    public static TiledMapTile create() {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture(
                    ImportUtils.importPixmap(Gdx.files.internal("miscellaneous/space/space.png"), 3));
        }

        staticTile = new ObstacleStaticTiledMapTile(ObstacleType.NONE, new TextureRegion(texture));
        return staticTile;
    }
}

package com.uit.battlecity.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.utils.SoundManager;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Base {
    private static Texture texture, destroyedBaseTexture;

    public static void create(TiledMapTileLayer layer) {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture("miscellaneous/base/base.png");
            destroyedBaseTexture = new Texture("miscellaneous/base/destroyed_base.png");
        }
        for (int i = 14; i < 16; i++)
            for (int j = 2; j > 0; j--) {
                staticTile = new ObstacleStaticTiledMapTile(ObstacleType.BASE,
                        new TextureRegion(texture,
                                (i - 14) * texture.getWidth() / 2,
                                (2 - j) * texture.getHeight() / 2,
                                texture.getWidth() / 2,
                                texture.getHeight() / 2));
                layer.setCell(i, j, new Cell().setTile(staticTile));
            }
    }

    public static void destroyBase() {
        TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");
        SoundManager.BASE_EXPLOSION.play();

        for (int i = 14; i < 16; i++)
            for (int j = 2; j > 0; j--) {
                layer.getCell(i, j).getTile().getTextureRegion().setTexture(destroyedBaseTexture);
            }
    }
}

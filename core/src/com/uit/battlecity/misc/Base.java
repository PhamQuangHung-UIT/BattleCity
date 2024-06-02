package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.uit.battlecity.enums.ObstacleType;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.utils.ImportUtils;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Base {
    private static Texture texture, destroyedBaseTexture;
    private static Base instance;


    public static Base getInstance() {
        if (instance == null) {
            instance = new Base();
        }
        return instance;
    }

    public static void create(TiledMapTileLayer layer) {
        StaticTiledMapTile staticTile;

        if (texture == null) {
            texture = new Texture(
                    ImportUtils.importPixmap(Gdx.files.internal("miscellaneous/base/base.png"), 3));
            destroyedBaseTexture = new Texture(ImportUtils.importPixmap(Gdx.files.internal("miscellaneous/base/destroyed_base.png"), 3));
        }
        for (int i = 14; i < 16; i++)
            for (int j = 2; j > 0; j--) {
                staticTile = new ObstacleStaticTiledMapTile(ObstacleType.BASE,
                        new TextureRegion(texture,
                                (i - 14) * texture.getWidth() / 2,
                                (2 - j) * texture.getHeight() / 2,
                                texture.getWidth() / 2,
                                texture.getHeight() / 2));
                staticTile.getProperties().put("base", getInstance());
                layer.setCell(i, j, new Cell().setTile(staticTile));
            }
    }

    public static void destroyBase() {
        TiledMapTileLayer layer = (TiledMapTileLayer) LevelScreen.getInstance().getLevelMap().getMap().getLayers().get("block");

        for (int i = 14; i < 16; i++)
            for (int j = 2; j > 0; j--) {
                layer.getCell(i, j).getTile().getTextureRegion().setTexture(destroyedBaseTexture);
            }
    }
}

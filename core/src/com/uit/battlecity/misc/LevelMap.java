package com.uit.battlecity.misc;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelMap extends Actor {
    TiledMap map;

    public LevelMap(int level) {
        map = new TiledMap();
        TiledMapTileLayer blockLayer = new TiledMapTileLayer(26, 26, 8, 8);
        blockLayer.setName("block");
        TiledMapTileLayer iceLayer = new TiledMapTileLayer(26, 26, 8, 8);
        iceLayer.setName("ice");
        TiledMapTileLayer bushLayer = new TiledMapTileLayer(26, 26, 8, 8);
        bushLayer.setName("bush");
        for (int x = 0; x < 26; x++) {
            for (int y = 0; y < 26; y++) {
                // layer.getCell(x, y).setTile(TiledMapTile);
            }
        }
        map.getLayers().add(iceLayer);
        map.getLayers().add(blockLayer);
        map.getLayers().add(bushLayer);
    }
}
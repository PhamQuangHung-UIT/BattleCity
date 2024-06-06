package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelMap extends Actor {
    private final TiledMap map = new TiledMap(), bushMap = new TiledMap();

    public LevelMap(int level) {
        FileHandle file = Gdx.files.internal("levels/" + level + "/map");
        TiledMapTileLayer blockLayer = new TiledMapTileLayer(32, 28, 8, 8);
        blockLayer.setName("block");
        TiledMapTileLayer iceLayer = new TiledMapTileLayer(32, 28, 8, 8);
        iceLayer.setName("ice");
        TiledMapTileLayer bushLayer = new TiledMapTileLayer(32, 28, 8, 8);
        bushLayer.setName("bush");

        String[] data = file.readString().split("\r\n");
        for (int y = 0; y < 26; y++) {
            for (int x = 0; x < 26; x++) {
                char blockChar = data[y].charAt(x);
                switch (blockChar) {
                    case '#':
                        blockLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Brick.create()));
                        break;
                    case '~':
                        blockLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(River.create()));
                        break;
                    case '/':
                        bushLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Bush.create()));
                        blockLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Space.create()));
                        break;
                    case '@':
                        blockLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Steel.create()));
                        break;
                    case '_':
                        iceLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Ice.create()));
                        blockLayer.setCell(x + 2, 26 - y,new TiledMapTileLayer.Cell().setTile(Space.create()));
                        break;
                    default:
                        blockLayer.setCell(x + 2, 26 - y, new TiledMapTileLayer.Cell().setTile(Space.create()));
                        break;
                }
            }
        }
        Base.create(blockLayer);
        map.getLayers().add(iceLayer);
        map.getLayers().add(blockLayer);
        bushMap.getLayers().add(bushLayer);
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMap getBushMap() {
        return bushMap;
    }
}
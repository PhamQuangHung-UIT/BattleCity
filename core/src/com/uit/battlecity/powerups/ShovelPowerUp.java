package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.uit.battlecity.misc.Brick;
import com.uit.battlecity.misc.LevelMap;
import com.uit.battlecity.misc.Steel;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.PointInt;
import com.uit.battlecity.utils.SoundManager;


public class ShovelPowerUp extends PowerUp {
    public static final PointInt[] baseSurroundedBlockPositions = new PointInt[]{
            new PointInt(13, 1),
            new PointInt(13, 2),
            new PointInt(13, 3),
            new PointInt(14, 3),
            new PointInt(15, 3),
            new PointInt(16, 3),
            new PointInt(16, 2),
            new PointInt(16, 1),
    };
    public final Texture texture = new Texture("miscellaneous/power_up/shovel.png");
    private Task currentTask;

    public ShovelPowerUp(Point position) {
        create(position, texture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.BONUS.play();
        LevelMap map = LevelScreen.getInstance().getLevelMap();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getMap().getLayers().get("block");
        for (PointInt position : baseSurroundedBlockPositions) {
            layer.getCell(position.getX(), position.getY()).setTile(Steel.create());
        }
        if (currentTask != null) {
            currentTask.cancel();
        }
        currentTask = Timer.schedule(new Task() {
            @Override
            public void run() {
                for (PointInt position : baseSurroundedBlockPositions) {
                    layer.getCell(position.getX(), position.getY()).setTile(Brick.create());
                }
            }
        }, 8f);
    }
}

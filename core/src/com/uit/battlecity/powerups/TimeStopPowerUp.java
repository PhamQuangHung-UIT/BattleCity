package com.uit.battlecity.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.uit.battlecity.misc.EnemyGroup;
import com.uit.battlecity.misc.LevelStage;
import com.uit.battlecity.tank.Player;
import com.uit.battlecity.utils.Point;
import com.uit.battlecity.utils.SoundManager;


public class TimeStopPowerUp extends PowerUp {
    public final Texture texture = new Texture("miscellaneous/power_up/time.png");
    private static Timer.Task currentTask;

    public TimeStopPowerUp(Point position) {
        create(position, texture);
    }

    @Override
    public void handle(Player player) {
        SoundManager.BONUS.play();
        EnemyGroup enemyGroup = ((LevelStage) getStage()).getEnemyGroup();
        enemyGroup.setTimeFreeze(true);
        if (currentTask != null) {
            currentTask.cancel();
        }
        currentTask = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                enemyGroup.setTimeFreeze(false);
            }
        }, 8f);
    }
}

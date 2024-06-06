package com.uit.battlecity.misc;

import com.badlogic.gdx.scenes.scene2d.Group;

public class EnemyGroup extends Group {
    private boolean isTimeFreeze = false;

    @Override
    public void act(float delta) {
        if (!isTimeFreeze) {
            super.act(delta);
        }
    }

    public void setTimeFreeze(boolean timeFreeze) {
        isTimeFreeze = timeFreeze;
    }
}

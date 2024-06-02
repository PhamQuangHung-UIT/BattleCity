package com.uit.battlecity.misc;

import com.badlogic.gdx.math.Rectangle;
import com.uit.battlecity.enums.ObstacleType;

public interface Obstacle {
    ObstacleType getObstacleType();

    void setObstacleType(ObstacleType type);
}

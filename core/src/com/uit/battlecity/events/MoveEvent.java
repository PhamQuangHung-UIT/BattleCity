package com.uit.battlecity.events;

import com.uit.battlecity.enums.Direction;

public class MoveEvent extends TankEvent {
    Direction direction;
    float delta;

    public MoveEvent(Direction direction, float delta) {
        this.direction = direction;
        this.delta = delta;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getDelta() {
        return delta;
    }
}

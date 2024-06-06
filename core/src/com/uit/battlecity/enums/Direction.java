package com.uit.battlecity.enums;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static Direction getRandom(Direction exclude) {
        Direction result;
        do {
            result = getRandom();
        } while (result == exclude);
        return result;
    }
}
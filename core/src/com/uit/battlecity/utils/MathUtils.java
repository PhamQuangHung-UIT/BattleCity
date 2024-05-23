package com.uit.battlecity.utils;

public class MathUtils {
    public static int roundInt(float value, int unitValue) {
        return Math.round(value / unitValue) * unitValue;
    }

    public static float roundFloat(float value, float unitValue) {
        return Math.round(value / unitValue) * unitValue;
    }
}

package com.uit.battlecity.events;

public class ShootEvent extends TankEvent {
    private final float speed;

    private final boolean destroySteel;

    private final int damage;

    public ShootEvent(int damage, float speed, boolean canDestroySteel) {
        this.speed = speed;
        this.damage = damage;
        this.destroySteel = canDestroySteel;
    }

    /** Get the current bullet speed
     * @return The current bullet's speed
     */
    public float getSpeed() {
        return speed;
    }

    /** The ability of the bullet to destroy steel wall
     * @return true if the bullet can destroy the steel wall
     */

    public boolean canDestroySteel() {
        return destroySteel;
    }

    public int getDamage() {
        return damage;
    }
}

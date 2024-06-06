package com.uit.battlecity.events;

public class ShootEvent extends TankEvent {

    private final boolean destroySteel;

    private final int damage;

    public ShootEvent(int damage, boolean canDestroySteel) {
        this.damage = damage;
        this.destroySteel = canDestroySteel;
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

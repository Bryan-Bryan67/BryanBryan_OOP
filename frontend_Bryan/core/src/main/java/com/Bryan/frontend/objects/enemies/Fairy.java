package com.Bryan.frontend.objects.enemies;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.objects.Collidable;
import com.Bryan.frontend.objects.patterns.entityStrategy.LinearEntityMovement;
import com.Bryan.frontend.objects.patterns.shootingStrategy.FrontShot;
import com.badlogic.gdx.graphics.Color;

public class Fairy extends Enemy {
    private float shootTimer = 0f;
    private float shootDelay = 0.67f;
    private float shootInterval = 1.4f;
    private int maxShots = 2;
    private int shotsFired = 0;
    public Fairy(String name, int hp) {
        super(150, 380, 24, 24,Color.PINK, name,hp ,500L);
        // TODO (1 point): Inject default strategies FrontShot(150f, 10) and LinearEntityMovement(0f, -40f)
        shootingPattern=new FrontShot(150f, 10);
        movementPattern=new LinearEntityMovement(0f, -40f);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24,Color.PINK, name,hp,500L);
        // TODO: Inject the same default strategies
        shootingPattern=new FrontShot(150f, 10);
        movementPattern=new LinearEntityMovement(0f, -40f);
    }

    public Fairy(float x, float y, String name, int hp, float shootDelay, float shootInterval, int maxShots) {
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
        this.shootDelay = shootDelay;
        this.shootInterval = shootInterval;
        this.maxShots = maxShots;
        // TODO (2 points): Inject default strategies FrontShot and LinearEntityMovement
        shootingPattern=new FrontShot(150f, 10);
        movementPattern=new LinearEntityMovement(0f, -40f);
    }
    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other.getClass()==Player.class){
            System.out.println("Player touches fairy");
        }
        // TODO: Print "Player touches fairy"
    }

    public boolean canShoot(float delta) {
        shootTimer += delta;
        if (shotsFired == 0) {
            if (shootTimer >= shootDelay) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        } else if (shotsFired < maxShots) {
            if (shootTimer >= shootInterval) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        }
        return false;
    }

    public void setShootTiming(float shootDelay, float shootInterval, int maxShots) {
        this.shootDelay = shootDelay;
        this.shootInterval = shootInterval;
        this.maxShots = maxShots;
    }

    public int getShotsFired() { return shotsFired; }
    public float getShootTimer() { return shootTimer; }
    public float getShootDelay() { return shootDelay; }
    public float getShootInterval() { return shootInterval; }
    public int getMaxShots() { return maxShots; }
}

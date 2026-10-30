package com.Bryan.frontend.objects.enemies;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.objects.Collidable;
import com.Bryan.frontend.objects.patterns.ShootingPattern;
import com.Bryan.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Bryan.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy{
    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
    //       and immediately initialize it with the `SpreadShot` we just created.
    //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.

    private ShootingPattern shootingPattern=new SpreadShot(200f, 3, 30f);

    public Boss(String name, int hp) {
        super(380, 400, 48, 48,Color.BLUE,name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48,Color.BLUE, name,hp,5000L);
    }

    @Override
    public void onCollision(Collidable other) {
        if(other.getClass()== Player.class){
            System.out.println("Player touches boss");
        }
    }

    // TODO: Create the shootBullet(BulletManager bulletManager) method:
    //       If shootingPattern is not null and the boss hasn't been destroyed,
    //       execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).

    public void shootBullet(BulletManager bulletManager){
        if (shootingPattern!=null&&!isDestroyed()){
            shootingPattern.execute(x+width/2-8, y, bulletManager, false);
        }
    }

    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }
    public void setShootingPattern(ShootingPattern shootingPattern){
        this.shootingPattern=shootingPattern;
    }
}

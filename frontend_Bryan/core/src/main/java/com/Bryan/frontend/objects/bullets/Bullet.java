package com.Bryan.frontend.objects.bullets;
import com.Bryan.frontend.objects.Collidable;
import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.enemies.Enemy;
import com.Bryan.frontend.systems.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject{
    private BulletType bulletType;
    private int damage;
    // TODO: Declare the following private attributes:
    // 1. float vx and vy (2D velocity vector components)
    private float vx;
    private float vy;
    // 2. float angle initialized to 90f (visual rotation angle in degrees)
    private float angle=90f;
    // 3. boolean isPlayerBullet (flag indicating whether this bullet belongs to the player or an enemy)
    private boolean isPlayerBullet;
    // 4. boolean grazed (flag indicating whether this bullet has already been grazed by the player)
    private boolean grazed;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);
        this.bulletType=bulletType;
        this.damage=damage;
        this.vx=0f;
        this.vy=400f;
        this.angle=90f;
        this.isPlayerBullet=true;
        this.grazed=false;
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 16, 16, speed, Color.YELLOW);
        this.bulletType=bulletType;
        this.damage=damage;
        this.vx=0f;
        if (speed<0){
            this.angle=270f;
        } else{
            this.angle=90f;
        }
        this.isPlayerBullet=true;
        this.grazed=false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //ASK L8R TO REMOVE OR KEEP THIS
        y+=speed*delta;

        x+=vx*delta;
        y+=vy*delta;
    }

    public BulletType getBulletType(){
        return bulletType;
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            System.out.println("Bullet hit " + enemy.name+ " for " +damage+ " DMG!");
            enemy.takeDamage(getDamage());
            AssetManager.getInstance().playSound("enemy_shot");
            this.destroy();
        }
    }

    // Pool Re-initialization for the Object Pool Pattern
    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // TODO:
        // 1. Update position (x, y) and velocity vector components (vx, vy) from the argument parameters
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        // 2. Calculate the visual rotation angle using Math.atan2 as explained above, then convert it to degrees
        this.angle=(float)Math.toDegrees(Math.atan2(vy, vx));
        // 3. Update speed by calculating the resultant length of both velocity vectors using Math.hypot as explained above
        this.speed=(float)Math.hypot(vx, vy);
        // 4. Save the bullet's identity attributes (bulletType, damage, and isPlayerBullet) from the parameters
        this.bulletType=bulletType;
        this.damage=damage;
        this.isPlayerBullet=isPlayerBullet;
        // 5. Reactivate this bullet and reset its animation state time from 0
        this.active=true;
        this.stateTime=0f;
        // 6. Make sure the grazed status is reset to inactive so this recycled bullet can be grazed again
        this.grazed=false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                // TODO: Draw the bullet sprite using batch.draw() with the following parameters:
                // sprite, position (x, y), rotation pivot origin at center (width / 2f, height / 2f),
                // dimensions (width, height), scale (1f, 1f), and rotation angle (angle - 90f)
                batch.draw(sprite, x, y, width/2f, height/2f, width, height, 1f, 1f, angle-90f);
            } else {
                super.render(batch);
            }
        }
    }

    // TODO:
    // 1. Create getters for isPlayerBullet, vx, and vy
    public boolean getIsPlayerBullet() {
        return isPlayerBullet;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }
    // 2. Create a getter and setter for angle
    public float getAngle() {
        return angle;
    }
    public void setAnglt(float angle){
        this.angle=angle;
    }
    // 3. Create a getter hasBeenGrazed() that returns the grazed attribute
    public boolean hasBeenGrazed() {
        return grazed;
    }
    // 4. Create a setter setGrazed(boolean grazed)

    public void setGrazed(boolean grazed){
        this.grazed=grazed;
    }
}


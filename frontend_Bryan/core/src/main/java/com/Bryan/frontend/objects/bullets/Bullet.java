package com.Bryan.frontend.objects.bullets;
import com.Bryan.frontend.objects.Collidable;
import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.enemies.Enemy;
import com.Bryan.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Bryan.frontend.systems.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject{
    private BulletType bulletType;
    private int damage;
    private float vx;
    private float vy;
    private float angle=90f;
    private boolean isPlayerBullet;
    private boolean grazed;
    private BulletMovementPattern movementPattern;


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
        // TODO (6 points):
        // Check whether movementPattern is not null:
        // - If present (not null): delegate movement to movementPattern.move(this, delta);
        // - If null: retain default movement (add vx * delta to x and vy * delta to y)
        if (movementPattern!=null){
            movementPattern.move(this, delta);
        }
        else {
            x+=vx*delta;
            y+=vy*delta;
        }
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
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        this.angle=(float)Math.toDegrees(Math.atan2(vy, vx));
        this.speed=(float)Math.hypot(vx, vy);
        this.bulletType=bulletType;
        this.damage=damage;
        this.isPlayerBullet=isPlayerBullet;
        this.active=true;
        this.stateTime=0f;
        this.grazed=false;
        this.movementPattern = null;
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
    public BulletMovementPattern getMovementPattern() {
        return movementPattern;
    }
    public void setMovementPattern(BulletMovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }
}


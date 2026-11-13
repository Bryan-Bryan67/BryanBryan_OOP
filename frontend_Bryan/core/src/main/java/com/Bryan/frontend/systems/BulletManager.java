package com.Bryan.frontend.systems;

import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.bullets.Bullet;
import com.Bryan.frontend.objects.bullets.BulletType;
import com.Bryan.frontend.objects.items.Item;
import com.Bryan.frontend.objects.items.ItemType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.Bryan.frontend.objects.patterns.bulletStrategy.HomingBulletMovement;
import java.util.*;

import static com.Bryan.frontend.systems.EntityFactory.createPlayerBullet;

public class BulletManager {
    // 1. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;
    // 2. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;
    private List<GameObject> entities;
    public BulletManager() {
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_amulet"; // Add local String variable key
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key)); // Add setSprite using key
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage, key); // Add key as 4th argument
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        }
        activePlayerBullets.add(bullet);
        return bullet;
    }


    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        return spawnPlayerBullet(x, y, vx, vy, damage, "bullet_amulet");
    }


    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage, String spriteKey) {
        String key = (spriteKey != null) ? spriteKey : "bullet_danmaku";
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key));
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage, key);
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        }
        activeEnemyBullets.add(bullet);
        return bullet;
    }


    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        return spawnEnemyBullet(x, y, vx, vy, damage, "bullet_danmaku");
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Player bullet recycling & movement
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            if (entities != null && bullet.getMovementPattern() instanceof HomingBulletMovement homing && homing.getEntities() == null) {
                homing.setEntities(entities);
            }
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.destroy();
                pIter.remove();
                playerBulletPool.offer(bullet);
            }
        }

        // 2. Enemy bullet recycling & movement
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();
        while (eIter.hasNext()) {
            Bullet bullet = eIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.destroy();
                eIter.remove();
                enemyBulletPool.offer(bullet);
            }
        }
    }

    public void freePlayerBullet(Bullet bullet) {
        if (activePlayerBullets.remove(bullet)) {
            bullet.destroy();
            playerBulletPool.offer(bullet);
        }
    }

    public void freeEnemyBullet(Bullet bullet) {
        if (activeEnemyBullets.remove(bullet)) {
            bullet.destroy();
            enemyBulletPool.offer(bullet);
        }
    }


    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
            Iterator<Bullet> eIter = activeEnemyBullets.iterator();
            while (eIter.hasNext()) {
                Bullet bullet = eIter.next();
                if (entities!=null){
                    Item pointItem=EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT);
                    entities.add(pointItem);
                }
                bullet.destroy();
                eIter.remove();
                enemyBulletPool.offer(bullet);
            }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    // Clear all player bullets when restarting the game (Copy-Paste):
    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }

    public List<GameObject> getEntities() { return entities; }
    public void setEntities(List<GameObject> entities) { this.entities = entities; }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy, List<GameObject> entities) {
        int orbCount = 8;
        int orbDamage = 35; // 35 damage x 8 orbs = 280 total spell card damage potential!

        List<GameObject> targetEntities = (entities != null) ? entities : this.entities;
        // TODO (2 points): Apply Strategy Composition — instantiate a RingShot object with bulletSpeed 150f, bullet count orbCount, damage orbDamage, and bullet movement strategy FantasySealMovement(targetEnemy, targetEntities), then call execute(originX, originY, this, true)
        RingShot(150f, orbCount, orbDamage, FantasySealMovement(targetEnemy, targetEntites), execute(originX, originY, this, true));
        System.out.println("[BulletManager] Spawned 8 Bomb Spirit Orbs via RingShot composed with FantasySealMovement!");
    }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy) {
        spawnBombOrbs(originX, originY, targetEnemy, null);
    }
}


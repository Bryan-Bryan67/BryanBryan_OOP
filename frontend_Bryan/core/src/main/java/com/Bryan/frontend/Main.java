package com.Bryan.frontend;

import com.Bryan.frontend.commands.InputHandler;
import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.objects.bullets.Bullet;
import com.Bryan.frontend.objects.enemies.Boss;
import com.Bryan.frontend.objects.enemies.Fairy;
import com.Bryan.frontend.objects.items.Item;
import com.Bryan.frontend.objects.items.ItemType;
import com.Bryan.frontend.systems.AssetManager;
import com.Bryan.frontend.systems.BulletManager;
import com.Bryan.frontend.systems.CollisionReferee;
import com.Bryan.frontend.systems.EntityFactory;
import com.Bryan.frontend.ui.GameHUD;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Input;
import java.util.Iterator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private SpriteBatch batch;
    private List<GameObject> entities;
    // TODO: Create a private field to store BulletManager
    private BulletManager bulletManager;
    // TODO: Create a private field to store CollisionReferee
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;
    // TODO: Declare private fields InputHandler inputHandler and GameHUD gameHUD
    InputHandler inputHandler;
    GameHUD gameHUD;


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch=new SpriteBatch();
        fairy=new ArrayList<>();
        entities=new ArrayList<>();
        // TODO: instantiate a new BulletManager
        bulletManager=new BulletManager();
        // TODO: instantiate a new CollisionReferee
        collisionReferee=new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        inputHandler=new InputHandler();
        gameHUD=new GameHUD();
        AssetManager.getInstance().init();
        AssetManager.getInstance().playMusic("BGM_1", true);

        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);
        player.registerObserver(gameHUD);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
        inputHandler.setEntites(entites);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.shootBullet(bulletManager);
            AssetManager.getInstance().playSound("player_shoot");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            restartGame();
        }
        updateEnemyScheduler(delta);
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        collisionReferee.resolveCollisions(player, entities, bulletManager);
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
    }


    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove();
            }
        }
    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

}

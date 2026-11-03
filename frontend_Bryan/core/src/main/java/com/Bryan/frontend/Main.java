package com.Bryan.frontend;

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



    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch=new SpriteBatch();
        fairy=new ArrayList<>();
        entities=new ArrayList<>();
        AssetManager.getInstance().init();
        AssetManager.getInstance().playMusic("BGM_1", true);
        player=EntityFactory.createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);
        Fairy redFairy=EntityFactory.createFairy(150, 380, "Red Fairy", 20);
        Fairy blueFairy=EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue");
        boss=EntityFactory.createBoss(380, 400, "Rumia", 150);
        powerItem=EntityFactory.createItem(200, 450, ItemType.POWER);
        pointItem=EntityFactory.createItem(320, 480, ItemType.POINT);
        fairy.add(redFairy);
        fairy.add(blueFairy);
        entities.add(player);
        entities.add(redFairy);
        entities.add(blueFairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        // TODO: instantiate a new BulletManager
        bulletManager=new BulletManager();
        // TODO: instantiate a new CollisionReferee
        collisionReferee=new CollisionReferee();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.shootBullet(bulletManager);
            AssetManager.getInstance().playSound("player_shoot");
        }
        updateEnemyScheduler(delta);
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        collisionReferee.resolveCollisions(player, entities, bulletManager);
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
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

        // TODO: Call dispose on AssetManager to release the loaded Textures as well.
        AssetManager.getInstance().dispose();
    }


    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        // 1. Get an Iterator<T> from the given list.
        Iterator<T> iterator=list.iterator();
        // 2. While there are still elements available (hasNext()):
        //    a. Get the current element using next() and store it in a variable of type T.
        //    b. Call update(delta) on the element.
        //    c. If the element is off-screen (isOffScreen(screenWidth, screenHeight))
        //       OR isDestroyed():
        //       - Display the message: "Removed via Generic Iterator: " + [entity class name, using getClass().getSimpleName()]
        //       - Remove the element from the list using the Iterator's method
        //         (NOT list.remove()!).
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

}

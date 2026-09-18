package com.Bryan.frontend;

import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.enemies.Boss;
import com.Bryan.frontend.objects.enemies.Fairy;
import com.Bryan.frontend.objects.items.Item;
import com.Bryan.frontend.objects.items.ItemType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>
    private Player player;
    private Fairy fairy;
    private Boss boss;
    private Item item;
    private List<GameObject> gameObjects;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        gameObjects = new ArrayList<>();

        // TODO 2: Instantiate Player (Red square) at (280, 40)
        player =new Player("Reimu Hakurei", 100, 15, 3);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        fairy=new Fairy("Stage 1 Fairy", 20);

        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        boss=new Boss("Cirno (Stage 2 Boss)", 150);

        // TODO 5: Instantiate Items (White squares) with downward speeds
        item=new Item(16, 16, Color.WHITE);

        // TODO 6: Add all entities into the gameObjects list polymorphically
        gameObjects.add(player);
        gameObjects.add(fairy);
        gameObjects.add(boss);
        gameObjects.add(item);

        powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Polymorphic Update Loop: Items move downward automatically via Item.update(delta)
        for (GameObject obj : gameObjects) {
            obj.update(delta);
        }

        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                // TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
                if (a.getCoreHitbox().overlaps(b.getCoreHitbox())){
                    a.onCollison(a);
                    b.onCollison(b);
                }
                // TODO: Call a.onCollision(b) and b.onCollision(a)
            }
        }
        // 2. Clear Screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 3. Polymorphic Render Loop: Draw hitboxes with ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : gameObjects) {
            obj.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

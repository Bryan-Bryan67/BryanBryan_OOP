package com.Bryan.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    // 1. Single instance for the Singleton pattern
    private static AssetManager instance;

    // 2. Caches for the Flyweight pattern
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    // Find out: why is the AssetManager constructor *private*?
    private AssetManager() {
        // TODO: Initialize the three Maps above as empty HashMaps
        textureRegionMap=new HashMap<>();
        animationMap=new HashMap<>();
        textureMap=new HashMap<>();
    }

    // Global access point for the single instance
    public static AssetManager getInstance() {
        // TODO: If instance is still null, create a new instance (Lazy Initialization)
        // Return the instance reference
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        // TODO:
        if (textureMap.containsKey(filename)){
            return textureMap.get(filename);
        }
        if(Gdx.files!=null && Gdx.files.internal(filename).exists()){
            Texture texture=new Texture(Gdx.files.internal(filename));
            textureMap.put(filename, texture);
            return texture;
        }
        else {
            return null;
        }
    }
// ========================================================================
// Register the Region Textures
// ========================================================================

    // Register a single region
    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    // Extract a specific cell from the sprite sheet at [row][col]
    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            // TODO: Store the region grid[row][col] in textureRegionMap under this key
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    // Convenience overload: register an animation starting at column 0 with PlayMode.LOOP
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    // Register a sequence of horizontal frames as an Animation object
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames=new TextureRegion[numFrames];
            for (int i = 0; i<numFrames; i++){
                frames[i]=grid[row][startCol+i];
            }
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key,anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

    // Overload for flipped animations (e.g., facing left using a horizontal flip)
    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames=new TextureRegion[numFrames];
            for (int i = 0; i<numFrames; i++){
                TextureRegion frame=new TextureRegion(grid[row][startCol+i]);
                frame.flip(flipX, flipY);
                frames[i]=frame;
            }
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key,anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

// ========================================================================
// Getting the Region Textures
// ========================================================================

    // Retrieve a region by key
    public TextureRegion getTextureRegion(String key) {
        return textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    // Retrieve an animation by key
    public Animation<TextureRegion> getAnimation(String key) {
        return animationMap.get(key);
    }

    public void init() {
        // Tip 1: Explore values for row, startCol, numFrames, frameDuration, and Animation.PlayMode that look good to you.
        // Tip 2: Among the .png files registered below, which sprite sheets contain *animation* frames, and which contain *static images*? What does this mean for how you use them?

        // TODO: Register Reimu Hakurei's idle animation (player.png: 32x48 per cell)
            registerAnimationFromSheet("player_idle","player.png", 32,48,8,8,0.125f);
        // TODO: Register the Boss's idle animation (rumia.png: 64x64 per cell)
            registerAnimationFromSheet("boss_idle","rumia.png", 64,64,0,4,0.2f);
        // TODO: Register the Fairy animations
            registerAnimationFromSheet("Fairy","fairy.png", 32,32, 8, 8, 8);
        // TODO: Register the enemy bullet (bullets_small.png: 16x16 cell at row 2, column 3)
            registerRegionFromSheet("bullet","bullets_small.png", 16,15, 2, 3);
        // TODO: Register the 4 Item variants (items.png: 16x16 per cell)
            registerRegionFromSheet("Item 1","items.png", 16,16, 1, 0);
            registerRegionFromSheet("Item 2","items.png", 16,16, 1, 1);
            registerRegionFromSheet("Item 3","items.png", 16,16, 1, 2);
            registerRegionFromSheet("Item 4","items.png", 16,16, 1, 3);

         // TODO IN CS
            registerAnimationFromSheet("player_left","player.png", 32,48,1,4,0.12f);
            registerAnimationFromSheet("player_right","player.png", 32,48,2,4,0.12f);
            registerAnimationFromSheet("boss_left","rumia.png", 64,64,1,4,0.2f);
            registerAnimationFromSheet("boss_right","rumia.png", 64,64,2,4,0.2f);
            registerAnimationFromSheet("fairy_idle_red","fairy.png", 32,32, 1, 8, 0.125f);
            registerAnimationFromSheet("fairy_idle_blue","fairy.png", 32,32, 0, 8, 0.125f);
    }


    public void dispose() {
        // TODO: Release the VRAM resources of all loaded Textures, then clear all Maps
        for(Texture texture:textureMap.values()){
            texture.dispose();
        }
        textureRegionMap.clear();
        animationMap.clear();
        textureMap.clear();
    }
}

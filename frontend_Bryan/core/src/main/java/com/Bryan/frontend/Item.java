package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Item extends GameObject{
    private String itemType;
    private long scoreValue;
//Pre-CS2 answer Q9:I think it needs to be overridden because in games, player and enemy store/drop items
    public Item(float x, float y, String itemType){
        super(x,y,16,16,100f, Color.WHITE);
        this.x=x;
        this.y=y;
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType){
        super(x,y,16,16,100f,Color.WHITE);
        this.x=x;
        this.y=y;
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue){
        super(x,y,16,16,100f,Color.WHITE);
        this.x=x;
        this.y=y;
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }
}

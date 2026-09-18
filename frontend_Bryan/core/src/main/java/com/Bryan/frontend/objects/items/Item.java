package com.Bryan.frontend.objects.items;

import com.Bryan.frontend.Player;
import com.Bryan.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.Bryan.frontend.objects.GameObject;

public class Item extends GameObject{
    private String itemType;
    private long scoreValue;
//Pre-CS2 answer Q9:I think it needs to be overridden because in games, player and enemy store/drop items
    private ItemType itemTypeEnum;
    public Item(float x, float y, String itemType){
        super(x,y,16,16,100f, Color.WHITE);
        this.x=x;
        this.y=y;
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType){
        super(x,y,16,16,100f,Color.WHITE);
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue){
        super(x,y,16,16,100f,Color.WHITE);
        this.itemType=itemType;
        this.scoreValue=1000L;
    }

    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }

    public Item(float x, float y, ItemType itemTypeEnum){
        this.x=x;
        this.y=y;
        this.width=16;
        this.height=16;
        this.speed=100f;
        this.itemTypeEnum=itemTypeEnum;
        this.scoreValue=itemTypeEnum.getScoreValue();
    }

    public Item(float x, float y, float width, float height, float speed, ItemType itemTypeEnum, long scoreValue){
        super(x,y,width,height,speed);
        this.itemTypeEnum=itemTypeEnum;
        this.scoreValue=scoreValue;
        this.itemType=itemTypeEnum.name();
    }
    public ItemType getItemTypeEnum(){
        return itemTypeEnum;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            // Item pickup is handled on the Player side via collectItem()
        }
    }
}
